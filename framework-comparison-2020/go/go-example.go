package main

import (
	"encoding/json"
	"log"
	"net/http"
	"strings"
	"time"
)

type HelloMessage struct {
	Msg string `json:"msg"`
	Ts  int64  `json:"ts"`
}

func jsonHandler(w http.ResponseWriter, r *http.Request) {
	name := strings.TrimPrefix(r.URL.Path, "/helloJSON/")
	helloMessage := HelloMessage{Msg: sayHello(name), Ts: time.Now().Unix()}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(helloMessage)
}

func sayHello(name string) string {
	return "Hello " + name
}

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/helloJSON/", jsonHandler)

	server := &http.Server{
		Addr:         ":8080",
		Handler:      mux,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
		IdleTimeout:  30 * time.Second,
	}

	err := server.ListenAndServe()
	log.Fatal(err)
}
