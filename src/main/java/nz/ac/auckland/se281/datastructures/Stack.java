package nz.ac.auckland.se281.datastructures;

public class Stack<T> {
  private LinkedList<T> stack;

  public Stack() {
    stack = new LinkedList<T>();
  }

  public void push(T data) {
    stack.append(data);
  }

  public T pop() {
    T data = stack.getTail().getData();
    stack.remove(stack.size() - 1);
    return data;
  }

  public T peek() {
    return stack.getTail().getData();
  }

  public int size() {
    return stack.size();
  }

  public boolean isEmpty() {
    return stack.size() == 0;
  }

  @Override
  public String toString() {
    return stack.toString();
  }
}
