import java.util.ArrayList;
import java.util.List;

public class Queue<T> {
  List<T> queue;

  public Queue() {
    queue = new ArrayList<T>();
  }

  public void enqueue(T data) {
    queue.add(data);
  }

  public T dequeue() {
    T data = queue.get(0);
    queue.remove(0);
    return data;
  }

  public T peek() {
    return queue.get(0);
  }

  public boolean isEmpty() {
    return queue.size() == 0;
  }

  @Override
  public String toString() {
    String string;
    StringBuilder sb = new StringBuilder();
    for (T data : queue) {
      sb.append(data + ", ");
    }
    int length = sb.length();
    if (length > 2) {
      sb.delete(length - 2, length);
    }

    string = "[" + sb.toString() + "]";
    return string;
  }

  public static void main(final String[] args) {
    Queue<Integer> classUnderTest = new Queue<>();
    System.out.println(classUnderTest.toString());

    System.out.println();

    Queue<Integer> classUnderTest1 = new Queue<>();
    classUnderTest1.enqueue(1);
    classUnderTest1.enqueue(2);
    classUnderTest1.enqueue(3);
    System.out.println(classUnderTest1.peek());
    System.out.println(classUnderTest1.toString());
  }
}