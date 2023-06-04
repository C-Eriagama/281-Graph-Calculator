package nz.ac.auckland.se281.datastructures;

/** A doubly Linked-list. */
public class LinkedList<T> {

  private enum End {
    APPEND,
    PREPEND,
  }

  private Node<T> head;
  private Node<T> tail;

  /** Creates a new doubly Linked-list. */
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
   * Get the head node of the list.
   *
   * @return The head node of the list.
   */
  public Node<T> getHead() {
    return head;
  }

  /**
   * Get the tail node of the list.
   *
   * @return The tail node of the list.
   */
  public Node<T> getTail() {
    return tail;
  }

  /**
   * Create node and add node to end of list.
   *
   * @param data The data to add.
   */
  public void append(T data) {
    add(data, End.APPEND);
  }

  /**
   * Create node and add node to start of list.
   *
   * @param data The data to add.
   */
  public void prepend(T data) {
    add(data, End.PREPEND);
  }

  /**
   * Get the data from a node at the index.
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
   * Creates a new node and inserts node at index and update next and previous nodes.
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
   * Remove a node from linked-list at index and update next and previous nodes.
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

  /** Remove the current head of linked-list and update new head. */
  public void removeHead() {

    // Removing only node
    if (head == tail) {
      head = null;
      tail = null;
      return;
    }

    // If removing head, update head and next node
    head = head.getNext();
    head.setPrevious(null);
    return;
  }

  /** Remove the current tail of linked-list and update new tail */
  public void removeTail() {
    // Removing only node
    if (head == tail) {
      head = null;
      tail = null;
      return;
    }

    // If removing Tail, update tail and previous node
    tail = tail.getPrevious();
    tail.setNext(null);
    return;
  }

  /**
   * Find the node at specified index of linked list.
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
   * Get the size of the linked-list.
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
   * Find index of data by going through linked-list until a match is found.
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
}
