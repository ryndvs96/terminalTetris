package blocks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BlockAnalysis {
	final static String fileName = "/home/ryan/tetris/terminalTetris/pentris/src/blocks/blockLog.txt";
	public static void main(String[] args) {
		Map<String, Integer> myMap = new TreeMap<String, Integer>();
		Scanner scan = null;
		File file = new File(fileName);
		try {
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String block = scan.nextLine();
				for (int i = 0; i + 1 < block.length(); i++) {
					String key = block.substring(i, i + 2);
					if (myMap.containsKey(key)) {
						myMap.put(key, ((Integer) myMap.get(key)) + 1);
					}
					else {
						myMap.put(key, 1);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			scan.close();
		}
		Object[] keys = myMap.keySet().toArray();
		Map<Integer, String> newMap = new TreeMap<Integer, String>();
		for (int i = 0; i < keys.length; i++) {
			String key = (String) keys[i];
			int val = myMap.get(key).intValue();
			newMap.put(val, key);
		}
		Object[] vals = newMap.keySet().toArray();
		for (int i = 0; i < vals.length; i++) {
			int key = (int) vals[i];
			String val = newMap.get(key);
			System.out.println(key + " : " + val);
		}
	}
}
