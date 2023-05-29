public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;

  public LinkedList() {
    head = null;
    tail = null;
  }

  public void add(T data) {
    Node<T> node = new Node<T>(data);

    if (head == null) {
      head = node;
      tail = node;
      return;
    }

    tail.setNext(node);
    node.setPrevious(tail);
    tail = node;

  }

  public void append(T data) {
    add(data);
  }

  public T get(int index) {
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.getNext();
    }
    return node.getData();
  }

  public void insert(int index, T data) {

    Node<T> node = locateNode(index);

    Node<T> newNode = new Node<T>(data);
    newNode.setNext(node);
    newNode.setPrevious(node.getPrevious());
    node.getPrevious().setNext(newNode);
    node.setPrevious(newNode);
  }

  public void remove(int index) {

    Node<T> node = locateNode(index);

    node.getPrevious().setNext(node.getNext());
    node.getNext().setPrevious(node.getPrevious());

  }

  private Node<T> locateNode(int index) {
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.getNext();
    }
    return node;
  }

  public int size() {
    Node<T> node = head;

    int size = 0;
    while (node != tail) {
      node = node.getNext();
      size++;
    }
    return size;
  }

  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    }
    return false;
  }

  public int indexOf(T data) {
    Node<T> node = head;
    int index = 0;
    while (node != null) {
      if (node.getData().equals(data)) {
        return index;
      }
      node = node.getNext();
      index++;
    }

    return -1;
  }

  @Override
  public String toString() {
    String string;
    StringBuilder sb = new StringBuilder();

    Node<T> node = head;
    while (node != null) {
      sb.append(node.getData().toString() + ", ");
      node = node.getNext();
    }

    int length = sb.length();
    if (length > 2) {
      sb.delete(length - 2, length);
    }

    string = "[" + sb.toString() + "]";
    return string;

  }

  public static void main(final String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    list.append(1);
    list.append(2);
    list.insert(1, 3);
    System.out.println(list);
  }
}
