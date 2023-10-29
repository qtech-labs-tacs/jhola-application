#!/bin/bash

DOCKER_HUB_USERNAME=${1}
IMAGE_VERSION=${2}

echo Docker hub username entered:  ${DOCKER_HUB_USERNAME}
echo Image version entered:  ${IMAGE_VERSION}

echo "setting environment variable"
#source ./env-variable-file-for-container-services.env



compileAndPackageApplicationArtifactsThatIsJar(){
	echo "compiling and creating application artifacts"
	mvn clean install -DskipTests
}

buildImages(){
	
	
	cd ./api-gateway
	docker image build -t ${DOCKER_HUB_USERNAME}/api-gateway:${IMAGE_VERSION} .
	
	cd ../discovery-service
	docker image build -t ${DOCKER_HUB_USERNAME}/discovery-service:${IMAGE_VERSION} .
	
	cd ../product
	docker image build -t ${DOCKER_HUB_USERNAME}/product:${IMAGE_VERSION} .
	
	cd ../email
	docker image build -t ${DOCKER_HUB_USERNAME}/email:${IMAGE_VERSION} .
	
	cd ../security
	docker image build -t ${DOCKER_HUB_USERNAME}/security:${IMAGE_VERSION} .

	cd ../payment-gateway
	docker image build -t ${DOCKER_HUB_USERNAME}/payment-gateway:${IMAGE_VERSION} .
	
	cd ../
}




pushImagesToDockerHub(){
	docker image push ${DOCKER_HUB_USERNAME}/api-gateway:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/discovery-service:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/security:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/product:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/email:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/payment-gateway:${IMAGE_VERSION}
}



dockerLogin(){
	docker login -u cloudgeekview -p Scl@ud#1816$
}


_main(){

	clusterRoleBindingRules
	compileAndPackageApplicationArtifactsThatIsJar
	
	dockerLogin
	
	echo "building image artifacts"
	echo 
	
	    
}

#Execution starts from here
_main



