/*
 * Blocks.h
 *
 *  Created on: Aug 16, 2015
 *      Author: ryan
 */

#ifndef BLOCKS_H_
#define BLOCKS_H_
#include <map>
#include <string>
using namespace std;

class Blocks {
public:
	Blocks(int);
	void build();
	virtual ~Blocks();
private:
	map<string, int> blocks;
	bool one;
	bool zero;
	long newBlocks;
	int size;
	int iteratedSize;

	void build(bool block[], int round);
	bool * rotate(bool block[], int times);
	int calculateRotation(bool block[]);
	string getKeyString(bool block[]);
	bool cellCheck(bool block[], int i, int j);
	int convert(int i, int j);
	bool * blockCopy(bool block[]);
	bool * newBlock();
	bool * format(bool block[]);
	bool * format(bool block[], int rot);
	void add(bool block[]);
	void addToList(bool block[]);
	bool blockCheck(bool block[]);
	void print(string key);
};

#endif /* BLOCKS_H_ */
