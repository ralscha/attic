package main

import "golang.org/x/exp/slices"

type Post struct {
	Title       string
	Html        string
	Published   string
	Updated     string
	FeedbackUrl string
	Tags        []string
}

type URLCheck struct {
	Url      string
	Post     string
	Status   int
	Location string
}

type YearNavigation struct {
	Year    int
	Current bool
}

func sortYearNavigation(years []YearNavigation) {
	slices.SortFunc(years, func(i, j YearNavigation) int {
		return i.Year - j.Year
	})
}

type PostMetadata struct {
	Draft        bool
	Url          string
	Markdown     string
	MarkdownFile string
	FeedbackUrl  string
	Title        string
	Tags         []string
	Published    string
	Updated      string
	Summary      string
}

type SearchResults struct {
	Posts []PostMetadata
	Query string
	Years []YearNavigation
}

type PostHeader struct {
	Title     string
	Tags      []string
	Draft     bool
	Summary   string
	Published string
	Updated   string
}
