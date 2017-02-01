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

;; word-count
;; counts the words in a vector
;; pre-condition - a vector of words (TODO: tagging)
;; post-condition - a positive integer 
(defn word-count [words] (count words))

;; add-word 
;; adds a vector of a single word (string)
;; to the end of a vector of words (strings)
;; pre-condition: first parameter must be a vector of one string
;;                second paramter is the vector to be appended
;; post-condition: returned vector will have first paramter appended to end
;;                 of second parameter
(defn add-word [word words] (apply conj words word))

;; character-count
;; calculates the sum of all characters in a vector of words
;; pre-condition: parameter is vector of words
;; postcondition: a positive integer
(defn character-count [word-vector] (apply + (map count word-vector)))


;; line-width
;; calculates the size of a column line based on word length and word spacing
;; pre-condition: a vector of words
;; post-condition: a positive integer
(defn line-width [words] (+ (character-count words) (- (count words) 1)))

(defrecord Accumulator [in-progress accumulated])
;; columnnate
;; takes an unpacked paragraph and returns a list of strings with the length
;; that approaches but does not exceed column-width.
;; word integrity retained.
;; pre-condition: a list of strings representing words
;; postcondition: a list of strings representing column of words
(defn columnnate [unformatted-paragraph column-width]
  (loop [accum Accumulator u-p unformatted-paragraph]
    (let [[head rest] u-p]
      (if (empty? u-p)
        accum
        (if (> 15 (+ (count (:in-progress accum)) (count head)))
         nil 
         nil))))) 
    

      
  
;;    (if (empty? unformatted-paragraph)
;;      accumulator
;;      (let [[head rest] unformatted-paragraph]
;;        (if (empty? accumulator)
;;           (recur rest column-width  
;;        (def proposed-add (add-word ))
;;        (if (> 15 (line-width proposed-add)) 
;;         recur)))

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

(defn hyphy-length-test [s c-width] (< (count s) (- c-width 1)))

;; hyphinator
;; takes words longer than column-width and returns the same 
;; word vectorized and hyphenated
(defn hyphy [superlong c-width] 
  (loop [v [] s superlong] 
    (if (hyphy-length-test s c-width) 
      (->Accumulator s v) 
      (recur (conj v (str (subs s 0 14) "-")) (subs s 14)))))
