(ns game.ui.app
  (:require
   [rum.core :as rum]
   [game.board :as b]
   [game.cell :as c]
   [game.ui.board :refer [board]]
   [game.ui.top-menu :refer [top-menu]]
   [game.ui.bottom-menu :refer [bottom-menu]]))

(defn next-cell [board {:keys [pos data]}]
  (let [neighbors (mapv :data (b/neighbors board pos))]
    (c/transition data neighbors)))

(defn next-gen [board]
  (b/update-slots board (partial next-cell board)))

(defn tick! [app-state]
  (swap! app-state assoc :gen   (inc (:gen @app-state))
         :board (next-gen (:board @app-state))))

(defn start! [app-state]
  (let [id (js/setInterval #(tick! app-state)
                           (:speed @app-state))]
    (swap! app-state assoc :interval-id id)
    (swap! app-state assoc :running? true)))

(defn stop! [app-state]
  (js/clearInterval (:interval-id @app-state))
  (swap! app-state assoc :interval-id nil)
  (swap! app-state assoc :running? false))

(defn toggle-running! [app-state]
  (if (:running? @app-state)
    (stop! app-state)
    (start! app-state)))

(defn change-speed! [app-state speed]
  (swap! app-state assoc :speed speed)
  (when (:running? @app-state)
    (do (stop! app-state)
        (start! app-state))))

(defn change-size! [app-state size]
  (let [new-board (b/create size)]
    (swap! app-state assoc :board new-board :gen 0 :running? false)
    (stop! app-state)))

(defn clear-board! [app-state]
  (let [size (get-in @app-state [:board :size])]
    (swap! app-state assoc :board (b/create size) :gen 0 :running? false)
    (stop! app-state)))

(rum/defc app < rum/reactive
  [app-state]
  (let [state (rum/react app-state)]
    [:div
     [:header.header
      [:a.header__link {:href "https://www.math.cornell.edu/~lipa/mec/lesson6.html"
                        :target "_blank"} "Conway's Game of Life"]]
     (top-menu {:generation        (:gen state)
                :running?          (:running? state)
                :on-toggle-running #(toggle-running! app-state)
                :on-clear-board    #(clear-board! app-state)})
     (board (rum/cursor-in app-state [:board])
            (get-in state [:board :size]))
     (bottom-menu {:speed           (:speed state)
                   :size            (get-in state [:board :size])
                   :on-change-speed (partial change-speed! app-state)
                   :on-change-size  (partial change-size! app-state)})
     [:.note "Feel free to add cells while it's running.
              The cells in light yellow are older, dark yellow are younger. Enjoy!"]]))