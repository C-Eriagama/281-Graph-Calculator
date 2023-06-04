package nz.ac.auckland.se281.datastructures;

/** A node for use in doubly linked-lists */
public class Node<T> {

  private T data;
  private Node<T> next;
  private Node<T> previous;

  /**
   * Creates a new Node.
   *
   * @param data The data to store in the node.
   */
  public Node(T data) {
    this.data = data;
    this.next = null;
    this.previous = null;
  }

  /**
   * Get the data stored in the node.
   *
   * @return The data stored in the node.
   */
  public T getData() {
    return data;
  }

  /**
   * Set the data stored in the node.
   *
   * @param data The data to store in the node.
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Get the next node in the list.
   *
   * @return The next node in the list.
   */
  public Node<T> getNext() {
    return next;
  }

  /**
   * Set the next node in the list.
   *
   * @param next The next node in the list.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /**
   * Get the previous node in the list.
   *
   * @return The previous node in the list.
   */
  public Node<T> getPrevious() {
    return previous;
  }

  /**
   * Set the previous node in the list.
   *
   * @param previous The previous node in the list.
   */
  public void setPrevious(Node<T> previous) {
    this.previous = previous;
  }
}
