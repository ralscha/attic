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
	"log"
	"os"
	"os/exec"
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
		s3Object := record.S3

		dotPos := strings.Index(s3Object.Object.Key, ".")
		lastSlashPos := strings.LastIndex(s3Object.Object.Key, "/")
		recordId := s3Object.Object.Key[lastSlashPos+1 : dotPos]

		// Delete work directory
		err := removeWorkDirectory()
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Create work directory
		err = makeWorkDirectory()
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		err = downloadFile(s3Object.Bucket.Name, s3Object.Object.Key, recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Do the conversion
		err = convertToPng(recordId)
		if err != nil {
			log.Printf("%v\n", err)
			return err
		}

		// Upload converted file
		err = uploadFile(recordId)
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

func makeWorkDirectory() error {
	err := os.MkdirAll("/tmp/work", os.ModePerm)
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

func convertToPng(recordId string) error {
	convertCmd := []string{"-density", "300", "-strip", "-background",
		"white", "-flatten", "-alpha", "on", "-depth", "8", "-quality", "100",
		"/tmp/work/" + recordId + ".pdf", "/tmp/work/" + recordId + ".png"}

	cmd := exec.Command("convert", convertCmd...)
	err := cmd.Run()
	return err
}

func uploadFile(recordId string) error {
	uploader := s3manager.NewUploader(awsSession)
	file, err := os.Open("/tmp/work/" + recordId + ".png")
	if err != nil {
		return fmt.Errorf("unable to open converted file %w", err)
	}
	defer file.Close()

	before, _, _ := strings.Cut(recordId, "-")

	_, err = uploader.Upload(&s3manager.UploadInput{
		Bucket: new("rasc.test"),
		Key:    new("convert/" + before + "/" + recordId + ".png"),
		Body:   file,
	})
	if err != nil {
		return fmt.Errorf("unable to upload converted file %w", err)
	}

	return nil
}
