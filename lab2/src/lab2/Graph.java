package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import org.omg.Messaging.SyncScopeHelper;
import java.util.Queue;
import java.util.Scanner;

import lab2.Graph;

public class Graph {
	private Scanner scanner = null;
	private ArrayList<String> wordList;
	private Map<String, LinkedList<String>> adjacent;
	private int length;
	private Queue<String>[] L;
	private Map<String, Boolean> discovered;
	private PrintWriter pw;

	public Graph() {
	}

	@SuppressWarnings("unchecked")
	public void readWords(File words) {
		wordList = new ArrayList<String>();
		discovered = new HashMap<String, Boolean>();
		length = 0;
		try {
			scanner = new Scanner(words);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			wordList.add(s);
			length++;
			discovered.put(s, false);
		}
		adjacent = new HashMap<String, LinkedList<String>>();
		createGraph();
	}

	public void createGraph() {
		for (int i = 0; i < wordList.size(); i++) {
			String s = wordList.get(i);
			checkAdjacent(s, i);
		}
	}

	public void checkAdjacent(String s, int index) {
		adjacent.put(s, new LinkedList<String>());
		String lastFour = s.substring(s.length() - 4, s.length());
		for (int i = 0; i < wordList.size(); i++) {
			boolean found = true;
			String word = wordList.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(word);
			for (int j = 0; j < 4; j++) {
				String lol = Character.toString(lastFour.charAt(j));
				if (sb.lastIndexOf(lol) >= 0) {
					sb.setCharAt(word.indexOf(lastFour.charAt(j)), ' ');
				} else {
					found = false;
				}
			}
			if (found) {
				adjacent.get(s).add(word);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void bfs(String first, String last) {
		L = new LinkedList[wordList.size()];
		for (Entry<String, Boolean> e : discovered.entrySet()) {
			e.setValue(false);
		}
		discovered.put(first, true);
		L[0] = new LinkedList<String>();
		L[0].add(first);
		int layerCounter = 0;
		int counter = 0;
		String currentWord = first;
		outerloop: while (!L[layerCounter].isEmpty()) {
			L[layerCounter + 1] = new LinkedList<String>();
			int Lsize = L[layerCounter].size();
			for (int i = 0; i < Lsize; i++) {
				currentWord = L[layerCounter].poll();
				if (currentWord.equals(last)) {
					break outerloop;
				}
				int adjSize = adjacent.get(currentWord).size();
				for (int j = 0; j < adjSize; j++) {
					String adjacentWord = adjacent.get(currentWord).poll();
					adjacent.get(currentWord).add(adjacentWord);
					if (!discovered.get(adjacentWord).booleanValue()) {
						discovered.put(adjacentWord, true);
						L[layerCounter + 1].add(adjacentWord);
					}
				}
			}
			layerCounter++;
		}
		if (!discovered.get(last).booleanValue()) {
			layerCounter = -1;
		}
		pw.println(layerCounter);
//		System.out.println("ATT GÅ FRÅN " + first + " TILL " + last + " KOSTAR " + layerCounter);
	}

	public void readInput(File words) {
		String fullString = null;
		String s = null;
		String t = null;
		int n = 0;
		File newFile = new File("results.txt");
		try {
			pw = new PrintWriter(newFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			scanner = new Scanner(words);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			fullString = scanner.nextLine();
			n = fullString.indexOf(" ");
			s = fullString.substring(0, n);
			t = fullString.substring(n + 1, fullString.length());
			bfs(s, t);
		}
		pw.close();
	}

	public static void main(String[] args) throws URISyntaxException {
		Graph g = new Graph();
		URL url = Graph.class.getResource("words-5757.dat");
		File words = new File(url.toURI());
		g.readWords(words);
		url = Graph.class.getResource("words-5757-test.in");
		File path = new File(url.toURI());
		g.readInput(path);
	}
}
