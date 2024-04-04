package SnakeGame.Snake;
import javax.swing.JFrame;

public class SnakeGame extends JFrame{
    SnakeGame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        new SnakeGame();
    }
}
