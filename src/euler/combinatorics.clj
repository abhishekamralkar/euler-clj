(ns euler.combinatorics
  (:use euler.math
        clojure.math.numeric-tower))

(defn handshake-product-inc
  [base digits]
  (let [f (inc (or (first digits) 0))]
    (if (<= base f)
      ; Carry
      (let [res (handshake-product-inc base (rest digits))]
        (cons (first res) res))
      ; Increment
      (cons f (rest digits)))))

(defn handshake-product
  "Given a set of items, returns all n-tuples of elements from items,
  degenerate in order. Like cartesian product, but without duplicates: the
  cartesian product would include [1 1 2], [1 2 1], and [2 1 1], but the
  handshake product includes only [2 1 1].

  In some ways, this sequence is related to a generalization of the handshake
  problem and triangle numbers to higher dimensions, but I haven't figured out
  its name yet. I stopped looking when I got to the Riemann Zeta function. :-P

  (handshake-product [0 1 2] 3)
  [0 0 0]
  [1 0 0]
  [2 0 0]
  [1 1 0]
  [2 1 0]
  [2 2 0]

  [1 1 1]
  [2 1 1]
  [2 2 1]

  [2 2 2]
  "
  [items n]
  (let [base (count items)]
    (map (partial map (partial nth items))
         (take-while #(= (count %) n)
               (iterate (partial handshake-product-inc base)
                        (repeat n 0))))))

(defn cartesian-product
  "Lazy cartesian product: returns all n-element lists of elements from items.
  Uses nth on items, which could be expensive!"
  ([items n]
   (let [base (count items)]
     (map (partial map (partial nth items))
          (take (expt base n)
                (iterate (partial inc-in-base base)
                         (repeat n 0)))))))