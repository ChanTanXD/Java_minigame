package groupassignment;

import java.awt.Graphics;
import javax.swing.*;

public class PlayScreen extends JFrame
{
    GamePanel game = new GamePanel();
    public PlayScreen()
    {
        setTitle("Java Programming Assignment");
        add(game);
        setSize(700, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void paint(Graphics gr)
    {
        super.paint(gr);
        if(game.retry)
        {
            dispose();
            PlayScreen play = new PlayScreen();
        }
        
        if(game.MainMenu)
        {
            dispose();
            GroupAssignment group = new GroupAssignment();
        }
        repaint();
    }
}
