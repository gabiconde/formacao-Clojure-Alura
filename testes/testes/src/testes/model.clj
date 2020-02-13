(ns testes.model
  (:require [schema.core :as s])
  (:import (clojure.lang PersistentQueue)))

(defn novo-hospital []
  {:espera PersistentQueue/EMPTY
   :lab1   PersistentQueue/EMPTY
   :lab2   PersistentQueue/EMPTY
   :lab3   PersistentQueue/EMPTY})

(def fila-vazia
  PersistentQueue/EMPTY)

(s/def pacienteID s/Int)
(s/def departamento (s/queue pacienteID))
(s/def hospital {s/Keyword departamento})