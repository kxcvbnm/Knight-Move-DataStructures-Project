package Project2_6613119 ;
/*
Krittin Tejasen 6613112
Napat Jiaravijit 6613119
Kunanont Titayanunt 6613249
Teewara Pudpoo 6613254
Ruttapon Suppaakkarasolpon 6613270
 */

import java.util.ArrayList;
import java.util.Scanner;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.*;


public class KnightMoves {
    
    private Scanner                                         scanner ;
    private String                                        board[][] ;
    private int                              knightID[], castleID[] ;
    private ArrayList<int[]>                                 bombID ;
    private int                                                   N ;
    
    private Graph<String, DefaultEdge>                            G ; 
    
    public void printTable() {
        for (int i = 0; i < N; i++) {
            if (i == 0) System.out.print("         ");
            System.out.print((i + 1) + "  ");
        }
        System.out.println();
        for (int i = 0; i < N; i++) {
            System.out.print("row " + (i + 1) + " | ");
            for (int j = 0; j < N; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("=====================================");
    }
    
    public void setPos(String type, int row, int col)   {  board[row][col] = type ;  }
    public boolean isPlaced(int row, int col){
        if(board[row][col]!= ".") {
            System.out.println("Invalid position! There's already an object at (" + (row + 1) + "," + (col + 1) + ")");
            return true ;
        }
        else return false ;
    }
    private boolean isInsideBoard(int i, int j)         { return i >= 0 && i < N && j >= 0 && j < N; }
    
    public void initBoard() {
        
        board = new String[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = ".";
            }
        }
        
    }
    
    public void initGraph(){
        
        G = new SimpleGraph<>(DefaultEdge.class);
        
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String node = i + "," + j;
                G.addVertex(node);
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String from = i + "," + j;

                for (int[] move : knightMoves) {
                    int newRow = i + move[0];
                    int newCol = j + move[1];

                    if (isInsideBoard(newRow, newCol)) {
                        String to = newRow + "," + newCol;
                        G.addEdge(from, to);
                    }
                }
            }
        }
        
        if (bombID != null) {
            for (int[] bomb : bombID) {
                String bombNode = bomb[0] + "," + bomb[1];
                if (G.containsVertex(bombNode)) {
                    G.removeVertex(bombNode);
                }
            }
        }
    }
    public void inputBoardSize(){
        scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter N for N*N board (N must be at least 5) : ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter an integer.");
                    scanner.next();
                    continue;
                }

                N = scanner.nextInt();
                if (N < 5) {
                    System.out.println("Invalid input! N must be at least 5.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.next();
            }
        }
    }
    
    public void inputKnightPos(){
        knightID = new int[2] ;
        int row, col;
        while (true) {
                System.out.println("Enter Knight row");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid Input! Enter an integer.");
                    scanner.next();
                    continue;
                }
                row = scanner.nextInt() - 1;
                if (row >= 0 && row < N) break;
                System.out.println("Invalid Input! Enter row again.");
            }

            while (true) {
                System.out.println("Enter Knight column");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid Input! Enter an integer.");
                    scanner.next();
                    continue;
                }
                col = scanner.nextInt() - 1;
                if (col >= 0 && col < N) break;
                System.out.println("Invalid Input! Enter column again.");
            }
            knightID[0] = row ;
            knightID[1] = col ; 
            
            setPos("K", row, col);
            
    }
    public void inputCastlePos(){
        castleID = new int[2] ;
        int row, col;
        while(true){
            while (true) {
                    System.out.println("Enter Castle row");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid Input! Enter an integer.");
                        scanner.next();
                        continue;
                    }
                    row = scanner.nextInt() - 1;
                    if (row >= 0 && row < N) break;
                    System.out.println("Invalid Input! Enter row again.");
                }

                while (true) {
                    System.out.println("Enter Castle column");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid Input! Enter an integer.");
                        scanner.next();
                        continue;
                    }
                    col = scanner.nextInt() - 1;
                    if (col >= 0 && col < N) break;
                    System.out.println("Invalid Input! Enter column again.");
                }
            if (!isPlaced(row, col)) break ;
        }    
            castleID[0] = row ;
            castleID[1] = col ; 
            
            setPos("C", row, col);
            
    }
    
    public void inputBombPos(){
        bombID = new ArrayList<>();
        
        System.out.print("Enter number of bombs: ");
        
        int numBombs = 0;
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Enter an integer.");
                scanner.next();
                continue;
            }
            numBombs = scanner.nextInt();
            if (numBombs < 0) {
                System.out.println("Number must be non-negative.");
                continue;
            }
            break;
        }
        
        for (int i = 0; i < numBombs; i++) {
            int row, col;
            while (true) {
                System.out.println("Enter row for bomb #" + (i + 1));
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid Input! Enter an integer.");
                    scanner.next();
                    continue;
                }
                row = scanner.nextInt() - 1;
                if (row >= 0 && row < N) break;
                System.out.println("Invalid row. Try again.");
                }
            
            while (true) {
                System.out.println("Enter column for bomb #" + (i + 1));
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid Input! Enter an integer.");
                    scanner.next();
                    continue;
                }
                col = scanner.nextInt() - 1;
                if (col >= 0 && col < N) break;
                System.out.println("Invalid column. Try again.");
            }
             
            if (isPlaced(row, col)) {
                i--;
                continue;
            }
             
            bombID.add(new int[] { row, col });
            setPos("B", row, col);
        }
}
    
    public void setupBoard(){
        
        inputBoardSize();
        initBoard();
        
        printTable();
        
        inputKnightPos();
        
        inputCastlePos();
        
        inputBombPos();
        initGraph();
        
    }
    
    public void solveKnightMoves(){
        String source = knightID[0] + "," + knightID[1];
        String target = castleID[0] + "," + castleID[1];

        
        BFSShortestPath<String, DefaultEdge> bfs = new BFSShortestPath<>(G);
        GraphPath<String, DefaultEdge> path = bfs.getPath(source, target);

        if (path != null) {
            
            int i = 0 ;
            for (String vertex : path.getVertexList()) {
                String arg[] = vertex.split(",") ;
                int row = Integer.parseInt(arg[0]); 
                int col = Integer.parseInt(arg[1]) ;
                
                if(i==0){
                    System.out.println("Initial --> knight at " + (row+1)+", "+ (col+1));
                    printTable();
                    System.out.println("\nBest route to Castle : " + (path.getLength())+" move\n");
                }
                else{
                    if(board[row][col]=="C") setPos("C+K", row, col) ;
                    else setPos("K", row, col) ;
                    System.out.printf("Move %d --> jump to %d,%d \n\n", i, row+1, col+1);
                    printTable();
                }
                i++;
                setPos(".", row, col);
            }
            
        } 
        else {
            System.out.println("No path found! The knight cannot reach the castle.");
        }
    
    
    }
    
    public void solveKnightMovesProblem(){
      
        
        while(true){
            setupBoard();
            solveKnightMoves();
            
            System.out.println("New Game (y for continue, others for stop) : ");
            if(!scanner.next().equalsIgnoreCase("y")){
                break ;
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        
        KnightMoves mainApp = new KnightMoves();
        
        mainApp.solveKnightMovesProblem();
        
        
        
    }
    
}
