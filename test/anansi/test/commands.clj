(ns anansi.test.commands
  (:use [anansi.receptor])
  (:use [anansi.server])
  (:use [anansi.user])
  (:use [anansi.commands] :reload)
  (:use [clojure.test]
        [clojure.contrib.io :only [writer]]))

(defmacro def-command-test [name & body]
  `(deftest ~name
     (binding [*server-receptor* (create-membrane)
               *user-name* "eric"]
       ~@body)))

(def-command-test send-test
  (testing "sending a message"
    (is (= "created" (send-signal "{:to \"server.conjure\", :body {:name \"object2\"}}")))
    (is (= "I got 'message' from: eric.?" (send-signal "{:to \"object2.ping\", :body \"message\"}")))
    ))

(deftest help-test
  (testing "getting help"
    (is (= "exit: Terminate connection with the server\nsend: Send a signal to a receptor.\nhelp: Show available commands and what they do."
           (help)))))

(def-command-test exit-test
  (testing "exiting"
    (is (= "Goodbye eric!"
           (exit)))))

(def-command-test execute-test
  ;; Silence the error!
  (testing "executing a no argument command"
    (is (= "Goodbye eric!"
           (execute "exit"))))
  (testing "executing a non existent command"
    (is (= "Unknown command: 'fish'. Try help for a list of commands."
           (execute "fish"))))
  (testing "executing a multi argument command"
    (is (= "created"
           (execute "send {:to \"server.conjure\", :body {:name \"object2\"}}"))))
  (binding [*err* (java.io.PrintWriter. (writer "/dev/null"))]
    (testing "executing command that throws an error"
      (is (= (execute "send {:to \"zippy.?\", :body \"some body\"}")
             "ERROR: java.lang.RuntimeException: Receptor 'zippy' not found"
             )))))
