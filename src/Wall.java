import java.awt.*;

public class Wall extends PongObject {

    public Wall(int x, int y, int wid, int hi, int maxhp) {
        xPos = x;
        yPos = y;
        width = wid;
        height = hi;
        color = Color.CYAN;
        name = "WALL";
        solid = true;
        this.maxhp = maxhp;
        this.hp = maxhp;
    }

    public void draw(Graphics window) {
        if (visible) {
            window.setColor(color);
            window.drawRect(xPos, yPos, width, height);
            drawHP(window);
        }
    }

}
