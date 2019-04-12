# SEMESTRAL PROJECT - CHESS
Client/server application using JavaFX

(**Mandatory**/Optional)
* [ ] **Chess logic**
* [ ] **JavaFX GUI (multithreading)**
* [ ] **Clock**
* [ ] **2 players 1 machine**
* [ ] **Player vs AI**
* [ ] **Save Game**
* [ ] **Manual board setup**
* [ ] Client/Server
* [ ] PGN-completeness
* [ ] Advanced AI

## Running

`
	mvn clean compile
`

Running client

`
	mvn exec:java -Dexec.arguments=client
`

Running server for server testing

`
	mvn exec:java -Dexec.arguments=client
`

## How-to
Run client and select new game, that's all the functionality right now.

## TODO
Client creates it's own thread for server in case of game on 1 machine
