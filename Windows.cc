#include <ncurses.h>
#include <string.h>

// 1 + 20 + 1
// 1 + 60 + 1

int mainRows, mainCols, midy, midx;
WINDOW * mainWin() {
	WINDOW * win;
	win = newwin(height, width, starty, startx);
	box(win, 0, 0);
	return win;
}
WINDOW * blockWin() {
	WINDOW * win;
	
WINDOW * titleWin(int starty, int startx, char * title) {
	WINDOW * win;
	win = newwin(3, strlen(title), starty, startx);
	box(win, 0, 0);
	mvwprintw(win, 1, 1, "%s", title);
	return win;
}
WINDOW * infoWin(int starty, int startx, 
		char * label, char * value) {
	return NULL;
}	
void destroyWin(WINDOW * win) {
	wborder(win, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
	wrefresh(win);
	delwin(win);
	return;
}
void generateWindows(WINDOW * mainWin, WINDOW * blockWin, 
		WINDOW * pointWin, WINDOW * titleWin) {
	mainWin = mainWin();
	blockWin = blockWin();
	pointWin = pointWin();
	titleWin = titleWin();
	wrefresh(mainWin);
	wrefresh(blockWin);
	wrefresh(pointwin);
	wrefresh(titleWin);
	return;
}
void destroyAllWins(WINDOW * mainWin, WINDOW * blockWin,
		WINDOW * pointWin, WINDOW * titleWin) {
	destroyWin(mainWin);
	destroyWin(blockWin);
	destroyWin(pointWin);
	destroyWin(titleWin);
	return;
}
void initializeWindows() {
	getmaxyx(stdscr, mainRows, mainCols);
	infoStartX = 23;
	blockStartY = 1;
	blockStartX = 23;
	pointStart
	return;
}
