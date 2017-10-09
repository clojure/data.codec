(ns clojure.data.codec.test-base64
  (:import org.apache.commons.codec.binary.Base64
           java.io.ByteArrayInputStream
           java.io.ByteArrayOutputStream)
  (:require [clojure.test :refer [deftest is]] 
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop])
  (:use clojure.data.codec.base64))

(set! *warn-on-reflection* true)

(defmethod print-method (class (byte-array 0)) [bytes, ^java.io.Writer w]
  (.write w "#bytes")
  (print-method (vec bytes) w))

(defn aeq?
  ([a b]
    (= (seq a) (seq b)))
  ([a b len]
    (= (take len a) (take len b))))

(defn copy-bytes ^bytes [array offset length]
  (let [arr (byte-array length)]
    (when (pos? length)
      (System/arraycopy array offset arr 0 length))
    arr))

(defn expected-enc
  (^bytes [input]
    (Base64/encodeBase64 input))
  (^bytes [input offset length]
    (Base64/encodeBase64 (copy-bytes input offset length))))

(defn expected-dec
  (^bytes [^bytes input]
    (Base64/decodeBase64 input))
  (^bytes [input offset length]
    (Base64/decodeBase64 (copy-bytes input offset length))))

(defn gen-discrete [min max base]
  (let [min (quot min base)
        max (quot max base)]
    (gen/fmap #(* base %) (gen/large-integer* {:min min :max max}))))

(defn gen-with-offset-length [gen-array step]
  (gen/bind gen-array
    (fn [^bytes array]
      (if (zero? (alength array))
        (gen/tuple (gen/return array)
                   (gen/return -1)
                   (gen/return 0))
        (gen/bind (gen-discrete 0 (dec (alength array)) step)
                  (fn [len]
                    (gen/tuple
                      (gen/return array)
                      (gen-discrete 0 (- (alength array) len) step)
                      (gen/return len))))))))

(def gen-bytes-offset-length (gen-with-offset-length gen/bytes 1))

(def gen-encbytes (gen/fmap #(Base64/encodeBase64 %) gen/bytes))

(def gen-encbytes-offset-length (gen-with-offset-length gen-encbytes 4))

(defspec check-encode!
  (prop/for-all [[^bytes input offset length] gen-bytes-offset-length]
    (let [dest (byte-array (* 2 (alength input)))
          enc-len (encode! input offset length dest)
          exp (expected-enc input offset length)]
      (and (= enc-len (alength exp))
           (aeq? dest exp enc-len)))))

(deftest test-encode!-prim
  (is (instance? clojure.lang.IFn$OLLOL encode!)))

(defspec check-encode-1
  (prop/for-all [^bytes input gen/bytes]
    (let [enc (encode input)
          exp (expected-enc input)]
      (aeq? enc exp))))

(defspec check-encode-3
  (prop/for-all [[^bytes input offset length] gen-bytes-offset-length]
    (let [enc (encode input offset length)
          exp (expected-enc input offset length)]
      (aeq? enc exp))))

(defspec check-decode!
  (prop/for-all [[^bytes input offset length] gen-encbytes-offset-length]
		(let [dest (byte-array (alength input))
		      dec-len (decode! input offset length dest)
		      exp (expected-dec input offset length)]
		  (and (= dec-len (alength exp))
		       (aeq? dest exp dec-len)))))

(deftest test-decode!-prim
  (is (instance? clojure.lang.IFn$OLLOL decode!)))

(defspec check-decode-1
  (prop/for-all [^bytes input gen-encbytes]
		(let [dec (decode input)
		      exp (expected-dec input)]
		  (aeq? dec exp))))

(defspec check-decode-3
  (prop/for-all [[^bytes input offset length] gen-encbytes-offset-length]
		(let [dec (decode input offset length)
		      exp (expected-dec input offset length)]
		  (aeq? dec exp))))

(defspec check-encoding-transfer-default-buffer-size
  (prop/for-all [input gen/bytes]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (encoding-transfer in out)
      (aeq? (.toByteArray out) (expected-enc input)))))

(defspec check-encoding-transfer-buffer-size
  (prop/for-all [input gen/bytes
                 buffer-size (gen/fmap #(* 3 %) (gen/fmap inc gen/nat))]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (encoding-transfer in out :buffer-size buffer-size)
      (aeq? (.toByteArray out) (expected-enc input)))))

(defspec check-encoding-transfer-bad-buffer-size
  (prop/for-all [input gen/bytes]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (try
        (encoding-transfer in out :buffer-size 2)
        false
        (catch IllegalArgumentException _
          true)))))

(defspec check-decoding-transfer-default-buffer-size
  (prop/for-all [input gen-encbytes]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (decoding-transfer in out)
      (aeq? (.toByteArray out) (expected-dec input)))))

(defspec check-decoding-transfer-buffer-size
  (prop/for-all [input gen-encbytes
                 buffer-size (gen/fmap #(* 4 %) (gen/fmap inc gen/nat))]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (decoding-transfer in out :buffer-size buffer-size)
      (aeq? (.toByteArray out) (expected-dec input)))))

(defspec check-decoding-transfer-bad-buffer-size
  (prop/for-all [input gen-encbytes]
    (let [in (ByteArrayInputStream. input)
          out (ByteArrayOutputStream.)]
      (try
        (decoding-transfer in out :buffer-size 2)
        false
        (catch IllegalArgumentException _
          true)))))
