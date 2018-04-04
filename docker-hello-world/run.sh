#Build
docker build -t hello_world_container .

#Run
docker run -d -p 8001:8000 --rm hello_world_container 