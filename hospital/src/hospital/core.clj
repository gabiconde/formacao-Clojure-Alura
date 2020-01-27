(ns hospital.core
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

(let [hospital h.model/novo-hospital]
  (pprint hospital))