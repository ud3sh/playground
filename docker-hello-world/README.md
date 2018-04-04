Hello world docker
- Runs a python webserver
- Makes this readme file available on the website

#Build
docker build -t hello_world_container .                     #Builds the cotainer using Dockerfile in the current directory with name hello_world_container

#Run
docker run -d -p 8001:8000 --rm hello_world_container       #Runs hello_world_container redirecting port 8001 on host to port 8000 on docker machine

#View on browser
localhost:8001

#Use interactive shell
sudo docker exec -i -t --name hello_world_container /bin/bash


#Useful Docker Commands
docker attach               #Attach the shell's input/output/error stream to the container
docker build                #Build a Docker image based on a provided Dockerfile
docker cp                   #Copy files between container and host
docker exec                 #Execute a command in a running container
docker images               #List image available to your installation of docker
docker info                 #Display information about the system
docker inspect              #Display information about Docker layers, containers, images, etc
docker kill                 #Forcefully terminate a container 
docker logs                 #Display logs from a container since it last started
docker pause                #Pause all processes within a container
docker ps                   #List information about containers and their resource usage
docker pull                 #Pull an image from a remote repository into the local registry
docker push                 #Push an image from the local registry into a remote repository
docker rm                   #Remove a container
docker rmi                  #Remove an image from the local repository
docker run                  #Start a new container and run it
docker search               #Search DockerHub for images
docker start                #Start a stopped container
docker stop                 #Stop a running container nicely (wait for container to shut down)
docker tag                  #Create a tag for an image
docker top                  #Show running processes of a container
docker unpause              #Resume all processes in a paused container
docker version              #Show the Docker version

#Useful Docker File Commads
FROM <image_name>[:<tag>]                               #Base the current image on <image_name>
LABEL <key>=<value> [<key>=value>...]                   #Add metadata to the image
EXPOSE <port>                                           #Indicate which port should be mapped into the container
WORKDIR <path>                                          #Set the current directory for the following commands
RUN <command> [ && <command>... ]                       #Execute one or more shell commands
ENV <name>=<value>                                      #Set an environment variable to a specific value
VOLUME <path>                                           #Indicates that the <path> should be externally mounted volume
COPY <src> <dest>                                       #Copy a local file, a group of files, or a folder into the container
ADD <src> <dest>                                        #The same as COPY but can handle URIs and local archives
USER <user | uid>                                       #Set the runtime context to <user> or <uid> for commands after this one
CMD ["<path>", "<arg1>", ...]                           #Define the command to run when the container is started