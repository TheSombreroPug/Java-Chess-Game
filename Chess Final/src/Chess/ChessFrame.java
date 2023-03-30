package Chess;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.*;

public class ChessFrame extends JFrame {


    private final JPanel PlayFrame;
    //private GamePanel gamePanel;
    private  JPanel PlayFrame1;
    public Chess chess;
    private ButtonPanel ButtonPanel = new ButtonPanel(this);
    CardLayout PanelLayout = new CardLayout();
    CardLayout PanelLayout1 = new CardLayout();

    public ChessFrame() {

        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.PlayFrame = new JPanel();
        this.PlayFrame.setLayout(PanelLayout);
        this.PlayFrame.add(ButtonPanel);
        this.setResizable(true);
        this.setContentPane(PlayFrame);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }


    public void ChessBoard() {
        chess = new Chess();
        PlayFrame.remove(ButtonPanel);

    }

}