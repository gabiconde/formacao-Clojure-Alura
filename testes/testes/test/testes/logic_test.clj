(ns testes.logic-test
  (:require [clojure.test :refer :all]
            [testes.logic :refer :all]
            [testes.model :as model]
            [schema.core :as s]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer (defspec)]))

(s/with-fn-validation)

;testes baseados em exemplos
(deftest cabe-na-fila?-test
  (testing "cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Cabe pessoas em filas de tamanho 0 ate 4"
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4))]
      (is (cabe-na-fila? {:espera fila} :espera))))

  (testing "Não cabe em filas de 5 ou mais"
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 5 10))]
      (is (not (cabe-na-fila? {:espera fila} :espera)))))

  (testing "não cabe quando está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "fila com mais de 5"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "sem departamento"
    (is (not (cabe-na-fila? {} :espera)))))


#_(deftest chega-em-test
    ;doseq cruza os vetores um vezes o outro. gera 50 testes
    (testing "Coloca uma pessoa em filas menos que 5"
      (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4))
              pessoa (gen/sample (gen/string-alphanumeric 5))]
        (is (= 1 1)))))

;implementando a logica da funcao, nao garante que esta certo o teste.
;teste generativo mas nao testa pois o codigo do teste esta igual a implementacao
(defspec explorando-api 10 ;cria 10 testes
         (prop/for-all
           [fila (gen/vector gen/string-alphanumeric 0 4)
            pessoa gen/string-alphanumeric]
           (is (= {:espera (conj fila pessoa)}
                  (chega-em {:espera fila} :espera pessoa)))))

;properties:
; que propeiredades do meu sistema eu posso testar
;a fila nao pode ser maior que 5
;prop: manter a quantidade de pessoas dentro do hospital - transfere
;executar trocentas vezes todas as funcoes e ver se o resultado da qtd de pessoas é o esperado.
;;garantir que a primeira pessoa que chegou foi atendida primeiro.
;ex: conta bancaria com o saldo correto.

(def nome-aleatorio-gen
  (gen/fmap clojure.string/join
            (gen/vector gen/char-alphanumeric 5 10)))
(defn transforma-vetor-em-fila [vetor]
  (reduce conj model/fila-vazia vetor))

(def fila-nao-cheia-gen
  (gen/fmap transforma-vetor-em-fila
    (gen/vector nome-aleatorio-gen 0 4)))

(defn total-pacientes [hospital]
  (reduce + (map count (vals hospital))))

(defn transfere-ignorando-erro [hospital de para]
   (try
     (transfere hospital de para)
     (catch Error
       (println Error)
       hospital)))

(defspec tranfere-mantem-quantidade-de-pessoas 1
         (prop/for-all
           [espera fila-nao-cheia-gen
            raio-x fila-nao-cheia-gen
            hemograma fila-nao-cheia-gen
            vai-para (gen/elements [:raio-x :hemograma])]
           (let [hospital-inicio {:espera espera :raio-x raio-x :hemograma hemograma}
                 hospital-final (transfere-ignorando-erro hospital-inicio :espera vai-para)]
             (is (= (total-pacientes hospital-inicio)
                    (total-pacientes hospital-final))))))
