/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2;

import com.mycompany.proyecto2.MiniMax;
import com.mycompany.proyecto2.Board;

/**
 *
 * @author Michael
 */
public class Algorithms {
    
    /**
     * Algorithms cannot be instantiated.
     */
    private Algorithms() {}

    /**
     * Play a random move.
     * @param board     the Tic Tac Toe board to play on
     */
  
    /**
     * Play using the MiniMax Algorithm.
     * @param board     the Tic Tac Toe board to play on
     */
    public static void miniMax (Board board) {

        MiniMax.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /**
     * Play using the MiniMax algorithm. Include a depth limit.
     * @param board     the Tic Tac Toe board to play on
     * @param ply       the maximum depth
     */
    public static void miniMax (Board board, int ply) {
        MiniMax.run(board.getTurn(), board, ply);
    }

   
}
