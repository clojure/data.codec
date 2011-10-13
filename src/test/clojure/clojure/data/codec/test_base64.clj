(ns clojure.data.codec.test-base64
  (:import org.apache.commons.codec.binary.Base64
           [java.io ByteArrayInputStream ByteArrayOutputStream])
  (:use clojure.test
        clojure.data.codec.base64))

(set! *warn-on-reflection* true)

(defn rand-bytes [n]
  (->> #(byte (- (rand-int 256) 128))
    repeatedly
    (take n)
    (byte-array)))

(deftest enc-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (is (let [input (rand-bytes n)
              a1 (encode input)
              a2 (Base64/encodeBase64 input)]
          (= (into [] a1) (into [] a2))))))

(deftest offset-enc-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (doseq [off (range 1 (min n 5))]
      (is (let [input (rand-bytes n)
                len (- n off)
                a1 (encode input off len)
                input2 (byte-array len)
                _ (System/arraycopy input off input2 0 len)
                a2 (Base64/encodeBase64 input2)]
            (= (into [] a1) (into [] a2)))))))

(deftest buffer-enc-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (doseq [excess-buf-len (range 1 10)]
      (is (let [input (rand-bytes n)
                output (byte-array (+ (enc-length n) excess-buf-len))
                _ (encode! input 0 n output)
                a2 (Base64/encodeBase64 input)]
            (= (into [] (take (enc-length n) output)) (into [] a2)))))))

(deftest dec-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (is (let [orig (rand-bytes n)
              enc (encode orig)
              deco (decode enc)]
          (= (into [] deco) (into [] orig))))))

(deftest offset-dec-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (doseq [off (range 1 (min n 5))]
      (is (let [orig (rand-bytes n)
                enc (byte-array (concat (repeat off (byte 0)) (encode orig)))
                deco (decode enc off (- (alength enc) off))]
            (= (into [] deco) (into [] orig)))))))

(deftest buffer-dec-correctness
  (doseq [n (concat (range 1 6) (range 1001 1006))]
    (doseq [excess-buf-len (range 1 10)]
      (is (let [orig (rand-bytes n)
                enc (encode orig)
                deco (byte-array (+ n excess-buf-len))
                _ (decode! enc 0 (alength ^bytes enc) deco)]
            (= (into [] (take n deco)) (into [] orig)))))))

(deftest bad-buf-encoding-transfer
  (is (thrown-with-msg? IllegalArgumentException #"Buffer size must be a multiple of"
    (encoding-transfer nil nil :buffer-size 5))))

(deftest bad-buf-decoding-transfer
  (is (thrown-with-msg? IllegalArgumentException #"Buffer size must be a multiple of"
    (decoding-transfer nil nil :buffer-size 5))))

(deftest fit-encoding-transfer
  (let [raw (rand-bytes 3)
        in (ByteArrayInputStream. raw)
        out (ByteArrayOutputStream.)]
    (encoding-transfer in out :buffer-size 3)
    (is (= (into [] (.toByteArray out)) (into [] (encode raw))))))

(deftest split-encoding-transfer
  (let [raw (rand-bytes 4)
        in (ByteArrayInputStream. raw)
        out (ByteArrayOutputStream.)]
    (encoding-transfer in out :buffer-size 3)
    (is (= (into [] (.toByteArray out)) (into [] (encode raw))))))

(deftest fit-decoding-transfer
  (let [raw (rand-bytes 3)
        enc (encode raw)
        in (ByteArrayInputStream. enc)
        out (ByteArrayOutputStream.)]
    (decoding-transfer in out :buffer-size 4)
    (is (= (into [] (.toByteArray out)) (into [] raw)))))





