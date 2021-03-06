package blocks;

import java.util.ArrayList;
public class BlockAlgorithm1 {
	public static void main(String[] args) {
		int size = 9;
		long time1 = System.currentTimeMillis();
		Blocks blocks = (new BlockAlgorithm1()).new Blocks(size);
		blocks.build();
		blocks.print(true);
		long time2 = System.currentTimeMillis();
//		blocks.finalPrint();
		double time = ((double) (time2 - time1)) / 1000;
		String newBlocks = String.valueOf(blocks.getNewBlocks());
		System.out.println(time + " seconds : " + comma(newBlocks) 
				+ " new Blocks() : For " + size + " block piece.");
	}
	public class Blocks {
		public ArrayList<char[][]> blocks = new ArrayList<char[][]>();
		private char c = 'o';
		private char empty = ' ';
		private long newBlocks = 0;
		private long currentNewBlocks = 0;
		private long currentTime = 0;
		private int size;

		public Blocks(int size) {
			this.size = size;
		}

		public void build() {
			currentTime = System.currentTimeMillis();
			char[][] block = newBlock();
			block[0][0] = c;
			build(block, 1);
			if (size >= 5) {
				int halfsize = size % 2 == 0 ? (size / 2) : (size - 1) / 2;
				for (int i = 1; i < halfsize; i++) {
					for (int j = 1, round = 0; j < halfsize; j++) {
						round = 0;
						block = newBlock();
						for (int k = 0; k < j; k++) {
							block[i][k] = c;
							round++;
						}
						for (int k = 0; k < i; k++) {
							block[k][j] = c;
							round++;
						}
						block[i][j] = c;
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
					for (int j = 0; j + i < size; j++) {
						if (cellCheck(block, i, j)) {
							char[][] copy = blockCopy(block);
							copy[i][j] = c;
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
			char[][] copy = newBlock();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					int newi = size - 1 - j;
					int newj = i;
					copy[newi][newj] = block[i][j] == empty ? empty : block[i][j];
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
			if (block[i][j] == c) {
				return false;
			}
			try {
				if (block[i + 1][j] != c) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i - 1][j] != c) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i][j + 1] != c) {
					nullCount++;
				}
			} catch (Exception e) {
				nullCount++;
			}
			try {
				if (block[i][j - 1] != c) {
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
				for (int j = 0; j < size; j++) {
					blockCopy[i][j] = block[i][j] == empty ? empty : block[i][j];
				}
			}
			return blockCopy;
		}
		private boolean blockCheck(char[][] block) {
			int dif1 = 0;
			block = blockCopy(block);
			for (int k = 0; k < blocks.size(); k++) {
				char[][] blockCheck = blockCopy(blocks.get(k));
				int dif = 0;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (blockCheck[i][j] != block[i][j]) {
							dif++;
						}
						if (j + i > size - 1 && block[i][j] != empty && block[i][j] == c) {
							return false;
						}
					}
				}
				if (dif > 0) {
					dif1++;
				}
			}
			if (blocks.size() > 0 && dif1 != blocks.size()) {
				return false;
			}
			else {
				int cell = 0;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (block[i][j] != empty && block[i][j] == c) {
							cell++;
						}
					}
				}
				if (cell != size) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		private int calculateRotation(char[][] block) {
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int i = 0; i < size; i++) {
				rows[i] = 0;
				cols[i] = 0;
			}
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (block[i][j] != empty && block[i][j] == c) {
						rows[i]++;
						cols[j]++;
					}
				}
			}
			int rowTop = 0;
			int colLeft = 0;
			int rowBot = 0;
			int colRight = 0;
			for (int i = 0; i < size; i++) {
				if (rows[i] > 0 && rowTop == 0) {
					rowTop = rows[i];
				}
				if (rows[size - i - 1] > 0 && rowBot == 0) {
					rowBot = rows[size - i - 1];
				}
				if (cols[i] > 0 && colLeft == 0) {
					colLeft = cols[i];
				}
				if (cols[size - 1 - i] > 0 && colRight == 0) {
					colRight = cols[size - i - 1];
				}
			}
			int piece = 0;
			if (rowTop >= colLeft && rowTop >= rowBot && rowTop >= colRight) {
				piece = 1;
			}
			if (rowBot >= colLeft && rowBot >= rowTop && rowBot >= colRight) {
				piece = 3;
			}
			if (colRight >= colLeft && colRight >= rowTop && colRight >= rowBot) {
				piece = 2;
			}
			if (rowTop == rowBot && rowTop > colRight && colRight == colLeft) {
				piece = 1;
			}
			if (rowTop == colLeft && rowTop > colRight && colRight == rowBot) {
				piece = 0;
			}
			if (rowTop == colRight && rowTop > colLeft && colLeft == rowBot) {
				piece = 1;
			}
			if (rowBot == colLeft && rowBot > colRight && colRight == rowTop) {
				piece = 3;
			}
			if (rowBot == colRight && rowBot > colLeft && colLeft == rowTop) {
				piece = 2;
			}
			return piece % 4;
		}
		private char[][] adjustLeftTop(char[][] block) {
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int i = 0; i < size; i++) {
				rows[i] = 0;
				cols[i] = 0;
			}
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (block[i][j] != empty && block[i][j] == c) {
						rows[i]++;
						cols[j]++;
					}
				}
			}
			int left = -1;
			int up = -1;
			for (int i = 0; i < size; i++) {
				if (rows[i] > 0 && up == -1) {
					up = i;
				}
				if (cols[i] > 0 && left == -1) {
					left = i;
				}
			}
			for (int i = 0; i + up < size; i++) {
				for (int j = 0; j + left < size; j++) {
					char x = block[i + up][j + left];
					block[i][j] = x;
					if (up != 0 || left != 0) {
						block[i + up][j + left] = empty;
					}
				}
			}
			return block;
		}
		private char[][] format(char[][] block) {
			int rot = calculateRotation(block);
			block = rotate(block, rot);
			return adjustLeftTop(block);
		}
		private char[][] format(char[][] block, int rot) {
			rot = rot % 4;
			block = rotate(block, rot);
			return adjustLeftTop(block);
		}
		private void add(char[][] block) {
			block = format(block);
			if (!blockCheck(format(block, 0))) {
				return;
			}
			if (!blockCheck(format(block, 1))) {
				return;
			}
			if (!blockCheck(format(block, 2))) {
				return;
			}
			if (!blockCheck(format(block, 3))) {
				return;
			}
			addToList(block);
		}
		private void addToList(char[][] block) {
			blocks.add(block);
			print(false);
		}
		public void print(boolean end) {
			long time2 = System.currentTimeMillis();
			double time = ((double) (time2 - currentTime)) / 1000;
			String print = null;
			if (!end) {
				print = String.format("%4d--- %1.3f seconds : used %10s newBlocks()", blocks.size(),
						time, comma(String.valueOf(newBlocks - currentNewBlocks)));
			}
			else {
				print = String.format(" END--- %1.3f seconds : used %10s newBlocks()",
						time, comma(String.valueOf(newBlocks - currentNewBlocks)));
			}
			System.out.println(print);
			currentNewBlocks = newBlocks;
			currentTime = System.currentTimeMillis();
		}
		public void finalPrint() {
			for (int k = 0; k < blocks.size(); k++) {
				System.out.println("------------ " + (k + 1));
				char[][] block = blocks.get(k);
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						char x = block[i][j] == empty ? empty : block[i][j];
						System.out.print(x + " ");
					}
					System.out.println();
				}
			}
		}
		private char[][] newBlock() {
			newBlocks++;
			char[][] newBlock = new char[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					newBlock[i][j] = empty;
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
