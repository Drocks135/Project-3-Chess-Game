package chess;

public class Pawn extends ChessPiece {

    protected int moveCounter;


    public Pawn(Player player) {
        super(player);
        moveCounter = 0;
    }

    public String type() {
        return "Pawn";
    }


    public boolean isValidMove(Move move, IChessPiece[][] board) {
        //check if move is generally valid in ChessPiece class
        if (!super.isValidMove(move, board))
            return false;

        //use helper methods to check two valid types of moves
        if ((isAdvancing(move, board)) || (isCapturing(move, board)) || (isEnPassantCapturing(move, board)))
            return true;
        else return false;
    }

    public boolean isAdvancing(Move move, IChessPiece[][] board) {
        if (move.fromColumn != move.toColumn)
            return false;
        if (moveCounter == 0) {
            if (this.player() == Player.BLACK) {
                if (move.toRow <= move.fromRow)
                    return false;
                if (move.toRow > move.fromRow + 2)
                    return false;
                if (move.toRow == move.fromRow + 2)
                    return ((board[move.toRow][move.toColumn] == null) && (board[move.toRow - 1][move.toColumn] == null));
                if (move.toRow == move.fromRow + 1)
                    return (board[move.toRow][move.toColumn] == null);
            }
            if (this.player() == Player.WHITE) {
                if (move.toRow >= move.fromRow)
                    return false;
                if (move.toRow < move.fromRow - 2)
                    return false;
                if (move.toRow == move.fromRow - 2)
                    return ((board[move.toRow][move.toColumn] == null) && (board[move.toRow + 1][move.toColumn] == null));
                if (move.toRow == move.fromRow - 1)
                    return (board[move.toRow][move.toColumn] == null);
            }
        } else {
            if (this.player() == Player.BLACK) {
                if (move.toRow <= move.fromRow)
                    return false;
                if (move.toRow > move.fromRow + 1)
                    return false;
                if (move.toRow == move.fromRow + 1)
                    return (board[move.toRow][move.toColumn] == null);
            }

            if (this.player() == Player.WHITE) {
                if (move.toRow >= move.fromRow)
                    return false;
                if (move.toRow < move.fromRow - 1)
                    return false;
                if (move.toRow == move.fromRow - 1)
                    return (board[move.toRow][move.toColumn] == null);
            }
        }
        return false;                                                       //is there any easy way to eliminate this unnecessary return
    }

    public boolean isCapturing(Move move, IChessPiece[][] board) {
        //if position is empty or has a friendly piece you are not capturing
        if (((board[move.toRow][move.toColumn] == null) ||
                (board[move.toRow][move.toColumn].player() == this.player())))
            return false;
        //black must move down the board
        if (this.player() == Player.BLACK) {
            //backwards, lateral, or more than one row are illegal
            if ((move.toRow <= move.fromRow) || (move.toRow > move.fromRow + 1))
                return false;
            if (move.toRow == move.fromRow + 1)
                //must capture diagonally (one forward and one across)
                if ((move.toColumn == move.fromColumn + 1) || (move.toColumn == move.fromColumn - 1))
                    return true;
        }
        //white must move up the board
        if (this.player() == Player.WHITE) {
            //backwards, lateral, or more than one row are illegal
            if ((move.toRow >= move.fromRow) || (move.toRow < move.fromRow - 1))
                return false;
            if (move.toRow == move.fromRow - 1)
                //must capture diagonally (one forward and one across)
                if ((move.toColumn == move.fromColumn + 1) || (move.toColumn == move.fromColumn - 1))
                    return true;
        }
        return false;
    }

    public boolean isEnPassantCapturing(Move move, IChessPiece[][] board) {
        boolean enPassant = false;
//        if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
//            if (move.fromRow != board.length - 5)
//                return enPassant;
//            if (move.fromColumn >= board[0].length - 7) {
//                if (board[move.fromRow][move.fromColumn - 1].equals(new Pawn(Player.BLACK)))
//                    enPassant = true;
//            }
//            if (move.fromColumn <= 7) {
//                if (board[move.fromRow][move.fromColumn + 1].equals(new Pawn(Player.BLACK)))
//                    enPassant = true;
//            }
//        }
//        if (board[move.fromRow][move.fromColumn].player() == Player.BLACK) {
//            if (move.fromRow != board.length - 4)
//                return enPassant;
//            if (move.fromColumn >= board[0].length - 7) {
//                if (board[move.fromRow][move.fromColumn - 1].equals(new Pawn(Player.WHITE)))
//                    enPassant = true;
//            }
//            if (move.fromColumn <= 7) {
//                if (board[move.fromRow][move.fromColumn + 1].equals(new Pawn(Player.WHITE)))
//                    enPassant = true;
//            }
//        }
        return enPassant;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Pawn clone = (Pawn) super.clone();
        clone.moveCounter = this.moveCounter;
        return clone;
    }
}

