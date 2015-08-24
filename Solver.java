import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
// import edu.princeton.cs.algs4.StdOut;


public class Solver {
    private MinPQ<SearchNode> pq, tpq;
    private boolean goal, tgoal;
    //private int moves;
    private SearchNode gsn;
    public Solver(Board initial)  { // find a solution to the initial board (using the A* algorithm)
       if (initial == null)
          throw new NullPointerException("Parameter for Solver is null!");
       pq = new MinPQ<SearchNode>();
       tpq = new MinPQ<SearchNode>();
       Stack<Board> st1, tst1;
       SearchNode sn, sn1, tsn, tsn1;
       Board br0, br, tbr0, tbr = initial;
       goal = false;
       tgoal = false;
       //moves = 0;
       int step = 0;
       sn = new SearchNode(initial, null);
       tsn = new SearchNode(sn.node.twin(), null);   // create twin
       // insert the initial search node (the initial board, 0 moves, 
       // and a null previous search node) into a priority queue
       pq.insert(sn);
       tpq.insert(tsn);
       while (!goal && !pq.isEmpty() && !tgoal) {
          //delete from the priority queue the search node with the minimum priority
          sn = pq.delMin();
          tsn = tpq.delMin();
//          StdOut.print(sn.node);
          if (sn.goal) {
             goal = true;
             gsn = sn;
  /*           StdOut.printf(" step=%d, moves=%d, manhattan=%d, hamming=%d, priorities=%d\n", 
                   step, sn.moves, sn.man, sn.ham, 
                   sn.moves + sn.man);
             StdOut.println("===================");
 */            break;
          } else {
             step++;
             // all neighboring search nodes
             if (sn.prev != null)
                br0 = sn.prev.node;
             else br0 = null;
             st1 =  (Stack<Board>) sn.node.neighbors();
             //insert onto the priority queue 
             while (!st1.isEmpty()) {
                br = st1.pop();
   //             StdOut.println("compare ");
   //             StdOut.print(br0);
   //             StdOut.println("with");
   //             StdOut.print(br);
                if (!br.equals(br0)) {
                   sn1 = new SearchNode(br, sn);
                   pq.insert(sn1);
                }
   //             else {
   //                StdOut.println("not inserted"); 
   //             }
             }
          }
          if (tsn.goal) { // twins tach goal. origin not solvable 
             tgoal = true;
   //          StdOut.printf("Twin resolved by %d move. Origin is not solvable!\n", tsn.moves);
          } else {
             if (tsn.prev != null)
                tbr0 = tsn.prev.node;
             else tbr0 = null;
             tst1 =  (Stack<Board>) tsn.node.neighbors();
             //insert onto the priority queue 
             while (!tst1.isEmpty()) {
                tbr = tst1.pop();
                if (!tbr.equals(tbr0)) {
                   tsn1 = new SearchNode(tbr, tsn);
                   tpq.insert(tsn1);
                }
             }
          }
     //     moves++;
       } // end main loop while
    }
    public boolean isSolvable()  { return goal; } // is the initial board solvable?
    public int moves() {  // min number of moves to solve initial board; -1 if unsolvable
       if (gsn == null) return -1;
       return gsn.moves;
    }
    public Iterable<Board> solution()  {    // sequence of boards in a shortest solution; null if unsolvable
       SearchNode tsn = gsn;
       Stack<Board> st = new Stack<Board>();
       while (tsn != null) {
          st.push(tsn.node);
          tsn = tsn.prev;
       }
       return st;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
       
    }
    
    private class SearchNode implements Comparable<SearchNode> {
       private int moves;
       private Board node;
       private SearchNode prev;
       private boolean goal;
       private int man; // ham
       SearchNode(Board board, SearchNode pre) {
          node = board;
          prev = pre;
          if (pre == null) moves = 0; 
          else moves = prev.moves + 1;
          goal = node.isGoal();
        //  ham = node.hamming();
          man = node.manhattan();
       }
       public int compareTo(SearchNode n) {
          int k1 = this.moves + this.man;
          int k2 = n.moves + n.man;
          //(those that can be reached in one move from the dequeued search node)
          if (k1 > k2) return 1;
          if (k1 < k2) return -1;
          return 0;
       }
    }
   
}
