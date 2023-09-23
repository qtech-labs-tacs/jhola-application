#!/bin/sh

APP_FILE=${1}

echo Starting ${APP_FILE}


runMicroservice(){
		java -jar /home/${APP_FILE}
}


_main(){
	runMicroservice

}

#Execution starts from here
_main
