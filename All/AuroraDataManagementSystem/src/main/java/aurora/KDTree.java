package aurora;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KDTree {
	//���ڵ�
	private KDTreeNode root=null;
	//�������нڵ�
	private List<KDTreeNode> treeNodes=new ArrayList<KDTreeNode>();
	//���k�������
	private List<KDTreeNode> minTreeNodes=new ArrayList<KDTreeNode>(); 
	//����ά��
	private int n;
	//m��ʾȡ�����ĵ�mά��Ϊ�������ݣ���ʼֵΪ0
	private int m=0;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public KDTree(){
		
	}
	
	/**
	 * ���ݴ���������ز��������ú�����������Խڵ���������k���ڵ�
	 * @param knnNodesList���Ѵ��ڲ��ҷֺ����knn�ڵ�
	 * @param testNode��������Ľڵ�
	 * @param k������k��������k����������Ľڵ�
	 * @return, ���ط��������������k���ڵ�
	 */
	public List<KNNNode> getKNearstNodes(List<KNNNode> knnNodesList, KNNNode testNode, int k){
		this.setN(testNode.getData().size());
		buildKDTree(knnNodesList,null,0,0);//��ʼʱ���ڵ�Ϊnull,���ѵ�Ϊ0
		search(this.getRoot(), testNode, k);//���÷��������������k���ڵ㣬����������minTreeNodes��
		//display(knnNodesList,testNode);
		List<KNNNode> KNearstNodes=new ArrayList<KNNNode>();
		for(KDTreeNode kNode:minTreeNodes){
			KNNNode node=new KNNNode(kNode.getIndex(), kNode.getData(), kNode.getCategory());
			KNearstNodes.add(node);
		}
		return KNearstNodes;
		
	}
	
	/**
	 * ����һ��knnNodes����kdTree
	 * @param knnNodes
	 */
	public void buildKDTree(List<KNNNode> knnNodes, KDTreeNode parentNode, int m, int child){
		if(knnNodes==null||knnNodes.size()==0)
			return;
		m=m%n;//ѡȡ���ѵ�m,m��ʼֵΪ0���Ժ�ÿ��ݼӲ�����ά������
		knnNodes=sortByPos(knnNodes,m);
		//ȡ��λ������Ϊ���ڵ�
		int k=(knnNodes.size())/2;
		//�������ڵ�
		KDTreeNode rootNode=new KDTreeNode(knnNodes.get(k));
		rootNode.setSplit(m);
		if(child==0){//child=0˵���ǹ����������ĸ��ڵ㣬���ڵ�û�и��ڵ�
			this.setRoot(rootNode);
		}
		else{
			if(child==-1){//child=-1˵���ǹ���parentNode��������
				parentNode.setLeft(rootNode);
				rootNode.setParent(parentNode);
			}
			else if(child==1){
				parentNode.setRight(rootNode);
				rootNode.setParent(parentNode);
			}
		}
		treeNodes.add(rootNode);
		if(knnNodes.size()==1)//˵��������������������Ϊ��
			return;
		else{
			List<KNNNode> dataLeft=knnNodes.subList(0, k);
			buildKDTree(dataLeft,rootNode,m+1,-1);
			if(knnNodes.size()>2){//ֻ��knnNodes.size����2ʱ�Ż���������
				List<KNNNode> dataRight=knnNodes.subList(k+1, knnNodes.size());
				buildKDTree(dataRight,rootNode,m+1,1);
			}//if
		}//else
	}
	

	//�����������ݵľ���(ŷ�Ͼ���)
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
	
	//�Խڵ㰴��ĳһλ��������
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
	 * @param root,���ĸ��ڵ�
	 * @param vec����������������
	 * @param distance����������С����
	 * @return��������������������������С��distance���ĵ�ļ���
	 */
	public void search(KDTreeNode root, KNNNode knnNode, int k){
		if(root==null||knnNode==null)
			return;
		//��ͨ�����������ҵ�����ڵĽ��Ƶ㣨Ҷ�ӽڵ㣩
		Stack<KDTreeNode> pathNodes=getPathNodes(root,knnNode);//��¼����·���ϵ����нڵ㣬������ݲ���
		while(!pathNodes.isEmpty())
		{
			KDTreeNode node=pathNodes.pop();
			if (minTreeNodes.size()<k){//�����ʼʱ��û���жϵ�k���ڵ㣬ֱ�ӽ��ýڵ����k������б�Ȼ��������һ�ߵ��ӿռ�����
				node.setDistance(getDistance(node.getData(),knnNode.getData()));
				addMinKDTreeNode(node);//���ýڵ�����б�
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
					getMaxNode();//�滻�ڵ�����½�distance���Ľڵ�����±�Ϊ0��λ��
					if(!node.isLeaf()){//����ýڵ��Ƿ�Ҷ�ӽڵ㣬������ж��Ƿ���Ҫ�����ӿռ���м���
						int split=node.getSplit();
						if(Math.abs(node.getData().get(split)-knnNode.getData().get(split))<minTreeNodes.get(0).getDistance()){
							if(knnNode.getData().get(split)<=node.getData().get(split)){////����ϸ��ڵ�λ�����ӿռ䣬��ô�������ӿռ�����
								search(node.getRight(),knnNode,k);
							}
							else{//����ϸ��ڵ�λ�����ӿռ䣬��ô�������ӿռ�����
								search(node.getLeft(),knnNode,k);
							}
						}
					}
				}
			}
		}//while
	}
	
	
	
	/**
	 * ͨ���������������Ƚϴ���ڵ�ͷ��ѽڵ�ķ���ά��ֵ��С�ڵ��ھͽ�����������֧�����ھͽ�����������֧��ֱ��Ҷ�ӽڵ㣩��
	 * @param root,ÿ��ִ�еĸ��ڵ�
	 * @param knnNode,������knn�ڵ�
	 * @return�����صõ�������·��
	 */
	public Stack<KDTreeNode> getPathNodes(KDTreeNode root, KNNNode knnNode){
		Stack<KDTreeNode> pathNode=new Stack<KDTreeNode>();
		KDTreeNode kNode=root;
		pathNode.push(root);
		while(!kNode.isLeaf()){//һֱ�ҵ�Ҷ�ӽڵ�
			int split=kNode.getSplit();
			if(knnNode.getData().get(split)<=kNode.getData().get(split)){//С�ڵ��ڽ���������
				if(kNode.getLeft()!=null){
					kNode=kNode.getLeft();
					pathNode.push(kNode);
				}
				else
					break;
			}
			else{//���ڽ���������
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
	 * ÿ���滻��������б���distance���Ľڵ�����²����б���distance���Ľڵ㣬����������±�0��λ��
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
	 * ÿ����������б��в���ڵ�ʱ��������һ�β������distance�Ĳ���,����ӵ�����distance�Ľڵ�����±�0��λ��
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
