(ns leiningen.new.reacl-fighweel-main
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "reacl-fighweel-main"))

(defn reacl-fighweel-main
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' reacl-fighweel-main project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))
