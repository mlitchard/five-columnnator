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

(defn five-columnator [filenames]
  (into [] 
        (map bordered-rows 
             (into [] 
                   (map (partial columnnate-file column-width) 
                        (into [] 
                              (map unpack-paragraphs 
                                   (into [] 
                                         (map partition-file 
                                              (into [] 
                                                    (map get-file filenames)))))))))))
;; (defn five-columnator [ filenames]
;;  (into [] (map (partial columnnate-file column-width) (map unpack-paragraphs (partition-file (get-files filenames))))))
;;

(defn -main
    "I don't do a whole lot ... yet."
    [& args]
    (map (fn [thing1] (map (fn [thing2] (map (fn [thing3] print thing3) thing2)) thing1)) (five-columnator args)))
