package org.openjfx;

import java.util.Arrays;

public class Simulation {
    private final int width , height;
    private int [][] board;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[height][width];
    }

    public void setAlive(int x , int y){ board[y][x] = 1; }
    public void setDead(int x, int y){ board[y][x] = 0; }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("=========================================\n");
        for (int x = 0; x < width; x++) {
            output.append("|");
            for (int y = 0; y < height; y++) {
                int elem = board[x][y];
                if (elem == 1) {
                    output.append(" x ");
                } else {
                    output.append(" . ");
                }
            }
            output.append("|\n");
        }
        output.append("=========================================\n");
        return output.toString();
    }

    public int getState(int x ,int y){
        if(x < 0 || x >= width) return 0;
        if(y < 0 || y >= height) return 0;
        return this.board[y][x];
    }


    public int countLiveNeighbours(int x, int y){
        int count = 0;
        int begin_y = y - 1;
        int max_x = x + 1;
        int max_y = y + 1;
        for (int offSet_y =  y - 1; offSet_y <= max_x; offSet_y++) {
            for (int offSet_x = y - 1;offSet_x <= max_x; offSet_x++) {
                if(offSet_x == x && offSet_y == y) break;
                else {
                    if(getState(offSet_x, offSet_y) == 1) count += 1;
                }

            }
        }
        return count;
    }
    public void step(){
        int [][] newBoard = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int liveNeighbours = countLiveNeighbours(x, y);
                if(getState(x, y) == 1){
                    if(liveNeighbours < 2) newBoard[x][y] = 0;
                    else if(liveNeighbours == 2 || liveNeighbours == 3) newBoard[x][y] = 1;
                    else newBoard[x][y] = 0;
                } else {
                    if(liveNeighbours == 3) newBoard[x][y] = 1;
                }
            }
        }
        this.board = newBoard;
    }
    public static void main(String[] args) {
        Simulation sim = new Simulation(5, 5);
        sim.setAlive(1, 2);
        sim.setAlive(2, 2);
        sim.setAlive(3, 2);
        System.out.println(sim);
        for (int i = 0; i < 10; i++) {
            sim.step();
            System.out.println(sim);
        }

    }
}
