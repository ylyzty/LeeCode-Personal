package Daily;

public class MyLinkedList {

    private Node head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public int get(int index) {
        if (index >= size) {
            return -1;
        }

        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        return temp.getVal();
    }
    
    public void addAtHead(int val) {
        Node node = new Node(val);
        if (size == 0) {
            head = node;
        }
        else {
            Node temp = head;
            head = node;
            head.setNext(temp);
        }

        size += 1;
    }
    
    public void addAtTail(int val) {
        Node node = new Node(val);
        if (size == 0) {
            head = node;
        }
        else {
            Node temp = head;
            for (int i = 0; i < size - 1; i++) {
                temp = temp.getNext();
            }
            temp.setNext(node);
        }

        size += 1;
    }
    
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }

        if (index <= 0) {
            addAtHead(val);
        }
        else if (index == size) {
            addAtTail(val);
        }
        else {
            Node node = new Node(val);
            Node temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            node.setNext(temp.getNext());
            temp.setNext(node);
            
            size += 1;
        }
    }
    
    public void deleteAtIndex(int index) {
        if (index >= size || index < 0) {
            return;
        }

        Node virtualHead = new Node(0, head);
        Node node = virtualHead;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        node.setNext(node.getNext().getNext());
        head = virtualHead.getNext();
        size -= 1;
    }
}

class Node {
    private int val;
    private Node next;

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
