(ns game.ui.board
  (:require
    [rum.core :as rum]
    [game.ui.cell :refer [cell]]))

(def size->class
  {[50  30] "board_small"
   [70  50] "board_medium"
   [100 80] "board_large"})

(def update-on-size-changed
  {:should-update 
    (fn [old-state new-state]
      (let [[_ old-size] (:rum/args old-state)
            [_ new-size] (:rum/args new-state)]
        (not= old-size new-size)))})

(rum/defc board < update-on-size-changed
  [grid size]
  [:.board {:class (size->class size)}
    (for [row (:slots (deref grid))]
      (for [slot row]
        (let [{c :data [x y] :pos} slot]
          (rum/with-key (cell (rum/cursor-in grid [:slots y x :data]))
                        (str x y)))))])