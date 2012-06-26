(ns cat-face.server
  (:use [cat-face.core])
  (:require [noir.server :as server]))

(server/load-views "src/cat_face/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'cat-face})))

