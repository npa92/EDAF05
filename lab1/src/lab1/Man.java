package lab1;

import java.util.LinkedList;

public class Man {
	private LinkedList<Woman> prefList;
	private boolean isFree;
	private int next;
	
	public Man(LinkedList<Woman> prefList){
		this.prefList = prefList;
		isFree = true;
		next = 0;
	}
	
	public Woman getPreferredWoman(){
		Woman w = prefList.get(next);
		next++;
		return w;
	}
	
	public void propose(Woman w, int i){
		w.becomeEngaged(this);
	}
	
}
