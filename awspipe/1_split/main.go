package main

import (
	"context"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/s3"
	"github.com/aws/aws-sdk-go/service/s3/s3manager"
	"github.com/segmentio/ksuid"
	"log"
	"net/url"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
)

var awsSession *session.Session

func init() {
	awsSession, _ = session.NewSession(&aws.Config{Region: new("us-east-1")})
}

func main() {
	lambda.Start(handle)
}

func handle(_ context.Context, s3Event events.S3Event) error {
	for _, record := range s3Event.Records {

		recordId := ksuid.New().String()

		// Delete work directory
		err := removeWorkDirectory()
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Create work directory
		err = makeWorkDirectories(recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Download file from S3
		s3Object := record.S3
		decodedS3Key, err := url.QueryUnescape(s3Object.Object.Key)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		err = downloadFile(s3Object.Bucket.Name, decodedS3Key, recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Do the split
		err = splitPdf(recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Upload splitted files
		err = uploadSplittedFiles(recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}
	}

	return nil
}

func removeWorkDirectory() error {
	err := os.RemoveAll("/tmp/work")
	if err != nil {
		return fmt.Errorf("unable to delete work directory %w", err)
	}
	return nil
}

func makeWorkDirectories(recordId string) error {
	err := os.MkdirAll("/tmp/work/"+recordId, os.ModePerm)
	if err != nil {
		return fmt.Errorf("unable to create work directory %w", err)
	}
	return nil
}

func downloadFile(bucket string, s3File string, recordId string) error {
	file, err := os.Create("/tmp/work/" + recordId + ".pdf")
	if err != nil {
		return fmt.Errorf("unable to open file %q, %s", s3File, err)
	}

	defer file.Close()

	downloader := s3manager.NewDownloader(awsSession)

	_, err = downloader.Download(file,
		&s3.GetObjectInput{
			Bucket: &bucket,
			Key:    &s3File,
		})

	if err != nil {
		return fmt.Errorf("unable to download s3File %q, %w", s3File, err)
	}

	return nil
}

func splitPdf(recordId string) error {
	cmd := exec.Command("qpdf", "--split-pages=1", "/tmp/work/"+recordId+".pdf",
		"/tmp/work/"+recordId+"/"+recordId+"-%d.pdf")
	err := cmd.Run()
	return err
}

func uploadSplittedFiles(recordId string) error {
	uploader := s3manager.NewUploader(awsSession)
	di := NewDirectoryIterator("/tmp/work/" + recordId)

	if di.Err() != nil {
		return fmt.Errorf("unable to create directory iterator %w", di.Err())
	}

	if err := uploader.UploadWithIterator(aws.BackgroundContext(), di); err != nil {
		return fmt.Errorf("failed to upload %w", err)
	}
	return nil
}

type DirectoryIterator struct {
	files        []string
	currentIndex int
	currentFile  *os.File
	err          error
}

func NewDirectoryIterator(dir string) s3manager.BatchUploadIterator {
	var paths []string
	err := filepath.Walk(dir, func(path string, info os.FileInfo, err error) error {
		if !info.IsDir() {
			paths = append(paths, path)
		}
		return nil
	})

	if err != nil {
		return &DirectoryIterator{
			err: err,
		}
	}

	return &DirectoryIterator{
		files:        paths,
		currentIndex: -1,
	}
}

func (di *DirectoryIterator) Next() bool {
	noOfFiles := len(di.files)
	if noOfFiles == 0 {
		return false
	}
	di.currentIndex++
	if di.currentIndex >= noOfFiles {
		return false
	}

	f, err := os.Open(di.files[di.currentIndex])
	di.currentFile = f
	di.err = err
	return err == nil
}

func (di *DirectoryIterator) Err() error {
	return di.err
}

func (di *DirectoryIterator) UploadObject() s3manager.BatchUploadObject {
	s3FileName := strings.Replace(di.currentFile.Name(), "/tmp/work", "split", 1)
	return s3manager.BatchUploadObject{
		Object: &s3manager.UploadInput{
			Bucket: new("rasc.test"),
			Key:    new(s3FileName),
			Body:   di.currentFile,
		},
		After: func() error {
			return di.currentFile.Close()
		},
	}
}
