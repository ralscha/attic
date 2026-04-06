package main

import (
	"errors"
	"github.com/go-git/go-git/v5"
	"github.com/go-git/go-git/v5/plumbing/transport/ssh"
	"os"
)

func (app *application) pullPosts() error {
	postsDir := app.config.Blog.PostDir
	newDir := false
	if _, err := os.Stat(postsDir); os.IsNotExist(err) {
		err = os.Mkdir(postsDir, 0755)
		if err != nil {
			return err
		}
		newDir = true
	}

	auth, err := ssh.NewPublicKeysFromFile("git", app.config.Github.PrivateKey, "")
	if err != nil {
		return err
	}

	if newDir {
		_, err := git.PlainClone(postsDir, false, &git.CloneOptions{
			URL:      app.config.Github.Url,
			Progress: os.Stdout,
			Auth:     auth,
		})
		if err != nil {
			return err
		}
	} else {
		repo, err := git.PlainOpen(postsDir)
		if err != nil {
			return err
		}

		worktree, err := repo.Worktree()
		if err != nil {
			return err
		}

		err = worktree.Pull(&git.PullOptions{RemoteName: "origin", Auth: auth})
		if err != nil && !errors.Is(err, git.NoErrAlreadyUpToDate) {
			return err
		}
	}
	return nil
}
