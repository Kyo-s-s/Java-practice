import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class App {
    public static ShootingFrame shootingFrame;
    public static boolean loop;
    
    public static void main(String[] args) {

        shootingFrame = new ShootingFrame();
        loop = true;

        Graphics gra = shootingFrame.panel.image.getGraphics();

        shootingFrame.panel.draw();
        
        // FPS
        int fps = 30, fpsCount = 0, FPS = 0;
        long startTime, fpsTime = 0;

        EnumShootingScreenEnum screen = EnumShootingScreenEnum.START;

        // GAME
        int playerX = 0, playerY = 0;
        int bulletInterval = 0;
        ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
        ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        Random random = new Random();
        int score = 0;

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


            switch (screen) {
                case START:                    
                    gra.setColor(Color.black);
                    Font font = new Font("SansSerif", Font.PLAIN, 50);
                    gra.setFont(font);
                    FontMetrics metrics = gra.getFontMetrics(font);
                    gra.drawString("Shooting", 250 - metrics.stringWidth("Shooting") / 2, 100);
                    font = new Font("SansSerif", Font.PLAIN, 20);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("Press ENTER to Start", 250 - metrics.stringWidth("Press ENTER to Start") / 2, 150);
                    if (Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
                        screen = EnumShootingScreenEnum.GAME;
                        playerBullets = new ArrayList<Bullet>();
                        enemyBullets = new ArrayList<Bullet>();
                        enemies = new ArrayList<Enemy>();
                        playerX = 230;
                        playerY = 420;
                        score = 0;
                    }
                    break;
                case GAME:

                    // player 
                    gra.setColor(Color.blue);
                    gra.fillRect(playerX + 10, playerY, 10, 10);
                    gra.fillRect(playerX, playerY + 10, 30, 10);

                    // bullet
                    ArrayList<Bullet> newPlayerBullets = new ArrayList<Bullet>();
                    for (Bullet bullet: playerBullets) {
                        gra.setColor(Color.blue);
                        gra.fillRect(bullet.x, bullet.y, 5, 5);
                        bullet.y -= 10;
                        if (bullet.y >= 0) {
                            newPlayerBullets.add(bullet);
                        }
                        ArrayList<Enemy> removeEnemies = new ArrayList<Enemy>();
                        for (Enemy enemy: enemies) {
                            if (bullet.x >= enemy.x && bullet.x <= enemy.x + 30 && bullet.y >= enemy.y && bullet.y <= enemy.y + 20) {
                                removeEnemies.add(enemy);
                            }
                        }
                        for (Enemy removeEnemy: removeEnemies) {
                            enemies.remove(removeEnemy);
                            score += 100;
                        }
                    }
                    playerBullets = newPlayerBullets;

                    // enemy
                    ArrayList<Enemy> newEnemies = new ArrayList<Enemy>();
                    if (random.nextInt(25) == 1) {
                        newEnemies.add(new Enemy(random.nextInt(455), 0));
                    }
                    for (Enemy enemy : enemies) {
                        gra.setColor(Color.red);
                        gra.fillRect(enemy.x, enemy.y, 30, 10);
                        gra.fillRect(enemy.x + 10, enemy.y + 10, 10, 10);
                        enemy.y += 5;
                        if (enemy.y < 480) {
                            newEnemies.add(enemy);
                        }
                        if (random.nextInt(50) == 1) {
                            enemyBullets.add(new Bullet(enemy.x + 15, enemy.y));
                        }
                        // TODO: 当たり判定(面倒なのでやらないかも)
                        if (enemy.x >= playerX && enemy.x <= playerX + 30 && enemy.y >= playerY && enemy.y <= playerY + 20) {
                            screen = EnumShootingScreenEnum.GAMEOVER;
                        }
                    }
                    enemies = newEnemies;

                    // enemy bullet
                    ArrayList<Bullet> newEnemiesBullets = new ArrayList<Bullet>();
                    for (Bullet bullet: enemyBullets) {
                        gra.setColor(Color.red);
                        gra.fillRect(bullet.x, bullet.y, 5, 5);
                        bullet.y += 10;
                        if (bullet.y <= 470) {
                            newEnemiesBullets.add(bullet);
                        }
                        if (bullet.x >= playerX && bullet.x <= playerX + 30 && bullet.y >= playerY && bullet.y <= playerY + 20) {
                            screen = EnumShootingScreenEnum.GAMEOVER;
                        }
                    }

                    if (Keyboard.isKeyPressed(KeyEvent.VK_LEFT) && playerX > 0) playerX -= 8;
                    if (Keyboard.isKeyPressed(KeyEvent.VK_RIGHT) && playerX < 455) playerX += 8;
                    if (Keyboard.isKeyPressed(KeyEvent.VK_UP) && playerY > 30) playerY -= 8;
                    if (Keyboard.isKeyPressed(KeyEvent.VK_DOWN) && playerY < 425) playerY += 8;

                    if (bulletInterval == 0 && Keyboard.isKeyPressed(KeyEvent.VK_B)) {
                        playerBullets.add(new Bullet(playerX + 15, playerY));
                        bulletInterval = 8;
                    }

                    if (bulletInterval > 0) bulletInterval--;

                    gra.setColor(Color.black);
                    font = new Font("SansSerif", Font.PLAIN, 20);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("SCORE:" + score, 450 - metrics.stringWidth("SCORE:" + score), 430);

                    break;
                case GAMEOVER:

                    gra.setColor(Color.black);
                    font = new Font("SansSerif", Font.PLAIN, 50);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("Game Over", 250 - metrics.stringWidth("Game Over") / 2, 100);
                    font = new Font("SansSerif", Font.PLAIN, 20);
                    gra.setFont(font);
                    metrics = gra.getFontMetrics(font);
                    gra.drawString("Press ESC to Return Start Screen", 250 - metrics.stringWidth("Press ENTER to Return Start Screen") / 2, 150);
                    if (Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                        screen = EnumShootingScreenEnum.START;
                    }
                    break;
            }


            gra.setColor(Color.black);
            gra.setFont(new Font("SansSerif", Font.PLAIN, 10));
            gra.drawString(FPS + "FPS", 10, 455);



            shootingFrame.panel.draw();

            try {
                long runTime = System.currentTimeMillis() - startTime;
                if (runTime < 1000 / fps) {
                    Thread.sleep((1000 / fps) - runTime);
                }                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
