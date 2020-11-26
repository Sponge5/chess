# SEMESTRAL PROJECT - CHESS
Client/server application using JavaFX

## Requirements

Open JDK 10

## Running

Compile

`
	make
`

Run client in order to play local games

`
	make client
`

Run server and two clients in order to play remote game

`
	make server
`

HIT ENTER AFTER EACH TEXTFIELD ENTRY

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
