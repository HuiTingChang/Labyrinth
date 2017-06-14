#!/bin/bash
# This will start a gitlab runner docker container
# see https://docs.gitlab.com/runner/install/docker.html

docker run -d --name gitlab-runner --restart always \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /srv/gitlab-runner/config:/etc/gitlab-runner \
  gitlab/gitlab-runner:latest

echo "to create a config on first run:\n"
echo 'docker exec -it gitlab-runner gitlab-runner register'
