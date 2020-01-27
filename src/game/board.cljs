(ns game.board
  #_(:require
    [game.timing :refer-macros [c-time]]))

(defn- create-slot
  ([pos] (create-slot pos nil))
  ([[x y] data] {:pos [x y] :data data}))

(defn- normalize-pos [pos [t-width t-height]]
  (-> pos
      ((fn [[_ y]] (if (< y 0) [_ (dec t-height)] [_ y])))
      ((fn [[x _]] (if (> x (dec t-width)) [0 _] [x _])))
      ((fn [[_ y]] (if (> y (dec t-height)) [_ 0] [_ y])))
      ((fn [[x _]] (if (< x 0) [(dec t-width) _] [x _])))))

(defn create [[width height]]
  {:pre [(pos-int? width) (pos-int? height)]}
  {:size  [width height]
   :slots (vec (for [row-index (range height)]
                 (vec (for [column-index (range width)]
                        (create-slot [column-index row-index])))))})

(defn update-slots [board f]
  (let [{:keys [size slots]} board]
    {:size  size
     :slots (vec (for [rows slots]
                   (vec (for [slot rows]
                          (create-slot (:pos slot) (f slot))))))}))

(defn slot-exists? [board [x y]]
  (if (nil? (get-in board [:slots y x])) false true))

(defn update-slot [board [x y] f]
  (if (slot-exists? board [x y])
    (update-in board [:slots y x :data] f)
    board))

(defn get-slot [board [x y]]
  (get-in (:slots board) [y x]))

(defn fill [board filler]
  (reduce (fn [board [pos data]]
            (update-slot board pos (constantly data)))
          board
          filler))

(defn- delta->neighbor [board pos delta]
  (-> delta
      (#(mapv + pos %))
      (#(normalize-pos % (:size board)))
      (#(get-slot board %))))

(def deltas
  [[-1 -1] [0 -1] [1 -1]
   [-1  0]        [1  0]
   [-1  1] [0  1] [1  1]])

(defn neighbors [board pos]
  (mapv (partial delta->neighbor board pos) deltas))