package groupassignment;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class CreditPanel extends JPanel
{
    String ImagePath = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\GroupAssignment";
    JButton btnBack = new JButton("Back");
    ArrayList<Image> PeopleArr = new ArrayList<Image>();
    Image MainBackground = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\MainBackground.jpg");
    Image scaledBackImage = MainBackground.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
    ImageIcon BackIcon = new ImageIcon(scaledBackImage);
    JLabel background = new JLabel(BackIcon);
    int x = -500;
    public CreditPanel()
    {
        for(int i=0; i<3; i++)
        {
            Image people = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Avatar\\people" + i + ".jpg");
            PeopleArr.add(people);
        }
        background.setBounds(0,0,600,600);
        btnBack.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        btnBack.setFocusPainted(false);
        btnBack.setBounds(195,480,200,50);
        add(btnBack);
        add(background);
        setLayout(null);
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void paint(Graphics gr)
    {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 25));
        g.setColor(Color.gray);
        g.drawString("First Student", x, 60);
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
        g.drawString("Name: Koh You Qian", x, 90);
        g.drawString("Student ID: 0128997", x, 120);
        g.drawString("Email Address: 0128997@kdu-online.com", x, 150);
        
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 25));
        g.setColor(Color.gray);
        g.drawString("Second Student", x, 200);
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
        g.drawString("Name: How Chee Jian", x, 230);
        g.drawString("Student ID: 0128977", x, 260);
        g.drawString("Email Address: 0128977@kdu-online.com", x, 290);
        
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 25));
        g.setColor(Color.gray);
        g.drawString("Third Student", x, 340);
        g.setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
        g.drawString("Name: Tan Yee Chun", x, 370);
        g.drawString("Student ID: 0129028", x, 400);
        g.drawString("Email Address: 0129028@kdu-online.com", x, 430);
        if(x != 40)
            x++;
        else
        {
            x = 40;
            g.drawImage(PeopleArr.get(0), 435, 40, 110, 110, this);
            g.drawImage(PeopleArr.get(1), 435, 180, 110, 110, this);
            g.drawImage(PeopleArr.get(2), 435, 320, 110, 110, this);
        }
        repaint();
    }
}
