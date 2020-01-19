(ns game.ui.core
  (:require [game.ui.app]
            [game.ui.board]
            [game.ui.top-menu]
            [game.ui.bottom-menu]))

(def app         game.ui.app/app)
(def board       game.ui.board/board)
(def top-menu    game.ui.top-menu/top-menu)
(def bottom-menu game.ui.bottom-menu/bottom-menu)