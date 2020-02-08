(ns schemas.aula4
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])
(def Paciente
  {:id PosInt
   :nome s/Str
   :plano Plano
   (s/optional-key :idade) s/Str})

;Pacientes é opcional e pode ser {nil} pois não usa keywords
;ja o Paciente todas as chaves keywords são obrigatorias
(def Pacientes
  {PosInt Paciente})

(pprint (s/validate Pacientes {}))
(let [gabi {:id 2 :nome "gabi" :plano []}]
  (pprint (s/validate Pacientes {25 gabi}))
  (pprint (s/validate Paciente {})))

