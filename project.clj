(defproject org.clojure/data.codec "0.1.2-SNAPSHOT"
  :description "Clojure codec implementations."
  :url "https://github.com/clojure/data.codec"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :dependencies [[org.clojure/clojure "1.11.4"]]
  :aliases {"perf" ["with-profile" "perf" "run"]}
  :profiles {:dev {:dependencies [[org.clojure/test.check "1.1.3"]
                                  [commons-codec "1.12.0"]]
                   :plugins [[lein-cloverage "1.0.9"]]}
             :perf {:dependencies [[commons-codec "1.16.1"]
                                   [criterium "0.4.6"]]
                    :source-paths ["src/perf/clojure"]
                    :main clojure.data.codec.perf-base64}})
