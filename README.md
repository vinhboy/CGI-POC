SMIL-POC [![Build Status](https://jenkins.com/job/check-manifest/badge/icon)]
========

POC project

## Welcome to SMIL-POC

This is a prepatory site for the SMIL-POC.

## Getting Started

1. Pull from git

2. Ensure Perl is installed (ideally at `/usr/bin/perl`, type `which perl` to confirm)

## Using Docker/Docker Compose

1. Ensure you are at the root of the project directory

2. Execute `./dc up` in a shell.
      This is a helper Perl script that executes `docker-compose build && docker-compose up -d`

3. Execute `./dc ng_bash` in a shell to open a bash prompt in the angular container.
      This is a helper Perl script that executes `docker-compose exec angular /bin/bash`

4. Execute `./dc dw_bash` in a shell to open a bash prompt in the dropwizard container.
      This is a helper Perl script that executes `docker-compose exec dropwizard /bin/bash`

6. Execute `./dc nx_bash` in a shell to open a bash prompt in the nginx container.
      This is a helper Perl script that executes `docker-compose exec nginx /bin/bash`

7. Execute `./dc ms_bash` in a shell to open a bash prompt in the mysql container.
      This is a helper Perl script that executes `docker-compose exec mysql /bin/bash`

## Other helpful commands

If you need to check the status of the docker containers: `./dc ps`

If you need to shut down the docker containers: `./dc down`

## Useful commands once inside the dropwizard container

1. Follow the steps in dropwizard/README.md

2. The helper Perl script to run migrations: `./dw db`

3. The helper Perl script to start Jetty: `./dw serve`

## Useful commands once inside the angular container

1. `grunt test` to run all the tests

2. `grunt serve` to start up a development web server. You can browse to http://localhost:9000

3. `grunt` when you are ready to serve from nginx. You can browse to http://localhost
