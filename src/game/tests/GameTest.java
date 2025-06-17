package game.tests;

import java.util.Collection;
import game.*;

public class GameTest extends Test {
    public static void main(String[] args) {
        // Constructor tests
        boolean caught = false;
        try {
            new GameImpl(0);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            new GameImpl(5);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(false, caught);
        // ToDo
        // Basic game setup
        Game game = new GameImpl(5);
        expect(PieceColour.WHITE, game.currentPlayer());
        expect(false, game.isOver());
        expect(PieceColour.NONE, game.winner());

        Collection<Move> moves = game.getMoves();
        expect(25, moves.size()); // 5x5 = 25 possible moves

        Grid grid = game.getGrid();
        expect(5, grid.getSize());
        expect(PieceColour.NONE, grid.getPiece(0, 0));
        expect(PieceColour.NONE, grid.getPiece(4, 4));

        // Test making moves and player switching
        Move firstMove = new MoveImpl(0, 0);
        game.makeMove(firstMove);
        expect(PieceColour.BLACK, game.currentPlayer());
        expect(PieceColour.WHITE, game.getGrid().getPiece(0, 0));
        expect(24, game.getMoves().size());

        game.makeMove(new MoveImpl(1, 1));
        expect(PieceColour.WHITE, game.currentPlayer());
        expect(PieceColour.BLACK, game.getGrid().getPiece(1, 1));

        // Invalid moves testing
        caught = false;
        try {
            game.makeMove(new MoveImpl(0, 0)); // already occupied
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        // Test boundary violations
        caught = false;
        try {
            game.makeMove(new MoveImpl(-1, 0)); // negative coordinate
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            game.makeMove(new MoveImpl(0, 5)); // out of bounds
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        // null move
        caught = false;
        try {
            game.makeMove(null);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        // Test winning horizontally - white wins
        Game winGame = new GameImpl(5);
        winGame.makeMove(new MoveImpl(0, 0)); // white
        winGame.makeMove(new MoveImpl(1, 0)); // black
        winGame.makeMove(new MoveImpl(0, 1)); // white
        winGame.makeMove(new MoveImpl(1, 1)); // black
        winGame.makeMove(new MoveImpl(0, 2)); // white
        winGame.makeMove(new MoveImpl(1, 2)); // black
        winGame.makeMove(new MoveImpl(0, 3)); // white
        winGame.makeMove(new MoveImpl(1, 3)); // black

        expect(false, winGame.isOver());

        winGame.makeMove(new MoveImpl(0, 4)); // white gets 5 in a row!
        expect(true, winGame.isOver());
        expect(PieceColour.WHITE, winGame.winner());

        // Vertical win for black
        Game vertWin = new GameImpl(5);
        vertWin.makeMove(new MoveImpl(0, 1)); // white
        vertWin.makeMove(new MoveImpl(0, 0)); // black
        vertWin.makeMove(new MoveImpl(0, 2)); // white
        vertWin.makeMove(new MoveImpl(1, 0)); // black
        vertWin.makeMove(new MoveImpl(0, 3)); // white
        vertWin.makeMove(new MoveImpl(2, 0)); // black
        vertWin.makeMove(new MoveImpl(1, 1)); // white
        vertWin.makeMove(new MoveImpl(3, 0)); // black
        vertWin.makeMove(new MoveImpl(1, 2)); // white

        expect(false, vertWin.isOver());

        vertWin.makeMove(new MoveImpl(4, 0)); // black wins vertically
        expect(true, vertWin.isOver());
        expect(PieceColour.BLACK, vertWin.winner());

        // Test draw - board fills up with no winner
        Game drawGame = new GameImpl(3);
        drawGame.makeMove(new MoveImpl(0, 1));
        drawGame.makeMove(new MoveImpl(0, 0));
        drawGame.makeMove(new MoveImpl(1, 0));
        drawGame.makeMove(new MoveImpl(1, 1));
        drawGame.makeMove(new MoveImpl(1, 2));
        drawGame.makeMove(new MoveImpl(0, 2));
        drawGame.makeMove(new MoveImpl(2, 0));
        drawGame.makeMove(new MoveImpl(2, 1));

        expect(false, drawGame.isOver());
        expect(1, drawGame.getMoves().size()); // one spot left

        drawGame.makeMove(new MoveImpl(2, 2)); // fill last spot

        expect(true, drawGame.isOver());
        expect(PieceColour.NONE, drawGame.winner()); // it's a draw

        // Can't make moves after game ends
        caught = false;
        try {
            winGame.makeMove(new MoveImpl(2, 2));
        } catch (IllegalStateException e) {
            caught = true;
        }
        expect(true, caught);

        caught = false;
        try {
            vertWin.makeMove(new MoveImpl(1, 3));
        } catch (IllegalStateException e) {
            caught = true;
        }
        expect(true, caught);

        // Test copy functionality
        Game orig = new GameImpl(5);
        orig.makeMove(new MoveImpl(0, 0));
        orig.makeMove(new MoveImpl(1, 1));

        Game copy = orig.copy();
        expect(orig.currentPlayer(), copy.currentPlayer());
        expect(orig.isOver(), copy.isOver());

        // Modify original, copy should be unchanged
        orig.makeMove(new MoveImpl(2, 2));
        expect(PieceColour.BLACK, orig.currentPlayer());
        expect(PieceColour.WHITE, copy.currentPlayer()); // No copy changed
        expect(PieceColour.NONE, copy.getGrid().getPiece(2, 2));

        // Grid should be immutable when accessed
        Grid gameGrid = game.getGrid();
        gameGrid.setPiece(2, 2, PieceColour.BLACK);
        expect(PieceColour.NONE, game.getGrid().getPiece(2, 2)); // No game changed
        expect(PieceColour.BLACK, gameGrid.getPiece(2, 2)); // but local copy changed

        checkAllTestsPassed();
    }
}