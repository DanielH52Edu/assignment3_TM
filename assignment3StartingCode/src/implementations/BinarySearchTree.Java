package implementations;

import utilities.BSTreeADT;
import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements BSTreeADT<T> {

    private class Node {
        T key;
        Node left, right;

        Node(T key) {
            this.key = key;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Node getRoot() {
        if (root == null) throw new NullPointerException("Tree is empty.");
        return root;
    }

    @Override
    public int getHeight() {
        return getHeightRec(root);
    }

    private int getHeightRec(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(getHeightRec(node.left), getHeightRec(node.right));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(T entry) {
        if (entry == null) throw new NullPointerException("Entry cannot be null.");
        return search(entry);
    }

    @Override
    public Node search(T entry) {
        return searchRec(root, entry);
    }

    private Node searchRec(Node node, T key) {
        if (node == null || key.compareTo(node.key) == 0) return node;
        return key.compareTo(node.key) < 0 ? searchRec(node.left, key) : searchRec(node.right, key);
    }

    @Override
    public boolean add(T newEntry) {
        if (newEntry == null) throw new NullPointerException("Entry cannot be null.");
        if (!contains(newEntry)) {
            root = insertRec(root, newEntry);
            size++;
            return true;
        }
        return false;
    }

    private Node insertRec(Node node, T key) {
        if (node == null) return new Node(key);
        if (key.compareTo(node.key) < 0) {
            node.left = insertRec(node.left, key);
        } else {
            node.right = insertRec(node.right, key);
        }
        return node;
    }

    @Override
    public Node removeMin() {
        if (isEmpty()) return null;
        Node minNode = root;
        Node parent = null;

        while (minNode.left != null) {
            parent = minNode;
            minNode = minNode.left;
        }

        if (parent == null) root = minNode.right;
        else parent.left = minNode.right;

        size--;
        return minNode;
    }

    @Override
    public Node removeMax() {
        if (isEmpty()) return null;
        Node maxNode = root;
        Node parent = null;

        while (maxNode.right != null) {
            parent = maxNode;
            maxNode = maxNode.right;
        }

        if (parent == null) root = maxNode.left;
        else parent.right = maxNode.left;

        size--;
        return maxNode;
    }

    @Override
    public Iterator<T> inorderIterator() {
        return new Iterator<>() {
            private final Stack<Node> stack = new Stack<>();
            private Node current = root;

            @Override
            public boolean hasNext() {
                return !stack.isEmpty() || current != null;
            }

            @Override
            public T next() {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                Node node = stack.pop();
                current = node.right;
                return node.key;
            }
        };
    }

    @Override
    public Iterator<T> preorderIterator() {
        return new Iterator<>() {
            private final Stack<Node> stack = new Stack<>();

            {
                if (root != null) stack.push(root);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.key;
            }
        };
    }

    @Override
    public Iterator<T> postorderIterator() {
        return new Iterator<>() {
            private final Stack<Node> stack1 = new Stack<>();
            private final Stack<Node> stack2 = new Stack<>();

            {
                if (root != null) stack1.push(root);
                while (!stack1.isEmpty()) {
                    Node node = stack1.pop();
                    stack2.push(node);
                    if (node.left != null) stack1.push(node.left);
                    if (node.right != null) stack1.push(node.right);
                }
            }

            @Override
            public boolean hasNext() {
                return !stack2.isEmpty();
            }

            @Override
            public T next() {
                return stack2.pop().key;
            }
        };
    }
}
