(ns multimethods.aula2
  (:use clojure.pprint))

(defrecord PacienteParticular [id nome idade])

(defprotocol Cobravel
  (pre-autorizacao? [paciente procedimento valor]))

;pode extender para um record que ja foi definido.
(extend-type PacienteParticular
  Cobravel
  (pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

;ainda podemos colocar em arquivos diferentes
;pode implementar o protocol na definicao do record ou fora
(defrecord PacientePlano [id nome idade plano]
  Cobravel
  (pre-autorizacao? [paciente procedimento valor]
    (let [plano (get paciente :plano)]
      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 2 "Gabriela" 22)
      plano (->PacientePlano 3 "Eduardo" 45 [:raio-x :ultrassom])]
  (pprint (pre-autorizacao? particular :raio-x 30))
  (pprint (pre-autorizacao? particular :hemograma 300))
  (pprint (pre-autorizacao? plano :raio-x 20))
  (pprint (pre-autorizacao? plano :hemograma 500)))


;utilizar objetos do java, polimorfismo
(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms 50))
(pprint (to-ms (java.util.Date.)))
(pprint (to-ms (java.util.GregorianCalendar.)))