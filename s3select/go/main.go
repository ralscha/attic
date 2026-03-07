package main

import (
	"context"
	"fmt"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/glacier"
	glacierTypes "github.com/aws/aws-sdk-go-v2/service/glacier/types"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/aws/aws-sdk-go-v2/service/s3/types"
	"log"
)

const bucketName = "rasc-select-demo"

func main() {
	// on AWS
	// cfg, err := config.LoadDefaultConfig(context.TODO())

	cfg, err := config.LoadDefaultConfig(context.TODO(), config.WithSharedConfigProfile("home"))
	check(err)
	s3Client := s3.NewFromConfig(cfg)

	query := "SELECT latitude,longitude,mag,\"time\",place FROM S3Object LIMIT 5"
	runSelect(query, s3Client, false)

	query = "SELECT \"time\",place,mag FROM S3Object WHERE mag <> '' AND cast(mag as float) > 6"
	runSelect(query, s3Client, false)

	query = "SELECT COUNT(*) FROM S3Object"
	runSelect(query, s3Client, true)

	query = "SELECT MAX(cast(mag as float)), MIN(cast(mag as float)) FROM S3Object where mag <> ''"
	runSelect(query, s3Client, true)
}

func runSelect(query string, s3Client *s3.Client, useCompressedFile bool) {
	key := "all_month.csv"
	if useCompressedFile {
		key = "all_month.csv.tar.gz"
	}
	compressionType := types.CompressionTypeNone
	if useCompressedFile {
		compressionType = types.CompressionTypeGzip
	}

	selectObjectInput := &s3.SelectObjectContentInput{
		Bucket:         aws.String(bucketName),
		Key:            &key,
		ExpressionType: types.ExpressionTypeSql,
		Expression:     aws.String(query),
		InputSerialization: &types.InputSerialization{
			CSV: &types.CSVInput{
				FileHeaderInfo: types.FileHeaderInfoUse,
			},
			CompressionType: compressionType,
		},
		OutputSerialization: &types.OutputSerialization{
			CSV: &types.CSVOutput{},
		},
	}

	resp, err := s3Client.SelectObjectContent(context.Background(), selectObjectInput)
	check(err)
	defer resp.GetStream().Close()

	for event := range resp.GetStream().Events() {
		switch v := event.(type) {
		case *types.SelectObjectContentEventStreamMemberRecords:
			fmt.Println(string(v.Value.Payload))
		case *types.SelectObjectContentEventStreamMemberStats:
			fmt.Println("Processed", *v.Value.Details.BytesProcessed, "bytes")
		case *types.SelectObjectContentEventStreamMemberEnd:
			fmt.Println("SelectObjectContent completed")
		}
	}

	if err := resp.GetStream().Err(); err != nil {
		check(err)
	}
}

func selectWithGlacier(cfg aws.Config) {
	glacierClient := glacier.NewFromConfig(cfg)
	key := "all_month.csv"
	query := "SELECT \"time\",place,mag FROM archive WHERE mag <> '' AND cast(mag as float) > 6"

	job := &glacier.InitiateJobInput{
		AccountId: aws.String("-"),
		VaultName: aws.String(bucketName),
		JobParameters: &glacierTypes.JobParameters{
			ArchiveId: &key,
			Tier:      aws.String("Expedited"), // or "Standard" or "Bulk"
			Type:      aws.String("select"),
			OutputLocation: &glacierTypes.OutputLocation{S3: &glacierTypes.S3Location{
				BucketName: aws.String("output-s3-bucket"),
				Prefix:     aws.String("1"),
			}},
			SelectParameters: &glacierTypes.SelectParameters{
				Expression:     aws.String(query),
				ExpressionType: glacierTypes.ExpressionTypeSql,
				InputSerialization: &glacierTypes.InputSerialization{
					Csv: &glacierTypes.CSVInput{
						FileHeaderInfo: glacierTypes.FileHeaderInfoUse,
					},
				},
				OutputSerialization: &glacierTypes.OutputSerialization{Csv: &glacierTypes.CSVOutput{}},
			},
		},
	}

	_, err := glacierClient.InitiateJob(context.Background(), job)
	check(err)
}

func check(e error) {
	if e != nil {
		log.Panicln(e)
	}
}
