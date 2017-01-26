(ns five-columnnator.five-columnnator 
 (:require [tupelo.core :as t])
 (:require [clojure.string :as s]))

;; module: file-columnnator
;; Takes a set of five essays and re-formats each essay to be in
;; it's corresponding column. For example - essay 1 would be formatted to
;; be in column 1, essay 5 in column 5.

;; partition-file
;; partitions essay into easily-mungable paragraphs
;; pre-conditions: expects a list of strings
;; post-condition: a list of list of strings representing a list of paragraphs
(defn partition-file
  [file]
  "partition-file groups strings by non-emptiness"
  (remove #(= '("") %) ;; figure out proper application of t/split-when
          (partition-by #(= "" %) file)))

;; words
;; takes a single string of words and returns a vector of words
;; pre-conditions: expects a string
;; post-condition: a vector of strings
(defn words [string ] (s/split string #"\s|--"))

;; unpack-paragraph
;; takes paragraph and returns same paragraph as list of words
;; pre-condition: list of strings representing a sentence
;; postcondition: list of strings representing a word
(defn unpack-paragraph [paragraph] (flatten (map words paragraph)))

;; columnnate
;; takes an unpacked paragraph and returns a list of strings with the length
;; that approaches but does not exceed column-width.
;; word integrity retained.
;; pre-condition: a list of strings representing words
;; postcondition: a list of strings representing column of words
(defn columnnate [unformatted-paragraph column-width]
    "columnnate undefined")

;; collate
;; takes a ?list? of column-formatted essays
;; and re-orders them such that each nth line of each essay
;; are grouped together
;; so column line 1 of essay 1 will be grouped with column line 1 of essay 2
;; 3,4,5
;; and so on
;; pre-condition: a list of column-formatted essays
;; postcondition: a list of line-grouped ?lists?
(defn collate [essay1 essay2 essay3 essay4 essay5]
    "collate undefined")

;; borderizer
;; takes collated essays and places border character between lines on the same
;; row
;; pre-condition: collated essays --implementation to be determined
;; postcondition: collated essays with border characters
(defn borderizer [collated] "borderizer undefined")


