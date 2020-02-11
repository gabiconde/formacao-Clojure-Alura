(ns testes.logic)

;qualquer um que der nil devolver nil
(defn cabe-na-fila?
      [hospital fila]
      (some-> hospital
              fila
              count
              (< 5)))

;ele testa se é nil apenas o que esta no when.
(defn cabe-nafila2?
  [hospital fila]
  (when-let [line (get hospital :fila)]
    (-> line
        count
        (< 5))))

#_(defn- tenta-colocar [hospital fila pessoa]
    (if (cabe-na-fila? hospital fila)
      (update hospital fila conj pessoa)
      (throw (ex-info "Não cabe na fila" {:hospital hospital
                                          :type :nao-cabe-na-fila}))))
(defn- tenta-colocar [hospital fila pessoa]
  (if (cabe-na-fila? hospital fila)
    (update hospital fila conj pessoa)))

(defn chega-em
  [hospital departamento pessoa]
  (if-let [novo-hosp (tenta-colocar hospital departamento pessoa)]
    {:hospital novo-hosp :status :success}
    {:hospital hospital :status :fail}))

    ;(throw (IllegalStateException. "Não cabe na fila" {:hospital hospital
                                                       ;:pessoa pessoa)))
