(ns five-columnnator.io)


;; Module: five-columnnator.io
;; This module handles the io edges of this essay re-formatting system

(defn get-file [file-name] (line-seq (clojure.java.io/reader file-name)))

(defn get-files [filenames] (into [] (map (partial into []) (map get-file filenames))))
