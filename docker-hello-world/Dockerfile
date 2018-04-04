FROM python:3

# Lables can be used for cache busting (need to increment version) or filtering. 
LABEL version="0.3"                     
LABEL app.name="docker-hello-world"

# Set a variable that we will keep reusing to prevent typos
ENV SRV_PATH=/srv/www/html

# Make sure we are fully up to date
RUN apt-get update -q
RUN apt-get dist-upgrade -y

# Let Docker know that the exposed port we will use is 8000
EXPOSE 8000

# Create our website's directory, then create a limited user
# and group
RUN mkdir -p $SRV_PATH 
RUN groupadd -r -g 350 pythonsrv && useradd -r -m -u 350 -g 350 pythonsrv

# Define ./external as an externally-mounted directory
VOLUME $SRV_PATH/external

# To serve things up with Python, we need to be in that same directory
WORKDIR $SRV_PATH

# Copy the project readme file to the webserver
COPY README.md $SRV_PATH/project-readme.txt

# Add a URL-hosted content into the image
ADD https://raw.githubusercontent.com/ud3sh/playground/master/README.md $SRV_PATH/repo-readme.txt

# Make sure that we can read all of these files as a limited user
RUN chown -R pythonsrv:pythonsrv $SRV_PATH

# From here on out, use the limited user
USER pythonsrv

# Comand to run after container starts.  In this case, runs the simple http python server to serve up the content
CMD [ "python3", "-m", "http.server" ]