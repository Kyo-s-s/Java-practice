import java.awt.event.*;
import java.util.*;

public class Keyboard extends KeyAdapter {
    
    private static ArrayList<Integer> pressedButtons = new ArrayList<Integer>();

    public static boolean isKeyPressed(int keyCode) {
        return pressedButtons.contains(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (!pressedButtons.contains(e.getKeyCode())) {
            pressedButtons.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        pressedButtons.remove((Integer) e.getKeyCode());
    }
}
