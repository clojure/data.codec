(ns clojure.data.codec.perf-base64
  (:import org.apache.commons.codec.binary.Base64)
  (:use clojure.test
        clojure.data.codec.base64)
  (:require [criterium.core :as crit]))

(set! *warn-on-reflection* true)

(defn rand-bytes [n]
  (->> #(byte (- (rand-int 256) 128))
    (repeatedly n)
    (byte-array n)))

(defmacro mean [expr]
  `(-> ~expr (crit/quick-benchmark {}) :mean first))

(defn -main [& _]
  (let [byte-counts [1e2 1e4 1e6]
        byte-arrays (map rand-bytes byte-counts)
        encoded-arrays (map encode byte-arrays)]
    (binding [crit/*final-gc-problem-threshold* 1]
      (printf "%23s %14s%n" "base64.clj" "commons-codec")
      (println "encode:")
      (flush)
      (doseq [^bytes ba byte-arrays]
        (printf "%7dB %ems %ems%n" (alength ba) (mean (encode ba)) (mean (Base64/encodeBase64 ba)))
        (flush))
      (println "decode:")
      (doseq [^bytes ba encoded-arrays]
        (printf "%7dB %ems %ems%n" (alength ba) (mean (decode ba)) (mean (Base64/decodeBase64 ba)))
        (flush)))))

