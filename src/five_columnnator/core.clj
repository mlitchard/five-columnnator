(ns five-columnnator.core
  (:gen-class))
(require 'five-columnnator.five-columnnator)
(refer 'five-columnnator.five-columnnator)

(require 'five-columnnator.io)
(refer 'five-columnnator.io)

(require 'five-columnnator.constants)
(refer 'five-columnnator.constants)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  printf args)

;; specialized for 80 column screen size and five files
;; expects list of five strings representing filenames
(defn five-columnator [ filenames]
  (into [] (map (partial columnnate-file column-width) (map unpack-paragraphs (partition-file (get-files filenames)))))) 
