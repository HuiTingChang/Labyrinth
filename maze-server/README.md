# About

This project contains the server and the GUI component of the student project
for 'Rechnernetze'. It opens a match for the "Das verrückte Labyrinth" boardgame
and waits for players. After each turn it checks, if the move is correct, and
request for annother one on fail. If one player wins, the match ends.
It comunicates through sockets and uses XML to serialize objects.
The XML-syntax is defined by `./src/XSD/mazeCom.xsd`.

