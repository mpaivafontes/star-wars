TAG?=latest
MESSAGE?=GIT commit mensagem automatica
BRANCH?=develop

IMAGE=starwars_app

commit: git-add git-commit git-push

git-add:
	git add --all

git-commit:
	git commit -m "${BRANCH} ${MESSAGE}"

git-push:
	git push origin ${BRANCH}

rr: stop start

start: install compose-start

compose-start:
	docker-compose up

stop: compose-stop remove-image

compose-stop:
	docker-compose down

remove-image:
	docker rmi -f ${IMAGE}

inside:
	docker exec -it ${IMAGE} sh

push: install docker-build docker-tag docker-push

install:
	mvn clean package

docker-build:
	docker build -t star-wars:latest .

docker-tag:
	docker tag star-wars:latest star-wars

docker-push:
	docker push venom-sync

run-jmeter:
	${JMETER_HOME}/bin/jmeter.sh -t star-wars.jmx
