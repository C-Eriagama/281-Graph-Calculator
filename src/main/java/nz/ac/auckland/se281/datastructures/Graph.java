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
  private Set<T> verticies;
  private Set<Edge<T>> edges;
  private Map<T, LinkedList<Edge<T>>> adjacencyMap; // Linked List is sorted

  private Set<Set<T>> allEquivalenceClasses;
  private Set<T> roots;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {

    // Add all verticies and edges to instance variables
    this.verticies = new HashSet<T>();
    this.verticies.addAll(verticies);

    this.edges = new HashSet<Edge<T>>();
    this.edges.addAll(edges);

    // Create adjacency list
    adjacencyMap = new HashMap<T, LinkedList<Edge<T>>>();
    createAdjacencyMap();

    // If graph is equivalence, find all equivalence classes
    if (isEquivalence()) {
      allEquivalenceClasses = getAllEquivalenceClasses();
    }

    this.roots = getRoots();
  }

  // Helper method to create adjacency map with vertice as key and sorted list of edges as value
  private void createAdjacencyMap() {

    // Use each vertex as key for each adjacency list
    for (T vertex : verticies) {
      LinkedList<Edge<T>> adjacentVertices = new LinkedList<Edge<T>>();

      // Find all edges that have the vertex as a source
      for (Edge<T> edge : edges) {

        if (!(edge.getSource().equals(vertex))) {
          continue;
        }

        // First edge just appended to list
        if (adjacentVertices.size() == 0) {
          adjacentVertices.append(edge);
          continue;
        }

        // Insert edge into list in order
        Node<Edge<T>> previous = adjacentVertices.getHead();
        int i = 0;
        while (previous.getNext() != null || adjacentVertices.size() == 1) {

          if (i != 0) {
            previous = previous.getNext();
          }

          T previousEdgeDestination = previous.getData().getDestination();

          // If edge is greater than previous edge, continue
          if (previousEdgeDestination.compareTo(edge.getDestination()) < 0) {

            // If previous is tail, append edge to tail
            if (previous == adjacentVertices.getTail()) {
              adjacentVertices.append(edge);
              break;
              // Else continue to next edge
            } else {
              if (i == 0) {
                i++;
              }
              continue;
            }
          }

          // If edge is less than previous edge, insert edge before previous edge
          int index = adjacentVertices.indexOf(previous.getData());
          adjacentVertices.insert(index, edge);
          break;
        }
      }

      // Add adjacency list to adjacency map
      adjacencyMap.put(vertex, adjacentVertices);
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

      for (Set<T> equivalenceClass : allEquivalenceClasses) {
        Set<Integer> integerEquivalenceClass = convertToIntegerSet(equivalenceClass);
        Integer min = Collections.min(integerEquivalenceClass);
        roots.add(getVertex(min, equivalenceClass));
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

    return true;
  }

  // Helper method to convert a set of generic type to a set of integers
  private Set<Integer> convertToIntegerSet(Set<T> set) {
    Set<Integer> integerSet = new HashSet<Integer>();
    for (T element : set) {
      if (element.getClass() == Integer.class) {
        integerSet.add((Integer) element);
        continue;
      }
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

    // Vertices visited
    List<T> verticiesVisited = new ArrayList<T>();

    // Vertices to visit
    Set<T> verticiesToVisit = new HashSet<T>();
    verticiesToVisit.addAll(roots);
    Set<Integer> verticiesToVisitIntegers = convertToIntegerSet(verticiesToVisit);

    // Add smallest root to queue
    Queue<T> queue = new Queue<T>();
    Integer min = Collections.min(verticiesToVisitIntegers);
    T minimum = getVertex(min, verticiesToVisit);
    queue.enqueue(minimum);
    verticiesToVisitIntegers.remove(Collections.min(verticiesToVisitIntegers));

    // Go through queue
    while (!queue.isEmpty()) {
      T vertex = queue.peek();
      verticiesVisited.add(vertex);

      // If vertex has no adjacent vertices, remove from queue and continue
      if (adjacencyMap.get(vertex).isEmpty()) {
        queue.dequeue();
        continue;
      }

      // Add all adjacent vertices to queue in order
      int i = 0;
      Node<Edge<T>> node = adjacencyMap.get(vertex).getHead();
      while (node.getNext() != null || i == 0) {

        if (i != 0) {
          node = node.getNext();
        }

        // If vertex is same as destination or vertice already visited, skip
        if (verticiesVisited.contains(node.getData().getDestination())
            || queue.contains(node.getData().getDestination())) {
          i++;
          continue;
        }

        // Add destination to queue
        queue.enqueue(node.getData().getDestination());

        if (i == 0) {
          i++;
        }

        if (adjacencyMap.get(vertex).size() == 1) {
          break;
        }
      }

      // Remove vertex from queue
      queue.dequeue();

      // If all roots have been visited, break
      if (verticiesToVisitIntegers.isEmpty()) {
        continue;
      }

      // Go to next root if queue is empty
      if (queue.isEmpty()) {
        Integer min2 = Collections.min(verticiesToVisitIntegers);
        T minimum2 = getVertex(min2, verticiesToVisit);
        queue.enqueue(minimum2);
        verticiesToVisitIntegers.remove(Collections.min(verticiesToVisitIntegers));
      }
    }
    return verticiesVisited;
  }

  // Helper method to return vertex from set
  private T getVertex(Integer vertex, Set<T> verticies) {
    for (T v : verticies) {
      Integer v1;

      if (v.getClass() == Integer.class) {
        v1 = (Integer) v;
      } else {
        v1 = (Integer.parseInt((String) v));
      }

      if (v1.equals(vertex)) {
        return v;
      }
    }
    return null;
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
