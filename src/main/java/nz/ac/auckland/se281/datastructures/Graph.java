package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  // Instance variables
  Set<T> verticies;
  Set<Edge<T>> edges;
  Map<T, LinkedList<Edge<T>>> adjacencyMap;

  Set<Set<T>> allEquivalenceClasses;
  Set<T> roots;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {

    // Add all verticies and edges to instance variables
    this.verticies = new HashSet<T>();
    this.verticies.addAll(verticies);

    this.edges = new HashSet<Edge<T>>();
    this.edges.addAll(edges);

    // Create adjacency list
    adjacencyMap = new HashMap<T, LinkedList<Edge<T>>>();
    createAdjacencyMap();

    if (isEquivalence()) {
      allEquivalenceClasses = new HashSet<Set<T>>();
      allEquivalenceClasses = getAllEquivalenceClasses();
    }

    this.roots = getRoots();
  }

  private void createAdjacencyMap() {

    // Use each vertex as key for each adjacency list
    for (T vertex : verticies) {
      LinkedList<Edge<T>> adjacecentVertices = new LinkedList<Edge<T>>();

      // Find all edges that have the vertex as a source
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {

          // First edge just appended to list
          if (adjacecentVertices.size() == 0) {
            adjacecentVertices.append(edge);
            continue;
          }

          // Insert edge into list in order
          Node<Edge<T>> previous = adjacecentVertices.getHead();
          do {
            T previousEdgeDestination = previous.getData().getDestination();

            // If edge is greater than previous edge, continue
            if (previousEdgeDestination.compareTo(edge.getDestination()) < 0) {
              previous = previous.getNext();
              continue;
            }

            // If edge is less than previous edge, insert edge before previous edge
            int index = adjacecentVertices.indexOf(previous.getData());
            adjacecentVertices.insert(index, edge);

            previous = previous.getNext();
          } while (previous.getNext() != null);
        }
      }

      // Add adjacency list to adjacency map
      adjacencyMap.put(vertex, adjacecentVertices);
    }
  }

  public Set<T> getRoots() {

    Set<T> roots = new HashSet<T>();

    // Iterate through all verticies and check if they are a root
    for (T vertex : verticies) {
      if (isRoot(vertex)) {
        roots.add(vertex);
      }
    }

    // Add all lowest values of equivalence classes to roots
    if (isEquivalence()) {
      Set<Set<T>> allEquivalenceClasses = getAllEquivalenceClasses();

      for (Set<T> equivalenceClass : allEquivalenceClasses) {
        Set<Integer> integerEquivalenceClass = convertToIntegerSet(equivalenceClass);
        Integer min = Collections.min(integerEquivalenceClass);
        roots.add((T) min);
      }
    }

    return roots;
  }

  // Helper method to check if a vertex is a root
  private boolean isRoot(T vertex) {

    // Check if vertice is a destination of any edge
    for (Edge<T> edge : edges) {
      if ((edge.getDestination().equals(vertex))) {
        return false;
      }
    }

    // TODO: Need to add lowest value of equivalence class to roots

    return true;
  }

  private Set<Integer> convertToIntegerSet(Set<T> set) {
    Set<Integer> integerSet = new HashSet<Integer>();
    for (T element : set) {
      integerSet.add(Integer.parseInt((String) element));
    }
    return integerSet;
  }

  public boolean isReflexive() {

    for (T vertex : verticies) {
      if (!findDestinationVertices(vertex).contains(vertex)) {
        return false;
      }
    }

    return true;
  }

  public boolean isSymmetric() {

    for (Edge<T> edge : edges) {
      // Graph is not symmetric if there is an edge that does not have a symmetric edge
      if (!isSymmetricEdge(edge)) {
        return false;
      }
    }
    return true;
  }

  // Helper method to check if an edge has a symmetric edge - including self loop
  private boolean isSymmetricEdge(Edge<T> edge) {

    // Check if there is an edge with the same source and destination but in the opposite direction
    for (Edge<T> e : edges) {
      if (edge.getSource().equals(e.getDestination())
          && edge.getDestination().equals(e.getSource())) {
        return true;
      }
    }
    return false;
  }

  public boolean isTransitive() {

    // Find all B such that A->B - for each vertex A
    for (T vertex : verticies) {
      Set<T> adjacentVertices = findDestinationVertices(vertex);

      // Find all C such that B->C - for each vertex B
      for (T adjacentVertex : adjacentVertices) {
        Set<T> adjacentAdjacentVertices = findDestinationVertices(adjacentVertex);

        // Check if A->B and B->C, then A->C - for each vertex C
        for (T adjacentAdjacentVertex : adjacentAdjacentVertices) {
          // Graph is not transitive if A->C does not exist
          if (!adjacentVertices.contains(adjacentAdjacentVertex)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  // Helper method to find all vertices that a vertex has an edge to
  private Set<T> findDestinationVertices(T vertex) {
    Set<T> adjacentVertices = new HashSet<T>();
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        adjacentVertices.add(edge.getDestination());
      }
    }
    return adjacentVertices;
  }

  public boolean isAntiSymmetric() {

    // Graph is not antisymmetric if there is a symmetrical edge that is not a self loop
    for (Edge<T> edge : edges) {
      if (isSymmetricEdge(edge) && edge.getSource() != edge.getDestination()) {
        return false;
      }
    }
    return true;
  }

  public boolean isEquivalence() {

    // Graph is an equivalence relation if it is reflexive, symmetric and transitive
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    }
    return false;
  }

  public Set<T> getEquivalenceClass(T vertex) {

    Set<T> equivalenceClass = findDestinationVertices(vertex);

    // Check if graph is an equivalence relation
    if (!isEquivalence()) {
      return new HashSet<T>();
    }

    return equivalenceClass;
  }

  // Helper method to find all equivalence classes
  private Set<Set<T>> getAllEquivalenceClasses() {

    // Create a new set of all verticies
    List<T> verticiesToAdd = new ArrayList<T>();
    verticiesToAdd.addAll(verticies);

    Set<Set<T>> allEquivalenceClasses = new HashSet<Set<T>>();

    // Find all equivalence classes
    while (verticiesToAdd.size() != 0) {
      T vertex = verticiesToAdd.get(0);
      Set<T> equivalenceClass = getEquivalenceClass(vertex);
      verticiesToAdd.removeAll(equivalenceClass);
      allEquivalenceClasses.add(equivalenceClass);
    }
    return allEquivalenceClasses;
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
