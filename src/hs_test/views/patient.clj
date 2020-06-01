(ns hs-test.views.patient
  (:require [hs-test.views.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]
            [ring.util.anti-forgery :as anti-forgery]))

(defn patient-form []
  [:div {:id "patient-form"}
   (form/form-to [:post "/create"]
                 (anti-forgery/anti-forgery-field)
                 [:div {:class "form-group"}
                  [:label {:for "full_name"} "ФИО"]
                  [:input {:name "full_name" :class "form-control" :required "required"}]]
                 [:div {:class "form-group"}
                    [:label {:for "gender"} "Пол"]
                    (form/drop-down {:class "form-control" :required "required"} "gender" [["Мужской" 1], ["Женский" 0]])]
                 [:div {:class "form-group"}
                    [:label {:for "birthday"} "День рождения"]
                    [:input {:type "date" :name "birthday" :class "form-control" :required "required"}]]
                 [:div {:class "form-group"}
                    [:label {:for "address"} "Адресс"]
                    [:input {:name "address" :class "form-control" :required "required"}]]
                 [:div {:class "form-group"}
                    [:label {:for "policy_number"} "Номер полиса ОМС"]
                    [:input {:name "policy_number" :class "form-control" :required "required"}]]
                 (form/submit-button {:class "btn btn-primary" } "Сохранить"))])

(defn patient-edit-form [patient]
  [:div {:id "patient-form"}
   (form/form-to [:post "/edit"]
                 (anti-forgery/anti-forgery-field)
                 [:input {:type "hidden" :name "patient-id" :value (:id patient)}]
                 [:div {:class "form-group"}
                  [:label {:for "full_name"} "ФИО"]
                  [:input {:name "full_name" :class "form-control" :required "required" :value (:full_name patient)}]]
                 [:div {:class "form-group"}
                  [:label {:for "gender"} "Пол"]
                  [:select {:class "form-control" :required "required" :name "gender"}
                   [:option (if (= 1 (:gender patient)) (assoc {:value 1} :selected "selected") {:value 1}) "Мужской"]
                   [:option (if (= 0 (:gender patient)) (assoc {:value 0} :selected "selected") {:value 0}) "Женский" ]
                   ]]
                 [:div {:class "form-group"}
                  [:label {:for "birthday"} "День рождения"]
                  [:input {:type "date" :name "birthday" :class "form-control" :required "required" :value (:birthday patient)}]]
                 [:div {:class "form-group"}
                  [:label {:for "address"} "Адресс"]
                  [:input {:name "address" :class "form-control" :required "required" :value (:address patient)} ]]
                 [:div {:class "form-group"}
                  [:label {:for "policy_number"} "Номер полиса ОМС"]
                  [:input {:name "policy_number" :class "form-control" :required "required" :value (:policy_number patient)} ]]
                 (form/submit-button {:class "btn btn-primary"} "Сохранить"))])

(defn display-patients [patients]
   [:table {:class "table"}
    [:th "#"] [:th "ФИО"] [:th "Пол"] [:th "Дата рождения"] [:th "Адрес"] [:th "Номер полиса ОМС"][:th {:colspan "2" :class "text-center"} "Опции" ]
     (map
      (fn [patient] 
           [:tr
            [:td (:id patient)]
            [:td (:full_name patient)]
            [:td (if (= 1 (:gender patient)) "Мужской" "Женский")] 
            [:td (:birthday patient)] 
            [:td (:address patient)] 
            [:td (:policy_number patient)]
            [:td [:a {:class "btn btn-warning" :href (str "/edit/"(h (:id patient)))} "Редактировать"]]
            [:td [:a {:class "btn btn-danger" :onclick (str "return confirmDelete('/delete/"(h (:id patient))"');") :href (str "/delete/"(h (:id patient)))} "Удалить"]]])
      patients)])

(defn index [patients]
  (layout/common "Health Samurai"
                 [:div {:class "row mb-3"}
                  [:div {:class "col"}
                   [:a {:class "btn btn-success text-white" :href "/new"} "Добавить пациента"]]]
                 (display-patients patients)))

(defn new
  []
  (layout/common "Health Samurai"
            (patient-form)))

(defn edit
  [patient]
  (layout/common "Health Samurai"
                 (patient-edit-form patient)))

