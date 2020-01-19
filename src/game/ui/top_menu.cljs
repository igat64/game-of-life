(ns game.ui.top-menu
  (:require [rum.core :as rum]))

(rum/defc top-menu < rum/static
  [{:keys [generation running? on-toggle-running on-clear-board]}]
  [:.top-menu
   [:button.btn.top-menu__btn
    {:class    (when running? "btn_pressed")
     :disabled (when running? "btn_pressed")
     :on-click (fn [_] (on-toggle-running))}
    "Run"]
   [:button.btn.top-menu__btn
    {:class    (when (not running?) "btn_pressed")
     :disabled (when (not running?) "btn_pressed")
     :on-click (fn [_] (on-toggle-running))}
    "Stop"]
   [:button.btn.top-menu__btn
    {:on-click (fn [_] (on-clear-board))}
    "Clear"]
   [:.top-menu__counter "Generation: " [:span generation]]])