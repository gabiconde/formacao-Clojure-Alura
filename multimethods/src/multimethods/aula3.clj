(ns multimethods.aula3
  (:use clojure.pprint)
  (:require [multimethods.logic :as m.logic]))

(defn carrega-paciente
  [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  {:id id :carregado-em (m.logic/agora)})

;(pprint (carrega-paciente 15))

;funcao pura
(defn- carrega-se-nao-existe
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))

;(pprint (carrega-se-nao-existe {} 1 carrega-paciente))
;(pprint (carrega-se-nao-existe {1 {:id 1 :nome "fulano"}} 1 carrega-paciente))


(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache
  [cache carregadora]

  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))

(def cache (->Cache (atom {}) carrega-paciente))
(pprint (:cache cache))
(carrega! cache 15)
(carrega! cache 20)
(carrega! cache 20)
(pprint (:cache cache))
