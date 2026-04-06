package main

import (
	"github.com/wneessen/go-mail"
	"time"
)

const defaultTimeout = 10 * time.Second

type Mailer struct {
	client mail.Client
	from   string
}

func NewMailer(host string, port int, username, password, from string) (*Mailer, error) {
	client, err := mail.NewClient(host, mail.WithTimeout(defaultTimeout), mail.WithSMTPAuth(mail.SMTPAuthLogin), mail.WithPort(port), mail.WithUsername(username), mail.WithPassword(password))
	if err != nil {
		return nil, err
	}

	mailer := &Mailer{
		client: *client,
		from:   from,
	}

	return mailer, nil
}

func (m *Mailer) SendFeedback(email, url, feedback string) error {
	msg := mail.NewMsg()

	err := msg.To(m.from)
	if err != nil {
		return err
	}

	err = msg.From(m.from)
	if err != nil {
		return err
	}

	if email != "" {
		err = msg.ReplyTo(email)
		if err != nil {
			return err
		}
	}

	msg.Subject("Feedback: " + url)

	msg.SetBodyString(mail.TypeTextPlain, feedback)

	for i := 1; i <= 3; i++ {
		err = m.client.DialAndSend(msg)

		if nil == err {
			return nil
		}

		if i != 3 {
			time.Sleep(2 * time.Second)
		}
	}

	return err
}
