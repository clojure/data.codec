{:namespaces
 ({:source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec/clojure.data.codec.base64-api.html",
   :name "clojure.data.codec.base64",
   :author "Alex Taggart",
   :doc "Functions for working with base64 encodings."}),
 :vars
 ({:arglists ([in-length pad-length]),
   :name "dec-length",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L39",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/dec-length",
   :doc
   "Calculates what would be the length after decoding of an input array of length\nin-length with the specified padding length.",
   :var-type "function",
   :line 39,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input] [input offset length]),
   :name "decode",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L139",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decode",
   :doc
   "Returns a base64 decoded byte array.\n\nNote: length must be a multiple of 4.",
   :var-type "function",
   :line 139,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input offset length output]),
   :name "decode!",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L59",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decode!",
   :doc
   "Reads from the input byte array for the specified length starting at the offset\nindex, and base64 decodes into the output array starting at index 0. Returns the\nlength written to output.\n\nNote: length must be a multiple of 4.",
   :var-type "function",
   :line 59,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input-stream output-stream & opts]),
   :name "decoding-transfer",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L260",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/decoding-transfer",
   :doc
   "Base64 decodes from input-stream to output-stream. Returns nil or throws IOException.\n\nOptions are key/value pairs and may be one of\n  :buffer-size  read buffer size to use, must be a multiple of 4; default is 8192.",
   :var-type "function",
   :line 260,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([n]),
   :name "enc-length",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L31",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/enc-length",
   :doc
   "Calculates what would be the length after encoding of an input array of length n.",
   :var-type "function",
   :line 31,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input] [input offset length]),
   :name "encode",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L230",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encode",
   :doc "Returns a base64 encoded byte array.",
   :var-type "function",
   :line 230,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input offset length output]),
   :name "encode!",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L151",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encode!",
   :doc
   "Reads from the input byte array for the specified length starting at the offset\nindex, and base64 encodes into the output array starting at index 0. Returns the\nlength written to output.\n\nNote: if using partial input, length must be a multiple of 3 to avoid padding.",
   :var-type "function",
   :line 151,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input-stream output-stream & opts]),
   :name "encoding-transfer",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L278",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/encoding-transfer",
   :doc
   "Base64 encodes from input-stream to output-stream. Returns nil or throws IOException.\n\nOptions are key/value pairs and may be one of\n  :buffer-size  read buffer size to use, must be a multiple of 3; default is 6144.",
   :var-type "function",
   :line 278,
   :file "src/main/clojure/clojure/data/codec/base64.clj"}
  {:arglists ([input offset length]),
   :name "pad-length",
   :namespace "clojure.data.codec.base64",
   :source-url
   "https://github.com/clojure/data.codec/blob/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj#L49",
   :raw-source-url
   "https://github.com/clojure/data.codec/raw/766746c11459a70cab27f7e0668a0be1f35fd679/src/main/clojure/clojure/data/codec/base64.clj",
   :wiki-url
   "http://clojure.github.com/data.codec//clojure.data.codec.base64-api.html#clojure.data.codec.base64/pad-length",
   :doc "Returns the length of padding on the end of the input array.",
   :var-type "function",
   :line 49,
   :file "src/main/clojure/clojure/data/codec/base64.clj"})}
