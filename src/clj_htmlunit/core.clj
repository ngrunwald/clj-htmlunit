(ns clj-htmlunit.core
  (:import (com.gargoylesoftware.htmlunit WebClient BrowserVersion))
  (:use [clojure.string :only [upper-case]]))

(def *client*)

(defn make-client
  ([]
     (make-client (.getNickname (BrowserVersion/getDefault))))
  ([version]
     (let [v (upper-case version)]
       (new WebClient
	    (cond
	     (= v "IE7") BrowserVersion/INTERNET_EXPLORER_7
	     (= v "IE6") BrowserVersion/INTERNET_EXPLORER_6
	     (= v "IE8") BrowserVersion/INTERNET_EXPLORER_8
	     (= v "FF3") BrowserVersion/FIREFOX_3
	     (= v "FF3.6") BrowserVersion/FIREFOX_3_6
	     :else (throw (Exception. (str "Browser " version " is unknown"))))))))

(defn get-page
  [client url]
  (.getPage client url))

(defn get-nodes-by-xpath
  [node xpath]
  (.getByXPath node xpath))

(defn get-first-node-by-xpath
  [node xpath]
  (.getFirstByXPath node xpath))

(defn get-node-anchors
  [node]
  (get-nodes-by-xpath node "//a"))

(defn get-nodes-anchors
  [nodes]
  (flatten (map #(get-node-anchors %) nodes))) 

(defn get-node-attributes
  [node]
  (let [attrs (.getAttributes node)
        length (.getLength attrs)
	items (map #(.item attrs %) (range 0 length))
	hash (reduce #(merge %1 {(keyword (.getName %2)) (.getValue %2)}) {} items)]
    hash))