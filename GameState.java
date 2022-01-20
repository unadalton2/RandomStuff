package com.company;

import java.util.ArrayList;

public class GameState implements Cloneable{
    int[] state;//0-5 are move-able | 6 is your pocket & 7-12 are non-move-able
    boolean alive;
    ArrayList<Integer> moves = new ArrayList<>();
    public GameState(int[] state, boolean alive){
        this.state = state;// must be 13 long
        this.alive = alive;
    }
    public GameState clone() throws CloneNotSupportedException
    {
        GameState cloned = (GameState) super.clone();
        cloned.setState(this.state.clone());
        cloned.setAlive(this.alive);
        cloned.setMoves((ArrayList<Integer>) this.moves.clone());
        return cloned;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setMoves(ArrayList<Integer> moves) {
        this.moves = moves;
    }

    public boolean[] getMoves(){
        boolean[] output = new boolean[6];

        for (int i = 0; i < 6; i++) {
            output[i]= state[i]!=0;
        }
        return output;
    }
    private int moveP(int index){// Gets the number of marbles at index, moves them around the board and returns the index of the final marble
        if(index == 6) throw new IllegalArgumentException("Cannot move marbles out of pocket");
        int holding = state[index];// Get marbles in pocket
        state[index] = 0;// remove them from the pocket
        int current_index = index;
        for (int i = 0; i < holding; i++) {
            current_index++;//update index
            if(current_index == state.length)current_index = 0;//reset index when reached end of board

            state[current_index]++;//put one marble in each hole
        }
        return current_index;//gets the last hole a marble was placed into
    }
    public boolean move(int index){// Moves marbles from index & returns if a player has another turn;
        if(index == 6) throw new IllegalArgumentException("Cannot move marbles out of pocket");
        if(index > 6) throw new IllegalArgumentException("Cannot move other players marbles");
        if(state[index]==0) throw new IllegalArgumentException("No marbles to move");
        moves.add(index);
        int nextHole = index;
        while(true){
            nextHole = moveP(nextHole);//Move and get next move
            if(nextHole==6) {
                this.alive = true;
                return true;
            }
            if(state[nextHole]==1){
                this.alive = false;
                return false;
            }
        }
    }
    public boolean isGameOver(){//returns true if game is over
        boolean v1 = true;
        for (int i = 0; i < 6; i++) {
            if(state[i] != 0) {
                v1 = false;
                break;
            }
        }
        boolean v2 = true;
        for (int i = 7; i < 13; i++) {
            if(state[i] != 0) {
                v2 = false;
                break;
            }
        }
        return v1||v2;
    }
    public int getScore(){//gets current score adds a bonus for ending the game
        if(isGameOver()) return state[6]+48;
        return state[6];
    }
}
