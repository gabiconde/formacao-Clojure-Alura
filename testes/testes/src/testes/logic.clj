(ns testes.logic
  (:require [schema.core :as s]
            [testes.model :as model]))

;qualquer um que der nil devolver nil
(defn cabe-na-fila?
      [hospital fila]
      (some-> hospital
              fila
              count
              (< 5)))

;ele testa se é nil apenas o que esta no when.
#_(defn cabe-nafila2?
    [hospital fila]
    (when-let [line (get hospital :fila)]
      (-> line
          count
          (< 5))))

#_(defn- tenta-colocar [hospital fila pessoa]
    (if (cabe-na-fila? hospital fila)
      (update hospital fila conj pessoa)))
(defn tenta-colocar [hospital fila pessoa]
  (if (cabe-na-fila? hospital fila)
    (update hospital fila conj pessoa)
    (throw (ex-info "Não cabe na fila" {:hospital hospital
                                        :type :nao-cabe-na-fila}))))

(defn chega-em
  [hospital departamento pessoa]
  (if-let [novo-hosp (tenta-colocar hospital departamento pessoa)]
    novo-hosp
    hospital))

    ;(throw (IllegalStateException. "Não cabe na fila" {:hospital hospital
                                                       ;:pessoa pessoa)))

(s/defn atende :- model/hospital
  [hospital :- model/hospital
   departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proximo :- model/pacienteID
  [hospital :- model/hospital
   departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(s/defn transfere :- model/hospital
  [hospital :- model/hospital
   de :- s/Keyword
   para :- s/Keyword]
  (let [pessoa (proximo hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))