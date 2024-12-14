package implementations;

import java.io.Serializable;

/**
 * A node in a binary search tree (BST).
 *
 * @param <T> the type of elements in the node, which must be comparable
 */
public class BSTreeNode<T extends Comparable<T>> implements Serializable {
	private T element;
	private BSTreeNode<T> left;
	private BSTreeNode<T> right;

	/**
	 * Constructs a new BSTreeNode with the specified element.
	 *
	 * @param element the element to store in the node
	 */
	public BSTreeNode(T element) {
		this.element = element;
		this.left = null;
		this.right = null;
	}

	/**
	 * Returns the element stored in the node.
	 *
	 * @return the element stored in the node
	 */
	public T getElement() {
		return element;
	}

	/**
	 * Sets the element stored in the node.
	 *
	 * @param element the element to store in the node
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * Returns the left child of the node.
	 *
	 * @return the left child of the node
	 */
	public BSTreeNode<T> getLeft() {
		return left;
	}

	/**
	 * Sets the left child of the node.
	 *
	 * @param left the left child to set
	 */
	public void setLeft(BSTreeNode<T> left) {
		this.left = left;
	}

	/**
	 * Returns the right child of the node.
	 *
	 * @return the right child of the node
	 */
	public BSTreeNode<T> getRight() {
		return right;
	}

	/**
	 * Sets the right child of the node.
	 *
	 * @param right the right child to set
	 */
	public void setRight(BSTreeNode<T> right) {
		this.right = right;
	}
}