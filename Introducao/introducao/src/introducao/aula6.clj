(ns introducao.aula6)

(def pedido {:mochila {:qtd 3 :vlr 3}
             :camiseta {:qtd 4 :vlr 4}
             :chaveiro {:qtd 2 :vlr 0}})

(defn imprime [[chave valor]]
  (println valor chave))

;(map imprime pedido)

(defn preco-por-produto
  [valor]
  (* (get valor :qtd) (get valor :vlr)))

;(println (map preco-por-produto pedido))

(defn total-pedido
  [pedido]
  (->> pedido
       vals
      (map preco-por-produto)
      (reduce +)))

(defn gratuito?
  [item]
  (<= (get item :vlr 0) 0))

(filter gratuito? (vals pedido))
(filter (fn [[chave item]] (gratuito? item)) pedido)
(filter #(gratuito? (second %)) pedido)

(println ((comp not gratuito?) {:vlr -9}))
;comp compoe funcoes
(println (total-pedido pedido))