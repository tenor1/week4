import edu.princeton.cs.algs4.Stack;

public class Board {
   
   private final int[][] b;
   private final int N;
   // construct a board from an N-by-N array of blocks 
   // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
       int dx = 0, dy = 0;
       if (blocks[0][0] == 0 && blocks[0][1] == 0) { // from Board array
          N = blocks[0].length - 1;                 
       } else {
          N = blocks[0].length;                      // from blocks array
          dy = 1;
          dx = 1;
       }
       b = new int[N+1][N+1];
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N; j++) {
             b[i][j] = blocks[i-dy][j-dx];
          }
    }
    
    public int dimension() {                // board dimension N
       return N;
    }
    
   // sum of Manhattan distances between blocks and goal
    public int manhattan() {
       int dr = 0;         // delta row
       int dc = 0;         // delta col
       int mn = 0;
       int bb;             // number in tile
       int num = 1;
       int[] er = new int[N*N+1]; // goal number row(er), and col(ec);
       int[] ec = new int[N*N+1];
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N; j++) {
             er[num] = i;
             ec[num++] = j;
          }
       er[0] = er[--num];
       ec[0] = ec[num];
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N; j++) {
             if (b[i][j] != 0) {
                bb = b[i][j];
                if (er[bb] > i) dr = er[bb] - i;
                else            dr = i - er[bb];
                if (ec[bb] > j) dc = ec[bb] - j;
                else            dc = j - ec[bb];
                mn += dr + dc;
             }
          }
       return mn;
    }

    public int hamming() { // number of blocks out of place
       int num = 1, hm = 0, ns = N*N;
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N && num < ns; j++) {
             if ((b[i][j] != 0) && (b[i][j] != num))
                hm++;
             num++;
          }
       if (b[N][N] != 0)
          hm++;
       return hm;      
    }

    
    public boolean isGoal() { // is this board the goal board?
       int num = 1, ns = N*N;
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N && num < ns; j++)
             if (b[i][j] != num++)
                return false;
       return true;       
    }              
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
       int k;
       k = 1;
       if (b[k][k] == 0 || b[k][k+1] == 0)
          k = 2;
       int tmp = b[k][1]; 
       b[k][1] = b[k][2];
       b[k][2] = tmp;
       Board tw = new Board(b);
       b[k][2] = b[k][1];
       b[k][1] = tmp;
       return tw;
    }
    
    public boolean equals(Object y) {       // does this board equal y?
       if (y == this) return true;
       if (y == null) return false;
       if (y.getClass() != this.getClass()) return false;
       Board that = (Board) y;
       if (that.N != this.N) return false;
       for (int i = 1; i <= N; i++)
          for (int j = 1; j <= N; j++)
             if (that.b[i][j] != this.b[i][j])
                return false;
       return true;
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
       Stack<Board> st = new Stack<Board>();
       int rr = 0, cc = 0;
       for (int i = 1; i <= N && rr == 0; i++)
          for (int j = 1; j <= N && cc == 0; j++)
             if (b[i][j] == 0) {
                rr = i;
                cc = j;
             }
       if (cc > 1)
          st.push(createNeiBoard(rr, cc - 1, rr, cc));  // not left
       if (cc < N)
          st.push(createNeiBoard(rr, cc + 1, rr, cc));  // not right
       if (rr > 1)
          st.push(createNeiBoard(rr - 1, cc, rr, cc));  // not top
       if (rr < N)
          st.push(createNeiBoard(rr + 1, cc, rr, cc));  // not bottom
       return st;
    }
    
    private Board createNeiBoard(int r1, int c1, int r2, int c2) {
       exch(r1, c1, r2, c2);
       Board br = new Board(b);
       exch(r1, c1, r2, c2);
       return br;
    }
    
    public String toString() {              // string representation of this board (in the output format specified below)
       StringBuilder ss = new StringBuilder();
       ss.append(N + "\n");
       for (int i = 1; i <= N; i++) {
           for (int j = 1; j <= N; j++) {
               ss.append(String.format("%2d ", b[i][j]));
           }
           ss.append("\n");
       }
       return ss.toString();
    }
   
    public static void main(String[] args) { // unit tests (not graded)
       
    }
    
    private void exch(int r1, int c1, int r2, int c2) {
       int tmp = b[r1][c1];
       b[r1][c1] = b[r2][c2];
       b[r2][c2] = tmp;
    }
    
}
