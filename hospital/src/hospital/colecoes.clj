(ns hospital.colecoes
  (:use [clojure pprint]))

;vetor adiciona e remove do final
(defn vetor []
      (let [espera [111 222]]
           (conj espera 333)
           (conj espera 444)
           (pop espera)))

;lista ligada adiciona e remove do começo
(defn lista-ligada []
      (let [espera '(111 222)]
           (conj espera 333)
           (conj espera 444)
           (pop espera)))

;conjunto tipo math, sem ordem, n empilha
;pop não funciona
(defn conjunto []
      (let [espera #{111 222}]))


;fila
(defn fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "333")]
    (println "Fila")
    (println (peek (conj espera "222")))
    (pprint espera)
    (pprint (pop espera))
    (pprint espera)))



(fila)