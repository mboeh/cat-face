(ns cat-face.test.core
  (:use [cat-face.core])
  (:use [clojure.test]))

(deftest profile-structure
  (testing "Profile structure"
    (let [cat (rando-cat)]
      (is (instance? java.lang.Long (cat :id)))
      (is (instance? String (cat :name)))
      (is (instance? String (cat :home)))
      (is (instance? String (cat :color)))
      (is (instance? String (cat :destiny)))
      (is (instance? clojure.lang.ISeq (cat :friends))))))

(deftest repeatability
  (testing "Repeatability"
    (let [cat-id 1234
          ids-only     (partial map :id)
          safe-friends #(update-in % [:friends] ids-only)
          same-cats (map safe-friends (repeatedly 10 #(cat-profile cat-id)))
          diff-cats (map safe-friends (repeatedly 10 rando-cat))]
      (is (= 1  (count (distinct same-cats))))
      (is (= 10 (count (distinct diff-cats)))))))
