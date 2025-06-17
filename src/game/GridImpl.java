package game;


public class GridImpl implements Grid {
    private final int size;
    private final PieceColour[][] grid;

    public GridImpl(int size) {
        if (size < 1) {  // Changed from <= 0 to < 1 to match GameImpl
            throw new IllegalArgumentException("Grid size must be positive");
        }
        this.size = size;
        this.grid = new PieceColour[size][size];

        // Set everything to empty initially
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = PieceColour.NONE;
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public PieceColour getPiece(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return grid[row][col];
    }

    @Override
    public void setPiece(int row, int col, PieceColour piece) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null");
        }
        grid[row][col] = piece;
    }

    @Override
    public Grid copy() {
        GridImpl copy = new GridImpl(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy.grid[i][j] = this.grid[i][j];
            }
        }
        return copy;
    }

    // Added this for debugging - useful to see the board state
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == PieceColour.WHITE) {
                    sb.append("W");
                } else if (grid[row][col] == PieceColour.BLACK) {
                    sb.append("B");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}