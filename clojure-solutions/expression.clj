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

(load-file "proto.clj")

(def evaluate (method :evaluate))
(def toString (method :toString))

(def operands (field :operands))
(def getValue (field :value))
(def getOp (field :op))
(def getStringOp (field :stringOp))

(defn Constant [number] {
                         :value    number
                         :evaluate (fn [expression varsAndValues] (getValue expression))
                         :toString (fn [expression] (str (getValue expression)))})


(defn Variable [name] {
                       :value    name
                       :evaluate (fn [expression varsAndValues] (varsAndValues (getValue expression)))
                       :toString (fn [expression] (getValue expression))})

(defn Operation [op stringOp & args]
      {
       :op       op
       :stringOp stringOp
       :operands args
       :evaluate (fn [expression varsAndValues]
                     (apply op (mapv #(evaluate % varsAndValues) (operands expression))))
       :toString (fn [expression]
                     (str "(" stringOp " " (clojure.string/join " " (mapv toString (operands expression))) ")"))})

(defn Add [a b] (Operation + "+" a b))
(defn Subtract [a b] (Operation - "-" a b))
(defn Multiply [a b] (Operation * "*" a b))
(defn Divide [a b] (Operation correctDivision "/" a b))
(defn Negate [a] (Operation - "negate" a))

(defn Sin [a] (Operation #(Math/sin %) "sin" a))
(defn Cos [a] (Operation #(Math/cos %) "cos" a))

(defn parseObject [rawExpression]
      (let [expression (read-string rawExpression)]
           (cond
             (number? expression) (Constant expression)
             (symbol? expression) (Variable (str expression))
             :else (let [operatorSymbol (first expression)
                         operandList (rest expression)
                         operatorFunction (case operatorSymbol
                                                + Add
                                                - Subtract
                                                * Multiply
                                                / Divide
                                                negate Negate
                                                sin Sin
                                                cos Cos)
                         parsedOperands (map #(parseObject (str %)) operandList)]
                        (apply operatorFunction parsedOperands)))))