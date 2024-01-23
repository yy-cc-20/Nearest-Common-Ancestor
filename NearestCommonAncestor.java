package pekingOnlineJudge;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

// Peking online judge #1330
/*
 * Sample Input

2
16
1 14
8 5
10 16
5 9
4 6
8 4
4 10
1 13
6 15
10 11
6 7
10 2
16 3
8 1
16 12
16 7
5
2 3
3 4
3 1
1 5
3 5
Sample Output

4
3
 */

class Node<T> {
	T value;
	Node<T> parent;
	
	public Node(Node<T> parent, T value) {
		this.parent = parent;
		this.value = value;
	}
	
	public void setParent(Node<T> parentNode) throws Exception {
		if (this.parent != null)
			throw new Exception("Multiple parents for a node is not allowed");
		
		this.parent = parentNode;
	}
	
	public Node<T> getParent() {
		return parent;
	}
	
	public T getValue() {
		return value;
	}
}

class Tree<T> {
	private HashSet<Node<T>> nodes;
	
	public Tree() {
		nodes = new HashSet<Node<T>>();
	}
	
	public void addNode(T parentValue, T value) throws Exception {
		// create parent node
		Node<T> parentNode = getNode(parentValue);
		if (parentNode == null) {
			parentNode = new Node<T>(null, parentValue);
			nodes.add(parentNode);
		}
		
		// create child node
		Node<T> childNode = getNode(value);
		if (childNode == null) {
			childNode = new Node<T>(parentNode, value);
			nodes.add(childNode);
		} else if (childNode.getParent() == null){
			childNode.setParent(parentNode);
		} 		
	}
	
	public Node<T> getNode(T value) {
		for (Node<T> node : nodes)
			if (node.getValue().equals(value))
				return node;
		return null;
	}

	public static <T> T findNearestCommonAncestor(Tree<T> tree, T valueA, T valueB) {
		LinkedHashSet<Node<T>> pathFromAToRoot = new LinkedHashSet<Node<T>>();
		
		Node<T> nodeA = tree.getNode(valueA);
		Node<T> nodeB = tree.getNode(valueB);
		
		Node<T> currentNode = nodeA;
		while (currentNode != null) {
			pathFromAToRoot.add(currentNode);
			currentNode = currentNode.getParent();
		}
		
		currentNode = nodeB;
		while (currentNode != null) {
			if (pathFromAToRoot.contains(currentNode))
				return currentNode.getValue();
			currentNode = currentNode.getParent();
		}
				
		return nodeA.getValue();
	}
}

public class NearestCommonAncestor {	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		
		int numberOfTestCases = input.nextInt();
		
		for (int testCase = 0; testCase < numberOfTestCases; testCase++) {
			int numberOfNodes = input.nextInt();
			Tree<Integer> tree = new Tree<Integer>();
			
			for (int node = 1; node < numberOfNodes; node++) {
				int parentNode = input.nextInt();
				int childNode = input.nextInt();
				tree.addNode(parentNode, childNode);
			}
			System.out.println(Tree.findNearestCommonAncestor(tree, input.nextInt(), input.nextInt()));
		}
		input.close();
	}
}
 