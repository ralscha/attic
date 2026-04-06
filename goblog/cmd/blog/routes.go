package main

import (
	"github.com/go-chi/chi/v5/middleware"
	"net/http"

	"github.com/go-chi/chi/v5"
)

func (app *application) routes() http.Handler {
	mux := chi.NewRouter()
	mux.Use(middleware.RealIP)

	mux.NotFound(app.notFound)
	mux.MethodNotAllowed(app.methodNotAllowed)
	mux.Use(app.recoverPanic)
	mux.Use(middleware.NoCache)

	mux.Post("/githubCallback", app.githubCallbackHandler)
	mux.Post("/submitFeedback", app.submitFeedbackHandler)
	mux.Get("/feedback/{url}", app.feedbackHandler)
	mux.Get("/", app.indexHandler)
	mux.Get("/index.html", app.indexHandler)

	return mux
}
