package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Command.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class, // Uncomment this line when you start Task 2
  MainTest.Task3.class, // Uncomment this line when you start Task 3
  MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {
  public static class Task1 extends CliTest {
    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_A_roots() throws Exception {
      runCommands(OPEN_FILE, "a.txt", LIST_ROOT_VERTICIES);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("[0]");
    }

    @Test
    public void T1_B_roots() throws Exception {
      runCommands(OPEN_FILE, "b.txt", LIST_ROOT_VERTICIES);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[6]");
    }

    @Test
    public void T1_C_roots() throws Exception {
      runCommands(OPEN_FILE, "c.txt", LIST_ROOT_VERTICIES);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0, 1]");
    }

    @Test
    public void T1_A_reflexivity() throws Exception {
      runCommands(OPEN_FILE, "a.txt", CHECK_REFLEXIVITY);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("The graph is NOT reflexive");
    }

    @Test
    public void T1_B_reflexivity() throws Exception {
      runCommands(OPEN_FILE, "b.txt", CHECK_REFLEXIVITY);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("The graph is NOT reflexive");
    }

    @Test
    public void T1_C_reflexivity() throws Exception {
      runCommands(OPEN_FILE, "c.txt", CHECK_REFLEXIVITY);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("The graph is reflexive");
    }

    @Test
    public void T1_A_symmetry() throws Exception {
      runCommands(OPEN_FILE, "a.txt", CHECK_SYMMETRY);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("The graph is NOT symmetric");
    }

    @Test
    public void T1_B_symmetry() throws Exception {
      runCommands(OPEN_FILE, "b.txt", CHECK_SYMMETRY);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("The graph is NOT symmetric");
    }

    @Test
    public void T1_C_symmetry() throws Exception {
      runCommands(OPEN_FILE, "c.txt", CHECK_SYMMETRY);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("The graph is symmetric");
    }

    @Test
    public void T1_A_transitivity() throws Exception {
      runCommands(OPEN_FILE, "a.txt", CHECK_TRANSITIVITY);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("The graph is NOT transitive");
    }

    @Test
    public void T1_B_transitivity() throws Exception {
      runCommands(OPEN_FILE, "b.txt", CHECK_TRANSITIVITY);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("The graph is NOT transitive");
    }

    @Test
    public void T1_C_transitivity() throws Exception {
      runCommands(OPEN_FILE, "c.txt", CHECK_TRANSITIVITY);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("The graph is transitive");
    }

    @Test
    public void T1_A_antisymmetry() throws Exception {
      runCommands(OPEN_FILE, "a.txt", CHECK_ANTISYMMETRY);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("The graph is antisymmetric");
    }

    @Test
    public void T1_B_antisymmetry() throws Exception {
      runCommands(OPEN_FILE, "b.txt", CHECK_ANTISYMMETRY);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("The graph is NOT antisymmetric");
    }

    @Test
    public void T1_C_antisymmetry() throws Exception {
      runCommands(OPEN_FILE, "c.txt", CHECK_ANTISYMMETRY);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("The graph is NOT antisymmetric");
    }

    @Test
    public void T1_A_equivalence() throws Exception {
      runCommands(OPEN_FILE, "a.txt", CHECK_EQUIVALENCE);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("The graph is NOT an equivalence relation");
    }

    @Test
    public void T1_B_equivalence() throws Exception {
      runCommands(OPEN_FILE, "b.txt", CHECK_EQUIVALENCE);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("The graph is NOT an equivalence relation");
    }

    @Test
    public void T1_C_equivalence() throws Exception {
      runCommands(OPEN_FILE, "c.txt", CHECK_EQUIVALENCE);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("The graph is an equivalence relation");
    }

    @Test
    public void T1_B_equivalence_class_1() throws Exception {
      runCommands(OPEN_FILE, "b.txt", COMPUTE_EQUIVALENCE, 1);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[]");
    }

    @Test
    public void T1_C_equivalence_class_0() throws Exception {
      runCommands(OPEN_FILE, "c.txt", COMPUTE_EQUIVALENCE, 0);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0]");
    }

    @Test
    public void T1_C_equivalence_class_1() throws Exception {
      runCommands(OPEN_FILE, "c.txt", COMPUTE_EQUIVALENCE, 1);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[1, 2, 3]");
    }
  }

  public static class Task2 extends CliTest {
    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_A_iterative_BFS() throws Exception {
      runCommands(OPEN_FILE, "a.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("[0, 1, 2, 3, 4, 5]");
    }

    @Test
    public void T2_B_iterative_BFS() throws Exception {
      runCommands(OPEN_FILE, "b.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[6, 5, 4, 3, 2, 1, 0]");
    }

    @Test
    public void T2_C_iterative_BFS() throws Exception {
      runCommands(OPEN_FILE, "c.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0, 1, 2, 3]");
    }

    @Test
    public void T2_A_iterative_DFS() throws Exception {
      runCommands(OPEN_FILE, "a.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("[0, 1, 3, 4, 5, 2]");
    }

    @Test
    public void T2_B_iterative_DFS() throws Exception {
      runCommands(OPEN_FILE, "b.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[6, 5, 4, 3, 2, 1, 0]");
    }

    @Test
    public void T2_C_iterative_DFS() throws Exception {
      runCommands(OPEN_FILE, "c.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0, 1, 2, 3]");
    }
  }

  public static class Task3 extends CliTest {
    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_A_recursive_BFS() throws Exception {
      runCommands(OPEN_FILE, "a.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("[0, 1, 2, 3, 4, 5]");
    }

    @Test
    public void T3_B_recursive_BFS() throws Exception {
      runCommands(OPEN_FILE, "b.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[6, 5, 4, 3, 2, 1, 0]");
    }

    @Test
    public void T3_C_recursive_BFS() throws Exception {
      runCommands(OPEN_FILE, "c.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0, 1, 2, 3]");
    }

    @Test
    public void T3_A_recursive_DFS() throws Exception {
      runCommands(OPEN_FILE, "a.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file a.txt");
      assertContains("[0, 1, 3, 4, 5, 2]");
    }

    @Test
    public void T3_B_recursive_DFS() throws Exception {
      runCommands(OPEN_FILE, "b.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file b.txt");
      assertContains("[6, 5, 4, 3, 2, 1, 0]");
    }

    @Test
    public void T3_C_recursive_DFS() throws Exception {
      runCommands(OPEN_FILE, "c.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file c.txt");
      assertContains("[0, 1, 2, 3]");
    }
  }

  public static class YourTests extends CliTest {
    public YourTests() {
      super(Main.class);
    }

    @Test
    public void Test_01_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file test.txt");
      assertContains("[0, 1, 2, 3, 4, 5, 6, 9, 10, 13, 7, 8, 15, 11, 20, 23, 12, 21, 22]");
    }

    @Test
    public void Test_02_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file test.txt");
      assertContains("[0, 1, 5, 7, 15, 23, 21, 22, 20, 8, 6, 2, 9, 3, 4, 10, 11, 12, 13]");
    }

    @Test
    public void Test_03_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file test.txt");
      assertContains("[0, 1, 2, 3, 4, 5, 6, 9, 10, 13, 7, 8, 15, 11, 20, 23, 12, 21, 22]");
    }

    @Test
    public void Test_04_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file test.txt");
      assertContains("[0, 1, 5, 7, 15, 23, 21, 22, 20, 8, 6, 2, 9, 3, 4, 10, 11, 12, 13]");
    }

    @Test
    public void Test2_01_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test2.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file test2.txt");
      assertContains("[0, 15, 21, 2, 8, 9, 23, 5, 6, 10, 24, 25]");
    }

    @Test
    public void Test2_02_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test2.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file test2.txt");
      assertContains("[0, 15, 21, 2, 8, 9, 23, 5, 6, 10, 24, 25]");
    }

    @Test
    public void Test2_03_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test2.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file test2.txt");
      assertContains("[0, 15, 21, 2, 8, 9, 23, 5, 6, 10, 24, 25]");
    }

    @Test
    public void Test2_04_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test2.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file test2.txt");
      assertContains("[0, 15, 21, 2, 8, 9, 23, 5, 6, 10, 24, 25]");
    }

    @Test
    public void Test3_01_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test3.txt", GRAPH_SEARCH_IBFS);
      assertContains("Successfully opened graph from file test3.txt");
      assertContains("[0, 1, 2, 3]");
    }

    @Test
    public void Test3_02_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test3.txt", GRAPH_SEARCH_IDFS);
      assertContains("Successfully opened graph from file test3.txt");
      assertContains("[0, 1, 2, 3]");
    }

    @Test
    public void Test3_03_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test3.txt", GRAPH_SEARCH_RBFS);
      assertContains("Successfully opened graph from file test3.txt");
      assertContains("[0, 1, 2, 3]");
    }

    @Test
    public void Test3_04_your_own_test() throws Exception {
      // Write your own test here, in the same format as the other tests.
      runCommands(OPEN_FILE, "test3.txt", GRAPH_SEARCH_RDFS);
      assertContains("Successfully opened graph from file test3.txt");
      assertContains("[0, 1, 2, 3]");
    }
  }
}
