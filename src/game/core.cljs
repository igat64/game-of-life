(ns game.core
  (:require
   [rum.core :as rum]
   [game.ui.core :as ui]
   [game.board :as board]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state
   (atom {:running? false
          :gen      0
          :speed    50
          :board    (board/create [70 50])}))

(rum/mount (ui/app app-state)
           (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
