(ns hospital.aula6
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

(defn cabe-na-fila? [fila]
  (-> fila
      count
      (> 3)))

(defn chega-em [fila pessoa]
  (if cabe-na-fila? fila)
  (conj fila pessoa)
  (throw (ex-info "Fila cheia!" {:error "fila cheia"})))

#_ (defn chega-em!
     "Troca de referencia via ref-set, precisa do deref"
     [hospital pessoa]
     (let [fila (get hospital :espera)]
       (ref-set fila (chega-em @fila pessoa))))

(defn chega-em!
  "Troca de referencia por alter"
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

(defn simula-um-dia []
  (let [hospital {:espera (ref h.model/fila-vazia)
                  :lab1 (ref h.model/fila-vazia)
                  :lab2 (ref h.model/fila-vazia)
                  :lab3 (ref h.model/fila-vazia)}]
    (dosync (chega-em! hospital "guilerme")
            (chega-em! hospital "Ana")
            (chega-em! hospital "Gabi")
            (chega-em! hospital "Manoel"))
    (pprint hospital)))

(defn async-chega-em! [hospital pessoa]
  (future
    (Thread/sleep (rand 2000))
    (dosync
      (println "Trying sync" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera (ref h.model/fila-vazia)
                  :lab1 (ref h.model/fila-vazia)
                  :lab2 (ref h.model/fila-vazia)
                  :lab3 (ref h.model/fila-vazia)}
        futures (mapv #(async-chega-em! hospital %) (range 5))]
       (future (dotimes [n 4]
                 (Thread/sleep 3000)
                 (pprint hospital)
                 (pprint futures)))))

(simula-um-dia-async)
