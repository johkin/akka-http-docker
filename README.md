# Akka-http Docker Prototyping Lab
This repository contains various investigations about how to use akka-http, 
actors, database in a Docker environment. FLyway is used to migrate the 
database in runtime.
## Setup

1. Install Docker Toolbox
1. Add `eval "$(docker-machine env default)"`to .bashrc
1. Add the IP address of your docker-machine to your hosts-file, like
`192.168.99.100  docker`
1. Build the system `./gradlew buildImage`
1. Start the complete setup `docker-compose up`

## Usage
1. The akka-http can be found at port 8080 of your docker-machine's IP, will 
mostly say "Hello world". Like <http://docker:8080>
1. The mysql database is not reachable from outside the docker environment.
1. To see what's inside the database, PhPmyAdmin is installed in the docker 
network. Found at <http://docker:18080> User/pass: test_user/test_pass

## Known issues
* If you change network configuration, you have to restart the docker tools. 
Otherwise there are all sorts of problems while pulling images from Docker Hub. 