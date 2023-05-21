package nz.ac.auckland.se281.datastructures;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>
 * You must NOT change the signature of the existing methods or constructor of
 * this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  // Instance variables
  Set<T> verticies;
  Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {

    // Add all verticies and edges to instance variables
    this.verticies = new HashSet<T>();
    for (T vertex : verticies) {
      this.verticies.add(vertex);
    }

    this.edges = new HashSet<Edge<T>>();
    for (Edge<T> edge : edges) {
      this.edges.add(edge);
    }

  }

  public Set<T> getRoots() {
    // TODO: Task 1.

    Set<T> roots = new HashSet<T>();

    // Iterate through all verticies and check if they are a root
    for (T vertex : verticies) {
      if (isRoot(vertex)) {
        roots.add(vertex);
      }
    }

    return roots;
    // throw new UnsupportedOperationException();

  }

  // Helper method to check if a vertex is a root
  private boolean isRoot(T vertex) {

    // Check if vertice is a destination of any edge
    for (Edge<T> edge : edges) {
      if ((edge.getDestination().equals(vertex))) {
        return false;
      }
    }

    return true;
  }

  public boolean isReflexive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isTransitive() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isAntiSymmetric() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public boolean isEquivalence() {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public Set<T> getEquivalenceClass(T vertex) {
    // TODO: Task 1.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeBreadthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> iterativeDepthFirstSearch() {
    // TODO: Task 2.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveBreadthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }
}
