#include <map>
#include <stdio.h>
#include <iostream>
#include <string.h>
#include <stdlib.h>

const int size = 5;
bool one = true;
bool zero = false;
long newBlocks = 0l;
int iteratedSize = 0;
std::map<std::string, int> blocks;
// blocks.insert(std::pair<std::string, int>("hi", 0));
// blocks.find

int main();
void build();
void build(bool[][size], int);
//bool[][] 
rotate(bool[][size], int);
int calculateRotation(bool[][size]);
std::string getKeyString(bool[][size]);
bool cellCheck(bool[][size], int, int);
//bool[][] 
blockCopy(bool[][size]);
//bool[][] 
newBlock(bool[][size]);
//bool[][] 
format(bool[][size]);
//bool[][] 
format(bool[][size], int);
void add(bool[][size]);
void addToList(bool[][size]);
bool blockCheck(bool[][size]);


