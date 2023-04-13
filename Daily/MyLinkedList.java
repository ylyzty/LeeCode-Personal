package Daily;

public class MyLinkedList {

    private MyLinkedListNode head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    
    public int get(int index) {
        if (index >= size) {
            return -1;
        }

        MyLinkedListNode temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        return temp.getVal();
    }
    
    public void addAtHead(int val) {
        MyLinkedListNode node = new MyLinkedListNode(val);
        if (size == 0) {
            head = node;
        }
        else {
            MyLinkedListNode temp = head;
            head = node;
            head.setNext(temp);
        }

        size += 1;
    }
    
    public void addAtTail(int val) {
        MyLinkedListNode node = new MyLinkedListNode(val);
        if (size == 0) {
            head = node;
        }
        else {
            MyLinkedListNode temp = head;
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
            MyLinkedListNode node = new MyLinkedListNode(val);
            MyLinkedListNode temp = head;
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

        MyLinkedListNode virtualHead = new MyLinkedListNode(0, head);
        MyLinkedListNode node = virtualHead;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        node.setNext(node.getNext().getNext());
        head = virtualHead.getNext();
        size -= 1;
    }
}

class MyLinkedListNode {
    private int val;
    private MyLinkedListNode next;

    public MyLinkedListNode(int val) {
        this.val = val;
    }

    public MyLinkedListNode(int val, MyLinkedListNode next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public MyLinkedListNode getNext() {
        return next;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setNext(MyLinkedListNode next) {
        this.next = next;
    }
}
