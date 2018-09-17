package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class AA {
	public static void main(String[] args) {
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

		HashMap<String, HashMap<String, Float>> list_prob = new HashMap<>();

		for (int i = 1; i < list_lan.length; ++i)
			list_prob.put(list_lan[i], new HashMap<>());

		while (sc.hasNextLine()) {
			String[] tmp = sc.nextLine().split(",");
			for (int i = 1; i < list_lan.length; ++i) {
				float prob = Float.valueOf(tmp[i]);
				if (prob > 0.0f)
					list_prob.get(list_lan[i]).put(tmp[0], prob);
			}

		}

		HashMap<String, Float> pp = list_prob.get("ger");
		for (Entry<String, Float> e : pp.entrySet())
			System.out.println(e);

	}
}
