(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

;deref pega só o valor do atom
;(deref name) ou (@name)
;Troca o valor: swap!
;swap! impede a concorrencia, tenta executar de novo (busyretry).
;melhor chamar funcoes puras, só o que deve ter o retry

;locking uso de travas e monitoramento

(defn testa-atom []
  (let [hospital (atom {:espera h.model/fila-vazia})]
    (pprint @hospital)
    (pprint (swap! hospital assoc :lab1 h.model/fila-vazia))
    (pprint (swap! hospital update :espera conj "111"))))

(testa-atom)