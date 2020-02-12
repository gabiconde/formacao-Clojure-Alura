(ns testes.logic-test
  (:require [clojure.test :refer :all]
            [testes.logic :refer :all]
            [testes.model :as model]))

(deftest cabe-na-fila?-test
  (testing "cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))

  (testing "não cabe quando está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "fila com mais de 5"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))

  (testing "sem departamento"
    (is (not (cabe-na-fila? {} :espera)))))

(deftest chega-em-test
  (let [hosp-cheio {:espera [4 50 3 21 45]}]
    (testing "aceita pessoas enquanto cabem na fila"
      (is (= {:espera [4 50 3 21 5]}
             (chega-em {:espera [4 50 3 21]} :espera 5))))

    ;usa dados da msg de erro para validar
    (testing "não aceita pessoas quando não cabe na fila"
      ;é o  erro generico para qualquer exception não da pra saber se é o erro esperado ou nao
      (is (thrown? clojure.lang.ExceptionInfo (chega-em hosp-cheio :espera 5)))

      #_(is (try
              (chega-em hosp-cheio :espera 5)
              false
              (catch clojure.lang.ExceptionInfo e
                (= :nao-cabe-na-fila (:type (ex-data e)))))))))

    ;(is (nil? (chega-em hosp-cheio :espera 5)))))
    ;não é bom testar a string do erro, pois pode mudar e quebra o teste
    ; nil não é a melhor opcao quando trabalhamos com atoms.

(deftest transfere-test
  (testing "aceita pessoas se cabe"
    (let [hosp-original {:espera (conj model/fila-vazia 5)
                         :raio-x (conj model/fila-vazia 13)}
          hosp-2 {:espera2 (conj model/fila-vazia 51 5)
                  :raio-x (conj model/fila-vazia 13)}]
      (is (= {:espera [] :raio-x [13 5]}
             (transfere hosp-original :espera :raio-x)))

      (is (= {:espera2 [5]  :raio-x [13 51]}
             (transfere hosp-2 :espera2 :raio-x)))))

  (testing "recusa pessoas se não cabe"
    (let [hosp-cheio {:espera [5]
                      :raio-x [2 45 67 34 5]}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (transfere hosp-cheio :espera :raio-x))))))


