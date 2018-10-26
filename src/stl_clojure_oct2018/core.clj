(ns stl-clojure-oct2018.core)

;; https://www.braveclojure.com/multimethods-records-protocols/

(defmulti full-moon-behavior
  (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people and sweat to the oldies"))

(full-moon-behavior {:were-type :wolf
                     :name "Rachel from next door"})
;; => "Rachel from next door will howl and murder"

(full-moon-behavior {:name "Andy the baker"
                     :were-type :simmons})
;; => "Andy the baker will encourage people and sweat to the oldies"

;;;;

(defmethod full-moon-behavior true
  [_]
  "BOO!!!")

(full-moon-behavior
 {:were-type true
  :anything 42})

(:a {:a 1 :b 2})
({:a 1 :b 2} :a)

(:were-type {:were-typee 42})

(defmethod full-moon-behavior nil
  [_]
  "You can't type, sorry :(")

(full-moon-behavior {:weere-type :oops :some 1234})

(full-moon-behavior {:were-type "Skinwalker" :comment "I wouldn't worry about it"})

(defmethod full-moon-behavior :default
  [were-creature]
  (str (:name were-creature) " will stay up all night fantasy footballing"))

(full-moon-behavior {:were-type "Skinwalker"
                     :name "Skinny McSkinnerson, III"
                     :comment "I wouldn't worry about it"})
;;;;

(defmulti types (fn [x y] [(class x) (class y)]))
(defmethod types [java.lang.String java.lang.String]
  [x y]
  "Two strings!")

(types "String 1" "String 2")

(types 12 333)

(defmethod types :default
  [_ _]
  "I don't know, it's some sort of Java stuff")

(types 1234 5678.90)

;;;;

(defmulti varm
  (fn [& args]
    (cond (every? integer? args)
          :all-integers

          (every? float? args)
          :all-floats

          :else
          :default)))

(defmethod varm :all-integers
  [& args]
  {:all-of-type :integers
   :values args})

(varm 1 2 3 4 45)

;;;

(defprotocol The➋Psychodynamics
  "Plumb the inner depths of your data types"
  (thoughts [x] "The data type's innermost thoughts")
  (feelings-about [x] [x y] "Feelings about self or other"))

(extend-type java.lang.String
  The➋Psychodynamics
  (thoughts [x] (str x " thinks, 'Truly, the character defines the data type'"))
  (feelings-about
    ([x] (str x " is longing for a simpler way of life"))
    ([x y] (str x " is envious of " y "'s simpler way of life"))))

(thoughts "a string")
(feelings-about "a string")

;;;; https://clojure.org/reference/multimethods

::rect
(derive ::rect ::shape)
(derive ::square ::rect)
(parents ::rect)
(ancestors ::square)
(descendants ::shape)
(isa? ::square ::shape)
(= ::square ::square)
(isa? ::square ::square)

(derive java.util.Map ::collection)
(derive java.util.Collection ::collection)
(isa? java.util.HashMap ::collection)

(defmulti foo class)
(defmethod foo ::collection [c] :a-collection)
(defmethod foo String [s] :a-string)

(foo [])
;; => :a-collection

(foo (java.util.HashMap.))
;; => :a-collection

(foo "bar")
;; => :a-string


(defmulti foo class)
(defmethod foo #{java.util.Map java.util.Collection java.util.HashMap} [c] :a-collection)
(defmethod foo String [s] :a-string)

;;;; https://www.braveclojure.com/multimethods-records-protocols/ - Records

(defrecord WereWolf [name title])

(WereWolf. "David" "London Tourist")
;; => #were_records.WereWolf{:name "David", :title "London Tourist"}

(->WereWolf "Jacob" "Lead Shirt Discarder")
;; => #were_records.WereWolf{:name "Jacob", :title "Lead Shirt Discarder"}

(map->WereWolf {:name "Lucian" :title "CEO of Melodrama"})
;; => #were_records.WereWolf{:name "Lucian", :title "CEO of Melodrama"}

(:name (map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))

(into {} (map->WereWolf {:name "Lucian" :title "CEO of Melodrama"}))

#stl_clojure_oct2018.core.WereWolf{:name "David", :title "London Tourist"}
