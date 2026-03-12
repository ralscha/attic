package main

import (
	"flag"
	"fmt"
	"golang.org/x/image/draw"
	"image"
	"image/color"
	"log"
	"math/cmplx"
	"net/http"
	"os"
	"os/signal"
	"runtime/pprof"
	"strconv"
	"strings"
	"sync"
)

const (
	defaultIterations = 200
	defaultContrast   = 15
	defaultWidth      = 80
	defaultHeight     = 40
)

func main() {
	port := flag.Int("port", 8080, "port number for the server")
	cpuProfile := flag.Bool("cpuprofile", false, "write cpu profile to file")
	flag.Parse()

	if *cpuProfile {
		f, err := os.Create("./default.pgo")
		if err != nil {
			log.Fatal(err)
		}
		err = pprof.StartCPUProfile(f)
		if err != nil {
			log.Fatal(err)
		}
		defer pprof.StopCPUProfile()
	}

	mux := http.NewServeMux()
	mux.HandleFunc("/mandelbrot", mandelbrotHandler)
	addr := fmt.Sprintf(":%d", *port)
	log.Printf("Server listening on http://localhost%s\n", addr)

	srv := &http.Server{Addr: addr, Handler: mux}
	go func() {
		if err := srv.ListenAndServe(); err != nil {
			log.Printf("Error starting server: %s\n", err)
		}
	}()

	quit := make(chan os.Signal, 1)
	signal.Notify(quit, os.Interrupt)
	<-quit
	log.Println("Shutdown Server ...")
	err := srv.Shutdown(nil)
	if err != nil {
		log.Fatalf("Could not gracefully shutdown the server: %v\n", err)
	}
	close(quit)
	log.Println("Server exiting")
}

func mandelbrotHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query()

	iterations, errIterations := parseIntQuery(query.Get("iterations"), defaultIterations)
	contrast, errContrast := parseIntQuery(query.Get("contrast"), defaultContrast)
	width, errWidth := parseIntQuery(query.Get("width"), defaultWidth)
	height, errHeight := parseIntQuery(query.Get("height"), defaultHeight)

	if errIterations != nil || errContrast != nil || errWidth != nil || errHeight != nil {
		http.Error(w, "Invalid parameters", http.StatusBadRequest)
		return
	}

	x := -0.5
	y := 0.0
	img := generateMandelbrotParallel(x, y, iterations, contrast, width, height)

	asciiArt := convertToASCII(img, width, height)
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, err := w.Write([]byte(asciiArt))
	if err != nil {
		log.Printf("Error writing response: %s", err)
	}
}

func parseIntQuery(value string, defaultValue int) (int, error) {
	if value == "" {
		return defaultValue, nil
	}
	return strconv.Atoi(value)
}

func generateMandelbrotParallel(x, y float64, iterations, contrast, width, height int) draw.Image {
	xmin, xmax := x-2.0, x+2.0
	ymin, ymax := y-2.0, y+2.0

	img := image.NewRGBA(image.Rect(0, 0, width, height))

	stripHeight := height / 4
	var wg sync.WaitGroup
	for i := 0; i < height; i += stripHeight {
		wg.Add(1)
		go func(startY, endY int) {
			run(startY, endY, height, ymax, ymin, width, xmax, xmin, img, iterations, contrast, &wg)
		}(i, i+stripHeight)
	}
	wg.Wait()

	return img
}

func run(startY int, endY int, height int, ymax float64, ymin float64, width int, xmax float64, xmin float64, img *image.RGBA, iterations int, contrast int, wg *sync.WaitGroup) {
	for py := startY; py < endY; py++ {
		y := float64(py)/float64(height)*(ymax-ymin) + ymin
		for px := range width {
			x := float64(px)/float64(width)*(xmax-xmin) + xmin
			z := complex(x, y)
			img.Set(px, py, mandelbrot(z, iterations, contrast))
		}
	}
	wg.Done()
}

func mandelbrot(z complex128, iterations, contrast int) color.Color {
	var v complex128
	for n := range iterations {
		v = v*v + z
		if cmplx.Abs(v) > 2 {
			intensity := uint8(n * contrast)
			return color.RGBA{R: 255 - intensity, G: 255 - intensity, B: 255 - intensity, A: 255}
		}
	}
	return color.Black
}

func convertToASCII(img draw.Image, width, height int) string {
	var asciiArt strings.Builder

	for y := range height {
		for x := range width {
			c := img.At(x, y)
			r, _, _, _ := c.RGBA()
			asciiArt.WriteByte(grayscaleToChar(r))
		}
		asciiArt.WriteByte('\n')
	}

	return asciiArt.String()
}

const grayscaleChars = "@%#*+=-:. "

func grayscaleToChar(value uint32) byte {
	index := int(value) * len(grayscaleChars) / 65535
	if index >= len(grayscaleChars) {
		index = len(grayscaleChars) - 1
	}
	return grayscaleChars[index]
}
