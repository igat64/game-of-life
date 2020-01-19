(ns game.ui.bottom-menu
  (:require
    [rum.core :as rum]))

(def speed->label {200 "Slow"
                   110 "Medium"
                   50  "Fast"})

(def size->label {[50  30] "50x30"
                  [70  50] "70x50"
                  [100 80] "100x80"})

(defn button [val label pressed? handler]
  [:button.btn.bottom-menu__btn
     {:class    (when pressed? "btn_pressed")
      :disabled (when pressed? true)
      :on-click (fn [_] (handler val))
      :key      label}
     label])

(rum/defc bottom-menu < rum/static
  [{:keys [speed size on-change-speed on-change-size]}]
  [:.bottom-menu
   [:div
    (concat [[:p.bottom-menu__paragraph {:key "board-size"} "Board Size:"]]
            (map (fn [[val label]] (button val label (= val size) on-change-size))
                 size->label))]
   [:div
     (concat [[:p.bottom-menu__paragraph {:key "game-speed"} "Speed:"]]
             (map (fn [[val label]] (button val label (= val speed) on-change-speed))
                  speed->label))]])