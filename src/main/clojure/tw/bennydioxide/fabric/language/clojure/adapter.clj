(ns tw.bennydioxide.fabric.language.clojure.adapter
  (:import (net.fabricmc.loader.api LanguageAdapter LanguageAdapterException))
  (:require [org.ajoberstar.cljj.function :as cljj.fn])
  (:gen-class
    :name tw.bennydioxide.fabric.language.clojure.ClojureAdapter
    :implements [net.fabricmc.loader.api.LanguageAdapter]))

(defn -create [_this mod value type]
  (.create (LanguageAdapter/getDefault) mod value type))

(defn -create [_ _ s ^Class t]
  (let [method-split (clojure.string/split s #"/")]
    (if (>= (count method-split) 3)
      (throw (LanguageAdapterException. (str "Invalid handle format: " s))))
    (try
      (case (count method-split)
        2 (cljj.fn/sam* t (find-var (symbol s))))
      (catch Exception e 
        (throw (LanguageAdapterException. ^Exception e))))))
