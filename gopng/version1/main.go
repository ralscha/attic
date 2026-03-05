package main

import (
	"image"
	"image/color"
	"image/png"
	"os"
	"strings"

	"golang.org/x/image/font"
	"golang.org/x/image/font/basicfont"
	"golang.org/x/image/math/fixed"
)

func main() {
	if len(os.Args) != 3 {
		panic("Usage: text2png <input.txt> <output.png>")
	}

	// Read input file
	inputFile := os.Args[1]
	outputFile := os.Args[2]

	data, err := os.ReadFile(inputFile)
	if err != nil {
		panic(err)
	}

	// Process text and split into lines
	text := string(data)
	text = strings.ReplaceAll(text, "\r\n", "\n")
	lines := strings.Split(text, "\n")
	for i := range lines {
		lines[i] = strings.TrimSuffix(lines[i], "\r")
	}

	// Handle empty input
	if len(lines) == 0 {
		lines = []string{""}
	}

	// Calculate image dimensions
	maxWidth := 0
	for _, line := range lines {
		numRunes := len([]rune(line))
		if numRunes > maxWidth {
			maxWidth = numRunes
		}
	}

	charWidth := 6 // Each character in basic font is 6 pixels wide
	lineHeight := 15
	ascent := 11

	imageWidth := maxWidth * charWidth
	if imageWidth == 0 {
		imageWidth = 1
	}

	numLines := len(lines)
	imageHeight := ascent + (numLines-1)*lineHeight + 2 // 2 pixels for descent

	// Create image with white background
	img := image.NewRGBA(image.Rect(0, 0, imageWidth, imageHeight))
	for y := range imageHeight {
		for x := 0; x < imageWidth; x++ {
			img.Set(x, y, color.White)
		}
	}

	// Set up font drawer
	drawer := &font.Drawer{
		Dst:  img,
		Src:  image.NewUniform(color.Black),
		Face: basicfont.Face7x13,
	}

	// Draw each line
	for i, line := range lines {
		y := ascent + i*lineHeight
		drawer.Dot = fixed.P(0, y)
		drawer.DrawString(line)
	}

	// Save PNG
	f, err := os.Create(outputFile)
	if err != nil {
		panic(err)
	}
	defer f.Close()

	if err := png.Encode(f, img); err != nil {
		panic(err)
	}
}
