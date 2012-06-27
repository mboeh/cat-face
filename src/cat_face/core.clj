(ns cat-face.core
  [:import [java.util Random]])

(def max-cat 100000)

(defn resource-seq [filename] 
  (line-seq
    (clojure.java.io/reader 
      (.getFile (clojure.java.io/resource filename)))))

(def cat-names (resource-seq "cat-names.txt"))
(def cat-homes (resource-seq "cat-homes.txt"))

(def cat-colors
  ["black", "white", "tuxedo", "calico", "tortoiseshell", "red tabby", "brown tabby", "siamese"])

(def cat-destiny
  (lazy-cat 
    (repeat 25 "Just a cat")
    (resource-seq "cat-destinies.txt")))

(defn rand-gen [seed]
  (Random. seed))

(defn rand-with
  ([rands] (. rands nextInt))
  ([rands n] (. rands nextInt n)))

(defn rand-int-with [rands n]
  (int (rand-with rands n)))

(defn rand-nth-with [rands coll]
  (nth coll (rand-int-with rands (count coll))))

(defn cat-profile [profile-id]
  (let [rands (rand-gen profile-id)
        pick-one #(rand-nth-with rands %)
        rand-int-l (partial rand-int-with rands max-cat)]
    { :id      profile-id
      :name    (pick-one cat-names)
      :home    (pick-one cat-homes)
      :color   (pick-one cat-colors)
      :destiny (pick-one cat-destiny)
      :friends (map cat-profile (distinct (repeatedly 10 rand-int-l)))}))

(defn cat-friend [profile-id n]
  (nth ((cat-profile profile-id) :friends) n))

(defn rando-cat []
  (cat-profile (rand-int max-cat)))
