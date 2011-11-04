package br.unifor.mia.formais.util.tree;

public class BinaryTree <T> {

	private BinaryTree<T> leftNode;
	private T operator;
	private BinaryTree<T> rightNode;
	
	private BinaryTree<T> root;

	public BinaryTree<T> getRoot() {
		return root;
	}

	public void setRoot(BinaryTree<T> root) {
		this.root = root;
	}

	public BinaryTree(T operator){
		this.operator = operator;
	}
	
	public BinaryTree<T> getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(BinaryTree<T> leftNode) {
		this.leftNode = leftNode;
	}
	public BinaryTree<T> getRightNode() {
		return rightNode;
	}
	public void setRightNode(BinaryTree<T> rightNode) {
		this.rightNode = rightNode;
	}
	public T getOperator() {
		return operator;
	}
	
	public void setOperator(T operator) {
		this.operator = operator;
	}
	
	public String toString(){
		return getOperator().toString();
	}
}
