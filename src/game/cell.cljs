(ns game.cell)

(defn alive? [cell] (not (nil? cell)))
(defn age [cell] (get cell :age))
(defn inc-age [cell] (update cell :age inc))
(defn kill [_] nil)
(defn spawn
  ([] (spawn 0))
  ([age] {:age age}))

(defn transition [cell neighbors]
  (let [an (count (filter alive? neighbors))]
    (if (alive? cell)
      (cond
        (< an 2) (kill cell)
        (> an 3) (kill cell)
        :else (inc-age cell))
      (cond
        (= an 3) (spawn)
        :else cell))))