(ns game.ui.cell
  (:require
    [rum.core :as rum]
    [clojure.string :as str]
    [game.cell :as cell]))

(defn toggle-cell [c]
  (swap! c #(if (cell/alive? %) (cell/kill %) (cell/spawn))))

(rum/defc cell < rum/reactive
  [cell-atom]
  (let [c       (rum/react cell-atom)
        old?    (> (cell/age c) 5)
        classes (if (cell/alive? c)
                    ["cell_alive" (when old? "cell_old")]
                    ["cell_dead"])]
    [:.cell {:on-click #(toggle-cell cell-atom)
             :class    (str/join " " classes)}]))