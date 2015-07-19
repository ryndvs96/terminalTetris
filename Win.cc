#include "Win.h"
#include <ncurses.h>
#include <stdio.h>
#include <stdlib.h>

public Win::Win(int height, int width, starty, startx) {
	this.win = newwin(height, width, starty, startx);
	box(win, 0, 0);
	wrefresh(this.win);
	this.height = height;
	this.width = width;
	this.starty = starty;
	this.startx = startx;
}

public void Win::refreshWin() {
	// TODO
}

public void Win::destroyWin() {
	// TODO
}

// Getters
public WINDOW * Win::getWin() {
	return this.win;
}

public int Win::getStarty() {
	return this.starty;
}

public int Win::getStartx() {
	return this.startx;
}

public int Win::getHeight() {
	return this.height;
}

public int Win::getWidth() {
	return this.width;
}

// Setters
public void Win::setStarty(int y) {
	this.starty = y;
}

public void Win::setStartx(int x) {
	this.startx = x;
}

public void Win::setHeight(int height) {
	this.height = height;
}

public void Win::setWidth(int width) {
	this.width = width;
}
