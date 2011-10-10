(ns clojure.data.codec.perf-base64
  (:import java.io.PrintWriter org.apache.commons.codec.binary.Base64)
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:use clojure.data.codec.base64))

(set! *warn-on-reflection* true)

(defn rand-bytes [n]
  (->> #(byte (- (rand-int 256) 128))
    repeatedly
    (take n)
    (byte-array)))

(defn gen-data-file [file from to times]
  (with-open [w (PrintWriter. (io/writer file))]
    (binding [*out* w]
      (doseq [n (range from (inc to))]
        (doseq [_ (range 0 times)]
          (println (into [] (rand-bytes n))))))))

(defn read-data-file [file]
  (->> (line-seq (io/reader file))
      (map read-string)
      (map #(map byte %))
      (map byte-array)))

(defmacro time-it [expr]
  `(let [start# (System/nanoTime)
         _# ~expr
         stop# (System/nanoTime)]
     (- stop# start#)))

(defn perf-clj [bas]
  (for [ba bas]
    (do
      (Thread/sleep 100)
      (time-it (encode ba)))))

(defn perf-clj-buf [bas]
  (let [out (memoize (fn [n] (byte-array (enc-length n))))]
    (for [^bytes ba bas]
      (let [len (alength ba)
            output (out len)]
        (do
          (Thread/sleep 100)
          (time-it (encode! ba 0 len output)))))))

(defn perf-apache [bas]
  (for [ba bas]
    (do
      (Thread/sleep 100)
      (time-it (Base64/encodeBase64 ba)))))

(defn append-times [table times]
  (map (fnil conj []) table times))

(defn write-time-file [table file]
  (with-open [w (PrintWriter. (io/writer file))]
    (binding [*out* w]
      (doseq [row table]
        (print (first row))
        (doseq [e (next row)]
          (print \tab)
          (print e))
        (println)))))

(defn read-time-file [file]
  (->> (line-seq (io/reader file))
    (map #(str/split % #"\t"))
    (map vec)))

(defn init-perf-apache [bas]
  (map #(vector (count %1) %2) bas (perf-apache bas)))

(defn third [[_ _ x]] x)

(defn latest-time-file [basis]
  (let [dir (io/file ".")
        nums (->> (.list dir )
               (filter #(.startsWith ^String % basis))
               (map #(re-matches #"(.+\.)([0-9]{3})" %))
               (keep identity)
               (map third)
               (map #(Integer/parseInt %)))]
    (if (seq nums)
      (format "%s.%03d" basis (apply max nums)))))

(defn next-time-file [file]
  (if-let [[_ prefix suffix] (re-matches #"(.+\.)([0-9]{3})" file)]
    (format "%s%03d" prefix (inc (Integer/parseInt suffix)))))

(defn init-time-file [data-file time-file]
  (write-time-file (init-perf-apache (read-data-file data-file)) (str time-file ".000")))

(defn run-perf [data-file time-file use-buffer?]
  (let [prev-time-file (latest-time-file time-file)
        next-time-file (next-time-file prev-time-file)]
    (write-time-file
      (append-times
        (read-time-file prev-time-file)
        (if use-buffer?
          (perf-clj-buf (read-data-file data-file))
          (perf-clj (read-data-file data-file))))
      next-time-file)))



