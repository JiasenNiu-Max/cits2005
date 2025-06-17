package game;

import java.util.ArrayList;
import java.util.Collection;

public class GameImpl implements Game {
    private Grid grid;
    private PieceColour currentPlayer;

    public GameImpl(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be positive");
        }
        this.grid = new GridImpl(size);
        this.currentPlayer = PieceColour.WHITE;
    }

    // Copy constructor
    private GameImpl(Grid grid, PieceColour player) {
        this.grid = grid.copy();
        this.currentPlayer = player;
    }

    @Override
    public boolean isOver() {
        // Check if someone won first
        if (winner() != PieceColour.NONE) {
            return true;
        }

        // Check if board is full (draw)
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if (grid.getPiece(i, j) == PieceColour.NONE) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public PieceColour winner() {
        // Check white first
        if (PathFinder.topToBottom(grid, PieceColour.WHITE) ||
                PathFinder.leftToRight(grid, PieceColour.WHITE)) {
            return PieceColour.WHITE;
        }

        // Then check black
        if (PathFinder.topToBottom(grid, PieceColour.BLACK) ||
                PathFinder.leftToRight(grid, PieceColour.BLACK)) {
            return PieceColour.BLACK;
        }

        return PieceColour.NONE;
    }

    @Override
    public PieceColour currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Collection<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        if (isOver()) {
            return moves;
        }

        int size = grid.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid.getPiece(row, col) == PieceColour.NONE) {
                    moves.add(new MoveImpl(row, col));
                }
            }
        }

        return moves;
    }

    @Override
    public void makeMove(Move move) {
        if (isOver()) {
            throw new IllegalStateException("Game is over");
        }

        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }

        int row = move.getRow();
        int col = move.getCol();

        // Check bounds
        if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize()) {
            throw new IllegalArgumentException("Move out of bounds");
        }

        // Check if position is free
        if (grid.getPiece(row, col) != PieceColour.NONE) {
            throw new IllegalArgumentException("Position already occupied");
        }

        // Make the move
        grid.setPiece(row, col, currentPlayer);

        // Switch players
        if (currentPlayer == PieceColour.WHITE) {
            currentPlayer = PieceColour.BLACK;
        } else {
            currentPlayer = PieceColour.WHITE;
        }
    }

    @Override
    public Grid getGrid() {
        return grid.copy();
    }

    @Override
    public Game copy() {
        return new GameImpl(grid, currentPlayer);
    }
}