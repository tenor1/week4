import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    private MinPQ<SearchNode> pq;
    private boolean goal;
    private int moves;
    public Solver(Board initial)  { // find a solution to the initial board (using the A* algorithm)
       pq = new MinPQ();
       Stack<Board> st1;
       SearchNode sn, sn1;
       Board br = initial;
       goal = false;
       moves = 0;
       sn = new SearchNode(initial, null);
       // insert the initial search node (the initial board, 0 moves, 
       // and a null previous search node) into a priority queue
       pq.insert(sn);
       while (!goal && !pq.isEmpty()) {
          //delete from the priority queue the search node with the minimum priority
          sn = pq.delMin();
          StdOut.println(sn.node);
          if (sn.node.isGoal()) {
             goal = true;
             break;
          }
          // all neighboring search nodes 
          st1 = (Stack<Board>) sn.node.neighbors();
          //insert onto the priority queue 
          while (!st1.isEmpty()) {
             br = st1.pop();
             sn1 = new SearchNode(br, sn);
             pq.insert(sn1);
          }
          moves++;
       }
       
    }
    public boolean isSolvable()            // is the initial board solvable?
    {return goal;}
    public int moves() {return moves;}                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()  {    // sequence of boards in a shortest solution; null if unsolvable
       Stack<Board> st = new Stack<Board>();
       return st;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
       
    }
    
    private class SearchNode implements Comparable<SearchNode>{
       private Board node;
       private SearchNode prev;
       public int moves;
       SearchNode(Board board, SearchNode pre) {
          node = board;
          prev = pre;
          if (pre == null) moves = 0; 
          else moves = prev.moves + 1;
       }
       public int compareTo(SearchNode n) {
          //(those that can be reached in one move from the dequeued search node)
          if (this.moves > n.moves) return 1;
          if (this.moves < n.moves) return -1;
          if (this.node.manhattan() > n.node.manhattan()) return 1;
          if (this.node.manhattan() < n.node.manhattan()) return -1;
          return 0;
       }
    }
   
}