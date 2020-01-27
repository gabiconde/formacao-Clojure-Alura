(ns hospital.aula4
      (:use [clojure pprint])
      (:require [hospital.model :as h.model]
                [hospital.logic :as h.logic]))

(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/checkin :espera pessoa)
  (pprint "após inserir" pessoa))


;a primeira funcao serve para receber um parametro antes de receber o outro na proxima chamada
(defn start-thread
  ;([hospital]
   ;(fn [pessoa] (start-thread hospital pessoa))
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em! hospital pessoa)))))


(defn simula-um-dia []
  ;mapv para forçar a execuçao
  (let [hospital (atom (h.model/novo-hospital))
        pessoas (range 6)]
    ;----------------- partial manda um argumento para a função para depois mandar o outro.
    ;start (partial start-thread hospital)]

    ;-------------------- para o mapv o partial é necessario
    ;(mapv start pessoas)

    ;--------------------- loop com side-effects com limite
    ;(doseq [p pessoas]
    ;(start-thread hospital p

    ;---------------------- loop de vezes em que a sequencia nem os elementos são importantes
    (dotimes [p 6]
      (start-thread hospital p))

    (.start (Thread. (fn [] (Thread/sleep 5000)
                       (pprint @hospital))))))

(simula-um-dia)

;mapv não é muito bom
;doseq for aninhados, [x [coisas]]