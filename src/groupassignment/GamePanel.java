package groupassignment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener
{
    Random ran = new Random();
    boolean playing = true, pause = false, GameOver = false, success = false, ending = false, retry = false, MainMenu = false;
    int loop = 0;
    int[] bLoop = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int[] bSlow = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int eLoop = 0;
    int eSlow = 0;
    int score = 0;
    int lives = 3;
    
    //bonus
    int health;
    int healthX = ran.nextInt((400 - 50) + 1) + 50;
    double healthY = -200;
    int healthNum = 1;
    
    //player
    Player player;
    int invincibleTime = 0;
    float opacity = 0.7f;
    int bulletLoop = 0;
    boolean fire = false;
    int bulletDistance;
    int bulletNum = 30;
    
    //enemy
    int enemyNum = 20;
    Boss boss = new Boss(0,0,0);
    
    //score point
    int scoreNum = 0;
    int killedEnemy = 0;
    int killedEBonus = 0;
    int remainLive = lives;
    int totalScore = 0;
    int transparent = 20;
    boolean isTransparent = false;
    
    //Ending
    double y = 900;
    
    //ArrayList
    ArrayList<Bullet> bullet = new ArrayList<Bullet>(bulletNum);
    ArrayList<Enemy> enemy = new ArrayList<Enemy>(enemyNum);
    ArrayList<EnemyBullet> eBullet = new ArrayList<EnemyBullet>(enemyNum);
    ArrayList<Image> bonus = new ArrayList<Image>(healthNum);
    ArrayList<Image> BulletArr = new ArrayList<Image>();
    ArrayList<Image> eArr = new ArrayList<Image>();
    ArrayList<Image> bArr = new ArrayList<Image>();
    ArrayList<FlexBullet> bBullet = new ArrayList<FlexBullet>(100);
    
    //Import Image
    String ImagePath = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\GroupAssignment";
    Image PauseAndGOBackground = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\MainBackground.jpg");
    Image BackgroundImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\PlayScreenBackground.jpg");
    Image GameBackgroundImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Background\\GamePanelBackground.jpg");
    Image CharacterImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\Character.png");
    Image EnemyImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\Enemy.png");
    Image EnemyBulletImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\EnemyBullet.png");
    Image BossImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\Boss.png");
    Image BossBulletImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\BossBullet1.png");
    Image BossBulletImage2 = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\CharacterAndEnemy\\BossBullet2.png");
    Image addLiveImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\1up.png");
    
    public GamePanel()
    {
        //Loop Bullet Image and add into arraylist
        for(int i=0; i<=8; i++)
        {
            Image BulletImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\bullet\\bullet" + i + ".png");
            BulletArr.add(BulletImage);
        }
        
        for(int i=0; i<10; i++)
        {
            Image CharImage = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\Ending\\up" + i + ".png");
            eArr.add(CharImage);
        }
        
        for(int i=0; i<8; i++)
        {
            Image explosion = Toolkit.getDefaultToolkit().getImage(ImagePath + "\\boom\\boom" + i + ".png");
            bArr.add(explosion);
        }
        
        //Setup class
        player = new Player(253, 650, 0.5);
        
        for(int i=0; i<enemyNum; i++)
        {
            double enemyPosX = ran.nextInt((420 - 50) + 1) + 50;
            double enemyPosY = ran.nextInt((350 - 60) + 1) + 60 - 300;
            enemy.add(new Enemy(enemyPosX, enemyPosY, 0.2));
        }
        
        for(int i=0; i<enemyNum; i++)
            eBullet.add(new EnemyBullet(0, 0, 0.8));
        
        boss.live = 100;
        
        for(int i=0; i<bulletNum; i++)
        {
            bullet.add(new Bullet(0, 0, 2));
        }
        
        for(int i=0; i<=healthNum; i++)
        {
            bonus.add(addLiveImage);
        }
        
        setLayout(null);
	setFocusable(true);
        addKeyListener(this);
	setVisible(true);
    }
    
    @Override
    public void paint(Graphics gr)
    {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        
        if(playing)
        {
            //Background
            g.drawImage(BackgroundImage, 0, 0, 700, 800, this);
            g.drawImage(GameBackgroundImage, 39, 33, 430, 703, this);
            g.setColor(Color.red);
            g.drawRect(39, 33, 430, 703);

            //Character
            if(!player.isInvincible)
            {
                g.setColor(new Color(140,230,189));
                g.drawImage(CharacterImage, (int)player.x - 16, (int)player.y - 17, 50, 50, this);
                g.fillOval((int)player.x, (int)player.y, 15, 15);
            }
            else
            {
                g.setColor(new Color(140,230,189,200));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g.drawImage(CharacterImage, (int)player.x - 16, (int)player.y - 17, 50, 50, this);
                g.fillOval((int)player.x, (int)player.y, 15, 15);
            }
            
            //Font
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            g.setColor(Color.white);
            g.drawString("Score: " + score, 485, 50);
            g.setColor(Color.green);
            g.drawString("Lives: " + lives, 485, 85);
            g.setColor(Color.red);
            g.drawString("Up: W", 485,170);
            g.drawString("Down: S", 485,205);
            g.drawString("Right: D", 485,240);
            g.drawString("Left: A", 485,275);
            g.drawString("Shoot: J", 485,305);
            g.drawString("Pause: P", 485,340);
            g.drawString("Retry: R", 485,375);
            g.drawString("Main Menu: M", 485,405);
            
            //Bonus
            if(health == 888)
            {
                if(healthY > 40)
                {
                    g.drawImage(bonus.get(bonus.size() - 1), (int)healthX, (int)healthY, 35, 50, this);
                    healthY += 0.2;
                }
                if(healthY > 705)
                {
                    if(bonus.size() - 1 > 0)
                    {
                        bonus.remove(healthNum);
                        healthNum --;
                        healthX = ran.nextInt((400 - 50) + 1) + 50;
                        healthY = -200;
                        health = ran.nextInt(1500);
                    }
                }
                else
                    healthY += 0.2;
            }
            else
                health = ran.nextInt(1500);
                
            //Shoot
            if(fire == true)
            {
                bulletDistance++;
                if(bulletDistance%25 == 0)
                {
                    if(bulletLoop < bulletNum - 1)
                    {
                        bulletLoop++;
                        bullet.get(bulletLoop).isFire = true;
                    }
                    else
                    {
                        bulletLoop = 0;
                    }
                }
            }

            g.setColor(Color.red);
            for(int i=0; i<bullet.size(); i++)
            {
                if(bullet.get(i).isFire)
                {
                    if(bullet.get(i).x == 0 && bullet.get(i).y == 0)
                    {
                        bullet.get(i).x = player.x - 23;
                        bullet.get(i).y = player.y - 60;
                    }
                    else if(bullet.get(i).y <= 30)
                    {
                        bullet.remove(i);
                        bullet.add(new Bullet(0, 0, 2));
                    }
                    else
                    {
                        bullet.get(i).y -= bullet.get(i).speed;
                        if(loop == 8)
                            loop = 0;
                        else
                        {
                            loop++;
                            ImageIcon image = new ImageIcon(BulletArr.get(loop));
                            g.drawImage(image.getImage(), (int)bullet.get(i).x, (int)bullet.get(i).y, 60, 80, this);
                        }
                    }
                }
            }

            //Enemy
            for(int i=0; i<enemy.size(); i++)
            {
                if(!enemy.get(i).isReady)
                {
                    if(enemy.get(i).y > 40)
                    {
                        if(enemy.get(i).live != 0)
                        {
                            g.drawImage(EnemyImage, (int)enemy.get(i).x, (int)enemy.get(i).y, 35, 35, this);
                            enemy.get(i).y += enemy.get(i).speed;
                        }
                        else    //death animation
                        {
                            g.drawImage(bArr.get(bLoop[i]), (int)enemy.get(i).x, (int)enemy.get(i).y, 35, 35, this);
                            if(bLoop[i] != 7 && bSlow[i]%100 == 0)
                                bLoop[i]++;
                            else if(bLoop[i] == 7)
                            {
                                enemy.remove(i);
                                enemyNum -= 1;
                            }
                            if(bSlow[i] == 105)
                                bSlow[i] = 0;
                            else
                                bSlow[i]++;
                        }
                    }
                    else
                        enemy.get(i).y += enemy.get(i).speed;

                    if(enemy.get(i).y > 200)
                    {
                        enemy.get(i).isReady = true;
                    }
                }
                else
                {
                    if(enemy.get(i).live != 0)
                    {
                        g.drawImage(EnemyImage, (int)enemy.get(i).x, (int)enemy.get(i).y, 35, 35, this);
                        EnemyPosition(i);
                    }
                    else
                    {
                        g.drawImage(bArr.get(bLoop[i]), (int)enemy.get(i).x, (int)enemy.get(i).y, 35, 35, this);
                        if(bLoop[i] != 7 && bSlow[i]%100 == 0)
                            bLoop[i]++;
                        else if(bLoop[i] == 7)
                        {
                            enemy.remove(i);
                            enemyNum -= 1;
                            bLoop[i] = 0;
                            bSlow[i] = 0;
                        }
                        if(bSlow[i] == 105)
                            bSlow[i] = 0;
                        else
                            bSlow[i]++;
                    }    
                    if(!eBullet.get(i).isFire)
                    {
                        eBullet.get(i).x = enemy.get(i).x + 15;
                        eBullet.get(i).y = enemy.get(i).y + 35;
                        eBullet.get(i).isFire = true;
                    }
                }
            }
            
            //Boss
            if(boss.live <= 0 && boss.state != "dead")
            {
                boss.state = "dead";
                boss.timer = 0;
            }
                        
            switch(boss.state)
            {
                case "stand_by":
                    if(enemyNum <= 0)
                    {
                        boss.x = 250;
                        boss.y = 200;
                        boss.state = "starting";
                    }
                    break;
                case "dead":
                    if(boss.timer >= 1000)
                    {
                        playing = !playing;
                        success = !success;
                    }
                    if((boss.timer/25) % 1 == 0 && boss.timer <= 175)
                        g.drawImage(bArr.get(boss.timer/25), (int)boss.x, (int)boss.y, 35, 35, this);
                    boss.timer++;
                    break;
                case "starting":
                    boss.alpha += 0.005f;
                    if(boss.alpha < 1)
                    {
                        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, boss.alpha);
                        g.setComposite(alcom);
                        g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
                        g.setComposite(alcom);
                    }else
                    {
                        g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                        boss.state = "idle";
                        boss.nextState = "attack1";
                        boss.timer = 500;
                        boss.alpha = 1;
                    }
                    break;
                case "idle":
                    g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                    if(boss.timer <= 0)
                    {
                        if("attack1".equals(boss.nextState))
                            boss.timer = 2500;
                        else if("attack2".equals(boss.nextState))
                            boss.timer = 2500;
                        else if("attack3".equals(boss.nextState))
                            boss.timer = 3500;
                        
                        boss.state = boss.nextState;
                    }else
                        boss.timer--;
                    break;
                case "attack1":
                    g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                    //switch case used for shooting multiple bullet at once
                    switch(boss.timer){
                        case 2500:
                            //check FlexBullet.java for parameter explainations
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-1.1,1,0.2,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.7,1,0.2,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.2,1,0.2,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.2,1,0.2,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.7,1,0.2,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,1.1,1,0.2,0,0));
                            break;
                        case 2000:
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-1,1,0,0.01,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.5,1,0,0.01,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0,1,0,0.01,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.5,1,0,0.01,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,1,1,0,0.01,0));
                            break;
                        case 0:
                            boss.state = "idle";
                            boss.nextState = "attack2";
                            boss.timer = 700;
                            break;
                        default:
                            break;
                    }
                    
                    //if else used for shooting/moving one at a time
                    if(boss.timer >= 1000 && boss.timer <= 1500)
                    {
                        if(boss.timer % 100 == 0)
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0,1,1,0,0));
                        if(boss.timer % 10 == 0)
                        {
                            boss.x += 2;
                            boss.y -= 1;
                        }
                    }
                    else if(boss.timer >= 500 && boss.timer <= 1000)
                    {
                        if(boss.timer % 100 == 0)
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0,1,1,0,0));
                        if(boss.timer % 5 == 0)
                            boss.x -= 2;
                    }else if(boss.timer >= 0 && boss.timer <= 500)
                    {
                        if(boss.timer % 100 == 0)
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0,1,1,0,0));
                        if(boss.timer % 10 == 0)
                        {
                            boss.x += 2;
                            boss.y += 1;
                        }
                    }
                    
                    boss.timer--;
                    break;
                case "attack2":
                    g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                    switch(boss.timer){
                        case 2500:
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.5,0.3,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.3,0.6,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.1,0.9,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.1,1.2,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.3,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.5,1.5,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.7,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.9,1.2,0.5,0,0));
                            break;
                        case 2000:
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.5,0.3,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.3,0.6,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.1,0.9,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.1,1.2,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.3,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.5,1.5,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.7,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.9,1.2,0.5,0,0));
                            break;
                        case 1500:
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.5,0.3,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.3,0.6,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.1,0.9,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.1,1.2,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.3,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.5,1.5,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.7,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.9,1.2,0.5,0,0));
                            break;
                        case 1000:
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.5,0.3,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.3,0.6,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,0.1,0.9,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.1,1.2,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.3,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.5,1.5,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.7,1.4,0.5,0,0));
                            bBullet.add(new FlexBullet(boss.x+8,boss.y+30,-0.9,1.2,0.5,0,0));
                            break;
                        case 500:
                            double flip = -1;
                            double xMod = -2;
                            for(int i = 1; i <= 50; i++)
                            {
                                if(xMod <= -2 || xMod >= 2 )
                                {
                                    xMod = Math.floor(xMod);
                                    flip *= -1;
                                }
                                xMod += 0.2*flip;
                                bBullet.add(new FlexBullet(boss.x+8,boss.y+30,xMod,1,0,(double)i/10000,0));
                            }
                            break;
                        case 0:
                            boss.state = "idle";
                            boss.nextState = "attack3";
                            boss.timer = 700;
                            break;
                        default:
                            break;
                    }
                    
                    boss.timer--;
                    break;
                case "attack3":
                    System.out.println(player.x);
                    g.drawImage(BossImage,(int)boss.x,(int)boss.y,35,35,this);
                    switch(boss.timer){
                        case 3500:
                            FlexBullet tempBullet = new FlexBullet(boss.x-10,boss.y+13,0.3,1,0.5,0,0);
                            tempBullet.enlarge = true;
                            bBullet.add(tempBullet);
                            break;
                        case 3000:
                            FlexBullet tempBulletB = new FlexBullet(boss.x-10,boss.y+13,-1,0.3,0.5,0,0);
                            tempBulletB.enlarge = true;
                            bBullet.add(tempBulletB);
                            break;
                        case 2500:
                            FlexBullet tempBulletC = new FlexBullet(boss.x-10,boss.y+13,-0.3,1,0.5,0,0);
                            tempBulletC.enlarge = true;
                            bBullet.add(tempBulletC);
                            break;
                        case 2000:
                            FlexBullet tempBulletD = new FlexBullet(boss.x-10,boss.y+13,1,0.3,0.5,0,0);
                            tempBulletD.enlarge = true;
                            bBullet.add(tempBulletD);
                            break;
                        case 1000:
                            for(int i = 0; i < bBullet.size(); i++)
                            {
                                if(bBullet.get(i).x < player.x)
                                    bBullet.get(i).xMod = (player.x - bBullet.get(i).x)/1000;
                                else
                                    bBullet.get(i).xMod = -(bBullet.get(i).x - player.x)/1000;
                                if(bBullet.get(i).y < player.y)
                                    bBullet.get(i).yMod = (player.y - bBullet.get(i).y)/1000;
                                else
                                    bBullet.get(i).yMod = -(bBullet.get(i).y - player.y)/1000;
                                bBullet.get(i).speedMod = 0.01;
                            }
                            break;
                        case 0:
                            boss.state = "idle";
                            boss.nextState = "attack1";
                            boss.timer = 700;
                            break;
                        default:
                            break;
                    }
                    
                    if(boss.timer > 100 && boss.timer <= 3500 && boss.timer % 75 == 0)
                    {
                        for(int i = 0; i < bBullet.size(); i++)
                        {
                            if(bBullet.get(i).enlarge)
                                bBullet.add(new FlexBullet(bBullet.get(i).x+18,bBullet.get(i).y+17,0,0,0,0,0));
                        }
                    }
                    
                    boss.timer--;
                    break;
                default:
                    System.out.println("Error: Boss in unrecognizable state");
                    break;
            }
            
            for(int i=0; i<eBullet.size(); i++)
            {
                if(eBullet.get(i).isFire)
                {
                    eBullet.get(i).y += eBullet.get(i).speed;
                    g.drawImage(EnemyBulletImage, (int)eBullet.get(i).x, (int)eBullet.get(i).y, 5, 20, this);
                    if(eBullet.get(i).y > 725)
                    {
                        eBullet.get(i).isFire = false;
                    }
                }
            }
            
            if(!bBullet.isEmpty())
            {
                //used decrement to paint new bullets first
                for(int i=bBullet.size()-1; i>=0; i--)
                {
                    bBullet.get(i).speed += bBullet.get(i).speedMod;
                    bBullet.get(i).x += bBullet.get(i).speed * bBullet.get(i).xMod;
                    bBullet.get(i).y += bBullet.get(i).speed * bBullet.get(i).yMod;
                    
                    int w = 18;
                    int h = 17;
                    
                    if(bBullet.get(i).enlarge)
                    {
                        w *= 3;
                        h *= 3;
                    }
                    
                    //frame 1-99 = image 1, frame 100-199 = image 2. refresh in frame 200
                    if(bBullet.get(i).frame < 100)
                        g.drawImage(BossBulletImage, (int)bBullet.get(i).x, (int)bBullet.get(i).y, w, h, this);
                    else
                        g.drawImage(BossBulletImage2, (int)bBullet.get(i).x, (int)bBullet.get(i).y, w, h, this);
                    if(bBullet.get(i).frame > 200)
                        bBullet.get(i).frame = 0;
                    bBullet.get(i).frame++;
                    
                    //bullet struck player
                    if(player.x >= bBullet.get(i).x - 15 && player.x <= bBullet.get(i).x + 8
                    && player.y >= bBullet.get(i).y && player.y <= bBullet.get(i).y + 20)
                    {
                        bBullet.remove(i);
                        lives--;
                        repaint();
                    }
                    
                    //bullet struck border
                    if(bBullet.get(i).y > 725 || bBullet.get(i).y < 30 ||
                        bBullet.get(i).x > 454 || bBullet.get(i).x < 42)
                        bBullet.remove(i);
                }
            }
            
            //Collision
            for(int i=0; i<bullet.size(); i++) //enemy is attacked
            {
                if(bullet.get(i).isFire)
                {
                    for(int j=0; j<enemy.size(); j++)
                    {
                        if(!enemy.get(j).isDead)
                        {
                            if(bullet.get(i).x >= enemy.get(j).x - 35 && bullet.get(i).x <= enemy.get(j).x + 5 && bullet.get(i).y >= enemy.get(j).y && bullet.get(i).y <= enemy.get(j).y + 25)
                            {
                                if(enemy.get(j).live > 0)
                                {
                                    enemy.get(j).live--;
                                    bullet.remove(i);
                                    bullet.add(new Bullet(0, 0, 2));
                                }
                                if(enemy.get(j).live == 0)
                                {
                                    killedEnemy += 1;
                                    score += 30;
                                    enemy.get(j).isDead = true;
                                }
                            }
                        }
                    }
                    //boss
                    if(bullet.get(i).x >= boss.x-35 && bullet.get(i).x <= boss.x+5 &&
                        bullet.get(i).y >= boss.y && bullet.get(i).y <= boss.y+25 &&
                        !"stand_by".equals(boss.state) && !"ready".equals(boss.state))
                    {
                        boss.live--;
                        bullet.remove(i);
                        bullet.add(new Bullet(0, 0, 2));
                    }
                }
            }
            
            //Player is attacked
            for(int i=0; i<enemy.size(); i++)
            {
                if(player.x >= eBullet.get(i).x - 15 && player.x <= eBullet.get(i).x + 8 && player.y >= eBullet.get(i).y && player.y <= eBullet.get(i).y + 20)
                {
                    eBullet.get(i).x = enemy.get(i).x + 15;
                    eBullet.get(i).y = enemy.get(i).y + 35;
                    if(!player.isInvincible)
                    {
                        lives--;
                        player.isInvincible = true;
                        invincibleTime = 0;
                    }
                }
            }
            
            //Player collided with enemy
            for(int i=0; i<enemy.size(); i++)
            {
                if(!enemy.get(i).isDead)
                {
                    if(player.x >= enemy.get(i).x && player.x <= enemy.get(i).x + 35 && player.y >= enemy.get(i).y && player.y <= enemy.get(i).y + 35 || 
                       player.x + 15 >= enemy.get(i).x && player.x + 15 <= enemy.get(i).x + 35 && player.y >= enemy.get(i).y && player.y <= enemy.get(i).y + 35)
                    {
                        if(!player.isInvincible)
                        {
                            lives--;
                            enemy.remove(i);
                            enemyNum -= 1;
                            killedEnemy += 1;
                            player.isInvincible = true;
                            invincibleTime = 0;
                        }
                    }
                }
            }
            
            //Player invincible
            if(player.isInvincible && invincibleTime > 1000)
                player.isInvincible = false;
            else if(player.isInvincible)
                invincibleTime++;
            
            //Bonus
            if(player.x >= healthX && player.x <= healthX + 35 && player.y >= healthY && player.y <= healthY + 50 || 
               player.x + 15 >= healthX && player.x + 15 <= healthX + 35 && player.y >= healthY && player.y <= healthY + 50)
            {
                if(bonus.size() - 1 > 0)
                {
                    bonus.remove(healthNum);
                    healthNum --;
                    healthX = ran.nextInt((400 - 50) + 1) + 50;
                    healthY = -200;
                    health = ran.nextInt(1500);
                    lives++;
                    remainLive++;
                }
                else
                {
                    healthX = -200;
                    healthY = -200;
                    lives++;
                    remainLive++;
                }
            }
            
            //Game Over
            if(lives == 0)
            {
                playing = !playing;
                GameOver = !GameOver;
            }
            
            playerMove();
        }
        
        //Pause
        else if(pause)
        {
            g.drawImage(PauseAndGOBackground, 0, 0, 700, 800, this);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 80));
            g.setColor(new Color(242,237,237));
            g.drawString("Pause", 245, 360);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
            g.setColor(new Color(255,74,74));
            g.drawString("Press P to continue", 192, 410);
        }
        
        //Game Over
        else if(GameOver)
        {
            g.drawImage(PauseAndGOBackground, 0, 0, 700, 800, this);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 90));
            g.setColor(new Color(255,74,74));
            g.drawString("Game Over", 110, 130);
            g.drawRect(73, 200, 540, 400);
            g.setColor(new Color(99,99,99,200));
            g.fillRect(74, 201, 539, 399);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
            g.setColor(Color.black);
            g.drawString("Result", 300, 241);
            g.setColor(new Color(223,240,229));
            g.drawLine(353, 270, 353, 560);
            g.drawLine(100, 520, 586, 520);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
            g.setColor(new Color(98,196,134));
            g.drawString("Score: ", 260, 291);
            g.drawString("" + scoreNum, 540, 291);
            g.setColor(new Color(227,93,93));
            g.drawString("Killed Enemies Bonus: ", 92, 331);
            g.drawString("" + killedEBonus, 540, 331);
            g.setColor(Color.black);
            g.drawString("Total Score: ", 192, 555);
            g.drawString("" + totalScore, 540, 555);
            g.setColor(new Color(247,237,237, transparent));
            g.drawString("Press R to Retry or Press M to Main Menu", 103, 640);
            if(scoreNum <= score - 1)
                scoreNum++;
            else if(killedEBonus <= (killedEnemy * 20) - 1)
                killedEBonus++;
            else if(totalScore <= (scoreNum + killedEBonus) - 1)
                totalScore++;
            if(transparent <= 200 && isTransparent == false)
                transparent++;
            else
                isTransparent = true;
            if(transparent > 20 && isTransparent == true)
                transparent--;
            else
                isTransparent = false;
        }
        
        //Game Clear
        else if(success)
        {
            g.drawImage(PauseAndGOBackground, 0, 0, 700, 800, this);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 90));
            g.setColor(new Color(227,187,95));
            g.drawString("Game Clear", 115, 130);
            g.setColor(new Color(255,74,74));
            g.drawRect(73, 200, 540, 400);
            g.setColor(new Color(99,99,99,200));
            g.fillRect(74, 201, 539, 399);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
            g.setColor(Color.black);
            g.drawString("Result", 300, 241);
            g.setColor(new Color(223,240,229));
            g.drawLine(353, 270, 353, 560);
            g.drawLine(100, 520, 586, 520);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
            g.setColor(new Color(232,232,232));
            g.drawString("Score: ", 260, 291);
            g.drawString("" + scoreNum, 540, 291);
            g.setColor(new Color(227,93,93));
            g.drawString("Killed Enemies Bonus: ", 92, 331);
            g.drawString("" + killedEBonus, 540, 331);
            g.setColor(new Color(98,196,134));
            g.drawString("Remaining Lives: ", 149, 371);
            g.drawString("" + remainLive, 540, 371);
            g.drawString("Boss Killed: ", 206, 411);
            g.drawString("0", 540, 411); //if boss dead then +500
            g.setColor(Color.black);
            g.drawString("Total Score: ", 192, 555);
            g.drawString("" + totalScore, 540, 555);
            g.setColor(new Color(247,237,237, transparent));
            g.drawString("Press ENTER to continue", 200, 640);
            if(scoreNum <= score - 1)
                scoreNum++;
            else if(killedEBonus <= (killedEnemy * 20) - 1)
                killedEBonus++;
            else if(remainLive <= (lives * 10) - 1)
                remainLive++;
            else if(totalScore <= (scoreNum + killedEBonus + remainLive) - 1)
                totalScore++;
            if(transparent <= 200 && isTransparent == false)
                transparent++;
            else
                isTransparent = true;
            if(transparent > 20 && isTransparent == true)
                transparent--;
            else
                isTransparent = false;
        }
        
        //Ending
        else if(ending)
        {
            g.drawImage(PauseAndGOBackground, 0, 0, 700, 800, this);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
            g.setColor(Color.white);
            g.drawString("Java Programming Group Assignment", 88, (int)y);
            g.drawString("Group 4", getWidth()/2 - 50, (int)y + 50);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));
            g.drawString("First Student", getWidth()/2 - 73, (int)y + 120);
            g.drawString("Koh You Qian", getWidth()/2 - 68, (int)y + 160);
            g.drawString("0128997", getWidth()/2 - 45, (int)y + 200);
            
            g.drawString("Second Student", getWidth()/2 - 88, (int)y + 280);
            g.drawString("How Chee Jian", getWidth()/2 - 80, (int)y + 320);
            g.drawString("0128977", getWidth()/2 - 47, (int)y + 360);
            
            g.drawString("Third Student", getWidth()/2 - 80, (int)y + 440);
            g.drawString("Tan Yee Chun", getWidth()/2 - 77, (int)y + 480);
            g.drawString("0129028", getWidth()/2 - 47, (int)y + 520);
            
            g.drawImage(eArr.get(eLoop), getWidth()/2 - 40, (int)y + 580, 80, 80, this);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
            g.drawString("Thank you for your playing", 105, (int)y + 1180);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
            g.drawString("by Group 4 all members", getWidth()/2 - 105, (int)y + 1220);
            if(eLoop == 9)
                eLoop = 0;
            else if(eLoop <= 9 && eSlow == 25)
            {
                eLoop++;
                eSlow = 0;
            }
            else
                eSlow++;
            if(y != -800)
                y -= 0.5;
            else
                y = -800;
        }
    }
    
    public void EnemyPosition(int i)
    {
        switch(enemy.get(i).Position)
        {
            case 1: //left
                enemy.get(i).x -= enemy.get(i).speed;
                break;
            case 2: //right
                enemy.get(i).x += enemy.get(i).speed; 
                break;
            case 3: //up
                enemy.get(i).y -= enemy.get(i).speed;
                break;
            case 4: //down
                enemy.get(i).y += enemy.get(i).speed;
                break;
            case 5: //upper left
                enemy.get(i).x -= enemy.get(i).speed;
                enemy.get(i).y -= enemy.get(i).speed;
                break;
            case 6: //lower left
                enemy.get(i).x -= enemy.get(i).speed;
                enemy.get(i).y += enemy.get(i).speed;
                break;
            case 7: //upper right
                enemy.get(i).x += enemy.get(i).speed;
                enemy.get(i).y -= enemy.get(i).speed;
                break;
            case 8: //lower right
                enemy.get(i).x += enemy.get(i).speed;
                enemy.get(i).y += enemy.get(i).speed;
                break;
        }
        if(enemy.get(i).x <= 42) 
        {
            enemy.get(i).x = 42;
            enemy.get(i).Position = ran.nextInt((8 + 1) + 1);
        }
        else if(enemy.get(i).x >= 429)
        {
            enemy.get(i).x = 429;
            enemy.get(i).Position = ran.nextInt((8 + 1) + 1);
        }
        if(enemy.get(i).y <= 38) 
        {
            enemy.get(i).y = 38;
            enemy.get(i).Position = ran.nextInt((8 + 1) + 1);
        }
        else if(enemy.get(i).y >= 300)
        {
            enemy.get(i).y = 300;
            enemy.get(i).Position = ran.nextInt((8 + 1) + 1);
        }
    }
    
    public void playerMove()
    {
        if(player.Left)
        {
            player.x -= player.speed;
        }
        if(player.Right)
        {
            player.x += player.speed;
        }
        if(player.Up)
        {
            player.y -= player.speed;
        }
        if(player.Down)
        {
            player.y += player.speed;
        }
    }
    
    public void CheckPosition()
    {
        if(player.x <= 50) 
        {
            player.x = 50;
            player.Left = false;
        }
        else if(player.x >= 442)
        {
            player.x = 442;
            player.Right = false;
        }
        if(player.y <= 33) 
        {
            player.y = 33;
            player.Up = false;
        }
        else if(player.y >= 706)
        {
            player.y = 706;
            player.Down = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.Left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.Right = true;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.Up = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.Down = true;
                break;
            case KeyEvent.VK_J:
                fire = true;
                break;
            case KeyEvent.VK_P:
                if(!GameOver && !success && !ending)
                {
                    playing = !playing;
                    pause = !pause;
                    repaint();
                }
                break;
            case KeyEvent.VK_R:
                if(playing || GameOver || ending)
                    retry = !retry;
                break;
            case KeyEvent.VK_M:
                if(playing || GameOver || ending)
                    MainMenu = !MainMenu;
                break;
            case KeyEvent.VK_ENTER:
                if(success)
                {
                    success = !success;
                    ending = !ending;
                }
                break;
        }
        CheckPosition();
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.Left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.Right = false;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.Up = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.Down = false;
                break;
            case KeyEvent.VK_J:
                fire = false;
                bulletDistance = 0;
                break;
            case KeyEvent.VK_K:
                //kill a batch of enemies(for testing purpose)
                for(int i=0; i<enemy.size(); i++)
                {
                    enemy.remove(i);
                    enemyNum -= 1;
                    killedEnemy += 1;
                }
                break;
        }
        CheckPosition();
    }
}
