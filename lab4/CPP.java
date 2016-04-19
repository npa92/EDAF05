package lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class CPP {

	private Scanner scanner;
	private Map<String, Point> points;
	private List<Point> pXPoint;
	private List<Point> pYPoint;

	public CPP() {
	}

	public void readFile(File file) {
		points = new HashMap<String, Point>();
		String s, name = "";
		String fileName = "";
		pXPoint = new ArrayList<Point>();
		pYPoint = new ArrayList<Point>();
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		while (scanner.hasNext()) {
			s = scanner.nextLine();
			if (s.contains("EOF")) {
				break;
			}
			if (s.contains("NAME")) {
				fileName = s;
			}
			while (i == 0 && !s.contains("NODE_COORD_SECTION")) {
				s = scanner.nextLine();
			}
			if (i == 0) {
				s = scanner.nextLine();
			}
			String[] split = s.replaceAll("\\s+", " ").trim().split(" ");
			name = split[0];
			double xCoord = Double.parseDouble(split[1]);
			double yCoord = Double.parseDouble(split[2]);
			points.put(name, new Point(xCoord, yCoord));
			pXPoint.add(new Point(xCoord, yCoord));
			pYPoint.add(new Point(xCoord, yCoord));
			i++;
		}
		Collections.sort(pXPoint, new Point()); // Sortera efter X
		Collections.sort(pYPoint); // Sortera efter Y
		System.out.println(fileName + " minsta avståndet: " + algorithm(pXPoint, pYPoint));
	}

	public double algorithm(List<Point> pXPoint, List<Point> pYPoint) {
		this.pXPoint = pXPoint;
		this.pYPoint = pYPoint;
		if (Math.abs(this.pXPoint.size()) <= 3) {
			return bruteForceList(pXPoint);
		}
		List<Point> qXPoint = new ArrayList<Point>();
		List<Point> qYPoint = new ArrayList<Point>();
		List<Point> rXPoint = new ArrayList<Point>();
		List<Point> rYPoint = new ArrayList<Point>();
		int n = pXPoint.size() / 2;
		for (int i = 0; i < n; i++) {
			qXPoint.add(pXPoint.get(i));
			qYPoint.add(pXPoint.get(i));
		}

		for (int i = n; i < pXPoint.size(); i++) {
			rXPoint.add(pXPoint.get(i));
			rYPoint.add(pXPoint.get(i));
		}
		double qMin = algorithm(qXPoint, qYPoint);
		double rMin = algorithm(rXPoint, rYPoint);
		double delta = (qMin < rMin) ? qMin : rMin;
		double xStar = qXPoint.get(qXPoint.size() - 1).getX();
		List<Point> sY = new ArrayList<Point>();
		for (Point p : pXPoint) {
			if (p.distCheck(delta, xStar)) {
				sY.add(p);
			}
		}
		Collections.sort(sY); // Sortera efter Y
		double sYMinDist = Double.MAX_VALUE;
		for (int i = 0; i < sY.size(); i++) {
			Point yPoint = sY.get(i);
			// Kolla 15 pointsen, än så länge kolla bara över sY.size() - ger O(n^2).
			for (int j = i + 1; j < sY.size(); j++) {
				Point pointToCheck = sY.get(j);
				if (yPoint.distanceToOtherPoint(pointToCheck) < sYMinDist) {
					sYMinDist = yPoint.distanceToOtherPoint(sY.get(j));
				}
			}
		}
		if (sYMinDist < delta) {
			return sYMinDist;
		} else if (qMin < rMin) {
			return qMin;
		} else {
			return rMin;
		}
	}

	public void readAllFiles() {
		File folder = new File("C:/[Your filepath]");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().contains("tsp")) {
				readFile(file);
			}
		}
	}

	public double bruteForceList(List<Point> theList) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < theList.size(); i++) {
			Point p = theList.get(i);
			for (int j = 0; j < theList.size(); j++) {
				Point other = theList.get(j);
				if (!p.equals(other)) {
					double dist = p.distanceToOtherPoint(other);
					if (dist < min) {
						min = dist;
					}
				}
			}
		}
		return min;
	}

	public static void main(String[] args) throws URISyntaxException {
		CPP c = new CPP();
		long time = System.currentTimeMillis();
		c.readAllFiles();
		System.out.println("Tog " + (System.currentTimeMillis() - time) + " millisekunder.");
	}
}
