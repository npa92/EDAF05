package lab1;

import java.util.LinkedList;

public class Woman {
	private LinkedList<Man> prefList;
	private boolean isEngaged;
	private int next;
	private Man currentMan;
	
	public Woman(LinkedList<Man> prefList) {
		this.prefList = prefList;
		isEngaged = false;
		next = 0;
		currentMan = null;
	}
	
	public void becomeEngaged(Man m){
		isEngaged = true;
		currentMan = m;
	}
	
	public Man getPreferredMan(){
		Man m = prefList.get(next);
		next++;
		return m;
	}
	
	
	public boolean getStatus(){
		return isEngaged;
	}
	
	public Man getCurrentMan(){
		return currentMan;
	}
	
	public int compareMen(Man m){
		return m.hashCode();
	}
	
}
