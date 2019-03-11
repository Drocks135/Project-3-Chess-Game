package chess;

import java.util.ArrayList;

import static chess.Player.BLACK;
import static chess.Player.WHITE;

public class ChessModel implements IChessModel, Cloneable {
    ArrayList<Move> moveArrayList;
    private IChessPiece[][] board;
    private Player player;

    // declare other instance variables as needed

    public ChessModel() {
        board = new IChessPiece[8][8];
        player = WHITE;

        board[7][0] = new Rook(WHITE);
        board[7][1] = new Knight(WHITE);
        board[7][2] = new Bishop(WHITE);
        board[7][3] = new Queen(WHITE);
        board[7][4] = new King(WHITE);
        board[7][5] = new Bishop(WHITE);
        board[7][6] = new Knight(WHITE);
        board[7][7] = new Rook(WHITE);

        for (int i = 0; i < board[0].length; i++) {
            board[board.length - 2][i] = new Pawn(WHITE);
        }

        board[0][0] = new Rook(BLACK);
        board[0][1] = new Knight(BLACK);
        board[0][2] = new Bishop(BLACK);
        board[0][3] = new Queen(BLACK);
        board[0][4] = new King(BLACK);
        board[0][5] = new Bishop(BLACK);
        board[0][6] = new Knight(BLACK);
        board[0][7] = new Rook(BLACK);

        for (int i = 0; i < board[0].length; i++) {
            board[1][i] = new Pawn(BLACK);
        }

        moveArrayList = new ArrayList<Move>();
    }

    public static void main(String[] args) {
        ChessModel cm = new ChessModel();
    }

    public boolean isComplete() {
        boolean valid = false;
        return valid;
    }

    public boolean isValidMove(Move move) {
        boolean valid = false;

        if ((board[move.fromRow][move.fromColumn] != null) &&
                (board[move.fromRow][move.fromColumn].isValidMove(move, board)) &&
                (board[move.fromRow][move.fromColumn].player() == currentPlayer())) {
            return true;
        }

        return valid;
    }


    public void move(Move move) {

        //check if castling before moving anything
        if (board[move.fromRow][move.fromColumn] instanceof King) {
            if (((King) board[move.fromRow][move.fromColumn]).isCastling(move, board)) {
                castlingRookHelper(move);
            }
            ((King) board[move.fromRow][move.fromColumn]).moveCounter++;
        }
        //move the piece
        board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;


        if (board[move.toRow][move.toColumn] instanceof Rook)
            ((Rook) board[move.toRow][move.toColumn]).moveCounter++;

        if (board[move.toRow][move.toColumn] instanceof Pawn) {
            ((Pawn) board[move.toRow][move.toColumn]).moveCounter++;
            //check for "queening"
            if ((board[move.toRow][move.toColumn].player() == BLACK) &&
                    (move.toRow == board.length - 1))
                board[move.toRow][move.toColumn] = new Queen(BLACK);

            if ((board[move.toRow][move.toColumn].player() == WHITE) &&
                    (move.toRow == 0))
                board[move.toRow][move.toColumn] = new Queen(WHITE);
        }
        moveArrayList.add(move);
    }

    public void undo() {
        //must reverse complex moves castling, queening
        //if move is castling then one undo should undo 2 "moves"

        //must also reverse "destroyed" pieces

        if (moveArrayList.size() >= 1) {
            //make local copy of last move for easy reference
            Move lastMove = moveArrayList.remove(moveArrayList.size() - 1);


            if (!board[lastMove.toRow][lastMove.toColumn].type().equals("King") &&
                    (!board[lastMove.toRow][lastMove.toColumn].type().equals("Queen"))) {

                //reverse the last move
                move(new Move(lastMove.toRow, lastMove.toColumn, lastMove.fromRow, lastMove.fromColumn));

                //remove the fake reversal "move" from the archive
                moveArrayList.remove(moveArrayList.size() - 1);

                //if you reverse the move then also decrement the moveCounter
                if (board[lastMove.fromRow][lastMove.toColumn] instanceof Pawn)
                    ((Pawn) board[lastMove.fromRow][lastMove.toColumn]).moveCounter--;

                if (board[lastMove.fromRow][lastMove.toColumn] instanceof Rook)
                    ((Rook) board[lastMove.fromRow][lastMove.toColumn]).moveCounter--;
            }
        }
    }

    public boolean inCheck() {
        Player defendingPlayer;
        if (player == BLACK)
            defendingPlayer = WHITE;
        else defendingPlayer = BLACK;
        int defendingKingRow = -1;
        int defendingKingCol = -1;
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c].equals(new King(defendingPlayer)))) {
                    defendingKingRow = r;
                    defendingKingCol = c;
                    //break out of nested loop search
                    r = board.length;
                    c = board[0].length;
                }
            }
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c] != null) &&
                        (board[r][c].player() == player) &&
                        (board[r][c].isValidMove(new Move(r, c, defendingKingRow, defendingKingCol), board)))
                    return true;
            }
        return false;
    }

    public void castlingRookHelper(Move kingMove) {
        int rookColStart = -1;
        int rookColEnd = -1;

        if (kingMove.toRow == 0) {
            if (kingMove.toColumn == board[0].length - 2) {
                rookColStart = board[0].length - 1;
                rookColEnd = board[0].length - 3;
            }
            if (kingMove.toColumn == board[0].length - 6) {
                rookColStart = 0;
                rookColEnd = board[0].length - 5;
            }
        }
        if (kingMove.toRow == 7) {
            if (kingMove.toColumn == board[0].length - 2) {
                rookColStart = board[0].length - 1;
                rookColEnd = board[0].length - 3;
            }
            if (kingMove.toColumn == board[0].length - 6) {
                rookColStart = 0;
                rookColEnd = board[0].length - 5;
            }
        }
        move(new Move(kingMove.toRow, rookColStart, kingMove.toRow, rookColEnd));
    }

    public boolean inCheck(Player p) {
        boolean valid = false;
        return valid;
    }

    public Player currentPlayer() {
        return player;
    }

    public int numRows() {
        return board.length;
    }

    public int numColumns() {
        return board[0].length;
    }

    public IChessPiece pieceAt(int row, int column) {
        return board[row][column];
    }

    public void setNextPlayer() {
        player = player.next();
    }

    public void setPiece(int row, int column, IChessPiece piece) {
        board[row][column] = piece;
    }

    public void AI() {
        /*
         * Write a simple AI set of rules in the following order.
         * a. Check to see if you are in check.
         * 		i. If so, get out of check by moving the king or placing a piece to block the check
         *
         * b. Attempt to put opponent into check (or checkmate).
         * 		i. Attempt to put opponent into check without losing your piece
         *		ii. Perhaps you have won the game.
         *
         *c. Determine if any of your pieces are in danger,
         *		i. Move them if you can.
         *		ii. Attempt to protect that piece.
         *
         *d. Move a piece (pawns first) forward toward opponent king
         *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
         */

    }

    @Override
    public Object clone() {
        ChessModel clone = new ChessModel();
        try {
            clone = (ChessModel) super.clone();
            clone.board = this.board.clone();
            // clone.player = this.player;    //probably ok but could be an issue
        } catch (CloneNotSupportedException e) {
            System.out.println("Error  ");    //FIXME
        }
        return clone;
    }

}
