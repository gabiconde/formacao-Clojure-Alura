(ns hospital.aula1
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn simula-um-dia []
  ; root binding
  (def hospital (h.model/novo-hospital))
  (def hospital (h.logic/checkin hospital :espera "111"))
  (def hospital (h.logic/checkin hospital :espera "222"))
  (def hospital (h.logic/checkin hospital :espera "333"))


  (def hospital (h.logic/atende hospital :espera))
  (pprint hospital))

;(simula-um-dia)