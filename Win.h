#ifndef WIN
#define WIN
class Win {
private:
	WINDOW * win;
	int starty;
	int startx;
	int height;
	int width;
public:
	Win(int height, int width, starty, startx);
	
	WINDOW * getWin();
	int getStarty();
	int getStartx();
	int getHeight();
	int getWidth();

	void setStarty(int y);
	void setStartx(int x);
	void setHeight(int height);
	void setWidth(int width);
	
	void refreshWin();
	void destroyWin();
}
#endif
