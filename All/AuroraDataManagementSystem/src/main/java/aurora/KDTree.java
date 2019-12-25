package aurora;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KDTree {
	//根节点
	private KDTreeNode root=null;
	//树的所有节点
	private List<KDTreeNode> treeNodes=new ArrayList<KDTreeNode>();
	//存放k个最近邻
	private List<KDTreeNode> minTreeNodes=new ArrayList<KDTreeNode>(); 
	//树的维度
	private int n;
	//m表示取向量的第m维作为排序依据，初始值为0
	private int m=0;
	
	/**
	 * 默认构造函数
	 */
	public KDTree(){
		
	}
	
	/**
	 * 根据传过来的相关参数，调用函数计算与测试节点距离最近的k个节点
	 * @param knnNodesList，已存在并且分好类的knn节点
	 * @param testNode，待分类的节点
	 * @param k，参数k，即返回k个距离最近的节点
	 * @return, 返回符合条件的最近的k个节点
	 */
	public List<KNNNode> getKNearstNodes(List<KNNNode> knnNodesList, KNNNode testNode, int k){
		this.setN(testNode.getData().size());
		buildKDTree(knnNodesList,null,0,0);//初始时根节点为null,分裂点为0
		search(this.getRoot(), testNode, k);//调用方法，搜索最近的k个节点，将结果存放在minTreeNodes中
		//display(knnNodesList,testNode);
		List<KNNNode> KNearstNodes=new ArrayList<KNNNode>();
		for(KDTreeNode kNode:minTreeNodes){
			KNNNode node=new KNNNode(kNode.getIndex(), kNode.getData(), kNode.getCategory());
			KNearstNodes.add(node);
		}
		return KNearstNodes;
		
	}
	
	/**
	 * 根据一组knnNodes构造kdTree
	 * @param knnNodes
	 */
	public void buildKDTree(List<KNNNode> knnNodes, KDTreeNode parentNode, int m, int child){
		if(knnNodes==null||knnNodes.size()==0)
			return;
		m=m%n;//选取分裂点m,m初始值为0，以后每层递加并对总维数求余
		knnNodes=sortByPos(knnNodes,m);
		//取中位数，作为根节点
		int k=(knnNodes.size())/2;
		//建立根节点
		KDTreeNode rootNode=new KDTreeNode(knnNodes.get(k));
		rootNode.setSplit(m);
		if(child==0){//child=0说明是构建整棵树的根节点，根节点没有父节点
			this.setRoot(rootNode);
		}
		else{
			if(child==-1){//child=-1说明是构建parentNode的左子树
				parentNode.setLeft(rootNode);
				rootNode.setParent(parentNode);
			}
			else if(child==1){
				parentNode.setRight(rootNode);
				rootNode.setParent(parentNode);
			}
		}
		treeNodes.add(rootNode);
		if(knnNodes.size()==1)//说明其左子树和右子树均为空
			return;
		else{
			List<KNNNode> dataLeft=knnNodes.subList(0, k);
			buildKDTree(dataLeft,rootNode,m+1,-1);
			if(knnNodes.size()>2){//只有knnNodes.size大于2时才会有右子树
				List<KNNNode> dataRight=knnNodes.subList(k+1, knnNodes.size());
				buildKDTree(dataRight,rootNode,m+1,1);
			}//if
		}//else
	}
	

	//计算两个数据的距离(欧氏距离)
	/**
	 * 
	 * @param list
	 * @param list2
	 * @return
	 */
	public float getDistance(List<Float> list, List<Float> list2){
		double count=0;
		for(int i=0;i<list.size();i++){
			double div=list.get(i)-list2.get(i);
			count+=div*div;
		}
		return (float) Math.sqrt(count);
	}
	
	//对节点按照某一位进行排序
	/**
	 * 
	 * @param data
	 * @param pos
	 * @return
	 */
	public List<KNNNode> sortByPos(List<KNNNode> knnNodes, int pos){
		for(int i=0;i<knnNodes.size();i++){
			int k=i;
			for(int j=i+1;j<knnNodes.size();j++){
				KNNNode nodeK=knnNodes.get(k);
				KNNNode nodeJ=knnNodes.get(j);
				if((nodeJ.getData().get(pos))<(nodeK.getData().get(pos)))
					k=j;
			}//for j
			if(i!=k){
				KNNNode tempNode=knnNodes.get(i);
				knnNodes.set(i, knnNodes.get(k));
				knnNodes.set(k, tempNode);
			}
		}//for i
		return knnNodes;
	}
	
	/**
	 * 
	 * @param root,树的根节点
	 * @param vec，给定的数据向量
	 * @param distance，给定的最小距离
	 * @return，返回满足条件（与给顶点距离小于distance）的点的集合
	 */
	public void search(KDTreeNode root, KNNNode knnNode, int k){
		if(root==null||knnNode==null)
			return;
		//先通过二叉搜索找到最近邻的近似点（叶子节点）
		Stack<KDTreeNode> pathNodes=getPathNodes(root,knnNode);//记录搜索路径上的所有节点，方便回溯查找
		while(!pathNodes.isEmpty())
		{
			KDTreeNode node=pathNodes.pop();
			if (minTreeNodes.size()<k){//如果开始时还没有判断到k个节点，直接将该节点加入k最近邻列表，然后跳到另一边的子空间搜索
				node.setDistance(getDistance(node.getData(),knnNode.getData()));
				addMinKDTreeNode(node);//将该节点加入列表
				if(!node.isLeaf()){
					int split=node.getSplit();
					if((knnNode.getData().get(split)<=node.getData().get(split))&&node.getRight()!=null){
						search(node.getRight(),knnNode,k);
					}
					else if((knnNode.getData().get(split)>node.getData().get(split))&&node.getLeft()!=null){
						search(node.getLeft(),knnNode,k);
					}
					
				}

			}
			else{
				node.setDistance(getDistance(node.getData(),knnNode.getData()));
				if(node.getDistance()<minTreeNodes.get(0).getDistance()){
					minTreeNodes.set(0, node);
					getMaxNode();//替换节点后重新将distance最大的节点放在下标为0的位置
					if(!node.isLeaf()){//如果该节点是非叶子节点，则继续判断是否需要进入子空间进行检索
						int split=node.getSplit();
						if(Math.abs(node.getData().get(split)-knnNode.getData().get(split))<minTreeNodes.get(0).getDistance()){
							if(knnNode.getData().get(split)<=node.getData().get(split)){////如果上个节点位于左子空间，那么跳到右子空间搜索
								search(node.getRight(),knnNode,k);
							}
							else{//如果上个节点位于右子空间，那么跳到左子空间搜索
								search(node.getLeft(),knnNode,k);
							}
						}
					}
				}
			}
		}//while
	}
	
	
	
	/**
	 * 通过二叉树搜索（比较待查节点和分裂节点的分裂维的值，小于等于就进入左子树分支，大于就进入右子树分支，直到叶子节点）。
	 * @param root,每次执行的根节点
	 * @param knnNode,给定的knn节点
	 * @return，返回得到的搜索路径
	 */
	public Stack<KDTreeNode> getPathNodes(KDTreeNode root, KNNNode knnNode){
		Stack<KDTreeNode> pathNode=new Stack<KDTreeNode>();
		KDTreeNode kNode=root;
		pathNode.push(root);
		while(!kNode.isLeaf()){//一直找到叶子节点
			int split=kNode.getSplit();
			if(knnNode.getData().get(split)<=kNode.getData().get(split)){//小于等于进入左子树
				if(kNode.getLeft()!=null){
					kNode=kNode.getLeft();
					pathNode.push(kNode);
				}
				else
					break;
			}
			else{//大于进入右子树
				if(kNode.getRight()!=null){
					kNode=kNode.getRight();
					pathNode.push(kNode);
				}
				else
					break;
			}
		}//while
		return pathNode;
	}
	
	/**
	 * 每次替换掉最近邻列表中distance最大的节点后，重新查找列表中distance最大的节点，并将其放在下标0的位置
	 */
	public void getMaxNode(){
		int index=0;
		for(int i=1;i<minTreeNodes.size();i++){
			if(minTreeNodes.get(i).getDistance()>minTreeNodes.get(index).getDistance()){
				index=i;
			}
		}
		if(index!=0){
			KDTreeNode temNode=new KDTreeNode();
			temNode=minTreeNodes.get(0);
			minTreeNodes.set(0, minTreeNodes.get(index));
			minTreeNodes.set(index, temNode);
		}

	}
	
	/**
	 * 每次向最近邻列表中插入节点时，都进行一次查找最大distance的操作,并将拥有最大distance的节点放在下标0的位置
	 * @param kNode
	 */
	public void addMinKDTreeNode(KDTreeNode kNode){
		int index=0;
		if(minTreeNodes.size()==0||minTreeNodes==null)
			minTreeNodes.add(kNode);
		else{
			minTreeNodes.add(kNode);
			if(minTreeNodes.size()>1){
				for(int i=1;i<minTreeNodes.size();i++){
					if(minTreeNodes.get(i).getDistance()>minTreeNodes.get(index).getDistance())
						index=i;
				}
			}
		}
		if(index!=0){
			KDTreeNode temNode=new KDTreeNode();
			temNode=minTreeNodes.get(0);
			minTreeNodes.set(0, minTreeNodes.get(index));
			minTreeNodes.set(index, temNode);
		}
	}
	
	public void displayTree(){
		for(int i=0;i<treeNodes.size();i++){
			KDTreeNode node=treeNodes.get(i);
			if(node.getLeft()!=null&&node.getRight()!=null){
				System.out.println(node.getData()+"  left:"+node.getLeft().getData()+"  right:"+node.getRight().getData());
			}
			else if(node.getLeft()!=null){
				System.out.println(node.getData()+"  left:"+node.getLeft().getData());
			}
			else if(node.getRight()!=null)
				System.out.println(node.getData()+"  right:"+node.getRight().getData());
			else
				System.out.println(node.getData());
			
		}
	}
	
	public void display(List<KNNNode> knnNodeList, KNNNode test){
		for(int j=0;j<knnNodeList.size();j++){
			System.out.println(knnNodeList.get(j).getData()+": "+getDistance(knnNodeList.get(j).getData(),test.getData()));
		}
		if(minTreeNodes!=null){
			System.out.println("result:");
			for(int i=0;i<minTreeNodes.size();i++){
				System.out.println(minTreeNodes.get(i).getData());
			}
		}
		else
			System.out.println("minTreeNodes is null");
	}
	

	public KDTreeNode getRoot() {
		return root;
	}

	public void setRoot(KDTreeNode root) {
		this.root = root;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	/*
	public static void main(String []args){
		List<KNNNode> knnNodesList=new ArrayList<KNNNode>();
		List<List<Double>> data=new ArrayList<List<Double>>();
		List<Double> point1=new ArrayList<Double>();
		point1.add(1.0);
		point1.add(3.0);
		KNNNode kn1=new KNNNode(1,point1,"1");
		knnNodesList.add(kn1);
		
		List<Double> point2=new ArrayList<Double>();
		point2.add(4.0);
		point2.add(5.0);
		KNNNode kn2=new KNNNode(2,point2,"2");
		knnNodesList.add(kn2);
		
		List<Double> point3=new ArrayList<Double>();
		point3.add(7.0);
		point3.add(6.0);
		KNNNode kn3=new KNNNode(3,point3,"3");
		knnNodesList.add(kn3);
		
		List<Double> point4=new ArrayList<Double>();
		point4.add(3.0);
		point4.add(4.0);
		KNNNode kn4=new KNNNode(4,point4,"4");
		knnNodesList.add(kn4);
		
		List<Double> point5=new ArrayList<Double>();
		point5.add(5.0);
		point5.add(9.0);
		KNNNode kn5=new KNNNode(5,point5,"5");
		knnNodesList.add(kn5);
		
		List<Double> point6=new ArrayList<Double>();
		point6.add(2.0);
		point6.add(4.0);
		KNNNode kn6=new KNNNode(6,point6,"6");
		knnNodesList.add(kn6);
		
		List<Double> point7=new ArrayList<Double>();
		point7.add(2.0);
		point7.add(6.0);
		KNNNode kn7=new KNNNode(7,point7,"7");
		knnNodesList.add(kn7);
		
		List<Double> point8=new ArrayList<Double>();
		point8.add(8.0);
		point8.add(5.0);
		KNNNode kn8=new KNNNode(8,point8,"8");
		knnNodesList.add(kn8);
		
		List<Double>test=new ArrayList<Double>();
		test.add(5.0);
		test.add(6.0);
		
		KNNNode testKnn=new KNNNode(test);
		
		KDTree kdt=new KDTree();
		kdt.setN(2);
		kdt.buildKDTree(knnNodesList,null,0,0);
		kdt.search(kdt.getRoot(), testKnn, 3);
		kdt.display(knnNodesList,testKnn);

	}
	*/

}
