package main

import (
	"net/http"
	"time"

	"github.com/labstack/echo/v4"
)

type HelloMessage struct {
	Msg string `json:"msg"`
	Ts  int64  `json:"ts"`
}

func jsonHandler(c echo.Context) error {
	helloMessage := HelloMessage{Msg: sayHello(c.Param("name")), Ts: time.Now().Unix()}
	return c.JSON(http.StatusOK, helloMessage)
}

func sayHello(name string) string {
	return "Hello " + name
}

func main() {
	e := echo.New()
	e.Debug = false
	e.HideBanner = true
	e.Server.ReadTimeout = 10 * time.Second
	e.Server.WriteTimeout = 10 * time.Second
	e.Server.IdleTimeout = 30 * time.Second

	e.GET("/helloJSON/:name", jsonHandler)

	e.Logger.Fatal(e.Start(":8080"))
}
