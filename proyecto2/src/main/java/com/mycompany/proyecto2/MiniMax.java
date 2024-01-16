package com.mycompany.proyecto2;

import com.mycompany.proyecto2.Game;
import Logica.Tree;
import com.mycompany.proyecto2.Board.State;
import javafx.stage.Stage;
import com.mycompany.proyecto2.Board.State;

public class MiniMax {

    private static double maxPly;

    private MiniMax() {
    }

    public static void run(State player, Board board, double maxPly) {
        if (maxPly < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        MiniMax.maxPly = maxPly;
        Tree<Board> tree = new Tree<>(board.getDeepCopy());
                        

        miniMax(tree, player, board, 0);
    }

    private static int miniMax(Tree<Board> tree, State player, Board board, int currentPly) {
        Game.intermediateBoards.add(board);
        if (currentPly++ == maxPly || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(tree, player, board, currentPly);
        } else {
            return getMin(tree, player, board, currentPly);
        }
    }

    private static int getMax(Tree<Board> tree, State player, Board board, int currentPly) {
       
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);
            Tree<Board> childTree = new Tree<>(modifiedBoard);

            tree.addChild(modifiedBoard);

            int score = miniMax(childTree, player, modifiedBoard, currentPly);
            if (score > bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }
        }
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return (int) bestScore;
    }

    private static int getMin(Tree<Board> tree, State player, Board board, int currentPly) {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);
            Tree<Board> childTree = new Tree<>(modifiedBoard);
            tree.addChild(modifiedBoard);

            int score = miniMax(childTree, player, modifiedBoard, currentPly);
            if (score < bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return (int) bestScore;
    }

    private static int score(State player, Board board) {
        if (player == State.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }
}
