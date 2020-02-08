(ns schemas.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s])
  (:import (com.sun.jndi.toolkit.ctx PartialCompositeContext)))

(s/set-fn-validation! true)

#_(s/defrecord Paciente
    [id :- Long
     nome :- s/Str])

;schema é um mapa
;not foward compatible

(def Paciente
  {:id s/Num :nome s/Str})

(pprint (s/explain Paciente))

(s/defn novo-paciente :- Paciente
  [id :- s/Num
   nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 14 "Gabi"))
;(pprint (novo-paciente 14 4))

(defn positivo? [x]
  (> x 0))            ; = pos?

;schema de positivo é um predicate fn
(def positivo (s/pred positivo? 'positivo))

(pprint (s/validate positivo 2))
(pprint (s/validate positivo -2))

(def Paciente
  ;:id (s/constrained s/Int pos?)
  {:id (s/Pred pos-int?)
   :nome s/Str})

(s/defn novo-paciente :- Paciente
  [id :- s/Num
   nome :- s/Str]
  {:id id :nome nome})

(pprint (novo-paciente 2.9 "a"))
(pprint (s/validate positivo -2))