IMAGE_TAG := wallyson/bexs
CONTAINER_NAME := challenge-wallyson
file := graph.csv

up:
	docker build --tag $(IMAGE_TAG) .
	docker run -d -p 8090:8090 --name $(CONTAINER_NAME) -e GRAPH_PATH=/graph_files/$(file) $(IMAGE_TAG)
cmd:
	docker exec -it $(CONTAINER_NAME) java -jar cli.jar /graph_files/$(file)