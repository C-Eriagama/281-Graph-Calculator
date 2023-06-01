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

      // Find lowest value of each equivalence class
      for (Set<T> equivalenceClass : allEquivalenceClasses) {
        Set<Integer> integerEquivalenceClass = convertToIntegerSet(equivalenceClass);
        Integer min = Collections.min(integerEquivalenceClass);
        roots.add(getVertex(min, equivalenceClass));
      }
    }

    return roots;
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

  public List<T> iterativeBreadthFirstSearch() {

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
      addAdjacentVerticesQueue(vertex, queue, verticiesVisited);

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
        verticiesToVisitIntegers.remove(min2);
      }
    }
    return verticiesVisited;
  }

  public List<T> iterativeDepthFirstSearch() {

    // Vertices visited
    List<T> verticiesVisited = new ArrayList<T>();

    // Vertices to visit
    Set<T> verticiesToVisit = new HashSet<T>();
    verticiesToVisit.addAll(roots);
    Set<Integer> verticiesToVisitIntegers = convertToIntegerSet(verticiesToVisit);

    // Add all roots to stack in reverse order
    Stack<T> stack = new Stack<T>();
    for (int i = 0; i < verticiesToVisit.size(); i++) {
      Integer max = Collections.max(verticiesToVisitIntegers);
      T maximum = getVertex(max, verticiesToVisit);
      stack.push(maximum);
      verticiesToVisitIntegers.remove(Collections.max(verticiesToVisitIntegers));
    }

    // Go through stack
    while (!stack.isEmpty()) {

      T vertex = stack.pop();
      if (verticiesVisited.contains(vertex)) {
        continue;
      }
      verticiesVisited.add(vertex);

      // If vertex has no adjacent vertices, remove from stack and continue
      if (adjacencyMap.get(vertex).isEmpty()) {
        continue;
      }

      // Add all adjacent vertices to stack in reverse order
      int i = 0;
      Node<Edge<T>> node = adjacencyMap.get(vertex).getTail();

      while (node.getPrevious() != null || i == 0) {

        // Iterate after first
        if (i != 0) {
          node = node.getPrevious();
        }

        // If vertex is in queue or vertice already visited, skip
        if (verticiesVisited.contains(node.getData().getDestination())) {
          i++;
          continue;
        }

        // Add destination to queue
        stack.push(node.getData().getDestination());

        // Iterate if first
        if (i == 0) {
          i++;
        }
      }

      // If all roots have been visited, break
      if (verticiesToVisitIntegers.isEmpty()) {
        continue;
      }
    }

    return verticiesVisited;
  }

  public List<T> recursiveBreadthFirstSearch() {

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

    // Go through queue recursively
    recursiveBreadthFirstSearchHelper(
        verticiesVisited, queue, verticiesToVisitIntegers, verticiesToVisit);

    return verticiesVisited;
  }

  public List<T> recursiveDepthFirstSearch() {
    // TODO: Task 3.
    throw new UnsupportedOperationException();
  }

  // Helper method for recursive call
  private void recursiveBreadthFirstSearchHelper(
      List<T> verticiesVisited,
      Queue<T> queue,
      Set<Integer> verticiesToVisitIntegers,
      Set<T> verticiesToVisit) {

    // Base case
    if (queue.isEmpty()) {
      return;
    }

    // Go through queue recursively
    T vertex = queue.peek();
    verticiesVisited.add(vertex);

    // Add all adjacent vertices to queue in order
    addAdjacentVerticesQueue(vertex, queue, verticiesVisited);

    // Remove vertex from queue
    queue.dequeue();

    // Go to next root if queue is empty
    if (queue.isEmpty()) {
      if (!verticiesToVisitIntegers.isEmpty()) {
        Integer min2 = Collections.min(verticiesToVisitIntegers);
        T minimum2 = getVertex(min2, verticiesToVisit);
        queue.enqueue(minimum2);
        verticiesToVisitIntegers.remove(min2);
      }
    }

    recursiveBreadthFirstSearchHelper(
        verticiesVisited, queue, verticiesToVisitIntegers, verticiesToVisit);
  }

  // Helper method for adding adjacent vertices to queue
  private void addAdjacentVerticesQueue(T vertex, Queue<T> queue, List<T> verticiesVisited) {

    // Add all adjacent vertices to queue in order
    int i = 0;
    if (adjacencyMap.get(vertex).isEmpty()) {
      return;
    }

    Node<Edge<T>> node = adjacencyMap.get(vertex).getHead();

    while (node.getNext() != null || i == 0) {

      // Iterate after first
      if (i != 0) {
        node = node.getNext();
      }

      // If vertex is in queue or vertice already visited, skip
      if (verticiesVisited.contains(node.getData().getDestination())
          || queue.contains(node.getData().getDestination())) {
        i++;
        continue;
      }

      // Add destination to queue
      queue.enqueue(node.getData().getDestination());

      // Iterate if first
      if (i == 0) {
        i++;
      }
    }
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

          // Iterate after first
          if (i != 0) {
            previous = previous.getNext();
          }

          T previousEdgeDestination = previous.getData().getDestination();
          // Convert to integer if not already
          Integer previousEdgeDestinationInteger = castToInteger(previousEdgeDestination);

          // If edge is greater than previous edge, continue
          if (previousEdgeDestinationInteger.compareTo(castToInteger(edge.getDestination())) < 0) {

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

  // Helper method to cast generic type to integer
  private Integer castToInteger(T data) {
    if (data.getClass() == Integer.class) {
      return (Integer) data;
    } else {
      return Integer.parseInt((String) data);
    }
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

    // Convert each element to integer
    for (T element : set) {
      // Cast if already Integer
      Integer elementInt = castToInteger(element);
      integerSet.add(elementInt);
    }
    return integerSet;
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

  // Helper method to return vertex from set
  private T getVertex(Integer vertex, Set<T> verticies) {
    // Loop through all verticies
    for (T v : verticies) {
      Integer v1 = castToInteger(v);

      // Compare verticies to find match
      if (v1.equals(vertex)) {
        return v;
      }
    }
    return null;
  }
}
