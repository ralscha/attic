package main

import (
	"fmt"
	"goblog/assets"
	"golang.org/x/net/html"
	"net/http"
	"os"
	"path/filepath"
	"strings"
	"text/template"
	"time"
)

func (app *application) checkBrokenLinks() {
	fmt.Println("Checking broken links...")

	ignoreUrlsFile, err := os.ReadFile(app.config.Blog.PostDir + "/ignore-urls.txt")
	if err != nil {
		app.logger.Error(err.Error())
		return
	}

	ignoreUrls := strings.Split(string(ignoreUrlsFile), "\n")
	for i, url := range ignoreUrls {
		ignoreUrls[i] = strings.ToLower(url)
	}

	posts, err := app.readAllMetadata()
	if err != nil {
		app.logger.Error(err.Error())
		return
	}

	httpClient := http.Client{
		Timeout: 30 * time.Second,
	}

	checkedUrls := make(map[string]struct{})
	var urlChecks []URLCheck

	for _, post := range posts {
		htmlFile := siblingPath(post.MarkdownFile, "html")
		htmlContent, err := os.ReadFile(htmlFile)
		if err != nil {
			app.logger.Error(err.Error())
			continue
		}

		links, err := collectLinks(string(htmlContent))
		if err != nil {
			app.logger.Error(err.Error())
			continue
		}

		for _, link := range links {
			ignore := false
			linkLower := strings.ToLower(link)
			for _, ignoreUrl := range ignoreUrls {
				if strings.HasPrefix(linkLower, ignoreUrl) {
					ignore = true
					break
				}
			}
			if ignore {
				continue
			}

			if strings.HasPrefix(link, "http://") || strings.HasPrefix(link, "https://") {
				cleanedUpLink := removeFragment(link)
				if _, ok := checkedUrls[cleanedUpLink]; ok {
					continue
				}

				resp, err := httpClient.Get(cleanedUpLink)
				if err != nil {
					app.logger.Error(err.Error())
					continue
				}
				checkedUrls[cleanedUpLink] = struct{}{}

				if resp.StatusCode >= 200 && resp.StatusCode <= 299 {
					continue
				}

				if resp.StatusCode == 429 {
					continue
				}

				if resp.StatusCode >= 300 && resp.StatusCode <= 399 {
					location := resp.Header.Get("Location")
					urlCheck := URLCheck{
						Url:      link,
						Post:     post.Url,
						Status:   resp.StatusCode,
						Location: location,
					}
					urlChecks = append(urlChecks, urlCheck)
				} else {
					urlCheck := URLCheck{
						Url:    link,
						Post:   post.Url,
						Status: resp.StatusCode,
					}
					urlChecks = append(urlChecks, urlCheck)
				}
			}
		}
	}

	if len(urlChecks) > 0 {
		fmt.Println("Broken links found:")
		for _, urlCheck := range urlChecks {
			fmt.Printf("  %s: %d\n", urlCheck.Url, urlCheck.Status)
		}
	} else {
		fmt.Println("No broken links found.")
	}

	tmpl := template.Must(template.ParseFS(assets.EmbeddedHtml, "html/urlcheck.tmpl"))

	var output strings.Builder
	err = tmpl.Execute(&output, urlChecks)
	if err != nil {
		app.logger.Error(err.Error())
		return
	}

	reportFile := app.config.Blog.PostDir + "/report/urlcheck.html"
	err = os.MkdirAll(filepath.Dir(reportFile), os.ModePerm)
	if err != nil {
		app.logger.Error(err.Error())
		return
	}

	err = os.WriteFile(reportFile, []byte(output.String()), 0644)
	if err != nil {
		app.logger.Error(err.Error())
		return
	}

}

func collectLinks(htmlContent string) ([]string, error) {
	doc, err := html.Parse(strings.NewReader(htmlContent))
	if err != nil {
		return nil, err
	}
	var links []string
	var f func(*html.Node)
	f = func(n *html.Node) {
		if n.Type == html.ElementNode && n.Data == "a" {
			for _, a := range n.Attr {
				if a.Key == "href" {
					links = append(links, a.Val)
				}
			}
		}
		for c := n.FirstChild; c != nil; c = c.NextSibling {
			f(c)
		}
	}
	f(doc)
	return links, nil
}

func removeFragment(url string) string {
	pos := strings.Index(url, "#")
	if pos != -1 {
		return url[:pos]
	}
	return url
}
