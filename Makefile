all: Window.cc Game.cc
	g++ -pthread -Wno-write-strings -g -o Game Game.cc -lncurses
	g++ -g -o Window Window.cc -lncurses
