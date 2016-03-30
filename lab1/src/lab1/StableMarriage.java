package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;

public class StableMarriage {
	LinkedList<Integer> freeMen;
	int n;
	LinkedList<Integer>[] menPref;
	LinkedList<Integer>[] womenPref;
	String[] names;
	String[] wife;
	String[] husband;

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
				wife = new String[n];
				husband = new String[n];

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
				if (husband[currentWomanID / 2 - 1] == null) {
					wife[currentManID / 2] = names[currentWomanID - 1];
					husband[currentWomanID / 2 - 1] = names[currentManID - 1];
					break;
				} else {
					LinkedList<Integer> currentWList = womenPref[currentWomanID / 2 - 1];
					int marriage = 0;
					int affair = 0;
					System.out.println("Vem är " + currentWomanID + "'s man? " + husband[currentWomanID / 2 - 1]);
					for (int i = 0; i < currentWList.size(); i++) {
					
						/** Ändrade här till Integer.toString*/
						
						if (Integer.toString(currentWList.get(i)).equals(husband[currentWomanID / 2 - 1])) {
							marriage = i;
						}
						if (currentWList.get(i).equals(currentManID)) {
							affair = i;
						}
					}
					System.out.println(marriage + " " + affair);
					if (marriage > affair) {
						int lonely = currentWomanID; // / 2 -1 ?
						System.out.println(wife[lonely]);
						wife[lonely] = null;
						wife[currentManID / 2] = names[currentWomanID - 1];
						husband[currentWomanID / 2 - 1] = names[currentManID - 1];
						System.out.println("Lägger in " + (lonely - 1) + " i freeMen");
						freeMen.add(lonely - 1);
						System.out.println("DIVORCE!!!" + wife[currentManID / 2] + " GIFTER OM SIG MED "
								+ husband[currentWomanID / 2 - 1]);

						break;
					}

					System.out.println("ACCPETERADE EJ OMGIFTE " + husband[currentWomanID / 2 - 1]
							+ " WILL NEVER HAPPEN " + wife[currentManID / 2]);

				}
			}
		}
		for (int i = 0; i < wife.length; i++) {
			int index = 0;
			for (int k = 0; k < n * 2; k++) {
				if (names[k].equals(wife[i])) {
					index = k / 2;
				}

			}
			System.out.println(husband[index] + " -- " + wife[i]);
		}
	}
	
	

	public static void main(String[] args) throws URISyntaxException {
		StableMarriage sb = new StableMarriage();
		URL url = StableMarriage.class.getResource("sm-random-5-in.txt");
		File file = new File(url.toURI());
		sb.read(file);
		sb.algorithm();
	}

}
