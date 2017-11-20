;; by Alex Taggart
;; October 11, 2011

;; Copyright (c) Alex Taggart, October 2011. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

(ns ^{:author "Alex Taggart"
      :doc "Functions for working with base64 encodings."}
  clojure.data.codec.base64
  (:import [java.io InputStream OutputStream]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

(def ^:private ^"[B" enc-bytes
  (byte-array
    (map (comp byte int)
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")))

(def ^:private ^"[B" dec-bytes
  (let [^bytes ba (byte-array (inc (apply max enc-bytes)))]
    (doseq [[idx enc] (map-indexed vector enc-bytes)]
      (aset ba enc (unchecked-byte idx)))
    ba))

(defn enc-length
  "Calculates what would be the length after encoding of an input array of length n."
  ^long [^long n]
  (-> n
    (+ 2)
    (quot 3)
    (* 4)))

(defn dec-length
  "Calculates what would be the length after decoding of an input array of length
   in-length with the specified padding length."
  ^long [^long in-length ^long pad-length]
  (-> in-length
    (quot 4)
    (* 3)
    (- pad-length)))


(defn pad-length
  "Returns the length of padding on the end of the input array."
  ^long [^bytes input ^long offset ^long length]
  (if (zero? length)
    0
    (let [end (+ offset length -1)]
      (if (== 61 (long (aget input end)))
        (if (== 61 (long (aget input (dec end))))
          2
          1)
        0))))

(defn decode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 decodes into the output array starting at index 0. Returns the
   length written to output.

   Note: length must be a multiple of 4."
  ^long [^bytes input ^long offset ^long length ^bytes output]
  (if (zero? length)
    0
    (let [pad-len (pad-length input offset length)
          out-len (dec-length length pad-len)
          tail-len (rem out-len 3)
          loop-lim (- out-len tail-len)]
      (loop [i offset j 0]
        (if (< j loop-lim)
          (let [a (long (aget dec-bytes (aget input i)))
                b (long (aget dec-bytes (aget input (inc i))))
                c (long (aget dec-bytes (aget input (+ 2 i))))
                d (long (aget dec-bytes (aget input (+ 3 i))))
                x1 (-> a
                     (bit-and 0x3F)
                     (bit-shift-left 2))
                x2 (-> b
                     (bit-shift-right 4)
                     (bit-and 0x3))
                y1 (->
                     (bit-and b 0xF)
                     (bit-shift-left 4))
                y2 (-> c
                     (bit-shift-right 2)
                     (bit-and 0xF))
                z1 (-> c
                     (bit-and 0x3)
                     (bit-shift-left 6))
                z2 (bit-and d 0x3F)
                x (bit-or x1 x2)
                y (bit-or y1 y2)
                z (bit-or z1 z2)]
            (aset output j (unchecked-byte x))
            (aset output (inc j) (unchecked-byte y))
            (aset output (+ 2 j) (unchecked-byte z))
            (recur (+ 4 i) (+ 3 j)))

          ; handle padded section
          (case tail-len
            0 j
            1 (let [a (long (aget dec-bytes (aget input i)))
                    b (long (aget dec-bytes (aget input (inc i))))
                    x1 (-> a
                         (bit-and 0x3F)
                         (bit-shift-left 2))
                    x2 (-> b
                         (bit-shift-right 4)
                         (bit-and 0x3))
                    x (bit-or x1 x2)]
                (aset output j (unchecked-byte x))
                (inc j))
            2 (let [a (long (aget dec-bytes (aget input i)))
                    b (long (aget dec-bytes (aget input (inc i))))
                    c (long (aget dec-bytes (aget input (+ 2 i))))
                    x1 (-> a
                         (bit-and 0x3F)
                         (bit-shift-left 2))
                    x2 (-> b
                         (bit-shift-right 4)
                         (bit-and 0x3))
                    y1 (->
                         (bit-and b 0xF)
                         (bit-shift-left 4))
                    y2 (-> c
                         (bit-shift-right 2)
                         (bit-and 0xF))
                    x (bit-or x1 x2)
                    y (bit-or y1 y2)]
                (aset output j (unchecked-byte x))
                (aset output (inc j) (unchecked-byte y))
                (+ 2 j))))))))

(defn decode
  "Returns a base64 decoded byte array.

  Note: length must be a multiple of 4."
  ([^bytes input]
    (decode input 0 (alength input)))
  ([^bytes input ^long offset ^long length]
    (let [dest (byte-array (dec-length length (pad-length input offset length)))]
      (decode! input offset length dest)
      dest)))


(defn encode!
  "Reads from the input byte array for the specified length starting at the offset
   index, and base64 encodes into the output array starting at index 0. Returns the
   length written to output.

   Note: if using partial input, length must be a multiple of 3 to avoid padding."
  ^long [^bytes input ^long offset ^long length ^bytes output]
  (if (zero? length)
    0
    (let [tail-len (rem length 3)
          loop-lim (- (+ offset length) tail-len)]
      (loop [i offset j 0]
        (if (< i loop-lim)
          (let [x (long (aget input i))
                y (long (aget input (inc i)))
                z (long (aget input (+ 2 i)))
                a (-> x
                    (bit-shift-right 2)
                    (bit-and 0x3F))
                b1 (-> x
                     (bit-and 0x3)
                     (bit-shift-left 4))
                b2 (-> y
                     (bit-shift-right 4)
                     (bit-and 0xF))
                b (bit-or b1 b2)
                c1 (-> y
                     (bit-and 0xF)
                     (bit-shift-left 2))
                c2 (-> z
                     (bit-shift-right 6)
                     (bit-and 0x3))
                c (bit-or c1 c2)
                d (bit-and z 0x3F)]
            (aset output j (aget enc-bytes a))
            (aset output (inc j) (aget enc-bytes b))
            (aset output (+ 2 j) (aget enc-bytes c))
            (aset output (+ 3 j) (aget enc-bytes d))
            (recur (+ 3 i) (+ 4 j)))

          ; write padded section
          (case tail-len
            0 j
            1 (let [x (long (aget input i))
                    a (-> x
                        (bit-shift-right 2)
                        (bit-and 0x3F))
                    b1 (-> x
                         (bit-and 0x3)
                         (bit-shift-left 4))]
                (aset output j (aget enc-bytes a))
                (aset output (inc j) (aget enc-bytes b1))
                (aset output (+ 2 j) (unchecked-byte 61))
                (aset output (+ 3 j) (unchecked-byte 61))
                (+ 4 j))
            2 (let [x (long (aget input i))
                    y (long (aget input (inc i)))
                    a (-> x
                        (bit-shift-right 2)
                        (bit-and 0x3F))
                    b1 (-> x
                         (bit-and 0x3)
                         (bit-shift-left 4))
                    b2 (-> y
                         (bit-shift-right 4)
                         (bit-and 0xF))
                    b (bit-or b1 b2)
                    c1 (-> y
                         (bit-and 0xF)
                         (bit-shift-left 2))]
                (aset output j (aget enc-bytes a))
                (aset output (inc j) (aget enc-bytes b))
                (aset output (+ 2 j) (aget enc-bytes c1))
                (aset output (+ 3 j) (unchecked-byte 61))
                (+ 4 j))))))))

(defn encode
  "Returns a base64 encoded byte array."
  ([^bytes input]
    (encode input 0 (alength input)))
  ([^bytes input ^long offset ^long length]
    (let [dest (byte-array (enc-length length))]
      (encode! input offset length dest)
      dest)))


(defn- read-fully
  "Will fill the buffer to capacity, or with whatever is left in the input.
   Returns the bytes read."
  ; This is necessary since a partial fill from .read does not necessarily mean EOS,
  ; and we need full buffers to avoid incorrect padding.
  ^long [^InputStream input ^bytes buf]
  (loop [off 0 len (alength buf)]
    (let [in-size (.read input buf off len)]
      (cond
        (== in-size len) (+ off in-size)
        (neg? in-size) off
        :else (recur (+ off in-size) (- len in-size))))))

(defn- buf-size ^long [opts ^long default ^long multiple-of]
  (if-let [in-size (:buffer-size opts)]
    (if (zero? (rem in-size multiple-of))
      in-size
      (throw (IllegalArgumentException. ^String (format "Buffer size must be a multiple of %d." multiple-of))))
    default))

(defn decoding-transfer
  "Base64 decodes from input-stream to output-stream. Returns nil or throws IOException.

  Options are key/value pairs and may be one of
    :buffer-size  read buffer size to use, must be a multiple of 4; default is 8192."
  [^InputStream input-stream ^OutputStream output-stream & opts]
  (let [opts (when opts (apply hash-map opts))
        in-size (buf-size opts 8192 4)
        out-size (if (== in-size 8192) 6144 (dec-length in-size 0))
        in-buf (byte-array in-size)
        out-buf (byte-array out-size)]
    (loop []
      (let [in-size (read-fully input-stream in-buf)]
        (when (pos? in-size)
          (let [out-size (decode! in-buf 0 in-size out-buf)]
            (.write output-stream out-buf 0 out-size)
            (recur)))))))

(defn encoding-transfer
  "Base64 encodes from input-stream to output-stream. Returns nil or throws IOException.

  Options are key/value pairs and may be one of
    :buffer-size  read buffer size to use, must be a multiple of 3; default is 6144."
  [^InputStream input-stream ^OutputStream output-stream & opts]
  (let [opts (when opts (apply hash-map opts))
        in-size (buf-size opts 6144 3)
        out-size (if (== in-size 6144) 8192 (enc-length in-size))
        in-buf (byte-array in-size)
        out-buf (byte-array out-size)]
    (loop []
      (let [in-size (read-fully input-stream in-buf)]
        (when (pos? in-size)
          (let [out-size (encode! in-buf 0 in-size out-buf)]
            (.write output-stream out-buf 0 out-size)
            (recur)))))))

