#include "Win.h"
#include <ncurses.h>
#include <stdio.h>
#include <stdlib.h>

Win::Win(int height, int width, starty, startx) {
	this.win = newwin(height, width, starty, startx);
	box(win, 0, 0);
	wrefresh(this.win);
	this.height = height;
	this.width = width;
	this.starty = starty;
	this.startx = startx;
}

WINDOW * Win::getWin() {
	return this.win;
}
