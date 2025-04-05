// Alex Stoffel
// This is the Maze solver class, and basically solves any maze

/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        // Store a Stack so we can reverse the solution
        Stack<MazeCell> solution = new Stack<MazeCell>();

        // Start at the end cell
        MazeCell cell = maze.getEndCell();
        while (cell != null && !cell.equals(maze.getStartCell())){
            // Add the cell to the solution
            solution.add(cell);
            cell = cell.getParent();
        }
        // If the cell is null return null (safety)
        if (cell.equals(null)){
            return null;
        }
        // Add the start cell, since the while loop wont include it
        solution.add(cell);
        ArrayList<MazeCell> arr = new ArrayList<MazeCell>();
        while (!solution.empty()){
            arr.add(solution.pop());
        }
        return arr;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Cells to visit Stack
        Stack<MazeCell> cellsToVisit = new Stack<MazeCell>();
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Starting the exploration
        MazeCell currentCell = maze.getStartCell();
        currentCell.setExplored(true);
        MazeCell endCell = maze.getEndCell();

        // Add the first cell into the solution
        cellsToVisit.push(currentCell);

        // Variable to store the last cell
        MazeCell lastCell = null;

        // Solving the path
        while(!currentCell.equals(maze.getEndCell())){
            currentCell = cellsToVisit.pop();


            // Check if it is the end cell
            if(currentCell.equals(endCell)){
                return this.getSolution();
            }

            // Now add all of them in order to the cells to visit
            ArrayList<MazeCell> neswCells = neighborsFinder(currentCell);
            while(neswCells.size() > 0){
                // Add the NESW cell to the cells to visit
                cellsToVisit.push(neswCells.remove(0));
            }
        }

        return null;
    }

    // DFS & BFS helper which will return all the neighbors in correct W,S,E,N form (reversed for the stack)
    public ArrayList<MazeCell> neighborsFinder(MazeCell cell) {
        ArrayList<MazeCell> array = new ArrayList<MazeCell>();
        // Add the available directions to the array in
        for (int i = -1; i < 2; i += 2){
            // Add all squares that are left and right
            // This will account for the out of bounds error
            if (cell.getCol() + i < maze.getNumCols() && cell.getCol() + i >= 0){
                MazeCell col = maze.getCell(cell.getRow(), cell.getCol() + i);
                // Make sure it is valid
                if (!col.isWall() && !col.isExplored()){
                    array.add(col);
                    // Set the parent and that it has been explored
                    col.setParent(cell);
                    col.setExplored(true);
                }
            }

            // Add all squares in the South and North
            if (cell.getRow() + i < maze.getNumRows() && cell.getRow() + i >= 0){
                MazeCell row = maze.getCell(cell.getRow() + i, cell.getCol());
                if (!row.isWall() && !row.isExplored()){
                    array.add(row);
                    // Set the parent and that it has been explored
                    row.setParent(cell);
                    row.setExplored(true);
                }
            }
        }
        // Return the arrayList of the mazecells
        return array;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Cells to visit Queue
        Queue<MazeCell> cellsToVisit = new LinkedList<MazeCell>();
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Starting the exploration
        MazeCell currentCell = maze.getStartCell();
        currentCell.setExplored(true);
        MazeCell endCell = maze.getEndCell();

        // Add the first cell into the solution
        cellsToVisit.add(currentCell);

        // Solving the path
        while(!currentCell.equals(maze.getEndCell())){
            currentCell = cellsToVisit.remove();

            // Check if it is the end cell
            if(currentCell.equals(endCell)){
                return this.getSolution();
            }

            // Now add all of them in order to the cells to visit
            ArrayList<MazeCell> neswCells = neighborsFinder(currentCell);
            while(neswCells.size() > 0){
                // Add the NESW cell to the cells to visit
                cellsToVisit.add(neswCells.remove(0));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
