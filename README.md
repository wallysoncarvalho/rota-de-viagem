# Running the application

To test the application easily, I created a docker file so we can run the application with simple commands and only
needing to have docker installed.

Before building the image, please put the test files on the folder `graph_files`.

To build the image, simple run the command bellow at the root of the project:

```
docker build --tag wallyson/bexs .
```

This may take a few minutes because we're creating the artifact the application from the source code.

### Rest

After the process described above is done, simply run the command bellow to start the container of the http server:

```
docker run -d -p 8090:8090 --name challenge-wallyson -e GRAPH_PATH=/graph_files/<graph_file_name> wallyson/bexs
```

Please, note the field `<graph_file_name>`. This should be replaced with the name of the file that was inserted on the
folder `graph_files`. For example:

```
docker run -d -p 8090:8090 --name challenge-wallyson -e GRAPH_PATH=/graph_files/graph.csv wallyson/bexs
```

### CLI

In the same container, I also copied the artifact for the CLI application. If the commands above were successfully
executed, we're now able to run the CLI app with the following command:

```
docker exec -it challenge-wallyson java -jar cli.jar /graph_files/<graph_file_name>
```

Note again the name of the file that should coincide with the file that was copied to the folder `graph_files` before
building the image.

This should start a prompt to input the graph nodes:

```
CLI INTERFACE. WRITE 'exit' to leave.
GETTING GRAPH FROM THE FILE: /graph_files/graph.csv

please enter the route: GRU-CDG
best route: GRU - BRC - SCL - ORL - CDG > $40
please enter the route:
```

# App file structure

|-- **cli** : Code to run the application through the command line interface

|-- **core** : The domain of the application. All the code related to the process of finding the best rout in a graph is
here.

|-- **persistence** : Giving that the graph could come from different sources, this module is responsible to implement
the ports defined on the core, that is, what the progra is expecting to receive to run. On this case, this module is
managing the graph information from files.

|-- **rest** : Code to run the application through a http server.

|-- **<module>/src/test/*** : In the modules, this is the path where the tests were implemented. With maven and Java 11
installed, the tests can be run with `mvn test`.

# Project's design

The idea of the structure of this project was to implement the main concepts of "**Clean Architecture**", that is,
separate the business rules from the details.

For this, I decided to solve the problem of searching the shortest path of nodes on a directed graph, by implementing
Dijkstra algorithm. So all the logic of this process, along with all the structures needed to process it, was
implemented on the core module.

As mentioned before, we have two modes of accessing the domain: HTTP server, and a command line interface. Both were
created on separate modules and depend only on the business rules.

In the future, we may need to get the graph from different sources (e.g, database). With this in mind I defined two
contracts on the core module that are implemented on the persistence module, so anytime we want to change the source of
the graph we just change the persistence module.

# Rest interface

A postman collection is provided at the root of the project so one can import it and test the rest interface once the
server is up.

### Add a new connection to file

Method: `POST`

Path: `/api/graph`

Body:
`
{
"source": string,
"destination": string,
"cost": integer }
`

Example:

```
{
    "source": "GRU",
    "destination": "SA",
    "cost": 2
}
```

### Find shortests path between two nodes

Method: `GET`

Path: `/api/graph/dijkstra`

Params:
`source` , required
`destination`, required

Example:

```
<host:port>/api/graph/dijkstra?source=GRU&destination=CDG
```
