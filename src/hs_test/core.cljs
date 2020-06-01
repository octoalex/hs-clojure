(ns hs-test.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [ajax.core :refer [GET POST raw-response-format]]
            ))


(enable-console-print!)

(defonce api-host (r/atom {:host "http://localhost:3000/"}))
(defonce app-state (r/atom {:title "Health Samurai patients"}))
(defonce patients-state (r/atom nil))
(defonce popup-show (r/atom false))
(defonce current-patient (r/atom nil))

(defn send-form []
  (let [form  (js/FormData. (js/document.getElementById "patient-form"))]
    (let [person {
                 :id (:full_name @form)
                 }]
      (println person))
    (POST(str (:host @api-host) "api/patient") 
      {:response-format (raw-response-format)
       :form-params form
       :keywords? true})
    ))


(defn modal-patient []
  [(fn [shown]
     (if shown
       [:div {:class "container card mt-4 p-4"}
        [:form {:id "patient-form" :method "post" :action "/api/patient"}
         [:input {:type "hidden" :name "patient-id" :defaultValue (:id @current-patient)}]
         [:div {:class "row"}
          [:div {:class "form-group col"}
           [:label {:for "full_name"} "ФИО"]
           [:input {:name "full_name" :class "form-control" :required "required" :defaultValue (:full_name @current-patient)}]]
          [:div {:class "form-group col"}
           [:label {:for "gender"} "Пол"]
           [:select {:class "form-control" :required "required" :name "gender" :defaultValue (:gender @current-patient)}
            [:option {:value 1 } "Мужской"]
            [:option {:value 0 } "Женский"]]]]
         [:div {:class "row"}
          [:div {:class "form-group col"}
           [:label {:for "birthday"} "День рождения"]
           [:input {:type "date" :name "birthday" :class "form-control" :required "required" :defaultValue (:birthday @current-patient)}]]
          [:div {:class "form-group col"}
           [:label {:for "policy_number"} "Номер полиса ОМС"]
           [:input {:name "policy_number" :class "form-control" :required "required" ::defaultValue (:policy_number @current-patient)}]]]
         [:div {:class "form-group"}
          [:label {:for "address"} "Адресс"]
          [:input {:name "address" :class "form-control" :required "required" :defaultValue (:address @current-patient)}]]
         [:div {:class "row"}
          [:button {:class "btn btn-primary mr-3" :type "submit"} "Сохранить"]
          [:button {:class "btn btn-secondary" :on-click #((reset! popup-show false)(reset! current-patient nil))} "Отменить"]]]]
       nil)) @popup-show])



(defn hadler-patients [response]
  (reset! patients-state response))


(defn fetch-patients []
  (GET (str (:host @api-host) "api/patients")
    {:response-format (ajax.core/json-response-format {:keywords? true})
     :handler hadler-patients}))

(defn delete-patient [id]
  (GET (str (:host @api-host) "api/patientd/" id))
  (fetch-patients))

(defn confirm-delete [id]
  (let [res (js/confirm "Хотите удалить?")]
    (if (true? res) (delete-patient id) nil)))

(defn render-patient-tr [patient]
  ^{:key patient}
  [:tr
   [:td (:id patient)]
   [:td (:full_name patient)]
   [:td (if (= 1 (:gender patient)) "Мужской" "Женский")]
   [:td (:birthday patient)]
   [:td (:address patient)]
   [:td (:policy_number patient)]
   [:td [:button {:class "btn btn-warning" :on-click #((reset! current-patient patient) (reset! popup-show true))} "Редактировать"]]
   [:td [:button {:class "btn btn-danger" :on-click #(confirm-delete (:id patient))} "Удалить"]]])

(defn patients-list []
  (fn []
    (fetch-patients)
    [:table {:class "table"}
     [:thead
      [:tr
       [:th "#"]
       [:th "ФИО"]
       [:th "Пол"]
       [:th "Дата рождения"]
       [:th "Адрес"]
       [:th "Номер полиса ОМС"]
       [:th {:colSpan "2" :class "text-center"} "Опции"]]]
     [:tbody
      (if (empty? @patients-state)
        [:tr [:td {:colSpan 8} "Нет данных"]]
        (for [item @patients-state]
          (render-patient-tr item)))]]))


(defn header [message]
  [:div
   [:h1
    [:a {:href "/"} message]]])


(defn app []
  (fn []
     [:div
      [header (:title @app-state)]
      [:div
       [:div [patients-list]]
       [:div {:class "mb-2"}
        [:button {:class "btn btn-success" :on-click #(reset! popup-show true)} "Добавить пациента"]]]
      [modal-patient]]))

(defn init! []
  (rd/render [app] (js/document.getElementById "app")))

(init!)