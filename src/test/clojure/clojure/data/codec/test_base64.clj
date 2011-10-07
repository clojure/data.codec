(ns clojure.data.codec.test-base64
  (:refer-clojure :exclude [time])
  (:import org.apache.commons.codec.binary.Base64)
  (:use clojure.test
        clojure.data.codec.base64))

(defn rand-bytes [n]
  (->> #(byte (- (rand-int 256) 128))
    repeatedly
    (take n)
    (byte-array)))

(deftest correctness
  (doseq [n (range 1 129)]
    (is (let [input (rand-bytes n)
              a1 (encode input)
              a2 (Base64/encodeBase64 input)]
          (= (into [] a1) (into [] a2))))))