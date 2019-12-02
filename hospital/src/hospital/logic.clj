(ns hospital.logic)

(defn checkin
  [hospital departamento pessoa]
  (update hospital  departamento conj pessoa))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))