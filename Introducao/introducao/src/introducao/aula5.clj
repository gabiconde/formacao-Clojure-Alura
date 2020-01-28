(ns introducao.aula5)

(def estoque { "mochila" 10
              "camiseta" 5})
;padrao utilizar keyword :key não string

(def estoque { :mochila 10
              :camiseta 5}) ;Não tem prioridade de ordem em maps
(println "conta elementos" (count estoque))
(println "Take the keys" (keys estoque))
(println "Take values" (vals estoque))

(println
  (assoc estoque :mochila 1)
  (assoc estoque :sapato 4)
  (update estoque :mochila inc))
(println (update estoque :mochila #(- % 3)))

(def pedido {:mochila {:qtd 3 :vlr 3}
             :camiseta {:qtd 4 :vlr 4}})

(println (pedido :a :default)
         (pedido :a :default)
         (:mochila pedido)
         (:qtd (:mochila pedido)))
; , is space
(-> pedido
    :mochila
    :qtd
    println)




