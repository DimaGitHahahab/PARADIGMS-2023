(defn coordinateVectFunc [operation]
      (fn [a b]
          (mapv operation a b)))

(def v+ (coordinateVectFunc +))
(def v- (coordinateVectFunc -))
(def v* (coordinateVectFunc *))
(def vd (coordinateVectFunc /))

(defn vect [[a_x a_y a_z] [b_x b_y b_z]]
      (mapv - [(* a_y b_z) (* a_z b_x) (* a_x b_y)]
            [(* a_z b_y) (* a_x b_z) (* a_y b_x)]))

(defn scalar [a b]
      (reduce + (mapv * a b)))

(defn funcToVect [operation]
      (fn [a b]
          (mapv #(operation % b) a)))

(def v*s (funcToVect *))

(def m+ (coordinateVectFunc v+))
(def m- (coordinateVectFunc v-))
(def m* (coordinateVectFunc v*))
(def md (coordinateVectFunc vd))

(def m*s (funcToVect v*s))

(defn m*v [matrix vector]
      (mapv #(scalar % vector) matrix))

(defn transpose [matrix]
      (apply mapv vector matrix))

(defn m*m [matrixA matrixB]
      (let [transposedB (transpose matrixB)]
           (mapv #(m*v transposedB %) matrixA)))

(defn varLengthFunc [a b operation]
      (cond
        (vector? a) (mapv #(varLengthFunc %1 %2 operation) a b)
        (vector? b) (mapv #(varLengthFunc %1 %2 operation) a b)
        :else (operation a b)))

(defn s+ [a b] ((varLengthFunc a b +)))
(defn s- [a b] (partial (varLengthFunc a b -)))
(defn s* [a b] (partial (varLengthFunc a b *)))
(defn sd [a b] (partial (varLengthFunc a b /)))

