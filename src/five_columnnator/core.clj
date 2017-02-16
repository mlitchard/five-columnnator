(ns five-columnnator.core
  (:gen-class))
(require 'five-columnnator.five-columnnator)
(refer 'five-columnnator.five-columnnator)

(require 'five-columnnator.io)
(refer 'five-columnnator.io)

(require 'five-columnnator.constants)
(refer 'five-columnnator.constants)

;; specialized for 80 column screen size and five files
;; expects list of five strings representing filenames

(defn five-columnator [filenames] (mapv bordered-rows (collate (mapv (partial columnnate-file column-width) (mapv unpack-paragraphs (mapv partition-file (into [] (map get-file filenames))))))))

(defn -main
    "I don't do a whole lot ... yet."
    [& args]
;;    (doseq [[& thing] (map (fn [thing1] (map (fn [thing2] (map (fn [thing3] thing3)) thing2) thing1)) (five-columnator args))] (prn thing))) 
    (dorun (map (fn [thing1] (map (fn [thing2] (map (fn [thing3] (doseq [[& thing4] thing3] print thing3)) thing2)) thing1)) (five-columnator args))))
