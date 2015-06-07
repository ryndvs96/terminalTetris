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
	int getStarty();
	int getStartx();
	int getHeight();
	int getWidth();

	void setStarty();
	void setStartx();
	void setHeight();
	void setWidth();
	
	Win();
	void refreshWin();
	void destroyWin();
}
#endif
