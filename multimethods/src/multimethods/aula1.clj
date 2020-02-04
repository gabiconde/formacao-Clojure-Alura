(ns multimethods.aula1
  (:use clojure.pprint))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (get paciente :id)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui ID" {:paciente paciente}))))

(defn testa-adiciona-paciente []
  (let [pacientes {}
        gabi {:id 2 :nome "Gabriela" :idade 22}
        manoel {:id 4 :nome "Manoel" :idade 30}
        None {:nome "Ninguem" :idade 0}]
    (pprint (adiciona-paciente pacientes gabi))
    (pprint (adiciona-paciente pacientes manoel))
    (pprint (adiciona-paciente pacientes None))))

;(testa-adiciona-paciente)


(defrecord Paciente [id nome idade])
;(defrecord Paciente [ˆLong id ˆString nome ...]) Tipagem

;o construtor normal não permite nada a mais nem a menos
(->Paciente 2 "Gabi" 22) ;Construtor posicional
(Paciente. 4 "Luiz" 56) ;Não garante a ordem

;no construtor com map os campos são opicionais aceita mais e com menos chave vira nil
(map->Paciente {:id 5 :nome "Rafa" :idade 5})
(map->Paciente {:id 5 :nome "Rafa" :idade 5 :rg 7836873687}) ;ok
(map->Paciente {:nome "Rafa" :idade 5}) ; :id nil

(let [pessoa (->Paciente 2 "Gabi" 22)]
  (pprint (:nome pessoa))
  (pprint (vals pessoa))
  (pprint (.nome pessoa)))
