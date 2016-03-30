package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

public class StableMarriage {
	LinkedList<Integer> freeMen;
	int n;
	LinkedList<Integer>[] menPref;
	LinkedList<Integer>[] womenPref;
	String[] names;
	int[] wife;
	int[] husband;
	private PrintWriter pw;

	public StableMarriage() {

	}

	@SuppressWarnings("unchecked")
	public void read(File filePath) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String s;
		while (scanner.hasNext()) {
			s = scanner.nextLine();
			while (s.startsWith("#")) {
				s = scanner.nextLine();
			}
			if (s.startsWith("n")) {
				String allPeople = s.substring(2, s.length());
				n = Integer.parseInt(allPeople);
				names = new String[2 * n];
				int i = 0;

				menPref = (LinkedList<Integer>[]) new LinkedList<?>[n];
				womenPref = (LinkedList<Integer>[]) new LinkedList<?>[n];
				freeMen = new LinkedList<Integer>();
				wife = new int[n];
				husband = new int[n];

				while (true) {
					if (i <= names.length - 1) {
						s = scanner.nextLine();
						String person = s.substring(s.lastIndexOf(" ") + 1, s.length());
						names[i] = person;
						i++;

					} else {
						break;
					}
				}
				s = scanner.nextLine();

				while (scanner.hasNext()) {
					s = scanner.nextLine();
					String sex = s.substring(0, s.lastIndexOf(':'));
					int sex2 = Integer.parseInt(sex);
					if (sex2 % 2 != 0) {
						menPref[sex2 / 2] = new LinkedList<Integer>();
						s = s.substring(s.lastIndexOf(":") + 1, s.length());
						s = s.substring(1, s.length());
						String[] splits = s.split(" ");
						freeMen.add(sex2);
						for (int j = 0; j < n; j++) {
							menPref[sex2 / 2].add(Integer.parseInt(splits[j]));
						}

					} else {
						womenPref[sex2 / 2 - 1] = new LinkedList<Integer>();
						s = s.substring(s.lastIndexOf(":") + 1, s.length());
						s = s.substring(1, s.length());
						String[] splits = s.split(" ");
						for (int j = 0; j < n; j++) {
							womenPref[sex2 / 2 - 1].add(Integer.parseInt(splits[j]));
						}
					}
				}
			}
		}

	}

	public void algorithm() {

		while (!freeMen.isEmpty()) {
			int currentManID = freeMen.poll();
			LinkedList<Integer> currentList = menPref[currentManID / 2];
			while (!currentList.isEmpty()) {
				int currentWomanID = currentList.poll();
				if (husband[currentWomanID / 2 - 1] == 0) {
					wife[currentManID / 2] = currentWomanID;
					husband[currentWomanID / 2 - 1] = currentManID;
					break;
				} else {
					LinkedList<Integer> currentWList = womenPref[currentWomanID / 2 - 1];
					int marriage = 0;
					int affair = 0;
					for (int i = 0; i < currentWList.size(); i++) {

						if (husband[currentWomanID / 2 - 1] == currentWList.get(i)) {
							marriage = i;
						}
						if (currentWList.get(i).equals(currentManID)) {
							affair = i;
						}
					}
					if (marriage > affair) {
						int lonely = husband[currentWomanID / 2 - 1]; // / 2 -1
																		// ?
						wife[lonely / 2] = 0;
						wife[currentManID / 2] = currentWomanID;
						husband[currentWomanID / 2 - 1] = currentManID;
//						System.out.println("Lägger in " + (lonely) + " i freeMen");
						freeMen.add(lonely);
						break;
					}
				}
			}
		}
		File newFile = new File("results.txt");
				try {
					pw = new PrintWriter(newFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		for (int i = 0; i < wife.length; i++) {
			int woman = wife[i];
//			System.out.println(names[i*2] + " -- " + names[woman-1]);
			pw.println(names[i*2] + " -- " + names[woman-1]);
		}
		pw.close();
	}

	
	public static void main(String[] args) throws URISyntaxException {
		StableMarriage sb = new StableMarriage();
		URL url = StableMarriage.class.getResource("sm-random-500-in.txt");
		File file = new File(url.toURI());
		sb.read(file);
		sb.algorithm();
	}

}
