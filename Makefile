compile:
	mvn -X -e clean compile

client:
	mvn exec:java -Dexec.arguments=client

server:
	mvn exec:java -Dexec.arguments=server

test:
	mvn test
