(ns leiningen.new.reacl-figwheel-main
  (:require [leiningen.new.templates :refer [renderer project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def render (renderer "reacl-figwheel-main"))



;; I copy this levenshtein impl everywhere
(defn- next-row
  [previous current other-seq]
  (reduce
    (fn [row [diagonal above other]]
      (let [update-val (if (= other current)
                          diagonal
                          (inc (min diagonal above (peek row))))]
        (conj row update-val)))
    [(inc (first previous))]
    (map vector previous (next previous) other-seq)))




(defn in-clj? []
  (resolve 'clj-new.helpers/create))

(defn test-runner-ns [main-ns]
  (string/join "." [(first (string/split main-ns #"\.")) "test-runner"]))

(defn windows? []
  (.contains (string/lower-case (System/getProperty "os.name")) "windows"))


(defn make-data [n]
  (let [to-att      #(keyword (str (name %) "?"))
        main-ns     (multi-segment (sanitize-ns n))
        test-run-ns (test-runner-ns main-ns)]
    {:raw-name         n
     :name             (project-name n)
     :namespace        main-ns
     :test-runner-ns   test-run-ns
     :test-runner-dirs (name-to-path test-run-ns)
     :windows?         (windows?)
     :main-file-path   (-> (name-to-path main-ns)
                         (string/replace "\\" "/"))
     :nested-dirs      (name-to-path main-ns)}))

(defn reacl-figwheel-main
  [name]
  (do
    (when (#{"figwheel" "cljs"} name)
      (main/abort
       (format
        (str "Cannot name a figwheel project %s the namespace will clash.\n"
             "Please choose a different name, maybe \"tryreacl\"?")
        (pr-str name))))
    (try
      (let [data (make-data name)
            files [["README.md" (render "README.md" data)]
                   ["dev.cljs.edn" (render "dev.cljs.edn" data)]
                   ["test.cljs.edn" (render "test.cljs.edn" data)]
                   ["src/{{nested-dirs}}.cljs" (render "core.cljs" data)]
                   ["resources/public/css/style.css" (render "style.css" data)]
                   ["resources/public/test.html" (render "test-page.html" data)]
                   [".gitignore" (render "gitignore" data)]
                   ["test/{{nested-dirs}}_test.cljs" (render "test.cljs" data)]
                   ["test/{{test-runner-dirs}}.cljs" (render "test_runner.cljs" data)]
                   ["project.clj" (render "project.clj" data)]
                   ["resources/public/index.html" (render "index.html" data)]]
            ]
        (main/info (format (str "Generating fresh reacl project including figwheel main.\n"
                                "   -->  To get started: Change into the '%s' directory and run '%s'\n")
                           (:name data)
                           "lein fig:build"))
        (apply ->files data files)
        ;; ensure target directory
        (.mkdirs (io/file (:name data) "target" "public")))
      (catch clojure.lang.ExceptionInfo t
        (if (-> t ex-data ::error)
          (main/warn (.getMessage t))
          (throw t))))))
