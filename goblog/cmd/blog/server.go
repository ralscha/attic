package main

import (
	"context"
	"errors"
	"log/slog"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"
)

func (app *application) serveHTTP() error {
	srv := &http.Server{
		Addr:         app.config.Http.Port,
		Handler:      app.routes(),
		ErrorLog:     slog.NewLogLogger(app.logger.Handler(), slog.LevelWarn),
		IdleTimeout:  time.Duration(app.config.Http.IdleTimeoutInSeconds) * time.Second,
		ReadTimeout:  time.Duration(app.config.Http.ReadTimeoutInSeconds) * time.Second,
		WriteTimeout: time.Duration(app.config.Http.WriteTimeoutInSeconds) * time.Second,
	}

	shutdownErrorChan := make(chan error)

	go func() {
		quitChan := make(chan os.Signal, 1)
		signal.Notify(quitChan, syscall.SIGINT, syscall.SIGTERM)
		<-quitChan

		ctx, cancel := context.WithTimeout(context.Background(), time.Duration(app.config.Http.DefaultShutdownPeriodInSeconds)*time.Second)
		defer cancel()

		app.logger.Info("stopping scheduled jobs")
		taskSchedulerShutdown := app.taskScheduler.Shutdown()
		<-taskSchedulerShutdown
		app.logger.Info("completing background tasks")

		shutdownErrorChan <- srv.Shutdown(ctx)
	}()

	app.logger.Info("starting server", slog.Group("server", "addr", srv.Addr))

	err := srv.ListenAndServe()
	if !errors.Is(err, http.ErrServerClosed) {
		return err
	}

	err = <-shutdownErrorChan
	if err != nil {
		return err
	}

	app.logger.Info("stopped server", slog.Group("server", "addr", srv.Addr))

	app.wg.Wait()
	return nil
}
