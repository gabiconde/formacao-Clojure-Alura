(ns introducao.aula3)

(defn desconto?
  [valor]
  (if (> valor 100)
    true))

(defn desconto?
  [valor]
  (> valor 100))

(defn valor-descontado
  [desconta? valor-bruto]
  (if (desconta? valor-bruto)
    (let [taxa-desconto (/ 10 100)
          desconto (* valor-bruto taxa-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado desconto? 1000))

(fn [valor-bruto] (> valor-bruto 100))

;high order function
(println (valor-descontado (fn [v] (> v 100)) 1000))
(println (valor-descontado  #(> %1 100) 1000)) ;quer o primeiro param q for passado
(println (valor-descontado  #(> % 100) 1000)) ;só 1 parametro