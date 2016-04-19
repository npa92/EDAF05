package lab4;

import java.util.Comparator;

public class Point implements Comparable<Point>, Comparator<Point> {

	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(){
	}
	
	public boolean distCheck(double delta, double xStar) {
		if (Math.abs(this.x - xStar) <= delta) {
			return true;
		} else {
			return false;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distanceToOtherPoint(Point other) {
		return Math.sqrt(Math.pow((x - other.x), 2) + Math.pow((y - other.y), 2));
	}

	public String toString() {
		return x + ", " + y + " break ";
	}

	@Override
	public boolean equals(Object other) {
		Point o = (Point) other;
		return x == o.x && y == o.y;
	}

	//CompareTo kommer att jämföra via y-coord, då den används för att sortera sY.

	@Override
	public int compareTo(Point other) {
		if (other.y > this.y) {
			return -1;
			
		} else if (this.y > other.y) {
			return 1;
		} else {
			return 0;
		}	
	}
	
	//Compare används för att sortera över x-coord.
	
	@Override
	public int compare(Point current, Point other) {
		if (current.x - other.x > 0) {
			return 1;
		} else if (current.x - other.x < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
