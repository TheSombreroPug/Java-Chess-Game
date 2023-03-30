package Chess;

final class ChessListener {
    private static final ChessListener  nullInstance = new ChessListener(null,null,null);
    private final Pieces moving;
    private final Coord from;
    private final ListOfMoves[][] moves;


    private ChessListener(Pieces moving, Coord from, ListOfMoves[][] moves){
        this.moving = moving;
        this.from = from;
        this.moves = moves;
    }

    static ChessListener ClickOne(){return nullInstance;}

    static ChessListener ClickTwo(Pieces moving, Coord from, ListOfMoves[][] moves){
        if (moving == null || from == null||moves == null){
            throw new IllegalArgumentException("Please Click on the board");
        }
        return new ChessListener(moving, from, moves);
    }


     boolean FirstClick(){return moving == null;}

     ListOfMoves getMove(Coord coord){
        if(FirstClick()){
            throw new IllegalStateException("CHANGE THIS LATER");

        }
        return moves[Coord.y()][Coord.x()];
     }
     Pieces getMoving(){
         if(FirstClick()){
             throw new IllegalStateException("CHANGE THIS LATER");

         }
         return moving;
     }
     Coord getFrom(){
         if(FirstClick()){
             throw new IllegalStateException("CHANGE THIS LATER");

         }
         return from;
     }

}
