(defn constant [value]
      (fn [varsAndValues]
          value))

(defn variable [varName]
      (fn [varsAndValues]
          (varsAndValues varName)))
(defn n-ary [operation]
      (fn [& expressions]
          (fn [varsAndValues]
              (apply operation (mapv #(% varsAndValues) expressions)))))

(def negate (n-ary -))
(def add (n-ary +))
(def subtract (n-ary -))
(def multiply (n-ary *))

(defn correctDivision [a b]
      (if (zero? b)
        (if (neg? a)
          Double/NEGATIVE_INFINITY
          Double/POSITIVE_INFINITY)
        (/ a b)))
(def divide (n-ary correctDivision))
(def exp (n-ary #(Math/exp %)))
(def ln (n-ary #(Math/log %)))
(defn parseFunction [rawExpression]
      (let [expression (read-string rawExpression)]
           (cond
             (number? expression) (constant expression)
             (symbol? expression) (variable (str expression))
             :else (let [operatorSymbol (first expression)
                         operandList (rest expression)
                         operatorFunction (case operatorSymbol
                                                + add
                                                - subtract
                                                * multiply
                                                / divide
                                                negate negate
                                                exp exp
                                                ln ln)
                         parsedOperands (map #(parseFunction (str %)) operandList)]
                        (apply operatorFunction parsedOperands)))))


