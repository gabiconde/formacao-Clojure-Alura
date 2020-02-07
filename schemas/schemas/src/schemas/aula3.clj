(ns schemas.aula3
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(defn maior-zero? [x] (>= x 0))

(def valorFinanceiro (s/constrained s/Num maior-zero?))
(def Plano [s/Keyword])

(def Paciente
  {:id (s/pred pos-int?)
   :nome s/Str
   :plano Plano})

(s/defn novo-paciente :- Paciente
        [id :- (s/pred pos-int?)
         nome :- s/Str
         plano :- Plano]
        {:id id :nome nome :plano plano})

(def Pedido
  {:paciente Paciente
   :valor valorFinanceiro
   :procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
  [paciente :- Paciente
   valor :- valorFinanceiro
   procedimento :- s/Keyword]
  {:paciente paciente :valor valor :procedimento procedimento})

(pprint (novo-paciente 4 "gabi" [:s :a :b]))
(pprint (novo-pedido (novo-paciente 3 "Ozob" [:a :b]) 45 :hemograma))

;vetor vazio e nil sào considerados validos e iguais
(pprint (s/validate Plano []))
(pprint (s/validate Plano nil))

;nil não é uma keyword - erro
;(pprint (s/validate Plano [nil]))