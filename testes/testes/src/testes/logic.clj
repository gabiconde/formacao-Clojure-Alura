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
