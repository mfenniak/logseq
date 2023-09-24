(ns logseq.shui.button.v2
  (:require 
    [clojure.string :as str]
    [logseq.shui.util :as util]
    [rum.core :as rum]
    [logseq.shui.icon.v2 :as icon]
    [clojure.string :as string]))

(rum/defc root < rum/reactive
  [{:keys [theme color text depth size icon interactive shortcut tiled on-click muted class] 
    :or {theme :color depth 1 size :md interactive true muted false class ""}} context]
  (let [color-string (or (some-> color name) (some-> context :state rum/react :ui/radix-color name) "custom")
        theme-class (str "shui__button-theme-" (name theme))
        depth-class (when-not (= :text theme) (str "shui__button-depth-" depth)) 
        color-class (str "shui__button-color-" color-string) 
        muted-class (when muted "shui__button-muted")
        size-class  (str "shui__button-size-" (name size))
        tiled-class (when tiled "shui__button-tiled")]
    [:button.shui__button {:class (str theme-class " " depth-class " " color-class " " size-class " " tiled-class " " muted-class " " class) 
                           :on-click (when on-click on-click)}
     (if-not tiled text
       (for [[index tile] (map-indexed vector (rest (string/split text #"")))]
         [:<> 
          (when (< 0 index) 
            [:div.shui__button__tile-separator])
          [:div.shui__button__tile {:class ""} tile]]))
        
     (when icon 
       (icon/root icon))
     (when (not-empty shortcut)
       (for [key shortcut]
         [:div.shui__button-shortcut-key
          (case key 
            "cmd" (icon/root "command") 
            "shift" (icon/root "arrow-big-top")
            "return" (icon/root "arrow-back")
            "esc" [:div.tracking-tightest {:style {:transform "scaleX(0.8) scaleY(1.2) " 
                                                   :font-size "0.5rem" 
                                                   :font-weight "500"}} "ESC"]
            key)]))]))
          
     
   
