package SnakeGame.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH=1300;
    static final int SCREEN_HEIGHT=700;
    static final int UNIT_SIZE=50;
    static final int UNIT_ITEM=(SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    int []x=new int [UNIT_ITEM];
    int []y=new int [UNIT_ITEM];
    static  int DELAY=100;
    int appleX;
    int appleY;
    int bodyparts=6;
    int appleEaten;
    Timer timer;
    boolean running=false;
    Random random;
    char direction='R';
    
    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
       if(running)
        {
            // for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            // g.drawLine(i*UNIT_SIZE ,0, i*UNIT_SIZE, SCREEN_HEIGHT);
            // g.drawLine(0,i*UNIT_SIZE ,SCREEN_WIDTH, i*UNIT_SIZE);
            // }
            g.setColor(Color.RED);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            for(int i=0;i<bodyparts;i++){
                if(i==0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{
                    // g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }

    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void checkCollison(){
        for(int i=1;i<bodyparts;i++){
            if(x[i]==x[0]&&y[i]==y[0]){
                running=false;
            }
        }
        if(x[0]<0||y[0]<0||x[0]>SCREEN_WIDTH||y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running)
            timer.stop();
    }
    public void checkApple(){
        if(x[0]==appleX&&y[0]==appleY){
            appleEaten++;
            bodyparts++;
            DELAY-=5;
            newApple();
        }
    }

    public void move(){
        for(int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
        }
    }


    public void gameOver(Graphics g){
        g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 80));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+appleEaten))/2, (SCREEN_HEIGHT/2)+100);
	
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollison();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
            }
        }
    }
    
}
