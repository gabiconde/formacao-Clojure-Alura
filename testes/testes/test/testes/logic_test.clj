(ns testes.logic-test
  (:require [clojure.test :refer :all]
            [testes.logic :refer :all]
            [testes.model :as model]
            [schema.core :as s]
            [clojure.test.check.generators :as gen]))

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


