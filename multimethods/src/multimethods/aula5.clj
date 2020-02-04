(ns multimethods.aula5
  (:use clojure.pprint))

(defn tipo-de-autorizador
  [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)]
    (cond (= :urgente situacao) :sempre-autorizado
          (:plano paciente) :plano-de-saude
          :else :particular)))

(defmulti precisa-autorizar? tipo-de-autorizador)

(defmethod precisa-autorizar? :sempre-autorizado
  [pedido]
  false)

(defmethod precisa-autorizar? :plano-de-saude
  [pedido]
  (let [paciente (:paciente pedido)
        plano (:plano paciente)]
    (not (some #(= % (:procedimento pedido)) plano))))

(defmethod precisa-autorizar? :particular
  [pedido]
  (>= (:valor pedido) 50))

(let [particular {:id 2 :nome "ozob" :idade 40 :situacao :normal}
      plano {:id 1 :nome "gabi" :idade 22 :plano [:ideal :raio-x] :situacao :urgente}]
  (pprint (precisa-autorizar? {:paciente particular :valor 300 :procedimento :raio-x}))
  (pprint (precisa-autorizar? {:paciente plano :valor 1000 :procedimento :hemograma})))

