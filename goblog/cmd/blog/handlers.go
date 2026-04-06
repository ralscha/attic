package main

import (
	"github.com/go-chi/chi/v5"
	"github.com/google/go-github/v57/github"
	"net/http"
	"strconv"
	"time"
)

func (app *application) githubCallbackHandler(w http.ResponseWriter, r *http.Request) {

	payload, err := github.ValidatePayload(r, []byte(app.config.Github.WebhookSecret))
	if err != nil {
		app.reportServerError(r, err)
		return
	}
	event, err := github.ParseWebHook(github.WebHookType(r), payload)
	if err != nil {
		app.reportServerError(r, err)
		return
	}

	_, ok := event.(*github.PushEvent)
	if ok {
		err := app.updatePosts()
		if err != nil {
			app.reportServerError(r, err)
		}
	}

	w.WriteHeader(http.StatusNoContent)
}

func (app *application) submitFeedbackHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/html; charset=utf-8")

	url := r.FormValue("url")
	token := r.FormValue("token")
	feedback := r.FormValue("feedback")
	email := r.FormValue("email")
	name := r.FormValue("name")

	if feedback != "" && url != "" && token != "" && name == "" {
		numbers, err := app.hashId.DecodeWithError(token)
		if err != nil {
			app.reportServerError(r, err)
			return
		}
		twoSecondsAgo := time.Now().Unix() - 2
		if len(numbers) == 1 && int64(numbers[0]) < twoSecondsAgo {
			app.backgroundTask(r, func() error {
				err := app.mailer.SendFeedback(email, url, feedback)
				if err != nil {
					app.reportServerError(r, err)
				}
				return nil
			})
		}
	}

	err := app.templates["feedback_ok"].Execute(w, nil)
	if err != nil {
		app.reportServerError(r, err)
		return
	}
}

func (app *application) feedbackHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/html; charset=utf-8")

	url := chi.URLParam(r, "url")
	token, err := app.hashId.Encode([]int{int(time.Now().Unix())})
	if err != nil {
		app.reportServerError(r, err)
		return
	}

	err = app.templates["feedback"].Execute(w, struct {
		PostUrl string
		Token   string
	}{
		PostUrl: url,
		Token:   token,
	})
	if err != nil {
		app.reportServerError(r, err)
		return
	}
}

func (app *application) indexHandler(w http.ResponseWriter, r *http.Request) {
	tag := r.FormValue("tag")
	query := r.FormValue("query")
	yearString := r.FormValue("year")

	year := -1
	if yearString != "" {
		var err error
		year, err = strconv.Atoi(yearString)
		if err != nil {
			app.reportServerError(r, err)
			return
		}
	}

	publishedYears := app.searchService.publishedYears
	data := SearchResults{}

	if tag != "" {
		posts, err := app.searchService.SearchWithTag(tag)
		if err != nil {
			app.reportServerError(r, err)
			return
		}

		yearNavigation := make([]YearNavigation, len(publishedYears))
		for i, year := range publishedYears {
			yearNavigation[i] = YearNavigation{
				Year:    year,
				Current: false,
			}
		}

		data.Posts = posts
		data.Query = "tags:" + tag
		data.Years = yearNavigation

	} else if query != "" {
		posts, err := app.searchService.Search(query)
		if err != nil {
			app.reportServerError(r, err)
			return
		}
		yearNavigation := make([]YearNavigation, len(publishedYears))
		for i, year := range publishedYears {
			yearNavigation[i] = YearNavigation{
				Year:    year,
				Current: false,
			}
		}

		data.Posts = posts
		data.Query = query
		data.Years = yearNavigation
	} else if year != -1 {
		posts, err := app.searchService.SearchPostsOfYear(year)
		if err != nil {
			app.reportServerError(r, err)
			return
		}

		yearNavigation := make([]YearNavigation, len(publishedYears))
		for i, y := range publishedYears {
			yearNavigation[i] = YearNavigation{
				Year:    y,
				Current: y == year,
			}
		}

		data.Posts = posts
		data.Years = yearNavigation
	} else {
		currentYear := time.Now().Year()
		posts, err := app.searchService.SearchPostsOfYear(currentYear)
		if err != nil {
			app.reportServerError(r, err)
			return
		}

		if len(posts) == 0 {
			currentYear = currentYear - 1
			posts, err = app.searchService.SearchPostsOfYear(currentYear)
			if err != nil {
				app.reportServerError(r, err)
				return
			}
		}

		yearNavigation := make([]YearNavigation, len(publishedYears))
		for i, y := range publishedYears {
			yearNavigation[i] = YearNavigation{
				Year:    y,
				Current: y == currentYear,
			}
		}

		data.Posts = posts
		data.Years = yearNavigation
	}

	w.Header().Set("Content-Type", "text/html; charset=utf-8")

	err := app.templates["index"].Execute(w, data)
	if err != nil {
		app.reportServerError(r, err)
		return
	}
}
