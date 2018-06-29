; https://en.wikipedia.org/wiki/Luhn_algorithm

(ns luhn.core
  (:require [clojure.string :as str]))

(defn string->digits
  [s]
  (map #(Integer/parseInt (str %)) (seq s)))

; From the rightmost digit, which is the check digit, and moving left,
; double the value of every second digit. The check digit is not
; doubled, the first digit doubled is immediately to the left of the
; check digit. If the result of this doubling operation is greater
; than 9 (e.g., 8 × 2 = 16), then add the digits of the product (e.g.,
; 16: 1 + 6 = 7, 18: 1 + 8 = 9) or, alternatively, the same result can
; be found by subtracting 9 from the product (e.g., 16: 16 − 9 = 7,
; 18: 18 − 9 = 9).
(defn double-and-sum
  [digits]
  (->>
   (map #(* 2 %) digits)
   (map #(if (> % 9) (- % 9) %))))

(defn luhn-checksum
  [s]
  (let [digits (reverse (string->digits s))
        [to-double rest] (apply map list (partition 2 digits))]
    (mod (* 9 (reduce + (flatten [(double-and-sum to-double) rest]))) 10)))

(defn valid-luhn-checksum?
  [s]
  (= (luhn-checksum (drop-last s))
     (Character/getNumericValue (last s))))
