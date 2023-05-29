import java.util.LinkedList;
import java.util.List;

public class Stack<T> {
  List<T> stack;

  public Stack() {
    stack = new LinkedList<T>();
  }

  public void push(T data) {
    stack.add(0, data);
  }

  public T pop() {
    T data = stack.get(0);
    stack.remove(0);
    return data;
  }

  public T peek() {
    return stack.get(0);
  }

  public int size() {
    return stack.size();
  }

  public boolean isEmpty() {
    if (stack.size() == 0) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    String string;
    StringBuilder sb = new StringBuilder();
    for (T data : stack) {
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
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    System.out.println(stack.toString());
  }

}