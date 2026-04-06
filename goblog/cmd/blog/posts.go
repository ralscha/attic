package main

import (
	"fmt"
	"gopkg.in/yaml.v3"
	"os"
	"path/filepath"
	"strings"
)

func (app *application) updatePosts() error {
	err := app.pullPosts()
	if err != nil {
		return fmt.Errorf("failed to pull posts: %w", err)
	}

	convertChanged, err := app.convertChangedMarkdowns()
	if err != nil {
		return fmt.Errorf("failed to convert markdowns: %w", err)
	}

	cleanupChanged, err := app.cleanup()
	if err != nil {
		return fmt.Errorf("failed to cleanup: %w", err)
	}

	if convertChanged || cleanupChanged {
		postMetadatas, err := app.readAllMetadata()
		if err != nil {
			return err
		}

		err = app.writeFeeds(postMetadatas)
		if err != nil {
			return err
		}

		err = app.searchService.DeleteAll()
		if err != nil {
			return err
		}

		err = app.searchService.IndexPosts(postMetadatas)
		if err != nil {
			return err
		}
	}

	return nil
}

func (app *application) readAllMetadata() ([]PostMetadata, error) {
	var postMetadatas []PostMetadata

	markdownFiles, err := app.collectAllMarkdownFiles()
	if err != nil {
		return nil, fmt.Errorf("failed to collect markdown files: %w", err)
	}

	for _, markdownFile := range markdownFiles {
		content, err := os.ReadFile(markdownFile)
		if err != nil {
			return nil, fmt.Errorf("failed to read markdown file: %w", err)
		}

		matcher := headerPattern.FindStringSubmatch(string(content))
		if matcher == nil {
			return nil, fmt.Errorf("content does not match header pattern")
		}

		headerString := matcher[1]
		header := PostHeader{}
		if err := yaml.Unmarshal([]byte(headerString), &header); err != nil {
			return nil, fmt.Errorf("failed to unmarshal header: %w", err)
		}

		// ignore drafts
		if header.Draft {
			continue
		}

		url, err := filepath.Rel(app.config.Blog.PostDir, siblingPath(markdownFile, "html"))
		if err != nil {
			return nil, fmt.Errorf("failed to get relative path: %w", err)
		}

		url = filepath.ToSlash(url)
		feedbackUrl := strings.Replace(url, "/", "-", -1)

		postMetadata := PostMetadata{
			Title:        header.Title,
			Url:          url,
			FeedbackUrl:  feedbackUrl,
			Published:    header.Published,
			Updated:      header.Updated,
			Summary:      header.Summary,
			Tags:         header.Tags,
			Markdown:     matcher[2],
			MarkdownFile: markdownFile,
			Draft:        header.Draft,
		}

		postMetadatas = append(postMetadatas, postMetadata)
	}

	return postMetadatas, nil

}

func (app *application) cleanup() (bool, error) {
	var htmlFiles []string

	err := filepath.Walk(app.config.Blog.PostDir, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return fmt.Errorf("error walking directory: %w", err)
		}

		if !info.IsDir() && isHtmlFile(path) {
			htmlFiles = append(htmlFiles, path)
		}

		return nil
	})

	if err != nil {
		return false, fmt.Errorf("error walking directory: %w", err)
	}

	changed := false
	for _, htmlFile := range htmlFiles {
		markdownFile := siblingPath(htmlFile, "md")
		_, err := os.Stat(markdownFile)
		if err != nil {
			if !os.IsNotExist(err) {
				return false, fmt.Errorf("failed to get markdown file info: %w", err)
			}

			changed = true

			if err := os.Remove(htmlFile); err != nil {
				if !os.IsNotExist(err) {
					return false, fmt.Errorf("failed to remove html file: %w", err)
				}
			}

			if err := os.Remove(htmlFile + ".gz"); err != nil {
				if !os.IsNotExist(err) {
					return false, fmt.Errorf("failed to remove html file: %w", err)
				}
			}

			if err := os.Remove(htmlFile + ".br"); err != nil {
				if !os.IsNotExist(err) {
					return false, fmt.Errorf("failed to remove html file: %w", err)
				}
			}
		}
	}
	return changed, nil
}
