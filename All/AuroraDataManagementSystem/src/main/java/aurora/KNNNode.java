package aurora;

import java.util.ArrayList;
import java.util.List;

public class KNNNode {
	private int index;
	private List<Float> data=new ArrayList<Float>();
	private Float distance;
	private int category;
	
	public KNNNode(){
		
	}
	
	public KNNNode(List<Float> data){
		this.data=data;
	}
	
	public KNNNode(int index, List<Float> data, int c){
		this.index=index;
		this.data=data;
		this.category=c;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<Float> getData() {
		return data;
	}
	public void setData(List<Float> data) {
		this.data = data;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	

}

