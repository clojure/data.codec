{:namespaces
 ({:doc "Functions for working with base64 encodings.",
   :author "Alex Taggart",
   :name "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec/clojure.data.codec.base64-api.html",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj"}),
 :vars
 ({:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "dec-length",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L39",
   :line 39,
   :var-type "function",
   :arglists ([in-length pad-length]),
   :doc
   "Calculates what would be the length after decoding of an input array of length\nin-length with the specified padding length.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/dec-length"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "decode",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L139",
   :line 139,
   :var-type "function",
   :arglists ([input] [input offset length]),
   :doc
   "Returns a base64 decoded byte array.\n\nNote: length must be a multiple of 4.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decode"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "decode!",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L59",
   :line 59,
   :var-type "function",
   :arglists ([input offset length output]),
   :doc
   "Reads from the input byte array for the specified length starting at the offset\nindex, and base64 decodes into the output array starting at index 0. Returns the\nlength written to output.\n\nNote: length must be a multiple of 4.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decode!"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "decoding-transfer",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L260",
   :line 260,
   :var-type "function",
   :arglists ([input-stream output-stream & opts]),
   :doc
   "Base64 decodes from input-stream to output-stream. Returns nil or throws IOException.\n\nOptions are key/value pairs and may be one of\n  :buffer-size  read buffer size to use, must be a multiple of 4; default is 8192.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decoding-transfer"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "enc-length",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L31",
   :line 31,
   :var-type "function",
   :arglists ([n]),
   :doc
   "Calculates what would be the length after encoding of an input array of length n.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/enc-length"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "encode",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L230",
   :line 230,
   :var-type "function",
   :arglists ([input] [input offset length]),
   :doc "Returns a base64 encoded byte array.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encode"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "encode!",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L151",
   :line 151,
   :var-type "function",
   :arglists ([input offset length output]),
   :doc
   "Reads from the input byte array for the specified length starting at the offset\nindex, and base64 encodes into the output array starting at index 0. Returns the\nlength written to output.\n\nNote: if using partial input, length must be a multiple of 3 to avoid padding.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encode!"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "encoding-transfer",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L278",
   :line 278,
   :var-type "function",
   :arglists ([input-stream output-stream & opts]),
   :doc
   "Base64 encodes from input-stream to output-stream. Returns nil or throws IOException.\n\nOptions are key/value pairs and may be one of\n  :buffer-size  read buffer size to use, must be a multiple of 3; default is 6144.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encoding-transfer"}
  {:raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :name "pad-length",
   :file "src/main/clojure/clojure/data/codec/base64.clj",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L49",
   :line 49,
   :var-type "function",
   :arglists ([input offset length]),
   :doc "Returns the length of padding on the end of the input array.",
   :namespace "clojure.data.codec.base64",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/pad-length"})}
