package main

import (
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"log"
	"os"
	"strings"

	"golang.org/x/image/font"
	"golang.org/x/image/font/opentype"
	"golang.org/x/image/math/fixed"
)

func main() {
	if len(os.Args) < 3 {
		log.Fatal("Usage: go run main.go <text> <output.png>")
	}
	textFile := os.Args[1]
	outputFile := os.Args[2]
	fontFile := "C:\\w\\ws\\preblog\\gopng\\font\\JetBrainsMonoNL-Regular.ttf"
	fontBytes, err := os.ReadFile(fontFile)
	if err != nil {
		log.Fatalf("Failed to read font file: %v", err)
	}

	// textFile is a file read the content
	text, err := os.ReadFile(textFile)
	if err != nil {
		log.Fatalf("Read file: %v", err)
	}

	lines := strings.Split(string(text), "\n")

	ttf, err := opentype.Parse(fontBytes)
	if err != nil {
		log.Fatalf("Parse: %v", err)
	}
	// Create font face
	face, err := opentype.NewFace(ttf, &opentype.FaceOptions{
		Size:    20,
		DPI:     72,
		Hinting: font.HintingFull,
	})
	if err != nil {
		log.Fatalf("Create face: %v", err)
	}
	defer face.Close()

	// Calculate max line width
	maxWidth := 0
	for _, line := range lines {
		w := font.MeasureString(face, line).Ceil()
		if w > maxWidth {
			maxWidth = w
		}
	}

	// Calculate image dimensions
	metrics := face.Metrics()
	lineHeight := metrics.Height.Ceil()
	padding := 20
	imgWidth := maxWidth + 2*padding
	imgHeight := len(lines)*lineHeight + 2*padding

	// Create image with dark background
	bgColor := color.RGBA{R: 0x28, G: 0x2A, B: 0x36, A: 0xFF} // Carbon-like background
	img := image.NewRGBA(image.Rect(0, 0, imgWidth, imgHeight))
	draw.Draw(img, img.Bounds(), &image.Uniform{C: bgColor}, image.Point{}, draw.Src)

	// Draw text lines
	fg := color.White
	drawer := &font.Drawer{
		Dst:  img,
		Src:  image.NewUniform(fg),
		Face: face,
	}
	y := padding + metrics.Ascent.Ceil()
	x := padding
	for _, line := range lines {
		drawer.Dot = fixed.P(x, y)
		drawer.DrawString(line)
		y += lineHeight
	}

	// Save PNG
	file, err := os.Create(outputFile)
	if err != nil {
		log.Fatalf("Create file: %v", err)
	}
	defer file.Close()

	if err := png.Encode(file, img); err != nil {
		log.Fatalf("Encode PNG: %v", err)
	}
}
