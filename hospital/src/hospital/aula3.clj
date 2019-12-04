(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

;deref pega só o valor do atom
;(deref name) ou (@name)
;swap!