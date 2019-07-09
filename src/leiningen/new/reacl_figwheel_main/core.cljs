(ns ^:figwheel-hooks {{namespace}}
  (:require
   [reacl2.core :as reacl :include-macros true]
   [reacl2.dom :as dom :include-macros true]))

(defn multiply [a b] (* a b))

(reacl/defclass core this app-state []

  render
  (dom/div
    (dom/p (dom/span "[reacl]") "Hello, dev!")
    (if (= app-state :empty)
     (dom/button {:onclick #(reacl/send-message! this :greet)}
       "Greet back")
     (dom/p (dom/span "[dev]") app-state)))


  handle-message
  (fn [msg]
    (cond

      (= :greet msg)
      (reacl/return
        :app-state "Hello, reacl!"
        :action :log-greeting)

      ;; more to come ...
      )))


(defn handle-actions [_app-state action]
  (cond
    (= action :log-greeting)
    (do
      (println "You greeted! Well, that's nice!")
      (reacl/return))

    ;; more to come ...
    ))


(reacl/render-component (.getElementById js/document "content") core
  (reacl/opt :reduce-action handle-actions)
  :empty)


;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  ;; do some special stuff on reload
  (println "reloaded!")
)
