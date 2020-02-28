(ns testes.logic
  (:require [schema.core :as s]
            [testes.model :as model]))

(defn cabe-na-fila?
      [hospital fila]
      (some-> hospital
              fila
              count
              (< 5)))

(defn tenta-colocar [hospital fila pessoa]
  (if (cabe-na-fila? hospital fila)
    (update hospital fila conj pessoa)
    (throw (ex-info "Não cabe na fila" {:hospital hospital
                                        :type :nao-cabe-na-fila}))))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (java.lang.IllegalStateException. "Não cabe na fila"))))


(s/defn atende :- (s/maybe model/hospital)
  [hospital :- model/hospital
   departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proximo :- model/pacienteID
  [hospital :- model/hospital
   departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho?
  [hospital novo-hospital de para]
  (= (+ (count (get hospital de)) (count (get hospital para)))
     (+ (count (get novo-hospital de)) (count (get novo-hospital para)))))

(s/defn transfere :- model/hospital
  [hospital :- model/hospital
   de :- s/Keyword
   para :- s/Keyword]
  {:pre [(contains? hospital de)
         (contains? hospital para)]
   :post [(mesmo-tamanho? hospital % de para)]}
  (if-let [pessoa (proximo hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))
    (hospital)))