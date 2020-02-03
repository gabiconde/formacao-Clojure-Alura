(ns multimethods.aula4
  (:use clojure.pprint))

(defrecord PacienteParticular [id nome idade])
(defrecord PacientePlano [id nome idade plano])

(defmulti deve-assinar-pre-autorizacao? class)

(defmethod deve-assinar-pre-autorizacao? PacienteParticular
  [paciente]
  true)

(defmethod deve-assinar-pre-autorizacao? PacientePlano
  [paciente]
  false)

(let [particular (->PacienteParticular 2 "ozob" 30)
      plano (->PacientePlano 1 "gabi" 22 [:ideal :raio-x])]
  (pprint (deve-assinar-pre-autorizacao? particular)))

; pedido {:paciente paciente :}
(defn tipo-autorizacao
  [pedido]
  (let [paciente (get pedido :paciente)
        situacao (get paciente :situacao :normal)
        urgente? (= situacao :urgente)]
    (if urgente?
      :sempre-autorizado
      (class paciente))))

(defrecord PacienteParticular [id nome idade situacao])
(defrecord PacientePlano [id nome idade plano situacao])

(defmulti autorizacao-do-pedido? tipo-autorizacao)

(defmethod autorizacao-do-pedido? :sempre-autorizado
  [pedido]
  false)

(defmethod autorizacao-do-pedido? PacienteParticular
  [pedido]
  (>= 50 (:valor pedido 0)))

(defmethod autorizacao-do-pedido? PacientePlano
  [pedido]
  (let [paciente (:paciente pedido)
        plano (:plano paciente)]
    (not (some #(= % (:procedimento pedido)) plano))))

(let [particular (->PacienteParticular 2 "ozob" 30 :urgente)
      plano (->PacientePlano 1 "gabi" 22 [:ideal :raio-x] :urgente)]
  (pprint (autorizacao-do-pedido? {:paciente particular :valor 300 :procedimento :raio-x}))
  (pprint (autorizacao-do-pedido? {:paciente plano :valor 1000 :procedimento :hemograma})))