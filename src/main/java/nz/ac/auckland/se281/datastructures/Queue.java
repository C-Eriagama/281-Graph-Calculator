package nz.ac.auckland.se281.datastructures;

public class Queue<T> {
  private LinkedList<T> queue;

  public Queue() {
    queue = new LinkedList<T>();
  }

  public void enqueue(T data) {
    queue.append(data);
  }

  public T dequeue() {
    T data = queue.getHead().getData();
    queue.remove(0);
    return data;
  }

  public T peek() {
    return queue.getHead().getData();
  }

  public int size() {
    return queue.size();
  }

  public boolean isEmpty() {
    return queue.size() == 0;
  }

  @Override
  public String toString() {
    return queue.toString();
  }
}
