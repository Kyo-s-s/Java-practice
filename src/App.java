import java.awt.*;

public class App {
    public static ShootingFrame shootingFrame;
    public static boolean loop;
    
    public static void main(String[] args) {

        shootingFrame = new ShootingFrame();
        loop = true;

        Graphics gra = shootingFrame.panel.image.getGraphics();

        shootingFrame.panel.draw();
        
        // FPS
        int fps = 30, fpsCount = 0, FPS = 0;;
        long startTime, fpsTime = 0;;
        
        while (loop) {
            if (System.currentTimeMillis() - fpsTime >= 1000) {
                fpsTime = System.currentTimeMillis();
                // System.out.println("FPS: " + fpsCount);
                FPS = fpsCount;
                fpsCount = 0;
            }

            startTime = System.currentTimeMillis();
            fpsCount++;


            gra.setColor(Color.white);
            gra.fillRect(0, 0, 500, 500);

            gra.setColor(Color.black);
            gra.fillRect(100, 100, 100, 100);

            gra.setColor(Color.black);
            gra.setFont(new Font("SansSerif", Font.PLAIN, 10));
            gra.drawString(FPS + "FPS", 0, 455);



            shootingFrame.panel.draw();

            try {
                Thread.sleep((1000 / fps) - (System.currentTimeMillis() - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
