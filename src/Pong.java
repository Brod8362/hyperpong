import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pong extends JFrame implements KeyListener {

    private final int width = 1280;
    private final int height = 720;
    private static ArrayList<PongObject> objects = new ArrayList<>();

    private JFrame globalFrame;



    Pong() {
        objects.add(new Paddle(50, height/2)); //create left paddle
        objects.add(new AIPaddle( width-100, height/2)); //create right paddle
        objects.add(new Ball(width/2, height/2));


        int wallcount = 9;
        int i = 0;
        while ( i < wallcount) {
            int randY = ThreadLocalRandom.current().nextInt(100, height-100);
            int randH = ThreadLocalRandom.current().nextInt(50,  125);
            int randX = ThreadLocalRandom.current().nextInt(200, width-200);
            int randmHP = ThreadLocalRandom.current().nextInt(5, 80);

            objects.add(new Wall(randX, randY, 30, randH, randmHP));
            i++;
        }


        // create a random wall with a random spawn position and a random height

    }


    @Override
    public void keyTyped(KeyEvent e) {
        //dont care
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //dont care
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PongObject player1 = objects.get(0);
        PongObject player2 = objects.get(1);
        PongObject wall = objects.get(3);
        Graphics window = globalFrame.getGraphics();
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_S) {
            player1.changeYPos(window,15);
        } else if (key == KeyEvent.VK_W) {
            player1.changeYPos(window, -15);
        } else if (key == KeyEvent.VK_Q) {
            System.exit(6);
        } else if (key == KeyEvent.VK_I) {
            player2.changeYPos(window, -15);
        } else if (key == KeyEvent.VK_K) {
            player2.changeYPos(window, 15);
        } else if (key == KeyEvent.VK_EQUALS) {
            player1.heal(5);
        } else if (key == KeyEvent.VK_MINUS) {
            player1.damage(5);
        } else if (key == KeyEvent.VK_0) {
            player1.heal(999);
        } else if (key == KeyEvent.VK_9) {
            player2.revive();
            player1.revive();
        } else if (key == KeyEvent.VK_8) {
            player1.kill();
            player2.kill();
            wall.kill();
        } else if (key == KeyEvent.VK_7) {
            player1.fullHeal();
            player2.fullHeal();
        } else if (key == KeyEvent.VK_6) {
            wall.heal(25);
        } else if (key == KeyEvent.VK_5) {
            wall.damage(25);
        } else if (key == KeyEvent.VK_4) {
            wall.revive();
        } else if (key == KeyEvent.VK_3) {
            wall.kill();
        } else if (key == KeyEvent.VK_2) {
            wall.fullHeal();
        } else if (key == KeyEvent.VK_1) {
            wall.setInvincible(true);
        } else if (key == KeyEvent.VK_TAB) {
            wall.setInvincible(false);
        }
    }


    public void randomEvent() {
        Graphics window = getGraphics();
        window.setColor(Color.WHITE);
        PongObject player1 = objects.get(0);
        PongObject player2 = objects.get(1);
        window.drawString("RANDOM EVENT!", width / 2, height / 2);
        try {
            Thread.sleep(750);
            if (Math.random() < 0.5) {
                player1.heal(25);
                player2.damage(25);
                window.drawString("P1 Gets Heal! P2 Gets Hurt!", width / 2, (height / 2)+50);
                Thread.sleep(1000);
            } else {
                player2.heal(25);
                player1.damage(25);
                window.drawString("P2 Gets Heal! P1 Gets Hurt!", width / 2, (height / 2)+50);
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void main() {
        int tick = 0;
        JFrame frame = this;
        globalFrame = this;
        frame.setSize(width, height); //set dimensions
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //exit operation
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setVisible(true);
        Graphics window = frame.getGraphics();
        PongObject player1 = objects.get(0);
        PongObject player2 = objects.get(1);

            while (true) {
                if (player1.hp < 0 ) {
                    System.out.println("Player 2 wins!");
                    System.exit(0);
                } else if (player2.hp < 0) {
                    System.out.println("Player 1 wins!");
                    System.exit(0);
                }

                try {
                    Thread.sleep(35);  //set to 16 for ~60fps
                } catch (InterruptedException ignored) { }

                window.setColor(Color.BLACK);
                window.fillRect(0, 0, width, height);

                int i = 0;

                for (PongObject object : objects) { //iterate over and draw objects
                    i ++;
                    if (tick % 250 == 0) {
                        if (object.getClass().getName().equals("Paddle") || object.getClass().getName().equals("AIPaddle")) {
                            object.heal((int)(object.getMaxHP()*0.05));
                        }
                    }
                    if (tick % 10 == 0) {
                        object.makeVulnerable();
                    }
                    object.update(window, objects);
                    // update object
                    object.draw(window);
                    // draw object
                    //object.drawHitbox(window);
                    // draw hitbox
                    //window.drawString(object.toString(), 25, i*30);
                    // draw diagnostic info
                    window.drawString("TICK: "+tick+" (= HEAL) (- DAMAGE) (0 +999) (9 REVIVE) (8 KILL) (7 MAXHP) (6 WALL UP) (5 WALL DOWN) (4 WALL REVIVE) (3 WALL KILL) (2 WALL MAX) (1 WALL INV ON) (TAB WALL INV OFF)", 25, height-30);
                }
                if (Math.random() < 0.001) {
                    randomEvent();

                }
                tick++;
            }
        }
    }

