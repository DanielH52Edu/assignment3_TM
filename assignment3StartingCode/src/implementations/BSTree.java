package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;

import java.util.NoSuchElementException;

/**
 * A binary search tree implementation.
 *
 * @param <T> the type of comparable elements in the tree. (Must be comparable)
 */
public class BSTree<T extends Comparable<T>> implements BSTreeADT<T> {

	/**
	 * The root node of the binary search tree.
	 */
	private BSTreeNode<T> root;

	/**
	 * The number of elements in the binary search tree.
	 */
	private int size;

	/**
	 * Constructor for an empty binary search tree.
	 */
	public BSTree() {
		root = null;
		size = 0;
	}

	/**
	 * Adds an element to the binary search tree.
	 *
	 * @param element the element to add
	 * @return true if the element was added successfully
	 * @throws NullPointerException if the element is null
	 */
	public boolean add(T element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null");
		}
		if (root == null) {
			root = new BSTreeNode<>(element);
		} else {
			addRecursive(root, element);
		}
		size++;
		return true;
	}

	/**
	 * Recursively adds an element to the binary search tree.
	 *
	 * @param node    the current node
	 * @param element the element to add
	 */
	private void addRecursive(BSTreeNode<T> node, T element) {
		if (element.compareTo(node.getElement()) < 0) {
			if (node.getLeft() == null) {
				node.setLeft(new BSTreeNode<>(element));
			} else {
				addRecursive(node.getLeft(), element);
			}
		} else {
			if (node.getRight() == null) {
				node.setRight(new BSTreeNode<>(element));
			} else {
				addRecursive(node.getRight(), element);
			}
		}
	}

	/**
	 * Returns the height of the binary search tree.
	 *
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeightRecursive(root);
	}

	/**
	 * Recursively calculates the height of the binary search tree.
	 *
	 * @param node the current node
	 * @return the height of the tree
	 */
	private int getHeightRecursive(BSTreeNode<T> node) {
		if (node == null) {
			return 0;
		}
		int leftHeight = getHeightRecursive(node.getLeft());
		int rightHeight = getHeightRecursive(node.getRight());
		return Math.max(leftHeight, rightHeight) + 1;
	}

	/**
	 * Returns the root node of the binary search tree.
	 *
	 * @return the root node
	 * @throws NullPointerException if the root is null
	 */
	public BSTreeNode<T> getRoot() {
		if (root == null) {
			throw new NullPointerException("Root is null");
		}
		return root;
	}

	/**
	 * Returns the number of elements in the binary search tree.
	 *
	 * @return the size of the tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the binary search tree contains a specific element.
	 *
	 * @param element the element to check for
	 * @return true if the element is found, false otherwise
	 * @throws NullPointerException if the element is null
	 */
	public boolean contains(T element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null");
		}
		return containsRecursive(root, element);
	}

	/**
	 * Recursively checks if the binary search tree contains a specific element.
	 *
	 * @param node    the current node
	 * @param element the element to check for
	 * @return true if the element is found, false otherwise
	 */
	private boolean containsRecursive(BSTreeNode<T> node, T element) {
		if (node == null) {
			return false;
		}
		if (element.compareTo(node.getElement()) == 0) {
			return true;
		}
		return element.compareTo(node.getElement()) < 0 ? containsRecursive(node.getLeft(), element) : containsRecursive(node.getRight(), element);
	}

	/**
	 * Searches for a specific element in the binary search tree.
	 *
	 * @param element the element to search for
	 * @return the node containing the element, or null if not found
	 * @throws NullPointerException if the element is null
	 */
	public BSTreeNode<T> search(T element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null");
		}
		return searchRecursive(root, element);
	}

	/**
	 * Recursively searches for a specific element in the binary search tree.
	 *
	 * @param node    the current node
	 * @param element the element to search for
	 * @return the node containing the element, or null if not found
	 */
	private BSTreeNode<T> searchRecursive(BSTreeNode<T> node, T element) {
		if (node == null || element == null) {
			return null;
		}
		if (element.compareTo(node.getElement()) == 0) {
			return node;
		}
		return element.compareTo(node.getElement()) < 0 ? searchRecursive(node.getLeft(), element) : searchRecursive(node.getRight(), element);
	}

	/**
	 * Clears the binary search tree, removing all elements.
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Checks if the binary search tree is empty.
	 *
	 * @return true if the tree is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes and returns the minimum element in the binary search tree.
	 *
	 * @return the node containing the minimum element, or null if the tree is empty
	 */
	public BSTreeNode<T> removeMin() {
		if (root == null) {
			return null;
		}
		BSTreeNode<T> minNode = root;
		BSTreeNode<T> parent = null;
		while (minNode.getLeft() != null) {
			parent = minNode;
			minNode = minNode.getLeft();
		}
		if (parent != null) {
			parent.setLeft(minNode.getRight());
		} else {
			root = root.getRight();
		}
		size--;
		return minNode;
	}

	/**
	 * Removes and returns the maximum element in the binary search tree.
	 *
	 * @return the node containing the maximum element, or null if the tree is empty
	 */
	public BSTreeNode<T> removeMax() {
		if (root == null) {
			return null;
		}
		BSTreeNode<T> maxNode = root;
		BSTreeNode<T> parent = null;
		while (maxNode.getRight() != null) {
			parent = maxNode;
			maxNode = maxNode.getRight();
		}
		if (parent != null) {
			parent.setRight(maxNode.getLeft());
		} else {
			root = root.getLeft();
		}
		size--;
		return maxNode;
	}

	/**
	 * Returns an iterator that traverses the binary search tree in order.
	 *
	 * @return an iterator for the binary search tree
	 */
	@Override
	public Iterator<T> inorderIterator() {
		return new InorderIterator();
	}

	private class InorderIterator implements Iterator<T> {
		private BSTreeNode<T> current;
		private BSTreeNode<T> pre;

		public InorderIterator() {
			current = root;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			while (current != null) {
				if (current.getLeft() == null) {
					T result = current.getElement();
					current = current.getRight();
					return result;
				} else {
					pre = current.getLeft();
					while (pre.getRight() != null && pre.getRight() != current) {
						pre = pre.getRight();
					}

					if (pre.getRight() == null) {
						pre.setRight(current);
						current = current.getLeft();
					} else {
						pre.setRight(null);
						T result = current.getElement();
						current = current.getRight();
						return result;
					}
				}
			}

			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns an iterator that traverses the binary search tree in preorder.
	 *
	 * @return an iterator for the binary search tree
	 */
	@Override
	public Iterator<T> preorderIterator() {
		return new PreorderIterator();
	}

	private class PreorderIterator implements Iterator<T> {
		private BSTreeNode<T>[] nodes;
		private int index;

		public PreorderIterator() {
			nodes = (BSTreeNode<T>[]) new BSTreeNode[size];
			index = 0;
			fillArrayPreorder(root);
			index = 0;
		}

		private void fillArrayPreorder(BSTreeNode<T> node) {
			if (node != null) {
				nodes[index++] = node;
				fillArrayPreorder(node.getLeft());
				fillArrayPreorder(node.getRight());
			}
		}

		@Override
		public boolean hasNext() {
			return index < nodes.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nodes[index++].getElement();
		}
	}

	/**
	 * Returns an iterator that traverses the binary search tree in postorder.
	 *
	 * @return an iterator for the binary search tree
	 */
	@Override
	public Iterator<T> postorderIterator() {
		return new PostorderIterator();
	}

	private class PostorderIterator implements Iterator<T> {
		private BSTreeNode<T>[] nodes;
		private int index;

		public PostorderIterator() {
			nodes = (BSTreeNode<T>[]) new BSTreeNode[size];
			index = 0;
			fillArrayPostorder(root);
			index = 0;
		}

		private void fillArrayPostorder(BSTreeNode<T> node) {
			if (node != null) {
				fillArrayPostorder(node.getLeft());
				fillArrayPostorder(node.getRight());
				nodes[index++] = node;
			}
		}

		@Override
		public boolean hasNext() {
			return index < nodes.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nodes[index++].getElement();
		}
	}
}