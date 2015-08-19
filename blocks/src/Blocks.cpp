/*
 * Blocks.cpp
 *
 *  Created on: Aug 16, 2015
 *      Author: ryan
 */

#include "Blocks.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
	int size = 9; // must be 11 or less
	Blocks * blocks = new Blocks(size);
	blocks->build();
}

Blocks::Blocks(int blocksize) {
	one = true;
	zero = false;
	newBlocks = 0l;
	size = 0;
	iteratedSize = 0;

	int i = 0;
	//////////////////////

	size = blocksize;
	for (i = size; i > 0; i--) {
		iteratedSize += i;
	}
}

void Blocks::build() {
	int i = 0, j = 0, k = 0;
	int halfSize = 0;
	int round = 0;

	bool * block;
	///////////////////////////////

	block = newBlock();
	block[0] = one;
	build(block, 1);
	if (size >= 5) {
		halfSize = size % 2 == 0 ? (size / 2) : (size - 1) / 2;
		for (i = 1; i < halfSize; i++) {
			for (j = 1, round = 0; j < halfSize; j++) {
				round = 0;
				block = newBlock();
				for (k = 0; k < j; k++) {
					block[convert(i, k)] = one;
					round++;
				}
				for (k = 0; k < i; k++) {
					block[convert(k, j)] = one;
					round++;
				}
				block[convert(i, j)] = one;
				round++;
				build(block, round);
			}
		}
	}
}

void Blocks::build(bool block[], int round) {
	int i = 0, j = 0;

	bool * copy;
	//////////////////////

	if (round < size) {
		for (i = 0; i < size; i++) {
			for (j = 0; j + i < size && j + i <= round; j++) {
				if (cellCheck(block, i, j)) {
					copy = blockCopy(block);
					copy[convert(i, j)] = one;
					build(copy, round + 1);
				}
			}
		}
	}
	else {
		add(block);
	}
}

bool * Blocks::rotate(bool block[], int times) {
	if (times == 0) {
		return block;
	}

	//////////////////////
	int i = 0, j = 0;
	int newi = 0;
	int newj = 0;
	int up = 0;
	int left = 0;
	bool breaker = false;

	int * rows;
	int * cols;
	bool * copy;
	//////////////////////

	rows = (int *) calloc(sizeof(int), size);
	cols = (int *) calloc(sizeof(int), size);
	breaker = false;
	for (i = 0; i < size; i++) {
		for (j = 0; j + i < size; j++) {
			if (block[convert(i, j)] == one) {
				rows[i]++;
				breaker = true;
				break;
			}
		}
		if (breaker) {
			break;
		}
	}
	breaker = false;
	for (i = size - 1; i >= 0 ; i--) {
		for (j = 0; j + i < size; j++) {
			if (block[convert(j, i)] == one) {
				cols[size - i - 1]++;
				breaker = true;
				break;
			}
		}
		if (breaker) {
			break;
		}
	}
	left = -1;
	up = -1;
	for (i = 0; i < size; i++) {
		if (rows[i] > 0 && left == -1) {
			left = i;
			break;
		}
	}
	for (i = 0; i < size; i++) {
		if (cols[i] > 0 && up == -1) {
			up = i;
			break;
		}
	}
	copy = newBlock();
	for (i = 0; i < size; i++) {
		for (j = 0; j + i < size; j++) {
			newi = size - 1 - j - up;
			newj = i - left;
			if (newi < 0 || newj < 0 || (newi + newj >= size)) {
				continue;
			}
			copy[convert(newi, newj)] = block[convert(i, j)];
		}
	}
	if (times > 1) {
		return rotate(copy, times - 1);
	}
	else {
		return copy;
	}
}

int Blocks::calculateRotation(bool block[]) {
	int i = 0, j = 0;

	int xLeftIndex = -1;
	int xRightIndex = -1;
	int yTopIndex = -1;
	int yBotIndex = -1;

	int rotation = -1;

	float height = 0.0;
	float width = 0.0;

	float xCenterOfMassTop = 0.0; // of equation
	float yCenterOfMassTop = 0.0; // of equation
	float xCenterOfMass = 0.0;
	float yCenterOfMass = 0.0;

	float leftHalf = 0.0;
	float rightHalf = 0.0;
	float topHalf = 0.0;
	float bottomHalf = 0.0;

	float topLeftQuarter = 0.0;
	float bottomLeftQuarter = 0.0;
	float topRightQuarter = 0.0;
	float bottomRightQuarter = 0.0;

	int * rows;
	int * cols;
	////////////////////////

	rows = (int *) calloc(sizeof(int), size);
	cols = (int *) calloc(sizeof(int), size);
	for (i = 0; i < size; i++) {
		for (j = 0; j + i < size; j++) {
			if (block[convert(i, j)] == one) {
				rows[i]++;
				cols[j]++;
			}
		}
	}
	xLeftIndex = -1;
	xRightIndex = -1;
	yTopIndex = -1;
	yBotIndex = -1;
	for (i = 0; i < size; i++) {
		if (rows[i] > 0 && yTopIndex == -1) {
			yTopIndex = i;
		}
		if (rows[size - i - 1] > 0 && yBotIndex == -1) {
			yBotIndex = size - i;
		}
		if (cols[i] > 0 && xLeftIndex == -1) {
			xLeftIndex = i;
		}
		if (cols[size - 1 - i] > 0 && xRightIndex == -1) {
			xRightIndex = size - i;
		}
		if (xLeftIndex > -1 && xRightIndex > -1 && yTopIndex > -1 && yBotIndex > -1) {
			break;
		}
	}
	height = yBotIndex - yTopIndex;
	width = xRightIndex - xLeftIndex;
	xCenterOfMassTop = 0;
	yCenterOfMassTop = 0;
	for (i = 0; i < size; i++) {
		if (cols[i] > 0) {
			xCenterOfMassTop += ((i + 0.5) * cols[i]);
		}
	}
	for (i = 0; i < size; i++) {
		if (rows[i] > 0) {
			yCenterOfMassTop += ((i + 0.5) * rows[i]);
		}
	}
	xCenterOfMass = ((float) xCenterOfMassTop) / ((float) size);
	yCenterOfMass = ((float) yCenterOfMassTop) / ((float) size);
	if (xCenterOfMass > width / 2) {
		if (yCenterOfMass > height / 2) {
			rotation = 2;
		}
		else if (yCenterOfMass < height / 2) {
			rotation = 1;
		}
		else {
			rotation = 1;
		}
	}
	else if (xCenterOfMass < width / 2) {
		if (yCenterOfMass > height / 2) {
			rotation = 3;
		}
		else if (yCenterOfMass < height / 2) {
			rotation = 0;
		}
		else {
			rotation = 3;
		}
	}
	else {
		if (yCenterOfMass > height / 2) {
			rotation = 2;
		}
		else if (yCenterOfMass < height / 2) {
			rotation = 0;
		}
		else {
			topHalf = 0.0f; bottomHalf = 0.0f;
			leftHalf = 0.0f; rightHalf = 0.0f;
			for (i = 1; i < width / 2; i++) {
				if (cols[i - 1] > 0) {
					leftHalf += cols[i - 1];
				}
			}
			for (i = (int) width - 1; i > width / 2; i--) {
				if (cols[i] > 0) {
					rightHalf += cols[i];
				}
			}
			for (i = 1; i < height / 2; i++) {
				if (rows[i - 1] > 0) {
					topHalf += rows[i - 1];
				}
			}
			for (i = (int) height - 1; i > height / 2; i--) {
				if (rows[i] > 0) {
					bottomHalf += rows[i];
				}
			}
			rotation = -1;
			if (topHalf > bottomHalf) {
				if (leftHalf > rightHalf) {
					rotation = 0;
				}
				else if (leftHalf < rightHalf) {
					rotation = 1;
				}
				else {
					rotation = 0;
				}
			}
			else if (topHalf < bottomHalf) {
				if (leftHalf > rightHalf) {
					rotation = 3;
				}
				else if (leftHalf < rightHalf) {
					rotation = 2;
				}
				else {
					rotation = 2;
				}
			}
			else {
				if (leftHalf > rightHalf) {
					rotation = 3;
				}
				else if (leftHalf < rightHalf) {
					rotation = 1;
				}
				else {
					rotation = -1;
				}
			}
			if (rotation == -1) {
				topLeftQuarter = 0.0f; bottomLeftQuarter = 0.0f;
				topRightQuarter = 0.0f; bottomRightQuarter = 0.0f;
				for (i = 1; i < width / 2; i++) {
					for (j = 1; j < height / 2; j++) {
						if (block[convert(j - 1, i - 1)] == one) {
							topLeftQuarter++;
						}
					}
					for (j = (int) height - 1; j > height / 2; j--) {
						if (block[convert(j, i - 1)] == one) {
							bottomLeftQuarter++;
						}
					}
				}
				for (i = (int) width - 1; i > width / 2; i--) {
					for (j = 1; j < height / 2; j++) {
						if (block[convert(j - 1, i)] == one) {
							topRightQuarter++;
						}
					}
					for (j = (int) height - 1; j > height / 2; j--) {
						if (block[convert(j, i)] == one) {
							bottomRightQuarter++;
						}
					}
				}
				if (topLeftQuarter > topRightQuarter && topLeftQuarter > bottomRightQuarter && topLeftQuarter > bottomLeftQuarter) {
					rotation = 0;
				}
				else if (topRightQuarter > topLeftQuarter && topRightQuarter > bottomRightQuarter && topRightQuarter > bottomLeftQuarter) {
					rotation = 1;
				}
				else if (bottomRightQuarter > topRightQuarter && bottomRightQuarter > topLeftQuarter && bottomRightQuarter > bottomLeftQuarter) {
					rotation = 2;
				}
				else if (bottomLeftQuarter > topRightQuarter && bottomLeftQuarter > bottomRightQuarter && bottomLeftQuarter > topLeftQuarter) {
					rotation = 3;
				}
				else {
					if (blockCheck(format(block, 1))) {
						rotation = 1;
					}
					else if (blockCheck(format(block, 2))) {
						rotation = 2;
					}
					else if (blockCheck(format(block, 3))) {
						rotation = 3;
					}
					else if (width > height) {
						rotation = 1;
					}
					else {
						rotation = 0;
					}
				}
			}
		}
	}
	return rotation;
}

string Blocks::getKeyString(bool block[]) {
	int i = 0;
	int count = 0;
	int prevCount = 0;
	int track = 0;
	int total = 0;

	char * keyChars;
	char * key;
	//////////////////////

	keyChars = (char *) calloc(sizeof(char), size);
	for (i = 0, count = 0; i < iteratedSize; i++) {
		if (total == size) {
			break;
		}
		if (block[i]) {
			if (track > 0 && prevCount == 0) {
				keyChars[track - 1] = (char) (65 + count);
				prevCount = size;
			}
			else {
				keyChars[track] = (char) (48 + count);
				track++;
				prevCount = count;
			}
			count = 0;
			total++;
		}
		else {
			count++;
		}
	}
	key = keyChars;
	return (string) key;
}

bool Blocks::cellCheck(bool block[], int i, int j) {
	int nullCount = 0;
	//////////////////////

	nullCount = 0;
	if (block[convert(i, j)] == one) {
		return false;
	}
	try {
		if (block[convert(i + 1, j)] != one) {
			nullCount++;
		}
	} catch (...) {
		nullCount++;
	}
	try {
		if (block[convert(i - 1, j)] != one) {
			nullCount++;
		}
	} catch (...) {
		nullCount++;
	}
	try {
		if (block[convert(i, j + 1)] != one) {
			nullCount++;
		}
	} catch (...) {
		nullCount++;
	}
	try {
		if (block[convert(i, j - 1)] != one) {
			nullCount++;
		}
	} catch (...) {
		nullCount++;
	}
	if (nullCount != 4) {
		return true;
	}
	else {
		return false;
	}
}

int Blocks::convert(int i, int j) {
	int k = 0;
	int tot = 0;
	int iSum = 0;
	////////////////////////

	if (i < 0 || j < 0 || i + j >= size) {
		return -1;
	}
	for (k = 0; k < i; k++) {
		int itSize = (size - k);
		iSum += itSize;
	}
	tot = j + iSum;
	return tot;
}

bool * Blocks::blockCopy(bool block[]) {
	int i = 0, j = 0;

	bool * blockCopy;
	///////////////////////

	blockCopy = newBlock();
	for (i = 0; i < size; i++) {
		for (j = 0; j + i < size; j++) {
			blockCopy[convert(i, j)] = block[convert(i, j)];
		}
	}
	return blockCopy;
}

bool * Blocks::newBlock() {
	int i = 0;

	bool * newBlock;
	//////////////////////

	newBlocks++;
	newBlock = (bool *) calloc(sizeof(bool), iteratedSize);
	for (i = 0; i < size; i++) {
		newBlock[i] = zero;
	}
	return newBlock;
}

bool * Blocks::format(bool block[]) {
	return block = rotate(block, calculateRotation(block));
}

bool * Blocks::format(bool block[], int rot) {
	return block = rotate(block, rot % 4);
}

void Blocks::add(bool block[]) {
	block = format(block);
	if (blockCheck(block)) {
		delete(block);
		return;
	}
	addToList(block);
}

void Blocks::addToList(bool block[]) {
	string key = getKeyString(block);
	blocks.insert(pair<string, int>(key, 0));
	print(key);
}

bool Blocks::blockCheck(bool block[]) {
	bool toreturn = false;
	string key = getKeyString(block);
	map<string, int>::iterator it = blocks.find(key);
	if (it == blocks.end()) {
		toreturn = false;
	}
	else {
		toreturn = true;
	}
	return toreturn;
}

void Blocks::print(string key) {
	printf("%7d--- %s\n", (int) blocks.size(), key.c_str());
}

Blocks::~Blocks() {
}

