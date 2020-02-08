(ns schemas.aula5
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])

(def Paciente
  {:id PosInt
   :nome s/Str
   :plano Plano
   (s/optional-key :idade) PosInt})

(def Pacientes
  {PosInt Paciente})

(def Visitas
  {PosInt [s/Str]})

(s/defn adiciona-paciente :- Pacientes
  [pacientes :- Pacientes
   paciente :- Paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(s/defn adiciona-visita :- Visitas
  [visitas :- Visitas
   paciente :- PosInt
   novas-visitas :- [s/Str]]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc visitas paciente novas-visitas)))

(s/defn relatorio-paciente
  [visitas :- Visitas
   paciente :- PosInt]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-paciente []
  (let [guilherme {:id 15 :nome "guilherme" :plano [:raio-x :hemograma]}
        Dani {:id 2 :nome "Daniela" :plano []}
        Gabriela {:id 14 :nome "Gabriela" :plano [] :idade 22}
        pacientes (reduce adiciona-paciente {} [guilherme Dani Gabriela])
        visitas {}
        visitas (adiciona-visita visitas 15 ["data1"])
        visitas (adiciona-visita visitas 2 ["dia1" "dia2"])]
    (pprint pacientes)
    (pprint visitas)
    (relatorio-paciente visitas 2)))

(testa-uso-paciente)