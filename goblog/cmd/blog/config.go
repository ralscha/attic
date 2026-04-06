package main

import (
	"github.com/spf13/viper"
)

type Config struct {
	Http struct {
		Port                           string
		ReadTimeoutInSeconds           int64
		WriteTimeoutInSeconds          int64
		IdleTimeoutInSeconds           int64
		DefaultShutdownPeriodInSeconds int64
	}
	Smtp struct {
		Host     string
		Port     int
		Username string
		Password string
		Sender   string
	}
	Github struct {
		Url           string
		WebhookSecret string
		PrivateKey    string
	}
	Blog struct {
		PostDir     string
		Title       string
		Author      string
		Description string
		Url         string
		Prismjs     string
		Secret      string
	}
	Meilisearch struct {
		Host string
		Key  string
	}
}

func applyDefaults() {
	viper.SetDefault("http.readTimeoutInSeconds", 10)
	viper.SetDefault("http.writeTimeoutInSeconds", 10)
	viper.SetDefault("http.idleTimeoutInSeconds", 60)
	viper.SetDefault("http.defaultShutdownPeriodInSeconds", 30)
}

func LoadConfig() (Config, error) {
	var cfg Config

	applyDefaults()
	viper.SetConfigName("app")
	viper.SetConfigType("env")
	viper.AddConfigPath(".")
	err := viper.ReadInConfig()
	if err != nil {
		return cfg, err
	}

	viper.SetEnvPrefix("golb")
	viper.AutomaticEnv()

	err = viper.Unmarshal(&cfg)
	if err != nil {
		return cfg, err
	}

	return cfg, nil
}
