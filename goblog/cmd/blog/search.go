package main

import (
	"github.com/meilisearch/meilisearch-go"
	"golang.org/x/exp/slices"
	"strconv"
	"time"
)

const (
	IndexName = "posts"
)

type SearchService struct {
	client         *meilisearch.Client
	publishedYears []int
}

func NewSearchService(config Config) (*SearchService, error) {
	c := meilisearch.NewClient(meilisearch.ClientConfig{
		Host:   config.Meilisearch.Host,
		APIKey: config.Meilisearch.Key,
	})

	_, err := c.Index(IndexName).UpdateFilterableAttributes(&[]string{"publishedYear", "tags"})
	if err != nil {
		return nil, err
	}

	var publishedYears []int

	request := meilisearch.DocumentsQuery{
		Fields: []string{"publishedYear"},
		Limit:  9_000,
	}
	var response meilisearch.DocumentsResult

	err = c.Index(IndexName).GetDocuments(&request, &response)
	if err != nil {
		return nil, err
	}
	for _, doc := range response.Results {
		publishedYear := int(doc["publishedYear"].(float64))
		publishedYears = append(publishedYears, publishedYear)
	}
	publishedYears = unique(publishedYears)
	slices.SortFunc(publishedYears, func(i, j int) int {
		return j - i
	})

	return &SearchService{
		client:         c,
		publishedYears: publishedYears,
	}, nil
}

func unique(intSlice []int) []int {
	keys := make(map[int]bool)
	var list []int
	for _, entry := range intSlice {
		if _, value := keys[entry]; !value {
			keys[entry] = true
			list = append(list, entry)
		}
	}
	return list
}

func (s *SearchService) DeleteAll() error {
	_, err := s.client.Index(IndexName).DeleteAllDocuments()
	if err != nil {
		return err
	}

	return nil
}

func (s *SearchService) IndexPosts(posts []PostMetadata) error {
	documents := make([]map[string]any, len(posts))
	for i, post := range posts {
		publishedTime, err := time.Parse(time.RFC3339, post.Published)
		if err != nil {
			return err
		}
		publishedYear := publishedTime.Year()

		var updatedSeconds int64 = 0
		if post.Updated != "" {
			updatedTime, err := time.Parse(time.RFC3339, post.Updated)
			if err != nil {
				return err
			}
			updatedSeconds = updatedTime.Unix()
		}

		documents[i] = map[string]any{
			"id":            i,
			"body":          post.Markdown,
			"summary":       post.Summary,
			"title":         post.Title,
			"url":           post.Url,
			"publishedTs":   publishedTime.Unix(),
			"updatedTs":     updatedSeconds,
			"publishedYear": publishedYear,
			"tags":          post.Tags,
		}
	}
	_, err := s.client.Index(IndexName).AddDocuments(documents)
	if err != nil {
		return err
	}

	return nil
}

var attributesToRetrieve = []string{
	"title",
	"summary",
	"url",
	"publishedTs",
	"updatedTs",
	"tags",
}

func (s *SearchService) SearchPostsOfYear(year int) ([]PostMetadata, error) {
	request := meilisearch.SearchRequest{
		Filter:               "publishedYear=" + strconv.Itoa(year),
		Limit:                9_000,
		AttributesToRetrieve: attributesToRetrieve,
	}
	response, err := s.client.Index(IndexName).Search("", &request)
	if err != nil {
		return nil, err
	}

	posts := s.mapToPostMetadata(response)
	return posts, nil
}

func (s *SearchService) SearchWithTag(tag string) ([]PostMetadata, error) {
	request := meilisearch.SearchRequest{
		Filter:               "tags=" + tag,
		Limit:                9_000,
		AttributesToRetrieve: attributesToRetrieve,
	}
	response, err := s.client.Index(IndexName).Search("", &request)
	if err != nil {
		return nil, err
	}

	posts := s.mapToPostMetadata(response)
	return posts, nil
}

func (s *SearchService) Search(query string) ([]PostMetadata, error) {
	request := meilisearch.SearchRequest{
		Limit:                9_000,
		AttributesToRetrieve: attributesToRetrieve,
	}
	response, err := s.client.Index(IndexName).Search(query, &request)
	if err != nil {
		return nil, err
	}

	posts := s.mapToPostMetadata(response)
	return posts, nil
}

func (s *SearchService) mapToPostMetadata(response *meilisearch.SearchResponse) []PostMetadata {
	var posts []PostMetadata
	for _, hit := range response.Hits {
		hitMap := hit.(map[string]any)

		published := ""
		updated := ""
		if hitMap["publishedTs"] != nil {
			publishedTime := time.Unix(int64(hitMap["publishedTs"].(float64)), 0)
			published = publishedTime.Format("2. January 2006")
		}
		if hitMap["updatedTs"] != nil && hitMap["updatedTs"].(float64) != 0 {
			updatedTime := time.Unix(int64(hitMap["updatedTs"].(float64)), 0)
			updated = updatedTime.Format("2. January 2006")
		}

		var tags []string
		if hitMap["tags"] != nil {
			tagsList := hitMap["tags"].([]interface{})
			for _, tag := range tagsList {
				tags = append(tags, tag.(string))
			}
		}

		posts = append(posts, PostMetadata{
			Title:     hitMap["title"].(string),
			Summary:   hitMap["summary"].(string),
			Url:       hitMap["url"].(string),
			Published: published,
			Updated:   updated,
			Tags:      tags,
		})
	}
	return posts
}
