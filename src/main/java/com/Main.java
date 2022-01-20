package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
	    GameState start = new GameState(new int[] {4,4,4,4,4,4,0,4,4,4,4,4,4}, true);
        Queue<GameState> queue = new LinkedList<>();
        ArrayList<GameState> visited = new ArrayList<>();
        queue.add(start);

        while(!queue.isEmpty()){
            GameState m = queue.remove();//get next GameState

            if(m.alive) {
                boolean[] neighbour = m.getMoves();//get neighbours
                for (int i = 0; i < neighbour.length; i++) {
                    if (neighbour[i]) {//checks if neighbour is a valid move
                        GameState n = null;//makes clone
                        try {
                            n = m.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        n.move(i);//moves clone into a new state
                        queue.add(n);//moves n into queue to explore its moves
                    }
                }
            } else visited.add(m);
        }

        System.out.println(visited.size()+" Possible moves");

        //Gets the highest possible score
        int max = 0;
        int index = 0;
        int total = 0;
        for (int i = 0; i < visited.size(); i++) {
            int score = visited.get(i).getScore();
            if(score>max){
                max = score;
                index = i;
            }
            total += score;
        }
        System.out.println("Has an average score of :"+(total/visited.size()));
        System.out.println(Arrays.toString(visited.get(index).moves.toArray()));
        System.out.println("With a score of :"+visited.get(index).getScore());

        //gets the fastest set of moves to end the game in one turn
        int turns = 10000;
        index = 0;
        int score = 0;
        for (int i = 0; i < visited.size(); i++) {
            if(visited.get(i).isGameOver()&&(visited.get(i).moves.size()<turns||(turns==visited.get(i).moves.size()&&score<visited.get(i).getScore()))){
                turns = visited.get(i).moves.size();
                score = visited.get(i).getScore();
                index = i;
            }
        }
        System.out.println("Shortest win is ");
        System.out.println(Arrays.toString(visited.get(index).moves.toArray()));
        System.out.println("With a score of :"+visited.get(index).getScore());
    }
}
