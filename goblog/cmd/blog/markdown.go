package main

import (
	"context"
	"github.com/yuin/goldmark"
	emoji "github.com/yuin/goldmark-emoji"
	"github.com/yuin/goldmark/extension"
	"github.com/yuin/goldmark/parser"
	"github.com/yuin/goldmark/renderer/html"
	"go.abhg.dev/goldmark/anchor"
	"go.abhg.dev/goldmark/mermaid"
	"os/exec"
	"strings"
)

type dockerCLI struct{}

func (c *dockerCLI) CommandContext(ctx context.Context, args ...string) *exec.Cmd {
	// docker run --rm -u `id -u`:`id -g` -v /path/to/diagrams:/data minlag/mermaid-cli -i diagram.mmd

	dir := args[1]
	baseDir := dir[:strings.LastIndex(dir, "\\")]
	filename := dir[strings.LastIndex(dir, "\\")+1:]

	args[1] = filename
	args[3] = args[3][len(baseDir)+1:]

	path := "docker"
	args = append([]string{"run", "--rm", "-v", baseDir + ":/data", "minlag/mermaid-cli"}, args...)
	return exec.CommandContext(ctx, path, args...)
}

type MarkdownService struct {
	md goldmark.Markdown
}

func NewMarkdownService() *MarkdownService {
	md := goldmark.New(
		goldmark.WithExtensions(extension.GFM,
			extension.DefinitionList,
			extension.Footnote,
			extension.TaskList,
			&mermaid.Extender{
				RenderMode: mermaid.RenderModeServer,
				CLI:        &dockerCLI{},
			},
			&anchor.Extender{},
			emoji.Emoji),
		goldmark.WithParserOptions(
			parser.WithAutoHeadingID(),
		),
		goldmark.WithRendererOptions(
			html.WithUnsafe(),
		),
	)

	return &MarkdownService{
		md: md,
	}
}

func (ms *MarkdownService) Convert(markdown string) (string, error) {
	var sb strings.Builder
	if err := ms.md.Convert([]byte(markdown), &sb); err != nil {
		return "", err
	}
	return sb.String(), nil
}
