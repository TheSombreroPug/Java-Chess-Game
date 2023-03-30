package Chess;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CgListener implements ActionListener {

    JFrame frame;
    JButton easy;
    boolean IgnoreAL = true;
    public CgListener(JFrame frame, JButton easy) {


        this.frame = frame;
        this.easy = easy;

    }


    public void actionPerformed(ActionEvent e) {
        if (IgnoreAL == true) {
            easy.setFocusable(false);
            easy.setVisible(false);
            ((ChessFrame) frame).ChessBoard();
            boolean IgnoreAl = false;
            //human.setFocusable(false);
        }
        else{
            return;
        }




    }

}
