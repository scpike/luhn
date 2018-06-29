(ns luhn.core-test
  (:require [clojure.test :refer :all]
            [luhn.core :refer :all]))

(deftest test-string->digits
  (testing "Converts a string to digits."
    (is (= [1 2 3] (string->digits "123")))))

(deftest compute-checksum
  (testing "Computes a checksum."
    (is (= 3 (luhn.core/luhn-checksum "7992739871")))))

(deftest checks-luhn
  (testing "Validates luhn."
    (is (= true (luhn.core/valid-luhn-checksum? "79927398713")))
    (is (= false (luhn.core/valid-luhn-checksum? "79927398711")))))
