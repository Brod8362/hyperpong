import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Pong extends JFrame implements KeyListener {

    private final int width = 1280;
    private final int height = 720;
    private static ArrayList<PongObject> objects = new ArrayList<>();

    private JFrame globalFrame;



    Pong() {
        objects.add(new Paddle(50, height/2)); //create left paddle
        objects.add(new Paddle( width-100, height/2)); //create right paddle
        objects.add(new Ball(width/2, height/2));

        int randY = ThreadLocalRandom.current().nextInt(100, height-100);
        int randH = ThreadLocalRandom.current().nextInt(50,  125);
        int randX = ThreadLocalRandom.current().nextInt(200, width-200);
        objects.add(new Wall(randX, randY, 30, randH));
        // create a random wall with a random spawn position and a random height

    }


    @Override
    public void keyTyped(KeyEvent e) {
        PongObject player1 = objects.get(0);
        PongObject player2 = objects.get(1);
        Graphics window = globalFrame.getGraphics();
        if (e.getKeyChar() == 's') {
            player1.changeYPos(window,5);
        } else if (e.getKeyChar() == 'w') {
            player1.changeYPos(window, -5);
        } else if (e.getKeyChar() == 'q') {
            System.exit(6);
        } else if (e.getKeyChar() == 'i') {
            player2.changeYPos(window, -5);
        } else if (e.getKeyChar() == 'k') {
            player2.changeYPos(window, 5);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //dont care
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //do nothing
    }


    public void main() {
        JFrame frame = this;
        globalFrame = this;
        frame.setSize(width, height); //set dimensions
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //exit operation
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setVisible(true);
        Graphics window = frame.getGraphics();

            while (true) {

                try {
                    Thread.sleep(50);  //set to 16 for ~60fps
                } catch (InterruptedException ignored) { }

                window.setColor(Color.BLACK);
                window.fillRect(0, 0, width, height);

                int i = 0;

                for (PongObject object : objects) { //iterate over and draw objects
                    i ++;
                    object.update(window, objects);
                    // draw object
                    object.draw(window);
                    // draw object
                    window.drawString(object+"", 25, i*30);
                    // draw diagnostic info

                }
            }
        }
    }
