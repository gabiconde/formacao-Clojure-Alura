(ns multimethods.logic
  (:require [multimethods.model :as m.model]))

(defn agora []
  (m.model/to-ms (java.util.Date.)))

