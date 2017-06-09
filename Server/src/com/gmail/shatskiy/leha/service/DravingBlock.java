package com.gmail.shatskiy.leha.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class DravingBlock {
	private static final DravingBlock dravingBlock = new DravingBlock();
	private static Map<String, String> map;

	private DravingBlock() {
		createGame();
	}
	
	public static DravingBlock getDravingBlock(){
		return dravingBlock;
	}

	public void createGame() {
		map = new LinkedHashMap<>();

		map.put("A1", " ");
		map.put("B1", " ");
		map.put("C1", " ");

		map.put("A2", " ");
		map.put("B2", " ");
		map.put("C2", " ");

		map.put("A3", " ");
		map.put("B3", " ");
		map.put("C3", " ");
	}

	public String printDravingBlock() {

		StringBuilder builder = new StringBuilder();

		builder.append(" |A|B|C|");
		builder.append("\n");
		builder.append("--------");
		builder.append("\n");

		int count = 0;
		for (Map.Entry entry : map.entrySet()) {
			if (count < 3) {
				if (count == 0) {
					builder.append("1|");
				}
				String value = (String) entry.getValue();

				builder.append(value + "|");
				if (count == 2) {
					builder.append("\n");
				}
			} else if (3 <= count && count < 6) {
				if (count == 3) {
					builder.append("--------");
					builder.append("\n");
					builder.append("2|");
				}
				String value = (String) entry.getValue();

				builder.append(value + "|");
				if (count == 5) {
					builder.append("\n");
				}
			} else if (6 <= count) {
				if (count == 6) {
					builder.append("--------");
					builder.append("\n");
					builder.append("3|");
				}
				String value = (String) entry.getValue();

				builder.append(value + "|");
				if (count == 8) {
					builder.append("\n");
				}
			}
			count++;
		}
		return builder.toString();
	}

	public synchronized void makeMove(String key, String flag) {
		String keyMap = key.toUpperCase();
		if (map.get(keyMap) == " ") {
			map.put(keyMap, flag);
		}
	}

	public boolean isVinner() {

		String a1 = map.get("A1");
		String b1 = map.get("B1");
		String c1 = map.get("C1");

		String a2 = map.get("A2");
		String b2 = map.get("B2");
		String c2 = map.get("C2");

		String a3 = map.get("A3");
		String b3 = map.get("B3");
		String c3 = map.get("C3");

		if (a1.equals(b1) && a1.equals(c1) && !a1.equals(" ")) {
			return true;
		} else if (a2.equals(b2) && a2.equals(c2) && !a2.equals(" ")) {
			return true;
		} else if (a3.equals(b3) && a3.equals(c3) && !a3.equals(" ")) {
			return true;
		} else if (a1.equals(a2) && a2.equals(a3) && !a3.equals(" ")) {
			return true;
		} else if (b1.equals(b2) && b2.equals(b3) && !b3.equals(" ")) {
			return true;
		} else if (c1.equals(c2) && c2.equals(c3) && !c3.equals(" ")) {
			return true;
		} else if (a1.equals(b2) && b2.equals(c3) && !c3.equals(" ")) {
			return true;
		} else if (c1.equals(b2) && b2.equals(a3) && !a3.equals(" ")) {
			return true;
		} else {
			return false;
		}
	}
}
