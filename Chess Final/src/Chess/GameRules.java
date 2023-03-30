package Chess;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class GameRules {
    private static final Board board = new Board();

    private final Map<ActionTaken, Integer> history = new HashMap<>();
    private int draw;
    private Coord enpassant;


    //Moving pieces
    public CGS normalmove(Pieces moving, Coord from, Coord to) {
        return executemove(moving, from, to);
    }

    private CGS executemove(Pieces pieces, Coord from, Coord to) {
        if (pieces instanceof King) {
            board.moveking(to);
        }
        int repeat = movepiece(pieces, from, to);
        board.flip();
        return GOstate(repeat);
    }

    private int movepiece(Pieces pieces, Coord start, Coord end) {
        int count = 0;
        if (pieces instanceof Pawn && start.y() - end.y() == 2) {
            enpassant = Coord.instance(end.x(), 6 - end.y());
        } else {
            enpassant = null;
        }
        if (board.getBoard(end) != null || pieces instanceof Pawn) {
            draw = 0;
            history.clear();
        } else {
            draw++;
            var gamestate = new ActionTaken(board, enpassant);
            count = history.getOrDefault(gamestate,0)+1;
            history.put(gamestate, count);
        }
        raw(pieces, start, end);
        pieces.setMove();
        return count;
    }

    private void raw(Pieces pieces, Coord start, Coord end) {

        board.setBoard(start, null);
        board.setBoard(end, pieces);
    }

    public Color[][] getImage(Coord coord) {
        return board.getImage(coord);
    }

    public static boolean isLightTile(Coord coord) {
        return board.isLightTile(coord);
    }

    public Pieces GAP(Coord coord) {
        return board.getAlliedPiece(coord);
    }

//Castling

    private void performcastle(Coord from, Coord to) {
        board.setBoard(to, board.getBoard(from));
        board.setBoard(from, null);
        board.getBoard(to).setMove();
    }

    private void finishcastling(Coord coord) {
        board.moveking(coord);
        enpassant = null;
        history.clear();
        board.flip();
    }

    public void KingSideCastle() {
        var king = Coord.instance(Board.KINGX, 7);
        var rook = Coord.instance(0, 7);
        performcastle(king, Coord.instance(Board.KINGX + 2, 7));
        performcastle(rook, Coord.instance(Board.KINGX + 1, 7));
        finishcastling(Coord.instance(Board.KINGX + 2, 7));
    }

    public void QueenSideCastle() {
        var king = Coord.instance(Board.KINGX, 7);
        var rook = Coord.instance(0, 7);
        performcastle(king, Coord.instance(Board.KINGX - 2, 7));
        performcastle(rook, Coord.instance(Board.KINGX - 1, 7));
        finishcastling(Coord.instance(Board.KINGX - 2, 7));
    }

    //add enpassant
    public CGS enPassant(Pieces moving, Coord from) {
        var squareabove = Coord.instance(enpassant.x(), enpassant.y() + 1);
        movepiece(moving, from, enpassant);
        board.setBoard(squareabove, null);
        board.flip();
        return GOstate(0);
    }

    public CGS pawnpromo(AllowedPromo promo, Coord from, Coord to) {
        return executemove(board.getpromopiece(promo), from, to);
    }
    /////////////////////////////////////////////////////////////////////////////////////
    // all stuff for gameover

    private CGS GOstate(int repeats) {
        if (GOfromCheckmate()) {
            return board.getAlliedKing().isWhite() ? CGS.BW : CGS.WW;
        }
        if (GOfromStalemate()) {
            return CGS.Draw;
        }
        if (toomanymoves()) {
            return CGS.TOOMANYMOVES;
        }
        if (toomanyrepeats(repeats)) {

            return CGS.TOOMANYREP;
        }
        if (board.getAlliedKing().checkKing(board.locateking())) {
            return CGS.CHECK;
        }
        return CGS.INPLAY;
    }

    private boolean impossiblemove() {
        var king = board.getAlliedKing();
        for (int i = 0; i < 8; i++) {
            for (int ii = 0; ii < 8; ii++) {
                var start = Coord.instance(i, ii);
                var piece = board.getBoard(start);
                if (piece != null && board.isAlly(piece)) {
                    for (int iii = 0; iii < 8; iii++) {
                        for (int iiii = 0; iiii < 8; iiii++) {
                            var end = Coord.instance(iii, iiii);
                            var save = board.getBoard(end);
                            if (piece.AllowedMove(start, end)) {
                                raw(piece, start, end);
                                var kingloc = board.locateking();
                                boolean notcheck = !king.checkKing(kingloc);
                                raw(piece, end, start);
                                board.setBoard(end, save);
                                if (notcheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        ;
        return true;
    }


    private boolean GOfromCheckmate() {
        var king = board.getAlliedKing();
        var kingloc = board.locateking();
        return king.checkKing(kingloc) && impossiblemove();
    }

    private boolean GOfromStalemate() {
        var king = board.getAlliedKing();
        var kingloc = board.locateking();
        return !king.checkKing(kingloc) && impossiblemove();

    }

    private boolean toomanymoves() {
        int maxbadmoves = 100;
        return draw >= maxbadmoves;

    }

    private boolean toomanyrepeats(int repeats) {
        int maxrep = 3;
        return repeats >= maxrep;
    }

    private boolean hasmoved(Pieces pieces) {
        return pieces == null || pieces.hasMoved();
    }

    private boolean canCastle(Coord from, int rookx) {
        if (!from.equals(Coord.instance(Board.KINGX, 7))) {
            return false;
        }
        var king = board.getAlliedKing();
        var rook = board.getBoard(Coord.instance(rookx, 7));
        if (hasmoved(king) || hasmoved(rook)) {
            return false;
        }
        int min = Math.min(rookx, board.locateking().x());
        int max = Math.min(rookx, board.locateking().x());
        for (int x = min + 1; x < max; x++) {
            if (board.getBoard(Coord.instance(x, 7)) != null) {
                return false;
            }
        }
        int direction = Integer.signum(rookx - board.locateking().x());
        for (int i = 0; i <= 2; i++) {
            var tile = Coord.instance(Board.KINGX + i * direction, 7);
            if (king.checkKing(tile)) {
                return false;
            }
        }
        return true;
    }

    private boolean canQSC(Coord from) {
        return canCastle(from, 0);
    }

    private boolean canKSC(Coord from) {
        return canCastle(from, 7);
    }

    private boolean canenpassant(Pieces moving, Coord from) {
        if (enpassant == null || !(moving instanceof Pawn)) {
            return false;
        }
        var squareabove = Coord.instance(enpassant.x(), enpassant.y() + 1);
        var isenemy = moving.isWhite() != board.getBoard(squareabove).isWhite();
        var diffx = Math.abs(from.x() - enpassant.x());
        return isenemy && from.y() == enpassant.y() + 1 && diffx == 1;
    }


    public ListOfMoves[][] avaliblemoves(Pieces moving, Coord from) {
        var moves = new ListOfMoves[8][8];
        for (var slice : moves) {
            Arrays.fill(slice, ListOfMoves.NONE);
        }
        for (int i = 0; i < 8; i++) {
            for (int ii = 0; ii < 8; ii++) {
                var checkloc = Coord.instance(ii, i);
                if (moving instanceof King) {
                    var backup = board.getBoard(from);
                    board.setBoard(from, null);
                    if (moving.AllowedMove(from, checkloc)) {
                        moves[i][ii] = ListOfMoves.REGULAR;
                    }
                    board.setBoard(from, backup);
                } else if (moving.AllowedMove(from, checkloc)) {
                    if (moving instanceof Pawn && checkloc.y() == 0) {
                        moves[i][ii] = ListOfMoves.PROMOTION;
                    } else {
                        moves[i][ii] = ListOfMoves.REGULAR;
                    }
                }
            }
        }
        if (canQSC(from)) {
            moves[7][0] = ListOfMoves.QSCastle;

        }
        if (canKSC(from)) {
            moves[7][7] = ListOfMoves.KSCastle;
        }
        if (canenpassant(moving, from)) {
            moves[enpassant.x()][enpassant.y()] = ListOfMoves.EP;
        }

return moves;
    }
}