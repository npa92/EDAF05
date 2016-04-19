package lab3;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class MST {

	private Scanner scanner;
	private LinkedList<String> cityList;
	private HashMap<String, HashMap<String, Integer>> outerMap;

	public MST() {
	}

	public void readFile(File file) {
		cityList = new LinkedList<String>();
		String s = "";
		outerMap = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> innerMap = new HashMap<String, Integer>();
		HashMap<String, Integer> innerMap2 = new HashMap<String, Integer>();
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scanner.hasNext() && !s.contains("--")) {
			s = scanner.nextLine();
			if (!s.contains("--")) {
				cityList.add(s);
				innerMap = new HashMap<String, Integer>();
				outerMap.put(s, innerMap);
			}
		}
		int numberBreaker = s.indexOf("[");
		int cityBreaker = s.indexOf("-");
		String firstCity = s.substring(0, cityBreaker) + " ";
		String secondCity = s.substring((cityBreaker) + 2, (s.indexOf("[") - 1)) + " ";
		int number = Integer.parseInt(s.substring(numberBreaker + 1, s.length() - 1));
		innerMap = outerMap.get(firstCity);
		innerMap.put(secondCity, number);
		innerMap2 = outerMap.get(secondCity);
		innerMap2.put(firstCity, number);
		while (scanner.hasNext()) {
			s = scanner.nextLine();
			cityBreaker = s.indexOf("--");
			numberBreaker = s.indexOf("[");
			firstCity = s.substring(0, cityBreaker) + " ";
			secondCity = s.substring((cityBreaker) + 2, s.indexOf("[") - 1) + " ";
			number = Integer.parseInt(s.substring(numberBreaker + 1, s.length() - 1));
			innerMap = outerMap.get(firstCity);
			innerMap.put(secondCity, number);
			innerMap2 = outerMap.get(secondCity);
			innerMap2.put(firstCity, number);
		}
	}

	public void algorithm() {
		PriorityQueue<City> queue = new PriorityQueue<City>();
		String startingCity = cityList.poll();
		City startCity = new City(startingCity, 0);
		queue.add(startCity);
		int total = 0;
		for (String s : cityList) {
			queue.add(new City(s, Integer.MAX_VALUE));
		}
		int i = 0;
		int dv = Integer.MAX_VALUE;
		while (!queue.isEmpty()) {
			City u = queue.poll();
			if (i != 0) {
				total += u.getDistance();
			}
			HashMap<String, Integer> map = outerMap.get(u.getName());
			for (Entry<String, Integer> e : map.entrySet()) {
				City v = new City(e.getKey(), e.getValue());
				int shittyC = 0;
				if (queue.contains(v)) {
					for (City lolCity : queue) {
						if (lolCity.equals(v)) {
							shittyC = lolCity.getDistance();
						}
					}
				}
				if (queue.contains(v) && (e.getValue() < shittyC)) {
					City temp = null;
					String name = "";
					int value = 0;
					for (City city : queue) {
						if (city.getName().equals(e.getKey())) {
							temp = city;
							name = city.getName();
							value = e.getValue();
						}
					}
					queue.remove(temp);
					queue.add(new City(name, value));
					dv = u.getDistance() + e.getValue();
				}
			}
			i++;
		}
		System.out.println(total);
	}

	public static void main(String[] args) throws URISyntaxException {
		MST mst = new MST();
		URL url = MST.class.getResource("USA-highway-miles.in");
		File file = new File(url.toURI());
		mst.readFile(file);
		mst.algorithm();
	}
}