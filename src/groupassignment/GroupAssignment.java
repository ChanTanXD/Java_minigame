package groupassignment;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GroupAssignment extends JFrame implements ActionListener, MouseMotionListener 
{
    String ImagePath = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\GroupAssignment";
    JButton btnStart = new JButton("Start Game");
    JButton btnCredit = new JButton("Credit");
    JButton btnExit = new JButton("Exit");
    Image MainBackground = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\MainBackground.jpg");
    Image scaledBackImage = MainBackground.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
    ImageIcon BackIcon = new ImageIcon(scaledBackImage);
    Image logo = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\logo.png");
    Image scaledLogoImage = logo.getScaledInstance(380, 380, Image.SCALE_DEFAULT);
    ImageIcon LogoIcon = new ImageIcon(scaledLogoImage);
    JLabel background = new JLabel(BackIcon);
    JLabel lblLogo = new JLabel(LogoIcon);
    
    public GroupAssignment() 
    {
        background.setBounds(0,0,600,600);
        lblLogo.setBounds(115,-5,380,380);
        btnStart.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        btnStart.setFocusPainted(false);
        //btnStart.setBackground(new Color(107,93,153));
        btnStart.setBounds(200,335,200,50);
        btnStart.addActionListener(this);
        btnCredit.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        btnCredit.setFocusPainted(false);
        btnCredit.setBounds(200,410,200,50);
        btnCredit.addActionListener(this);
        btnExit.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        btnExit.setFocusPainted(false);
        btnExit.setBounds(200,485,200,50);
        btnExit.addActionListener(this);
        add(btnStart);
        add(btnCredit);
        add(btnExit);
        add(lblLogo);
        add(background);
        this.addMouseMotionListener(this);
        setTitle("Java Programming Assignment");
        setSize(600, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) 
    {
        GroupAssignment start = new GroupAssignment();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == btnStart)
        {
            dispose();
            new PlayScreen();
        }
        if(e.getSource() == btnCredit)
        {
            new CreditScreen();
            this.setVisible(false);
        }
        if(e.getSource() == btnExit)
        {
            int exit = JOptionPane.showConfirmDialog(null,"Confirm exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if(exit == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        //System.out.println("x :" + e.getX());
        //System.out.println("y :" + e.getY());
    }
}
