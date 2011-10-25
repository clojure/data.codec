# data.codec

Native codec implementations. Currently only base64 has been implemented.

API documentation: http://clojure.github.com/data.codec

## base64

Implements the standard base64 encoding character set, but does not yet support automatic fixed line-length encoding.

All operations work on either byte arrays or Input/OutputStreams.

Performance is on par with Java implementations, e.g., Apache commons-codec.

### Example Usage

Transform a binary file into a base64 encoded file:

```clojure
(require '[clojure.data.codec.base64 :as b64]
         '[clojure.java.io :as io])

(with-open [in (io/input-stream "input.bin")
            out (io/output-stream "output.b64")]
  (b64/encoding-transfer in out))
```

## Installation

The data.codec library is available in Maven central.  Add it to your Maven project's `pom.xml`:

    <dependency>
      <groupId>org.clojure</groupId>
      <artifactId>data.codec</artifactId>
      <version>0.1.0</version>
    </dependency>

or your leiningen `project.clj`:

    [org.clojure/data.codec "0.1.0"]

## License

Copyright © 2011 Alex Taggart

Licensed under the EPL. (See the file epl.html.)
