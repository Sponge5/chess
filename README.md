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

Compile
`
	make
`

Only client -> can only play local game

`
	make client
`

Run server in order to play remote game

`
	make server
`

## TODO
* [x] Client creates it's own thread for server in case of game on 1 machine
* [x] Communication in own thread
* [x] Add modifiers to class datastructures (for security)
