(defproject {{ raw-name }} "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [reacl "2.1.3"]]

  :source-paths ["src"]

  :aliases {"fig"       [{{^windows?}}"trampoline" {{/windows?}}"run" "-m" "figwheel.main"]
            "fig:build" [{{^windows?}}"trampoline" {{/windows?}}"run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" {{test-runner-ns}}]}

  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.1.9"]{{^windows?}}
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]{{/windows?}}]
                   :resource-paths ["target"]
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["target"]}})
