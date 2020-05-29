(ns hs-test.views.layout
  (:require [hiccup.page :as h]))

(defn common [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (h/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css")]
   [:body
    [:div {:id "header"}
     [:a {:href "/"} [:h1 {:class "container"} "HealthSamurai"]]
     ]
    [:div {:id "content" :class "container mt-5"} body]
    (h/include-js "/js/main.js")]))


(defn not-found-page []
  (common "Page Not Found"
          [:div {:id "four-oh-four"}
           [:h1 "Запрашиваемая страница не найдена"]]))