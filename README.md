# Currency Exchange Microservice
Download the project or clone

    git clone -b main https://github.com/rverat/ms-currency-exchange-v1.git

Requirements

    Java 21
    IDE of your preference
    For Arch Linux, you can use:

    yay -S intellij-idea-community
    yay -S jdk-temurin

Compile the program
    
    mvn clean install

Running the program

After compiling the program, you can run it with the following command:

    java -jar target/ms-currency-exchange-v1.jar

You can find Postman requests and Spring documentation here.
 - https://documenter.getpostman.com/view/21762368/2sA3BuV8V5


Create Docker image

To create a Docker image, you need to have Docker and Docker Buildx installed.
Open a terminal in the docker directory and execute the following commands:

    #Create a builder, for example: ms-currency-exchange-builder, and use that builder
    sudo docker buildx create --name ms-currency-exchange-builder

    #Create the image, for example: ms-currency-exchange-v1
    sudo docker buildx build --builder ms-currency-exchange-builder -t ms-currency-exchange-v1 .

    #Load the build
    sudo docker buildx build --load -t ms-currency-exchange-v1 .

    #Run the container
    sudo docker run -p 9006:9006 ms-currency-exchange-v1

    #See logs
    sudo docker logs <container_id>

    #Stop and delete container
    sudo docker stop <container_id>
    sudo docker rm <container_id>

    #Push your image to Docker Hub, replacing <username> with your Docker Hub username and <tag> with your desired tag
    sudo docker login
    sudo docker tag ms-currency-exchange-v1 <username>/ms-currency-exchange-v1:<tag>
    sudo docker push <username>/ms-currency-exchange-v1:<tag>

Bibliographic references

- https://adriankodja.com/domain-driven-design-ddd-and-spring-boot
- https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749