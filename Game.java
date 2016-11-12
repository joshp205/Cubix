import java.util.Scanner;
import java.util.Random;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.Color;

public class Game extends JFrame implements Runnable, KeyListener{
   public final String TITLE = "GAME";
   public final int WIDTH = 960;
   public final int HEIGHT = WIDTH / 4 * 3;

   public final float LEVELWIDTH = 200f;
   public final float LEVELDEPTH = 10000f;
   public final float SPEED = 54f;

   public final double MS_PER_UPDATE = 60.0;
   
   private Thread thread;
   private BufferStrategy buffer;
   private Drawing dSurface;
   private Graphics g;
   private Graphics2D g2d;
   private boolean running;
   private boolean inGame = false;
   private boolean left, right, up, down, jump;

   private Camera cam;
   private Level level;
   private TechnoType player;

   private int[][] pColor = new int[][]{{255, 0, 0}, {0, 255, 0}, {0, 0, 255}};

   // Testing variables
   public float x = 3f;
   public float z = 500f;
   public float rot = 0f;
   public TechnoType[] enem;
   public Random rand = new Random();
   private String[] status;
   
   public static void main(String[] args) {
      Game game = new Game();
   }

   public Game() {
      thread = new Thread(this);
      
      setSize(WIDTH,HEIGHT);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setIgnoreRepaint(true);
      setVisible(true);

      addKeyListener(this);

      start();
   }
   
   private synchronized void start() {
   	running = true;
   	thread.start();
   }
   
   public synchronized void stop() {
   	running = false;
   	try {
   		thread.join();
   	} catch(InterruptedException e) {
   		e.printStackTrace();
   	}
   }

   public void run() {
    cam = new Camera(0f,65f,0f,1f);
    level = new Level(LEVELWIDTH, 0f, LEVELDEPTH, SPEED, 10);
    player = new TechnoType(TechnoType.Type.PLAYER,0,0,45,15,15,15,pColor,false);
    cam.setZ(1f);

    enem = new TechnoType[5];
    for(int i = 0; i < 5; i++) {
      enem[i] = new TechnoType(TechnoType.Type.ENEMY,rand.nextInt(50) - 25,0,rand.nextInt(10000) + 45,15,15,15,pColor,true);
    }
    

    double previousTime = System.currentTimeMillis();
    double lag = 0.0;
      
      while (running == true) {
        double currentTime = System.currentTimeMillis();
        double elapsedTime = currentTime - previousTime;
        previousTime = currentTime;
        lag += elapsedTime;
      
        processInput();
      
        while (lag >= MS_PER_UPDATE) {
          update();
          lag -= MS_PER_UPDATE;
        }
        render();
      }
   }
   
   public void update() {

    // LEVEL
    level.setTravelDistance(level.getTravelDistance() + 1/level.getSpeed());
    level.update(cam);

    // OBJECTS
    player.update();




    // Testing Code
    //cam.setZ((float)Math.cos(Math.toDegrees(rot)) * 45);
    Color c = xColor.rainbowShift(player.getColorR(0), player.getColorG(0), player.getColorB(0));
    Color d = xColor.rainbowShift(player.getColorR(1), player.getColorG(1), player.getColorB(1));
    Color e = xColor.rainbowShift(player.getColorR(2), player.getColorG(2), player.getColorB(2));
    player.setColor(0, c);
    player.setColor(1, d);
    player.setColor(2, e);
    player.setCoord3DZ(110);
    if(rot==360) {
      rot=0;
    }
    rot+=90*0.0000075;

    for(int i = 0; i < 5; i++) {
      enem[i].setColor(0, c);
      enem[i].setColor(1, c);
      enem[i].setColor(2, c);
      if(enem[i].getCoord3DZ() < cam.getZ()) {
        enem[i].setCoord3DZ(rand.nextInt(1000) + 9000);
      }
      enem[i].setCoord3DZ(enem[i].getCoord3DZ() - level.getSegmentSize()/level.getSpeed());
      //enem[i].setCoord3DX((float)Math.cos(Math.toDegrees(rot)) * 45);
    }

    // DEBUG
    status = new String[3];
    status[0] = String.format("Cam X: %f, Y: %f, Z: %f", cam.getX(), cam.getY(), cam.getZ());
    status[1] = String.format("Techno X: %f, Y: %f, Z: %f",player.getCoord3DX(),
                            player.getCoord3DY(), player.getCoord3DZ());
   }
   
   public void render() {
      buffer = getBufferStrategy();
   
      if(buffer == null) {
         createBufferStrategy(2);
         return;
      }
      
      g = buffer.getDrawGraphics();
      g2d = (Graphics2D)buffer.getDrawGraphics();
      dSurface = new Drawing(WIDTH,HEIGHT,g,g2d);

      dSurface.clearScreen();

      level.render(dSurface,cam);
      player.render(dSurface,cam);

      // Testing code
      for(int i = 0; i < 5; i++) {
        enem[i].render(dSurface,cam);
      }

      // DEBUG
      //Debug.draw(dSurface, Color.white, 5, 50);
      if(status != null){
        for(int i = 0; i < status.length; i++) {
          if(status[i] == null) {
            break;
          } else {
            dSurface.drawText(status[i], Color.white, 5, (i*15) + 50);
          }
        }
      }
      
      

      
      dSurface.dispose();
      buffer.show();
   }
   
   public void processInput() {
      if(up) {
         player.setCoord3DZ(player.getCoord3DZ() + 1);
         cam.setY(cam.getY() + 1);
      }
      if(down) {
         player.setCoord3DZ(player.getCoord3DZ() - 1);
         cam.setY(cam.getY() - 1);
      }
      if(right) {
        if(player.getCoord3DX() + player.getDimensionW() <= (LEVELWIDTH/2)) {
          player.setCoord3DX(player.getCoord3DX() + 1);
        }
      }
      if(left) {
        if(player.getCoord3DX() >= -(LEVELWIDTH/2)) {
          player.setCoord3DX(player.getCoord3DX() - 1);
        }
      }
   }

   @Override
   public void keyTyped(KeyEvent k) {
    if((k.getKeyCode() == KeyEvent.VK_A))
       left = true;
    if((k.getKeyCode() == KeyEvent.VK_D))
       right = true;
    if((k.getKeyCode() == KeyEvent.VK_W))
       up = true;
    if((k.getKeyCode() == KeyEvent.VK_S))
       down = true;
    if((k.getKeyCode() == KeyEvent.VK_SPACE))
       jump = true;
   }

   @Override
   public void keyPressed(KeyEvent k) {
    if((k.getKeyCode() == KeyEvent.VK_A))
       left = true;
    if((k.getKeyCode() == KeyEvent.VK_D))
       right = true;
    if((k.getKeyCode() == KeyEvent.VK_W))
       up = true;
    if((k.getKeyCode() == KeyEvent.VK_S))
       down = true;
    if((k.getKeyCode() == KeyEvent.VK_SPACE))
       jump = true;
   }

   @Override
   public void keyReleased(KeyEvent k) {
    if((k.getKeyCode() == KeyEvent.VK_A))
       left = false;
    if((k.getKeyCode() == KeyEvent.VK_D))
       right = false;
    if((k.getKeyCode() == KeyEvent.VK_W))
       up = false;
    if((k.getKeyCode() == KeyEvent.VK_S))
       down = false;
    if((k.getKeyCode() == KeyEvent.VK_SPACE))
       jump = false;
   }
}