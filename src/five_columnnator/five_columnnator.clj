(ns five-columnnator.five-columnnator 
 (:require [tupelo.core :as t])
 (:require [clojure.string :as s])
 (:require [five-columnnator.constants :as c]))
 

;; module: file-columnnator
;; Takes a set of five essays and re-formats each essay to be in
;; it's corresponding column. For example - essay 1 would be formatted to
;; be in column 1, essay 5 in column 5.
(defrecord File [order-recieved row-count file])

(defn label-file 
  [file-rows order]
    (def row-count (reduce + (mapv count file-rows)))
    (File. order row-count file-rows))

;; label-files
(defn label-files 
  [files] 
  (map label-file files (take (count files) (drop 1 (range)))))

;; sort-by-row-count
(defn sort-by-row-count [labelled-files]
  (sort #(> (:row-count %1) (:row-count %2))))


;; partition-file
;; partitions essay into easily-mungable paragraphs
;; pre-conditions: expects a list of strings
;; post-condition: a list of list of strings representing a list of paragraphs
(defn partition-file
  [file]
  "partition-file groups strings by non-emptiness"
  (into [] (map (partial into []) (remove #(= '("") %) (partition-by #(= "" %) file)))))

;; words
;; takes a single string of words and returns a vector of words
;; pre-conditions: expects a string
;; post-condition: a vector of strings
(defn words [string ] (s/split string #"\s|--"))

;; unpack-paragraph
;; takes paragraph and returns same paragraph as list of words
;; pre-condition: list of strings representing a sentence
;; postcondition: list of strings representing a word
(defn unpack-paragraph [paragraph] (into [] (flatten (map words paragraph))))

(defn unpack-paragraphs [file] (into [] (map unpack-paragraph file)))

(defn unpack-files [files] (into [] (map unpack-paragraphs files)))
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

(defn hyphy-length-test [s] (< (count s) c/column-width))

;; hyphinator
;; takes words longer than column-width and returns the same 
;; word vectorized and hyphenated
(defn hyphy [word] 
  (loop [v [] s word] 
    (if (hyphy-length-test s) 
      (->Accumulator s v) 
       (recur (conj v (str (subs s 0 14) "-")) (subs s 14)))))

(defn pad-string [word-string] 
  (format (str "%-" c/column-width "s") word-string))

;; columnnate
;; takes an unpacked paragraph and returns a list of strings with the length
;; that approaches but does not exceed column-width.
;; word integrity retained.
;; pre-condition: a list of strings representing words
;; postcondition: a list of strings representing column of words
(defn columnnate-paragraph [column-width unformatted-paragraph]
  (let [[first-word & unformatted-remainder] unformatted-paragraph]
    (loop [accum (Accumulator. first-word []) u-p unformatted-remainder]
      (let [[head & rest] u-p]
        (if (empty? head)
          (conj (:accumulated accum) (:in-progress accum))
          (if (> 15 (+ (count (:in-progress accum)) (count head)))
            (let [update-string (str (str (:in-progress accum) '" ") head)
                  accumulated   (:accumulated accum)
                  accumulator   (->Accumulator update-string accumulated)]
              (recur accumulator rest))
            (let [from-hyphy (hyphy head)
                  in-progress (:in-progress from-hyphy)
                  hyphenated  (:accumulated from-hyphy)
                  padded      (pad-string (:in-progress accum))
                  promoted    (conj (:accumulated accum) padded)
                  accum'      (into promoted hyphenated)]
              (recur (->Accumulator in-progress accum') rest))))))))

(defn columnnate-file [column-width unformatted-file] 
  (into [] (map (partial columnnate-paragraph column-width) unformatted-file)))
;;    (fn [paragraph] columnnate-paragraph column-width paragraph)
;;    unformatted-file))

(defn collate-row [row1 row2] (into [] (vector row1 row2)))

;; collate
;; takes a ?list? of column-formatted essays
;; and re-orders them such that each nth line of each essay
;; are grouped together
;; so column line 1 of essay 1 will be grouped with column line 1 of essay 2
;; 3,4,5
;; and so on
;; pre-condition: a list of column-formatted essays
;; postcondition: a list of line-grouped ?lists?
(defn collate [essay1 & rest]
  (into [] 
        (apply map (fn [str1 & rest'] 
                     (into [] (apply vector str1 rest'))) essay1 rest)))
    

(def p_into (partial into []))
;; borderizer
;; takes collated essays and places border character between lines on the same
;; row
;; pre-condition: collated essays --implementation to be determined
;; postcondition: collated essays with border characters
(defn borderizer [collated] (into [] (map (comp p_into (partial interpose " ")) collated)))



(defn rowified [borderized'] 
  (into [] (map (fn [row] (conj row "\n")) borderized')))

(defn test_comp_f [text] (map (comp rowified borderizer)) text)
(defn bordered-rows [text] (map (comp rowified borderizer)) text)
