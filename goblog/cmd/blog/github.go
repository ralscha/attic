package main

import (
	"fmt"
	"github.com/hashicorp/go-retryablehttp"
	"github.com/patrickmn/go-cache"
	"io"
	"net/http"
	"regexp"
	"strconv"
	"strings"
	"time"
)

const (
	gitHubLink    = "https://github.com/"
	gitHubRawLink = "https://raw.githubusercontent.com/"
)

type GitHubCodeService struct {
	httpClient    *http.Client
	siteCache     *cache.Cache
	githubPattern *regexp.Regexp
}

func NewGitHubCodeService() *GitHubCodeService {
	c := cache.New(5*time.Minute, 10*time.Minute)
	pattern := regexp.MustCompile(`\[github:` + regexp.QuoteMeta(gitHubLink) + `((.*?)(?:#L([0-9]+)(?:-L([0-9]+))??)??)(?::(.*?))??\]`)

	retryClient := retryablehttp.NewClient()
	retryClient.RetryMax = 4
	retryClient.Logger = nil
	stdClient := retryClient.StandardClient()
	stdClient.Timeout = 1 * time.Minute

	return &GitHubCodeService{
		httpClient:    stdClient,
		siteCache:     c,
		githubPattern: pattern,
	}
}

func (gcs *GitHubCodeService) InsertCode(markdown string) (string, error) {
	matches := gcs.githubPattern.FindAllStringSubmatchIndex(markdown, -1)
	var sb strings.Builder

	lastIndex := 0
	for _, match := range matches {
		sb.WriteString(markdown[lastIndex:match[0]])

		completeUrl := markdown[match[2]:match[3]]
		url := markdown[match[4]:match[5]]

		var from, to int
		var err error
		if match[6] != -1 {
			from, err = strconv.Atoi(markdown[match[6]:match[7]])
			if err != nil {
				return "", err
			}
		}
		if match[8] != -1 {
			to, err = strconv.Atoi(markdown[match[8]:match[9]])
			if err != nil {
				return "", err
			}
		}

		if from != 0 && to == 0 {
			to = from
		}

		language := ""
		if match[10] != -1 {
			language = markdown[match[10]:match[11]]
		} else {
			lastDot := strings.LastIndex(url, ".")
			if lastDot != -1 {
				language = url[lastDot+1:]
			}
		}

		url = strings.Replace(url, "/blob", "", 1)
		code, err := gcs.fetchCode(gitHubRawLink + url)
		if err != nil {
			return "", err
		}

		if code != "" {
			var replacementCode string
			if from != 0 && to != 0 {
				lines := getLines(code, from, to)
				replacementCode = strings.Join(lines, "\n")
			} else {
				replacementCode = code
			}
			replacementCode = strings.Replace(replacementCode, "\t", "  ", -1)

			fileName := url
			lastSlash := strings.LastIndex(url, "/")
			if lastSlash != -1 {
				fileName = url[lastSlash+1:]
			}

			replacement := fmt.Sprintf("\n```%s\n%s\n```\n<small class=\"gh\">[%s](%s%s)</small>\n",
				language, replacementCode, fileName, gitHubLink, completeUrl)

			sb.WriteString(replacement)
		}

		lastIndex = match[1]
	}
	sb.WriteString(markdown[lastIndex:])

	return sb.String(), nil
}

func getLines(code string, from, to int) []string {
	lines := strings.Split(code, "\n")
	if from <= len(lines) && to <= len(lines) {
		return lines[from-1 : to]
	}
	return lines
}

func (gcs *GitHubCodeService) fetchCode(url string) (string, error) {
	if code, found := gcs.siteCache.Get(url); found {
		return code.(string), nil
	}

	resp, err := gcs.httpClient.Get(url)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return "", fmt.Errorf("failed to fetch code, status code: %d", resp.StatusCode)
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return "", err
	}

	code := string(body)
	gcs.siteCache.Set(url, code, cache.DefaultExpiration)
	return code, nil
}
