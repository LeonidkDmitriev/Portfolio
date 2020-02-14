import org.w3c.dom.Node;

import java.util.NoSuchElementException;

public class Bst<Key extends Comparable<Key>, Value> {
    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size;
        int hight;
        public Node(Key key, Value value, int size, int hight) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.hight=hight;
        }
    }

    private Node root = null;

    public boolean isEmpty() {return root == null;}

    // методы hight
    public int hight() {return hight(root);}

    private int hight(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return node.hight;
        }
    }

    public int size() {return size(root);}

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return node.size;
        }
    }

    public Value get(Key key) {return get(root, key);}

    private Value get(Node node, Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть Null");
        }
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        }
        else if (cmp < 0) {
            return get(node.left, key);
        }
        else { //cmp > 0
            return get(node.right, key);
        }
    }

    public boolean contains(Key key) {return get(key) != null;}

    private Node put(Node node, Key key, Value value) { //a[key] = value;
        if (node == null) {
            return new Node(key, value, 1,0);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        }
        else if (cmp < 0) {
            node.left = put(node.left, key, value);
        }
        else {
            node.right = put(node.right, key, value);
        }
        node.size = size(node.left) + size(node.right) + 1;

        //вставка для hight
        node.hight = hight(node.left) + hight(node.right)+1;

        return node;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть Null");
        }
        root = put(root, key, value);
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        else {
            return min(node.left);
        }
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        else {
            return min(node.right);
        }
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        else {
            node.left = deleteMin(node.left);
        }
        return node;
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        root = deleteMin(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.left;
        }
        else {
            node.right = deleteMax(node.right);
        }
        return node;
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        root = deleteMax(root);
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        }
        else if (cmp > 0) {
            node.right = delete(node.right, key);
        }
        else {
            if (node.left == node) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            Node tmp = node;
            //node = min(node.left);
            node = max(node.left);
            //node.right = deleteMin(tmp.right)
            node.left = deleteMax(tmp.left);
            //node.left = tmp.left;
            node.right = tmp.right;
            tmp = null;
        }
        node.size = size(node.left) + size(node.right) + 1;

        //вставка для hight
        node.hight = hight(node.left) + hight(node.right)+1;

        return node;
    }

    public void delete(Key key) {root = delete(root, key);}

    // проверка сбалансированности дерева
    public boolean isBalanced(Node root) {
        return (root == null) || (isBalanced(root.left) && isBalanced(root.right) &&
                (hight(root.left) - hight(root.right)) <=1);
    }
}

