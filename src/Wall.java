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
    }

    public void draw(Graphics window) {
        window.setColor(color);
        window.drawRect(xPos, yPos, width, height);
    }

}
