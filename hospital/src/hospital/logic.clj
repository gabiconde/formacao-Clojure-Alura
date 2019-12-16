(ns hospital.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      (get departamento)
      count
      (< 3)))

(defn checkin
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "fila cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn checkin-pausado
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (do (Thread/sleep 1000)
        (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn proximo [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere [hospital de para]
  (let [pessoa (proximo hospital de)]
    (-> hospital
        (atende de)
        (checkin para pessoa))))


;juxt permite executar duas funcoes ao mesmo tempo, e retorna os dois ou mais valores finais
(defn invocacao-duas-funcoes
      [hospital depart]
  (let [fila (get hospital depart)
        peek-pop (juxt peek pop)
        [pessoa nova-fila] (peek-pop fila)]
    {:pessoa pessoa
     :fila nova-fila}))

