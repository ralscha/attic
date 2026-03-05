package main

import (
	"github.com/go-rod/rod"
	"github.com/go-rod/rod/lib/launcher"
)

func main() {
	l := launcher.MustNewManaged("")
	l.Set("disable-gpu").Delete("disable-gpu")
	l.Headless(false).XVFB("--server-num=5", "--server-args=-screen 0 1600x900x16")
	browser := rod.New().Client(l.MustClient()).MustConnect()
	defer browser.MustClose()

	urls := []string{
		"https://github.com/JetBrains/xodus/wiki",
		"https://github.com/JetBrains/xodus/wiki/Environments",
		"https://github.com/JetBrains/xodus/wiki/Entity-Stores",
		"https://github.com/JetBrains/xodus/wiki/Backup",
		"https://github.com/JetBrains/xodus/wiki/Database-Encryption",
		"https://raw.githubusercontent.com/JetBrains/xodus/master/README.md",
	}
	fileNames := []string{
		"intro.png",
		"env.png",
		"es.png",
		"backup.png",
		"de.png",
		"readme.png",
	}
	for n, url := range urls {
		page := browser.MustPage(url)
		page.MustWaitStable()
		page.MustScreenshotFullPage(fileNames[n])
	}

}
