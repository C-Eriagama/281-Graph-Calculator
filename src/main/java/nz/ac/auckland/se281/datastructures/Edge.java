package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {

  // Instance variables
  private T source;
  private T destination;

  /**
   * Creates a new Edge.
   *
   * @param source The source vertex of edge.
   * @param destination The destination vertex of edge.
   */
  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Returns the source vertex of this edge.
   *
   * @return source vertex of this edge.
   */
  public T getSource() {
    return source;
  }

  /**
   * Returns the destination vertex of this edge.
   *
   * @return destination vertex of this edge.
   */
  public T getDestination() {
    return destination;
  }
}
