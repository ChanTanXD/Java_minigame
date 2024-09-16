package groupassignment;

import java.awt.event.*;
import javax.swing.*;

public class CreditScreen extends JFrame implements ActionListener
{
    CreditPanel panel = new CreditPanel();
    public CreditScreen()
    {
        add(panel);
        panel.btnBack.addActionListener(this);
        setTitle("Java Programming Assignment");
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == panel.btnBack)
        {
            dispose();
            new GroupAssignment();
        }
    }
}
