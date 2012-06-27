(ns cat-face.views.common
  (:require [cat-face.core :as cats])
  (:use [clojure.string :only [join]]
        [noir.core :only [defpartial defpage]]
        [hiccup.page-helpers :only [include-css html5]]))

(defn cat-url [cat]
  (join "/" ["/cats" (cat :id)]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "CatFace, where cats have a face"]
               (include-css "/css/reset.css")
               (include-css "/css/catface.css")]
              [:body
               [:div#wrapper
                 [:h1 "CatFace"]
                 [:p#slogan "Where cats have a face"]
                 content]]))

(def cat-image-sizes { :thumb { :width "100" :height "100" } :full { :width "250" :height "250" } })

(defn cat-image-url [cat]
  "/img/default-cat.jpg") ; TODO

(defpartial cat-image [cat size]
  (let [scale (cat-image-sizes size)]
    [:img (merge scale { :src (cat-image-url cat) })]))

(defpartial cat-link [cat & content]
  [:a {:href (cat-url cat)} content])

(defpartial cat-friend [friend]
  [:li
    (cat-link friend 
      (cat-image friend :thumb)
      [:br]
      (friend :name))])

(defpartial cat-profile [cat]
  [:div#catProfile
    [:div#catPic
      (cat-image cat :full)]
    [:h2 (cat-link cat (cat :name))]
    [:h3 "\"" (cat :destiny) "\""]
    [:p
      [:strong "Current Employment:"]
      (cat :home)]
    [:p
      [:strong "Color:"]
      (cat :color)]
    [:p#colleagues
      [:strong "Colleagues:"]
      [:ul (map cat-friend (cat :friends))]]
    [:p#padd " "]])

(defpage "/" {}
  (layout (cat-profile (cats/rando-cat))))

(defpage "/cats/:profile-id" {:keys [profile-id]}
  (layout (cat-profile (cats/cat-profile (Integer/parseInt profile-id)))))
