package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private LinkedList<T> queue;

  /** Creates a new Queue using a linkedlist. */
  public Queue() {
    queue = new LinkedList<T>();
  }

  /**
   * Adds a node to the end of the queue.
   *
   * @param data The data to add.
   */
  public void enqueue(T data) {
    queue.append(data);
  }

  /**
   * Removes a node from the start of the queue.
   *
   * @return The data of the node removed.
   */
  public T dequeue() {
    T data = queue.getHead().getData();
    queue.remove(0);
    return data;
  }

  /**
   * View the data of the node at the start of the queue.
   *
   * @return The data of the node at the start of the queue.
   */
  public T peek() {
    return queue.getHead().getData();
  }

  /**
   * Get the size of the queue.
   *
   * @return The size of the queue.
   */
  public int size() {
    return queue.size();
  }

  /**
   * Check if the queue is empty.
   *
   * @return True if the queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  /**
   * Check if the queue contains a node with the given data.
   *
   * @param data The data to check for.
   * @return True if the queue contains a node with the given data, false otherwise.
   */
  public boolean contains(T data) {
    int index = queue.indexOf(data);
    return index != -1;
  }

  @Override
  public String toString() {
    return queue.toString();
  }
}
