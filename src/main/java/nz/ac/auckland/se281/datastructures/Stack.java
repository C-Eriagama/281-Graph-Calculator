package nz.ac.auckland.se281.datastructures;

/** A last-in-first-out data structure using a linked-list known as a stack */
public class Stack<T> {
  private LinkedList<T> stack;

  /** Creates a new Stack using a linkedlist. */
  public Stack() {
    stack = new LinkedList<T>();
  }

  /**
   * Adds a node to the end of the stack.
   *
   * @param data The data to add.
   */
  public void push(T data) {
    stack.append(data);
  }

  /**
   * Removes a node from the end of the stack.
   *
   * @return The data of the node removed.
   */
  public T pop() {
    T data = stack.getTail().getData();
    stack.removeTail();
    return data;
  }

  /**
   * View the data of the node at the end of the stack.
   *
   * @return The data of the node at the end of the stack.
   */
  public T peek() {
    return stack.getTail().getData();
  }

  /**
   * Get the size of the stack.
   *
   * @return The size of the stack.
   */
  public int size() {
    return stack.size();
  }

  /**
   * Check if the stack is empty.
   *
   * @return True if the stack is empty, false otherwise.
   */
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  /**
   * Check if the stack contains a node with the given data.
   *
   * @param data The data to check for.
   * @return True if the stack contains a node with the given data, false otherwise.
   */
  public boolean contains(T data) {
    int index = stack.indexOf(data);
    return index != -1;
  }
}
