(ns clojure.data.codec.base64)

(def ^:private enc-bytes 
  (byte-array 
    (map (comp byte int) 
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")))

(defn enc-length
  "Calculates what would be the length after encoding of an input array of length n."
  ^long [^long n]
  (-> n
    (unchecked-add 2)
    (quot 3)
    (unchecked-multiply 4)))

(defn encode
  "Returns a base64 encoded byte array."
  (^bytes [^bytes ba]
    (encode ba 0 (alength ba)))
  (^bytes [^bytes ba offset length]
    (let [^bytes enc-bytes enc-bytes ; can't type-hint var
          _ (if (> length 1610612733)
              (throw (IllegalArgumentException.
                       "Encoded length would exceed max array size.")))
          ca (byte-array (enc-length length))
          lim (case (mod length 3)
                0 length
                1 (unchecked-dec length)
                2 (unchecked-subtract length 2))]
      (loop [i offset j 0]
        (when (< i lim)
          (let [x (int (aget ba i)) ; can only bind long/double prims, but no widening, and can't cast from byte to long without boxing
                y (int (aget ba (unchecked-inc i)))
                z (int (aget ba (unchecked-add 2 i)))
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
            (aset ca j (aget enc-bytes a))
            (aset ca (unchecked-inc j) (aget enc-bytes b))
            (aset ca (unchecked-add 2 j) (aget enc-bytes c))
            (aset ca (unchecked-add 3 j) (aget enc-bytes d)))
          (recur (unchecked-add 3 i) (unchecked-add 4 j))))
      ; add padding:
      (case (mod length 3)
        0 ca
        1 (do
            (aset ca 
              (unchecked-subtract (alength ca) 4)
              (aget enc-bytes 
                (-> (aget ba (unchecked-dec length))
                  int
                  (bit-shift-right 2)
                  (bit-and 0x3F))))
            (aset ca 
              (unchecked-subtract (alength ca) 3)
              (aget enc-bytes 
                (-> (aget ba (unchecked-dec length))
                  int
                  (bit-and 0x3)
                  (bit-shift-left 4))))
            (aset ca (unchecked-subtract (alength ca) 2) (byte 61))
            (aset ca (unchecked-dec (alength ca)) (byte 61))
            ca)
        2 (do
            (aset ca 
              (unchecked-subtract (alength ca) 4)
              (aget enc-bytes 
                (-> (aget ba (unchecked-subtract length 2))
                  int
                  (bit-shift-right 2)
                  (bit-and 0x3F))))
            (aset ca 
              (unchecked-subtract (alength ca) 3)
              (aget enc-bytes 
                (-> (aget ba (unchecked-subtract length 2))
                  int
                  (bit-and 0x3)
                  (bit-shift-left 4)
                  (bit-or (-> (aget ba (unchecked-dec length))
                            int
                            (bit-shift-right 4)
                            (bit-and 0xF))))))
            (aset ca 
              (unchecked-subtract (alength ca) 2)
              (aget enc-bytes 
                (-> (aget ba (unchecked-dec length))
                  int
                  (bit-and 0xF)
                  (bit-shift-left 2))))
            (aset ca (unchecked-dec (alength ca)) (byte 61))
            ca)))))
