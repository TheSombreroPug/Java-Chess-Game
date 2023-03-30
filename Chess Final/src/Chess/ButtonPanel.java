package Chess;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    JFrame frame;
    ButtonPanel(JFrame frame) {
        setVisible(true);
        setPreferredSize(new Dimension(630,560));
        setBackground(Color.BLACK);
        setLayout(new GridLayout(5,0,50,50));
        //Adding Space for the text
        JLabel Clear = new JLabel();
        add(Clear);
//ADDING HUMAN BUTTON
        JButton Easy = new JButton("PLAY CHESS");
        Easy.setBackground(Color.GREEN);
        Easy.setBounds(400,400,100,100);
        Easy.setLayout(new GridLayout(2,0));
        add(Easy);
        Easy.addActionListener(new CgListener(frame,Easy));


        //ADDING HAMILTIONIAN CYCLE BUTTON
        JButton Medium = new JButton("LOGIN");
        Medium.setBackground(Color.ORANGE);
        Medium.setBounds(250,250,100,100);
        Medium.setLayout(new GridLayout(1,0));
        add(Medium);
        //Cycle.addActionListener(new HcAction(frame,Cycle));


        //PAthFinder Button
        JButton Hard = new JButton("CHECK SCORES");
        Hard.setBackground(Color.RED);
        Hard.setBounds(250,250,100,100);
        Hard.setLayout(new GridLayout(1,0));
        add(Hard);
        //Path.addActionListener(new PfAction(frame,Path));

        JButton Online = new JButton("Online");
        Online.setBackground(Color.magenta);
        Online.setBounds(250,250,100,100);
        Online.setLayout(new GridLayout(1,0));
        add(Online);

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Drawing the title for snake
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Chess", (630 - metrics.stringWidth("Chess"))/2,80 );
    }


}




