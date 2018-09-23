import java.awt.*;

public class Wall extends PongObject {

    public Wall(int x, int y, int wid, int hi) {
        xPos = x;
        yPos = y;
        width = wid;
        height = hi;
        color = Color.CYAN;
        name = "WALL";
        solid = true;
        maxhp = 150;
        hp = maxhp;
    }

    public void draw(Graphics window) {
        if (visible) {
            window.setColor(color);
            window.drawRect(xPos, yPos, width, height);
            drawHP(window);
        }
    }

}
