package aurora;

import java.util.ArrayList;
import java.util.List;

public class KDTreeNode {
	//以下三项存储与KNNNode相关的信息，分别是KNN节点的数据，索引号和所属类别
	private List<Float> data=new ArrayList<Float>();
	private int index;
	private int category;
	private Float distance;
	private KDTreeNode left;
	private KDTreeNode right;
	private KDTreeNode parent;
	private boolean isRoot=false; 
	private int split;
	//默认构造函数
	public KDTreeNode(){
		
	}
	
	//根据节点数据的构造函数
//	public KDTreeNode(List<Float> dataList, String c){
//		this.data=dataList;
//		this.category=c;
//	}
//	
	public KDTreeNode(KNNNode knnNode){
		this.data=knnNode.getData();
		this.category=knnNode.getCategory();
		this.setIndex(knnNode.getIndex());
	}
	
	//判断是否为叶子节点
	public boolean isLeaf(){
		return(left==null&&right==null);
	}
	
	public List<Float> getData() {
		return data;
	}
	public void setData(List<Float> data) {
		this.data = data;
	}
	public KDTreeNode getLeft() {
		return left;
	}
	public void setLeft(KDTreeNode left) {
		this.left = left;
	}
	public KDTreeNode getRight() {
		return right;
	}
	public void setRight(KDTreeNode right) {
		this.right = right;
	}
	public KDTreeNode getParent() {
		return parent;
	}
	public void setParent(KDTreeNode parent) {
		this.parent = parent;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public int getSplit() {
		return split;
	}

	public void setSplit(int split) {
		this.split = split;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	

}

