package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Huffman {

	HashMap<String, HashMap<Character, Float>> list_prob = null;
	HashMap<Character, Float> probMap = null;
	HashMap<Character, String> hufcode = null;
	Node head;

	public Huffman() {

		loadProb();
		probMap = list_prob.get(Lang.eng.name());
		probMap = list_prob.get(Lang.fra.name());
		probMap = list_prob.get(Lang.ger.name());
//		calcDist();
//		probMap = calcDistBible(Lang.eng);
//		probMap = calcDistBible(Lang.ger);

		hufcode = new HashMap<>();

		PriorityQueue<Node> pq = new PriorityQueue<>();
		for (Entry<Character, Float> e : probMap.entrySet()) {
			pq.add(new Node(e.getKey(), e.getValue()));
		}

		while (pq.size() > 1) {
			Node tmp1 = pq.poll();
			Node tmp2 = pq.poll();
			Node tmp = new Node(tmp1, tmp2);
			pq.add(tmp);
		}

		Node head = pq.poll();

		head.postorder("");

		for (Entry<Character, String> e : hufcode.entrySet()) {
			System.out.println(e);
		}

		float avgLen = 0.0f;
		for (Entry<Character, Float> e : probMap.entrySet()) {
			avgLen += e.getValue() * hufcode.get(e.getKey()).length();
//			System.out.println(e.getKey() + " " + e.getValue() + " " + hufcode.get(e.getKey()).length());
		}
//		avgLen /= probMap.size();
		System.out.println("Average length: " + avgLen);

	}

	public void calcDist() {
		probMap = new HashMap<>();
		probMap.put('a', 0.0817f);
		probMap.put('b', 0.0149f);
		probMap.put('c', 0.0278f);
		probMap.put('d', 0.0425f);
		probMap.put('e', 0.1270f);
		probMap.put('f', 0.0223f);
		probMap.put('g', 0.0202f);
		probMap.put('h', 0.0609f);
		probMap.put('i', 0.0697f);
		probMap.put('j', 0.0015f);
		probMap.put('k', 0.0077f);
		probMap.put('l', 0.0403f);
		probMap.put('m', 0.0241f);
		probMap.put('n', 0.0675f);
		probMap.put('o', 0.0751f);
		probMap.put('p', 0.0193f);
		probMap.put('q', 0.0010f);
		probMap.put('r', 0.0599f);
		probMap.put('s', 0.0633f);
		probMap.put('t', 0.0906f);
		probMap.put('u', 0.0276f);
		probMap.put('v', 0.0098f);
		probMap.put('w', 0.0236f);
		probMap.put('x', 0.0015f);
		probMap.put('y', 0.0197f);
		probMap.put('z', 0.0007f);
	}

	class Node implements Comparable<Node> {
		Node left = null;
		Node right = null;
		boolean hasData = false;
		char data;
		float prob = 0.0f;

		public Node(char data, float prob) {
			this(true, data, prob, null, null);
		}

		public Node(Node left, Node right) {
			this(false, '\0', left.prob + right.prob, left, right);
		}

		private Node(boolean hasData, char data, float prob, Node left, Node right) {
			this.hasData = hasData;
			this.data = data;
			this.prob = prob;
			this.left = left;
			this.right = right;
		}

		public String postorder(String str) {
			String ret = "";
			if (left != null)
				ret += left.postorder(str + "0");

			if (right != null)
				ret += right.postorder(str + "1");

			if (hasData) {
				System.out.println(str + " " + data + " " + prob);
				ret += data;
				hufcode.put(data, str);
			} else {
				System.out.println(str + " " + ret + " " + prob);
			}

			return ret;
		}

		@Override
		public int compareTo(Node o) {
			if (this.prob > o.prob)
				return 1;
			if (this.prob < o.prob)
				return -1;
			return 0;
		}

		@Override
		public String toString() {
			return data + " " + prob;
		}
	}

	public HashMap<Character, Float> calcDistBible(Lang lan) {
		String fileName = "bible_" + lan.name() + ".txt";
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		HashMap<Character, Long> frequency = new HashMap<>();
		long total = 0l;

		while (sc.hasNextLine()) {
			String str = sc.nextLine();
//			System.out.println(str);
			char[] tmp = str.toCharArray();
			total += tmp.length;
			for (char c : tmp) {
				if (frequency.containsKey(c))
					frequency.put(c, frequency.get(c) + 1l);
				else
					frequency.put(c, 1l);
			}
		}

		HashMap<Character, Float> probMap = new HashMap<>();

		float totalP = 0.0f;

		for (Entry<Character, Long> e : frequency.entrySet()) {
			float prob = (float) (e.getValue() / ((double) total));
			probMap.put(e.getKey(), prob);
			System.out.println(prob);
			totalP += prob;
		}
		System.out.println("TOTAL PROB " + totalP);

		if (sc != null) {
			sc.close();
			sc = null;
		}
		return probMap;
	}

	public void loadProb() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("letter_frequency.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String str = sc.nextLine();

		String[] list_lan = str.split(",");

		list_prob = new HashMap<>();

		for (int i = 1; i < list_lan.length; ++i)
			list_prob.put(list_lan[i], new HashMap<>());

		while (sc.hasNextLine()) {
			String[] tmp = sc.nextLine().split(",");
			for (int i = 1; i < list_lan.length; ++i) {
				float prob = Float.valueOf(tmp[i]);
				if (prob > 0.0f)
					list_prob.get(list_lan[i]).put(tmp[0].charAt(0), prob);
			}

		}

		HashMap<Character, Float> pp = list_prob.get("ger");
		for (Entry<Character, Float> e : pp.entrySet())
			System.out.println(e);

	}

	public static void main(String[] args) {
		new Huffman();
	}
}
