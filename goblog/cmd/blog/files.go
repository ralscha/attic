package main

import (
	"compress/gzip"
	"fmt"
	"github.com/andybalholm/brotli"
	"io"
	"os"
	"path/filepath"
)

func siblingPath(filePath, newExt string) string {
	ext := filepath.Ext(filePath)
	base := filePath[:len(filePath)-len(ext)]
	return base + "." + newExt
}

func compressFileWithBrotli(filePath string) error {

	sourceFile, err := os.Open(filePath)
	if err != nil {
		return fmt.Errorf("error opening source file: %w", err)
	}
	defer sourceFile.Close()

	destFilePath := filePath + ".br"
	destFile, err := os.Create(destFilePath)
	if err != nil {
		return fmt.Errorf("error creating destination file: %w", err)
	}
	defer destFile.Close()

	brotliWriter := brotli.NewWriter(destFile)
	defer brotliWriter.Close()

	if _, err := io.Copy(brotliWriter, sourceFile); err != nil {
		return fmt.Errorf("error compressing file with Brotli: %w", err)
	}

	return nil
}

func (app *application) collectAllMarkdownFiles() ([]string, error) {
	var markdownFiles []string

	err := filepath.Walk(app.config.Blog.PostDir, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return fmt.Errorf("error walking directory: %w", err)
		}

		if info.Name() == "DRAFT.md" {
			return nil
		}

		if !info.IsDir() && isMarkdownFile(path) {
			markdownFiles = append(markdownFiles, path)
		}

		return nil
	})

	if err != nil {
		return nil, fmt.Errorf("error walking directory: %w", err)
	}

	return markdownFiles, nil
}

func isMarkdownFile(path string) bool {
	return filepath.Ext(path) == ".md"
}

func isHtmlFile(path string) bool {
	return filepath.Ext(path) == ".html"
}

func compressFileWithGzip(filePath string) error {
	srcFile, err := os.Open(filePath)
	if err != nil {
		return fmt.Errorf("failed to open source file: %w", err)
	}
	defer srcFile.Close()

	destPath := filePath + ".gz"
	destFile, err := os.Create(destPath)
	if err != nil {
		return fmt.Errorf("failed to create destination file: %w", err)
	}
	defer destFile.Close()

	gzWriter, err := gzip.NewWriterLevel(destFile, gzip.BestCompression)
	if err != nil {
		return fmt.Errorf("failed to create gzip writer: %w", err)
	}
	defer gzWriter.Close()

	if _, err := io.Copy(gzWriter, srcFile); err != nil {
		return fmt.Errorf("failed to compress and write file: %w", err)
	}

	if err := gzWriter.Close(); err != nil {
		return fmt.Errorf("failed to close gzip writer: %w", err)
	}

	return nil
}
