(ns introducao.aula4)

(def precos [30 70 1000])

;busca por indice
(println (precos 2))
; (println (precos 5)) calling the vector as a functions with some index that doesnt exist, it return am exception
(println (get precos 0))
(println (get precos 3)) ;if the index does not exist return nil
(println (get precos 4 "Não existe")) ;add a default value to not found index
;get helps to avoid exception
(println (conj precos 5))
(println (update precos 0 inc))


(defn desconto?
  [valor]
  (> valor 100))

(defn valor-descontado
  [valor-bruto]
  (if (desconto? valor-bruto)
    (let [taxa-desconto (/ 10 100)
          desconto (* valor-bruto taxa-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (map valor-descontado precos))
(println (filter desconto? precos))
(println "even?" (filter even? (range 10)))
(->> precos
    (filter desconto?)
    (map valor-descontado)
    (println "Valores que descontaram"))

(println "valores somados"(reduce + precos))
(defn soma-valor [v1 v2]
  (+ v1 v2))

(println (reduce soma-valor precos))
(println (reduce soma-valor 3 precos)) ; passar valor inicial
;vazio no

