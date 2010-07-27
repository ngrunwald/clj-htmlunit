(ns clj-htmlunit.core
  (:import (com.gargoylesoftware.htmlunit WebClient)))

(def *client* (new WebClient))

(defn get-page
  [url]
  (.getPage *client* url))

(defn get-nodes-by-xpath
  [node xpath]
  (.getByXPath node xpath))

(defn get-node-anchors
  [node]
  (get-nodes-by-xpath node "//a"))

(defn get-nodes-anchors
  [nodes]
  (flatten (map #(get-node-anchors %) nodes))) 
