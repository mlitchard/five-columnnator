(ns five-columnnator.constants)

(def file-names '( "files/james1.txt"
                   "files/james2.txt"
                   "files/james3.txt"
                   "files/james4.txt"
                   "files/james5.txt"))

(def line-length 80)
(def number-of-columns 5)
(def border-size 1)

(def column-width (- (/ line-length number-of-columns) border-size))


