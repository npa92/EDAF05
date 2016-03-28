package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class StableMarriage {
	LinkedList<Man> freeMen;
	int n;
	LinkedList<Integer>[] menPref;
	ArrayList<Integer>[] womenPref;
	String[] names;
	int[] partner;
	

	public void read(String filePath) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filePath));
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
				names = new String[2*Integer.parseInt(allPeople)];
				
			}
		}

	}

	public StableMarriage(LinkedList<Man> freeMen, LinkedList<Woman> women, LinkedList<Man> men) {
		int i = 0;
		int j = 0;
		while (!freeMen.isEmpty()) {
			Man m = freeMen.poll();
			Woman w = m.getPreferredWoman();
			if (!w.getStatus()) {
				m.propose(w, 5);
			} else {
				if (w.compareMen(m) > w.compareMen(w.getCurrentMan())) {

				}
			}
		}
	}
	
	public static void main (String[] args) {
		
	}

}
