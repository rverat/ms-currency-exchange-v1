#Dowload the project o clone

    git clone -b main https://github.com/rverat/ms-currency-exchange-v1.git

#requirements

   - java 21
   - IDE of your preference

  for arch linux you would use it
    
   - yay -S intellij-idea-community
   - yay -S jdk-temurin Temurin (OpenJDK 21 Java binaries by Adoptium, formerly AdoptOpenJDK)

#Compile program
    
    mvn clean install
  
  when you compile the program, you can run

#Postman request and spring documentation

    https://documenter.getpostman.com/view/21762368/2RRRRRRRRR


#Create docker image
  you need install docker and docker buildx and start    
  open terminal in directory docker and execute this comands

    //create builder example:ms-currency-exchange-builder, and use that builder
    sudo docker buildx create --name ms-currency-exchange-builder
    sudo docker buildx create --use ms-currency-exchange-builder

    //create image example ms-exchange-rate-v1
    sudo docker buildx build --builder ms-currency-exchange-builder -t ms-currency-exchange-v1 .

    //load the build
    sudo docker buildx build --load -t ms-currency-exchange-v1 .

    //run
    sudo docker run -p 9082:9082 ms-currency-exchange-v1

    // see logs
    sudo docker logs <id_run_container>

    //Stop Container and delete

    sudo docker stop <container_id>
    sudo docker rm <container_id>

    //push your image to docker hub add username of docker hub rverat and tag 0.0.1
    sudo docker login
    sudo docker tag ms-exchange-rate-v1 rverat/ms-currency-exchange-v1:0.0.1
    sudo docker push rverat/ms-currency-exchange-v1:0.0.1


