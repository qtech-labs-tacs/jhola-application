#!/bin/bash

PROFILE=${1}

echo Docker hub username entered:  ${DOCKER_HUB_USERNAME}
echo Image version entered:  ${IMAGE_VERSION}

echo "setting environment variable"
#source ./env-variable-file-for-container-services.env



compileAndPackageApplicationArtifactsThatIsJar(){
	echo "compiling and creating application artifacts"
	mvn clean install -DskipTests
}


buildWithProfile(){

 dockerLogin
 one=''
 two=''
 three=''
 four=''

 all=''


 for microservice in $microservices-1
 do
 cd microservice/microservice-rest/target
 PWD=$(PWD)
 echo $PWD
 java -jar 
 cd ..
 done



}


_main(){

	buildWithProfile
	
	deployWithProfile	
	    
}

#Execution starts from here
_main



