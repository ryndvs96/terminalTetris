package blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class BlockAlgorithm3 {
	public static void main(String[] args) {
		int size = 9; // must be 11 or less
		long time1 = System.currentTimeMillis();
		Blocks blocks = (new BlockAlgorithm3()).new Blocks(size);
		blocks.build();
		blocks.print(true, null);
		long time2 = System.currentTimeMillis();
		double time = ((double) (time2 - time1)) / 1000;
		String newBlocks = String.valueOf(blocks.getNewBlocks());
		System.out.println(time + " seconds : " + comma(newBlocks) 
				+ " new Blocks() : " + 0.0 + " bytes used : For " + size + " block piece.");
	}
	public class Blocks {
		public Map<String, Object> blocks = new HashMap<String, Object>();
		public ArrayList<String> toDecrypt = new ArrayList<String>();
		private char one = '1';
		private char zero = '0';
		private long newBlocks = 0;
		private int size = 0;
		private int iteratedSize = 0;

		public Blocks(int size) {
			this.size = size;
			for (int i = size; i > 0; i--) {
				iteratedSize += i;
			}
		}

		public void build() {
			char[][] block = newBlock();
			block[0][0] = one;
			build(block, 1);
			if (size >= 5) {
				int halfsize = size % 2 == 0 ? (size / 2) : (size - 1) / 2;
				for (int i = 1; i < halfsize; i++) {
					for (int j = 1, round = 0; j < halfsize; j++) {
						round = 0;
						block = newBlock();
						for (int k = 0; k < j; k++) {
							block[i][k] = one;
							round++;
						}
						for (int k = 0; k < i; k++) {
							block[k][j] = one;
							round++;
						}
						block[i][j] = one;
						round++;
						build(block, round);
					}
				}
			}
			return;
		}
		private void build(char[][] block, int round) {
			if (round < size) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j + i < size && j + i <= round; j++) {
						if (cellCheck(block, i, j)) {
							char[][] copy = blockCopy(block);
							copy[i][j] = one;
							build(copy, round + 1);
						}
					}
				}
			}
			else {
				add(block);
				return;
			}
		}
		private char[][] rotate(char[][] block, int times) {
			if (times == 0) {
				return block;
			}
			int[] rows = new int[size];
			int[] cols = new int[size];
			boolean breaker = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (block[i][j] == one) {
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
			for (int i = size - 1; i >= 0 ; i--) {
				for (int j = 0; j + i < size; j++) {
					if (block[j][i] == one) {
						cols[size - i - 1]++;
						breaker = true;
						break;
					}
				}
				if (breaker) {
					break;
				}
			}
			int left = -1;
			int up = -1;
			for (int i = 0; i < size; i++) {
				if (rows[i] > 0 && left == -1) {
					left = i;
					break;
				}
			}
			for (int i = 0; i < size; i++) {
				if (cols[i] > 0 && up == -1) {
					up = i;
					break;
				}
			}
			char[][] copy = newBlock();

			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					int newi = size - 1 - j - up;
					int newj = i - left;
					if (newi < 0 || newj < 0 || (newi + newj >= size)) {
						continue;
					}
					copy[newi][newj] = block[i][j];
				}
			}
			if (times > 1) {
				return rotate(copy, times - 1);
			}
			else {
				return copy;
			}
		}
		private boolean cellCheck(char[][] block, int i, int j) {
			int nullCount = 0;
			if (block[i][j] == one) {
				return false;
			}
			try {
				if (block[i + 1][j] != one) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i - 1][j] != one) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i][j + 1] != one) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i][j - 1] != one) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			if (nullCount != 4) {
				return true;
			}
			else {
				return false;
			}
		}
		private char[][] blockCopy(char[][] block) {
			char[][] blockCopy = newBlock();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					blockCopy[i][j] = block[i][j];
				}
			}
			return blockCopy;
		}
		private boolean blockCheck(char[][] block) {
			return blocks.containsKey(getKeyString(block));
		}
		private int calculateRotation(char[][] block) {
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (block[i][j] == one) {
						rows[i]++;
						cols[j]++;
					}
				}
			}
			int xLeft = -1, xRight = -1, yTop = -1, yBot = -1;
			for (int i = 0; i < size; i++) {
				if (rows[i] > 0 && yTop == -1) {
					yTop = i;
				}
				if (rows[size - i - 1] > 0 && yBot == -1) {
					yBot = size - i;
				}
				if (cols[i] > 0 && xLeft == -1) {
					xLeft = i;
				}
				if (cols[size - 1 - i] > 0 && xRight == -1) {
					xRight = size - i;
				}
				if (xLeft > -1 && xRight > -1 && yTop > -1 && yBot > -1) {
					break;
				}
			}
			double height = yBot - yTop;
			double width = xRight - xLeft;
			double xComTop = 0;
			double yComTop = 0;
			for (int i = 0; i < cols.length; i++) {
				if (cols[i] > 0) {
					xComTop += ((i + 0.5) * cols[i]);
				}
			}
			for (int i = 0; i < rows.length; i++) {
				if (rows[i] > 0) {
					yComTop += ((i + 0.5) * rows[i]);
				}
			}
			double xCom = ((double) xComTop) / ((double) size);
			double yCom = ((double) yComTop) / ((double) size);
			if (xCom > width / 2) {
				if (yCom > height / 2) {
					return 2;
				}
				else if (yCom < height / 2) {
					return 1;
				}
				else {
					return 1;
				}
			}
			else if (xCom < width / 2) {
				if (yCom > height / 2) {
					return 3;
				}
				else if (yCom < height / 2) {
					return 0;
				}
				else {
					return 3;
				}
			}
			else {
				if (yCom > height / 2) {
					return 2;
				}
				else if (yCom < height / 2) {
					return 0;
				}
				else {
					double topHalf = 0.0, bottomHalf = 0.0;
					double leftHalf = 0.0, rightHalf = 0.0;
					for (int i = 1; i < width / 2; i++) {
						if (cols[i - 1] > 0) {
							leftHalf += cols[i - 1];
						}
					}
					for (int i = (int) width - 1; i > width / 2; i--) {
						if (cols[i] > 0) {
							rightHalf += cols[i];
						}
					}
					for (int i = 1; i < height / 2; i++) {
						if (rows[i - 1] > 0) {
							topHalf += rows[i - 1];
						}
					}
					for (int i = (int) height - 1; i > height / 2; i--) {
						if (rows[i] > 0) {
							bottomHalf += rows[i];
						}
					}
					int rotation = -1;
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
						double topLeftHalf = 0.0, bottomLeftHalf = 0.0;
						double topRightHalf = 0.0, bottomRightHalf = 0.0;
						for (int i = 1; i < width / 2; i++) {
							for (int j = 1; j < height / 2; j++) {
								if (block[j - 1][i - 1] == one) {
									topLeftHalf++;
								}
							}
							for (int j = (int) height - 1; j > height / 2; j--) {
								if (block[j][i - 1] == one) {
									bottomLeftHalf++;
								}
							}
						}
						for (int i = (int) width - 1; i > width / 2; i--) {
							for (int j = 1; j < height / 2; j++) {
								if (block[j - 1][i] == one) {
									topRightHalf++;
								}
							}
							for (int j = (int) height - 1; j > height / 2; j--) {
								if (block[j][i] == one) {
									bottomRightHalf++;
								}
							}
						}
						if (topLeftHalf > topRightHalf && topLeftHalf > bottomRightHalf && topLeftHalf > bottomLeftHalf) {
							return 0;
						}
						else if (topRightHalf > topLeftHalf && topRightHalf > bottomRightHalf && topRightHalf > bottomLeftHalf) {
							return 1;
						}
						else if (bottomRightHalf > topRightHalf && bottomRightHalf > topLeftHalf && bottomRightHalf > bottomLeftHalf) {
							return 2;
						}
						else if (bottomLeftHalf > topRightHalf && bottomLeftHalf > bottomRightHalf && bottomLeftHalf > topLeftHalf) {
							return 3;
						}
						else {
							if (blockCheck(format(block, 1))) {
								return 1;
							}
							else if (blockCheck(format(block, 2))) {
								return 2;
							}
							else if (blockCheck(format(block, 3))) {
								return 3;
							}
							if (width > height) {
								return 1;
							}
							else {
								return 0;
							}
						}
					}
					else {
						return rotation;
					}
				}
			}
		}

		private char[][] format(char[][] block) {
			int rot = calculateRotation(block);
			block = rotate(block, rot);
			return block;
		}
		private char[][] format(char[][] block, int rot) {
			rot = rot % 4;
			block = rotate(block, rot);
			return block;
		}
		private void add(char[][] block) {
			block = format(block);
			if (blockCheck(block)) {
				return;
			}
			addToList(block);
		}
		private void addToList(char[][] block) {
			String key = getKeyString(block);
			blocks.put(key, null);
			print(false, key);
		}
		private String getKeyString(char[][] block) {
			char[] keyChars = new char[size];
			int track = 0, total = 0;
			int prevCount = 0;
			for (int i = 0, count = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (total == size) {
						break;
					}
					if (block[i][j] == '1') {
						if (track > 0 && prevCount == 0) {
							keyChars[track - 1] = (char) (65 + count);
							prevCount = size;
						}
						else {
							keyChars[track] = (char) (count + 48);
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
				if (total == size) {
					break;
				}
			}
			String key = String.valueOf(keyChars);
			return key;
		}
		public void print(boolean end, String key) {
			String print = null;
			if (!end) {
				print = String.format("%7d--- %s", blocks.size(), key);
			}
			else {
				print = String.format("    END--- %s newBlocks()", comma(String.valueOf(newBlocks)));
			}
			System.out.println(print);
		}
		public void decrypt() {
			Object[] arr = blocks.keySet().toArray();
			for (int i = 0; i < arr.length; i++) {
				char[] key = ((String) arr[i]).toCharArray();
				System.out.println("------------------" + (i + 1) + " : " + blocks.get(String.valueOf(key)));
				char[] act = new char[iteratedSize];
				for (int j = 0, count = 0, tot = 0; tot < size && j < key.length; j++) {
					char val = key[j];
					if (val >= 65) {
						int spaces = val - 65;
						act[count] = '1';
						tot++;
						count++;
						for (int k = 0; k < spaces; k++) {
							count++;
						}
						act[count] = '1';
						tot++;
						count++;
					}
					else {
						int spaces = val - 48;
						for (int k = 0; k < spaces; k++) {
							count++;
						}
						act[count] = '1';
						tot++;
						count++;
					}
				}
				for (int j = size, count = 0; j > 0; j--) {
					for (int k = 0; k < j; k++) {
						if (act[count + k] == '1') {
							System.out.print("O ");
						}
						else {
							System.out.print("  ");
						}
					}
					count += j;
					System.out.println();
				}
			}
			System.out.println(arr.length);
		}
		private char[][] newBlock() {
			newBlocks++;
			char[][] newBlock = new char[size][];
			for (int i = 0; i < size; i++) {
				newBlock[i] = new char[size - i];
				for (int j = 0; j  + i < size; j++) {
					newBlock[i][j] = zero;
				}
			}
			return newBlock;
		}
		public long getNewBlocks() {
			return this.newBlocks;
		}
	}
	public static String comma(String str) {
		int len = str.length();
		if (len <= 3) {
			return str;
		}
		else {
			String formatted = "";
			int top = len % 3;
			int fix = (len - top) / 3;
			if (top > 0) {
				formatted += str.substring(0, top) + ",";
			}
			for (int i = 0; i < fix; i++) {
				int start = (i * 3) + top;
				int end = start + 3;
				if (i == fix - 1) {
					formatted += str.substring(start, end);
				}
				else {
					formatted += str.substring(start, end) + ",";
				}
			}
			return formatted;
		}
	}
}



