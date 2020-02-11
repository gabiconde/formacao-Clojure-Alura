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

(deftest chega-em-test
  (testing "aceita pessoas enquanto cabem na fila"
    (is (= {:espera [4 50 3 21 5]}
           (chega-em {:espera [4 50 3 21]} :espera 5))))

  (testing "Não aceita pessoas quando não cabe na fila"
    (is (nil? (chega-em {:espera [4 50 3 21 45]} :espera 5)))))

    ;é o  erro generico para qualquer exception não da pra saber se é o erro esperado ou nao
    ;(is (thrown? clojure.lang.ExceptionInfo (chega-em {:espera [4 50 3 21 45]} :espera 5)))))

    ;não é bom testar a string do erro, pois pode mudar e quebra o teste
    ; nil não é a melhor opcao quando trabalhamos com atoms.

