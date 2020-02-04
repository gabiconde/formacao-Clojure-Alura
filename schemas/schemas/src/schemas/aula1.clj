(ns schemas.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn adiciona-visita
  [visitas paciente novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn relatorio-paciente
  [visitas
   paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-paciente []
  (let [guilherme {:id 15 :nome "guilherme"}
        Dani {:id 2 :nome "Daniela"}
        Gabriela {:id 14 :nome "Gabriela"}
        pacientes (reduce adiciona-paciente {} [guilherme Dani Gabriela])
        visitas {}
        visitas (adiciona-visita visitas 15 ["data1"])
        visitas (adiciona-visita visitas 2 ["dia1" "dia2"])]
    (pprint pacientes)
    (pprint visitas)
    (relatorio-paciente visitas 2)))

(testa-uso-paciente)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "h"))

(s/set-fn-validation! true)
;Não é validado sem chamar o validation

(s/defn teste-simples [x :- Long]
  (println x))
(teste-simples 3)
;(teste-simples "gabi")

(s/defn novo-paciente
  [id :- Long
   nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 13 "ozob"))
(pprint (novo-paciente "ozob" 30))
