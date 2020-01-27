(ns hospital.aula5
      (:use [clojure pprint])
      (:require [hospital.model :as h.model]
                [hospital.logic :as h.logic]))

(defn checkin! [hospital pessoa]
  (swap! hospital h.logic/checkin :espera pessoa))

(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))



(defn simula-um-dia []
  (let [hospital (atom (h.model/novo-hospital))]
    (checkin! hospital "gabi")
    (checkin! hospital "Igor")
    (transfere! hospital :espera :lab1)
    (pprint hospital)))

(simula-um-dia)
