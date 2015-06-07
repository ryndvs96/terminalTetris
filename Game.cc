#include <ncurses.h>
#include <time.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "Window.cc"

#define ARROW_DOWN 'l'
#define ARROW_UP 'o'
#define ARROW_LEFT 'k'
#define ARROW_RIGHT ';'
#define PAUSE 'p'

void initialize() {
	initscr();
	raw();
	keypad(stdscr, TRUE);
	clear();
	noecho();
	curs_set(0);
	cbreak();
	nodelay(stdscr, TRUE);
	timeout(60000);
	refresh();
}

int main() {
	initialize();
	initializeWindows();
	endwin();
	return 0;
}
