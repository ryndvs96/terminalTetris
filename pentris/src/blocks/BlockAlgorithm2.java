package blocks;

import java.util.HashMap;
import java.util.Map;
public class BlockAlgorithm2 {
	public static void main(String[] args) {
		int size = 9; // must be 10 or less
		long time1 = System.currentTimeMillis();
		Blocks blocks = (new BlockAlgorithm2()).new Blocks(size);
		blocks.build();
		blocks.print(true);
		long time2 = System.currentTimeMillis();
		blocks.finalPrint();
		double time = ((double) (time2 - time1)) / 1000;
		String newBlocks = String.valueOf(blocks.getNewBlocks());
		System.out.println(time + " seconds : " + comma(newBlocks) 
				+ " new Blocks() : For " + size + " block piece.");
	}
	public class Blocks {
		public Map<String, Object> blocks = new HashMap<String, Object>();
		public Map<String, String> compressMap = new HashMap<String, String>();
		private boolean one = true;
		private boolean zero = false;
		private long newBlocks = 0;
		private long currentNewBlocks = 0;
		private long currentTime = 0;
		private int size = 0;

		public Blocks(int size) {
			this.size = size;
			char start = 'A';
			compressMap.put("00", String.valueOf(start));
			start++;
			for (int i = 1; i < size; i++, start++) {
				compressMap.put("0" + i, String.valueOf(start));
				start++;
				compressMap.put("" + i + "0", String.valueOf(start));
			}
		}

		public void build() {
			currentTime = System.currentTimeMillis();
			boolean[][] block = newBlock();
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
		private void build(boolean[][] block, int round) {
			if (round < size) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j + i < size && j + i <= round; j++) {
						if (cellCheck(block, i, j)) {
							boolean[][] copy = blockCopy(block);
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
		private boolean[][] rotate(boolean[][] block, int times) {
			if (times == 0) {
				return block;
			}
			// calculate adjustment
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int i = 0; i < size; i++) {
				rows[i] = 0;
				cols[i] = 0;
			}
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (block[i][j]) {
						rows[i]++;
						cols[j]++;
					}
				}
			}
			int left = -1;
			int up = -1;
			for (int i = 0; i < size; i++) {
				if (rows[i] > 0 && left == -1) {
					left = i;
				}
				if (cols[size - i - 1] > 0 && up == -1) {
					up = i;
				}
				if (left > -1 && up > -1) {
					break;
				}
			}
			boolean[][] copy = newBlock();
			
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
		private boolean cellCheck(boolean[][] block, int i, int j) {
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
		private boolean[][] blockCopy(boolean[][] block) {
			boolean[][] blockCopy = newBlock();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					blockCopy[i][j] = block[i][j];
				}
			}
			return blockCopy;
		}
		private boolean blockCheck(boolean[][] block) {
			return blocks.containsKey(getKeyString(block));
		}
		private int calculateRotation(boolean[][] block) {
			int[] rows = new int[size];
			int[] cols = new int[size];
			for (int i = 0; i < size; i++) {
				rows[i] = 0;
				cols[i] = 0;
			}
			for (int i = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (block[i][j]) {
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

		private boolean[][] format(boolean[][] block) {
			int rot = calculateRotation(block);
			block = rotate(block, rot);
			return block;
		}
		private boolean[][] format(boolean[][] block, int rot) {
			rot = rot % 4;
			block = rotate(block, rot);
			return block;
		}
		private void add(boolean[][] block) {
			block = format(block);
			if (blockCheck(format(block, 0))) {
				return;
			}
			if (blockCheck(format(block, 1))) {
				return;
			}
			if (blockCheck(format(block, 2))) {
				return;
			}
			if (blockCheck(format(block, 3))) {
				return;
			}
			addToList(block);
		}
		private void addToList(boolean[][] block) {
			String key = getKeyString(block);
			blocks.put(key, null);
			print(false);
		}
		private String getKeyString(boolean[][] block) {
			char[] keyChars = new char[size];
			int track = 0;
			for (int i = 0, count = 0; i < size; i++) {
				for (int j = 0; j + i < size; j++) {
					if (track == size) {
						break;
					}
					if (block[i][j]) {
						keyChars[track] = (char) (count + 48);
						count = 0;
						track++;
					}
					else {
						count++;
					}
				}
				if (track == size) {
					break;
				}
			}
			String key = String.valueOf(keyChars);
//			compress(key);
			return key;
		}
//		private String compress(String key) {
//			Object[] tags = compressMap.keySet().toArray();
//			for (int i = 0; i < tags.length; i++) {
//				String tag = (String) tags[i];
//				key = key.replaceAll(tag, compressMap.get(tag));
//			}
//			return key;
//		}
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
			Object[] keySet = blocks.keySet().toArray();
			for (int k = 0; k < keySet.length; k++) {
				String key = (String) keySet[k];
//				System.out.println("---------------------" + (k + 1));
//				int pos = 0;
//				for (int i = 0; i < size; i++) {
//					String print = key.substring(pos, pos + (size - i));
//					pos += (size - i);
//					for (int j = 0; j < print.length(); j++) {
//						System.out.print(print.charAt(j) == 1 ? "o " : "  ");
//					}
//					System.out.println();
//				}
				System.out.println(key);
			}
		}
		private boolean[][] newBlock() {
			newBlocks++;
			boolean[][] newBlock = new boolean[size][];
			for (int i = 0; i < size; i++) {
				newBlock[i] = new boolean[size - i];
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

