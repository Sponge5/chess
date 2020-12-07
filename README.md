# SEMESTRAL PROJECT - CHESS

This repo is supposed to act as a demonstration of my Java skills, *please do tread lightly*. It is a client/server application using Java 10, JavaFX 11, JUnit, Maven, java.util.logging, sockets and some special data structures. It can also serve educational purposes, since making an application of similar scope and functionality by yourself *should* pass you through Programming in Java course at CTU.

**Warning:** This game is barely functional.

## Requirements

Open JDK 10, Maven, JavaFX 11.0.2 (This requirement should be sorted by Maven, as I understand it)

## Running

Compile

`
	make compile
`

Run client in order to play local games

`
	make client
`

**REMOTE GAME IS CURRENTLY BROKEN**
Run server and two clients in order to play remote game

`
	make server
`

**HIT ENTER AFTER EACH TEXTFIELD ENTRY**

To play as white, type localhost as address and 8888 as port to play as white, 
then hit connect, on second client type localhost, 8889 and check
the radio button.

Testing

`
	make test
`

## TODO
* [x] Client creates it's own thread for server in case of game on 1 machine
* [x] Communication in own thread
* [x] Add modifiers to class datastructures (for security)
* [ ] FIX - Check on king against random computer player can desync BoardPane and Board
* [ ] FIX - Remote game doesn't even remotely work...
* [ ] Highlight selected piece in GUI
* [ ] Actually add checkmate and en passant
* [ ] Replace client/server communication with JSON
