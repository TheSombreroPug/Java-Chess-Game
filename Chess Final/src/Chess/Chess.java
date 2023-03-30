package Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

final class Chess{
    

    private boolean isTurnWhite = true;
    private boolean isTurnBlack = true;


    public static final Color[][] squares = new Color[64][64];
    private final JFrame frame = new JFrame("Chess");

    private ChessListener chessListener = ChessListener.ClickOne();

    //private final Game game = new Game();
    public static boolean Initialisation;

    private final GameRules gameRules = new GameRules();


    public Chess(){
        //runs all of the front end to create the visuals and the pieces

        UIBackEnd();
        BackgroundPaint();
        PiecePaint();
        Initialisation = true;

    }

    public void UIBackEnd(){
        //creates the frame and adds all of its rules
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        //adds "DrawGrid" from further down the code
        frame.add(new DrawGrid());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void BackgroundPaint(){
        //Both of these algorithms are responsible for painting the board black and white
        // using two for loops to create the alternating pattern
        var black =  Color.BLACK;
        var white = Color.WHITE;

        for(int i = 0; i<8 ; i++){
            for (int ii = 0; ii<8; ii++){
                var OldColor = GameRules.isLightTile(Coord.instance(ii,i)) ? white : black;

                drawtilebg(OldColor,ii,i);

                }
            }
        }
    private void drawtilebg(Color OldColor, int x, int y){
        for (int a = 0; a<8;a++){
            for (int b = 0; b<8;b++){
                squares[a + y * 8][b + x *8] = OldColor;
            }
    }}

    private void PiecePaint(){
        //both of these algorithms are used to paint the pieces onto the board
        // The image for the piece is gotten from gameRules using its co ordinates
        //It is then painted
        for(int i = 1; i<7 ; i++){
            for (int ii = 1; ii<7; ii++){
                var Image = gameRules.getImage(Coord.instance(ii,i));
                if (Image != null) {
                    pieceui(ii*8,i*8,Image);
            }
        }
    }}
    private void pieceui(int x, int y, Color[][] Image){
        for (int a = 0; a<8;a++){
            for (int b = 0; b<8;b++){
                if (Image[a-1][b-1]!=null){
                    squares[a+y][b+x] = Image[a-1][b-1];
                }
            }
        }
    }

        private static int DisDialogue(String text, String[] options){
        //used to show text boxes on the screen
            return JOptionPane.showOptionDialog(null,text, "Chess",-1,-1,null,options,options[0]);

        }

    private void HandleClickListener(int x, int y) {
        //Checks if a piece has been clicked on and if so, what piece.
        if (chessListener.FirstClick()) {
            LockPiece(Coord.instance(x,y));
            return;
        }
        var status = Action(x,y);
        chessListener = ChessListener.ClickOne();
        BackgroundPaint();
        //also ensures you cant move a piece when in check
        if(status.inCheck()){
            String text = "You are in check";
            String[] options = {"ok"};
            Chess.DisDialogue(text,options);
            if(status.GameOver()){
                System.exit(0);
            }
        }

        }
    private CGS Action(int x,  int y){
        //checks for the type of move played. Depending on if its a  special game case or not
        //a different return is given
        var coord = Coord.instance(x,y);
        switch (chessListener.getMove(coord)){
            case EP:
                return gameRules.enPassant(chessListener.getMoving(),chessListener.getFrom());
            case REGULAR:
                CGS normalmove = gameRules.normalmove(chessListener.getMoving(), chessListener.getFrom(), coord);
                return normalmove;
            case NONE:
                return CGS.INPLAY;
            case KSCastle:
                gameRules.KingSideCastle();

            case PROMOTION:
                return gameRules.pawnpromo(pawnpromotion(),chessListener.getFrom(),coord);
            case QSCastle:
                gameRules.QueenSideCastle();
            default:throw new IllegalStateException("Illegal Move");
        }}
    //ADD PAWN PROMOTION
   private AllowedPromo pawnpromotion(){
        // adds the text for when a pawn reaches promotion
       //Depending on the chosen promotion piece, return a different allowed promotion
        var text = "Pawn Promotion Options";
        String[] options = {
                "Queen","Horse","Rook","Bishop",
        };
        int promotion = -1;
        while(promotion < 0){
            promotion = DisDialogue(text,options);
        }
        switch(promotion){
            case 0:
                return AllowedPromo.QUEEN;
            case 1:
                return AllowedPromo.HORSE;
            case 2:
                return AllowedPromo.ROOK;
            case 3:
                return AllowedPromo.BISHOP;
            default://incase an issues arrise
                throw new IllegalStateException(" Did not return promo");
        }
   }

   //used to locate the piece and lock onto it
    private void LockPiece(Coord coord) {
        var piece = gameRules.GAP(coord);
        //if there is no piece at the coord then nothing happens to the board
        if (piece == null) {
            chessListener = ChessListener.ClickOne();
        } else { //however if there is a piece then run legal move finder for that piece and display legal moves
            chessListener = LegalMoveFinder(piece, coord);
        }
    }
    private ChessListener LegalMoveFinder(Pieces moving, Coord from){
        //same as  the tiles for the background except this time its for shwoing valid moves
        var lightRed = new Color(255,204,203);
        var darkRed = Color.RED;
        var moves = gameRules.avaliblemoves(moving,from);
        boolean PLACEHOLDER = false;
        for(int i = 0; i<8 ; i++){
            for (int ii = 0; ii<8; ii++){
                //paints all the moves that can be played
                if (moves[i][ii] != ListOfMoves.NONE){
                    PLACEHOLDER = true;
                    var OldColor = GameRules.isLightTile(Coord.instance(ii,i))? lightRed:darkRed;

                    drawtilebg(OldColor,ii,i);
                }
            }}
            PiecePaint();
            if(PLACEHOLDER){
                return ChessListener.ClickTwo(moving,from,moves);
            }
            return ChessListener.ClickOne();


    }

//All below is used to manage the squares and graphics for the frame and board
    public class DrawGrid extends JPanel {
        private final List<Rectangle> cells;


        DrawGrid(){
            cells = new ArrayList<>(64*64);
            addMouseListener(new MouseAdapter() {
                @Override
                public  void mouseClicked(MouseEvent e){
                    if (Chess.Initialisation){
                        int VClick = 8*e.getY()/getHeight();
                        int HClick = 8*e.getX()/getWidth();
                        HandleClickListener(HClick,VClick);
                    }

                }
            });

        }
        @Override
        public Dimension getPreferredSize(){
            var screensize = Toolkit.getDefaultToolkit().getScreenSize();
            int multi;
            if(screensize.getWidth() < screensize.getHeight()){
                multi = (int) (screensize.getWidth()*0.8)/64;
            }else{
                multi = (int) (screensize.getHeight()*0.8)/64;

            }
            return new Dimension(multi *64, multi*64);

        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            var g2d = (Graphics2D) g.create();
            int width = getWidth()/64;
            int height = getHeight()/64;
            if(cells.isEmpty()){
                for (int row = 0; row < 64; row++){
                    for(int col = 0; col <64; col++){
                        var cell = new Rectangle(col*width, row*height,width,height);
                        cells.add(cell);
                    }
                }
            }
            for(int i =0;i<64;i++){
                for (int ii=0;ii<64;ii++){
                    g2d.setColor(squares[i][ii]);
                    var cell = cells.get(ii+i*64);
                    g2d.fill(cell);
                    repaint();
                }
            }
        }


    }}

