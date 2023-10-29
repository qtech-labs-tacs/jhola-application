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
	
	
	cd ./cisco
	docker image build -t ${DOCKER_HUB_USERNAME}/cisco:${IMAGE_VERSION} .
	
	cd ../analytics
	docker image build -t ${DOCKER_HUB_USERNAME}/analytics:${IMAGE_VERSION} .
	
	cd ../security
	docker image build -t ${DOCKER_HUB_USERNAME}/security:${IMAGE_VERSION} .
	
	cd ../swagger-gateway
	docker image build -t ${DOCKER_HUB_USERNAME}/swagger-gateway:${IMAGE_VERSION} .
	
	cd ../zuul-gateway
	docker image build -t ${DOCKER_HUB_USERNAME}/zuul-gateway:${IMAGE_VERSION} .
	
	cd ../
}


buildImagesLocal(){
	
	
	cd ./cisco
	docker image build -t cisco:v1 .
	
	cd ../analytics
	docker image build -t analytics:v1 .
	
	cd ../security
	docker image build -t security:v1 .
	
	cd ../swagger-gateway
	docker image build -t swagger-gateway:v1 .
	
	cd ../zuul-gateway
	docker image build -t zuul-gateway:v1 .
	
	cd ../
}


pushImagesToDockerHub(){
	docker image push ${DOCKER_HUB_USERNAME}/cisco:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/analytics:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/security:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/swagger-gateway:${IMAGE_VERSION}
	docker image push ${DOCKER_HUB_USERNAME}/zuul-gateway:${IMAGE_VERSION}
}


startApplicationStack(){
   	echo "username ${DOCKER_HUB_USERNAME} and version ${IMAGE_VERSION}"
	docker-compose -f docker-compose.yaml up -d
}

kubernetesDeployment(){

	
	cd ./cisco
	kubectl apply -f deployment.yaml
		
	cd ../analytics
    kubectl apply -f deployment.yaml
    
	cd ../security
	kubectl apply -f deployment.yaml	
	
	cd ../swagger-gateway
	kubectl apply -f deployment.yaml
		
	cd ../zuul-gateway
	kubectl apply -f deployment.yaml

}


dockerLogin(){
	docker login -u cloudgeekview -p Scl@ud#1816$
}

clusterRoleBindingRules(){
	kubectl apply -f fabric8-rbac.yaml
}


_main(){

	clusterRoleBindingRules
	compileAndPackageApplicationArtifactsThatIsJar
	
	dockerLogin
	
	echo "building image artifacts"
	buildImagesLocal
	echo 
	
	    
    #echo "starting application stack"
	startApplicationStack

	 echo "kubernetes deployment"
	 kubernetesDeployment
	
}

#Execution starts from here
_main



