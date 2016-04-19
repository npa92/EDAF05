package lab3;

public class City implements Comparable<City> {

	private String s;
	private int distance;

	public City(String s, int distance) {
		this.s = s;
		this.distance = distance;
	}

	public int getDistance(){
		return distance;
	}
	
	public String getName(){
		return s;
	}
	
	
	public void setDistance(int n){
		distance = n;
	}
	
	@Override
	public boolean equals(Object other) {
		City o = (City) other;
		return s.equals(o.s);
	}
	
	public String toString() {
		return s;
	}

	@Override
	public int compareTo(City other) {
		if (other.distance > this.distance) {
			return -1;
		} else if (other.distance == this.distance) {
			return 0;
		} else {
			return 1;
		}
	}

}
