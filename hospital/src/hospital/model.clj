(ns hospital.model
  (:import (clojure.lang PersistentQueue)))

(defn novo-hospital []
  {:espera PersistentQueue/EMPTY
   :lab1 PersistentQueue/EMPTY
   :lab2 PersistentQueue/EMPTY
   :lab3 PersistentQueue/EMPTY})

(def fila-vazia
  PersistentQueue/EMPTY)

