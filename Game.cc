#include <ncurses.h>
#include <time.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "Win.cc"

#define ARROW_DOWN 'l'
#define ARROW_UP 'o'
#define ARROW_LEFT 'k'
#define ARROW_RIGHT ';'
#define PAUSE 'p'

private Win * gameWin;
private Win * blockWin;
private Win * infoWin;
private Win * titleWin;

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

void initializeWindows() {
	this.gameWin = new Win();
	this.blockWin = new Win();
	this.infoWin = new Win();
	this.titleWin = new Win();
}

int main() {
	initialize();
	initializeWindows();
	endwin();
	return 0;
}
