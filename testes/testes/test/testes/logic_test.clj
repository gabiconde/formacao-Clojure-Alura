(ns testes.logic-test
  (:require [clojure.test :refer :all]
            [testes.logic :refer :all]))

(deftest cabe-na-fila?-test
  (testing "Cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "Não cabe quando está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "fila com mais de 5"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "sem departamento"
    (is (not (cabe-na-fila? {} :espera)))))
