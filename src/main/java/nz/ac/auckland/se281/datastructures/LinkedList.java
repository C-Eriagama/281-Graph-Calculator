package nz.ac.auckland.se281.datastructures;

public class LinkedList<T> {

  private enum End {
    APPEND,
    PREPEND,
  }

  private Node<T> head;
  private Node<T> tail;

  /** Creates a new doubly LinkedList. */
  public LinkedList() {
    head = null;
    tail = null;
  }

  /**
   * Adds a node one end of the list.
   *
   * @param data The data to add.
   * @param end The end to add the data to.
   */
  private void add(T data, End end) {
    Node<T> node = new Node<T>(data);

    // If list is empty, set head and tail to node
    if (head == null) {
      head = node;
      tail = node;
      return;
    }

    // Add node to end of list
    if (end == End.APPEND) {
      tail.setNext(node);
      node.setPrevious(tail);
      tail = node;
      tail.setNext(null);

      // Add node to start of list
    } else if (end == End.PREPEND) {
      head.setPrevious(node);
      node.setNext(head);
      head = node;
      head.setPrevious(null);
    }
  }

  /**
   * Get the head of the list.
   *
   * @return The head of the list.
   */
  public Node<T> getHead() {
    return head;
  }

  /**
   * Get the tail of the list.
   *
   * @return The tail of the list.
   */
  public Node<T> getTail() {
    return tail;
  }

  /**
   * Add node to end of list.
   *
   * @param data The data to add.
   */
  public void append(T data) {
    add(data, End.APPEND);
  }

  /**
   * Add node to start of list.
   *
   * @param data The data to add.
   */
  public void prepend(T data) {
    add(data, End.PREPEND);
  }

  /**
   * Get data at index.
   *
   * @param index The index of the node to get.
   * @return The data of the node at the index.
   */
  public T get(int index) {
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.getNext();
    }
    return node.getData();
  }

  /**
   * Insert node at index.
   *
   * @param index The index node will be.
   * @param data The data to insert.
   */
  public void insert(int index, T data) {

    Node<T> node = locateNode(index);

    Node<T> newNode = new Node<T>(data);

    // If inserting at end or start, use append or prepend
    if (index == size()) {
      append(data);
      return;
    }

    if (index == 0) {
      prepend(data);
      return;
    }

    // Overwrite next and previous of node and new node
    newNode.setNext(node);
    newNode.setPrevious(node.getPrevious());
    node.getPrevious().setNext(newNode);
    node.setPrevious(newNode);
  }

  /**
   * Remove node at index.
   *
   * @param index The index of the node to remove.
   */
  public void remove(int index) {

    Node<T> node = locateNode(index);

    // Removing only node
    if (head == tail) {
      head = null;
      tail = null;
      return;
    }

    // If removing head, update head and next node
    if (node == head) {
      head = node.getNext();
      head.setPrevious(null);
      return;
    }

    // If removing tail, update tail and previous node
    if (node == tail) {
      tail = node.getPrevious();
      tail.setNext(null);
      return;
    }

    // Remove node from list
    node.getPrevious().setNext(node.getNext());
    node.getNext().setPrevious(node.getPrevious());
  }

  /**
   * Find node at index.
   *
   * @param index The index of the node to find.
   * @return The node at the index.
   */
  private Node<T> locateNode(int index) {
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.getNext();
    }
    return node;
  }

  /**
   * Get the size of the list.
   *
   * @return The size of the list.
   */
  public int size() {

    int size = 0;

    // If list is empty, return 0
    if (head == null) {
      return size;
    }

    // If list has one node, return 1
    if (head == tail) {
      return 1;
    }

    Node<T> node = head;

    // Count nodes
    while (node != tail) {
      node = node.getNext();
      size++;
    }
    size++;
    return size;
  }

  /**
   * Check if list is empty.
   *
   * @return True if list is empty, false otherwise.
   */
  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    }
    return false;
  }

  /**
   * Find index of data.
   *
   * @param data The data to find.
   * @return The index of the node with the data.
   */
  public int indexOf(T data) {
    Node<T> node = head;
    int index = 0;

    // Find node with data
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

    // Add all nodes to string
    Node<T> node = head;
    while (node != null) {
      sb.append(node.getData().toString() + ", ");
      node = node.getNext();
    }

    // Remove last comma and space
    int length = sb.length();
    if (length > 2) {
      sb.delete(length - 2, length);
    }

    // Add square brackets
    string = "[" + sb.toString() + "]";
    return string;
  }
}
