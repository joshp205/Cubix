import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.Color;

public class Game extends JFrame implements Runnable, KeyListener{
   public final String TITLE = "Quadratfrei";
   public final int WIDTH = 960;
   public final int HEIGHT = WIDTH / 4 * 3;

   public final float LEVELWIDTH = 200f;
   public final float LEVELDEPTH = 1000f;
   public final float SPEED = 0.025f;
   public final int LAYERS = 4;

   public final double MS_PER_UPDATE = 60.0;
   
   private Thread thread;
   private BufferStrategy buffer;
   private Drawing dSurface;
   private Graphics g;
   private Graphics2D g2d;
   private boolean running;
   private boolean inGame = false;
   private boolean left, right, up, down, jump, pause;

   private Camera cam;
   private Level level;
   private TechnoType[] tt;
   private TechnoType player;

   private int[][] pColor = new int[][]{{182,176,238}, {124,80,169}, {91,68,172}};
   private int[][] eColor = new int[][]{{199,102,42}, {166,50,40}, {112,28,52}};
   private String[] status = new String[3];

   // Testing variables
   public Random rand = new Random();
   
   
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
    // Initialize Objects
    
    cam = new Camera(0f,50f,5f,1f);  //0 25 0 1
    level = new Level(LEVELWIDTH, 0f, LEVELDEPTH, SPEED, 25, LAYERS);
    tt = new TechnoType[5];
    tt[0] = new TechnoType(TechnoType.Type.PLAYER,0,0,45,15,15,15,pColor,false);

    for(int i = 1; i < tt.length; i++) {
      tt[i] = new TechnoType(TechnoType.Type.ENEMY,rand.nextInt((int)LEVELWIDTH) - LEVELWIDTH/2f,0, i*100,15,15,15,eColor,false);
    }

    cam.setZ(1f);
    tt[0].setCoord3DZ(110);
    for(int i = 0; i < status.length; i++) {
      status[i] = "x";
    }
    
    // Enter Game Loop
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
     
     //dustin implemented to test pause
     if(!PauseMenu.getIsPaused())
     {
       // LEVEL
      level.update(cam);

      // OBJECTS
      for(TechnoType t : tt) {
         t.update(tt[0], level, cam);
      }

      // UI
      status[0] = String.format("Lives: %d", tt[0].getLives());
      status[1] = String.format("Score: %d", (int)level.getScore());
      }
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
      for(int i = 0; i <= LAYERS; i++) {
        switch(i) {
          // BACKGROUND
          case 0: level.render(dSurface, cam, 0);
                  break;
          // OBJECTS
          case 1: for(TechnoType t : tt) {
                    if(t.getLayer() == 1) {
                      t.render(tt[0], dSurface, cam);
                    }
                  }
                  break;
          // FOREGROUND
          case 2: level.render(dSurface, cam, 2);
                  for(TechnoType t : tt) {
                    if(t.getLayer() == 2) {
                      t.render(tt[0], dSurface, cam);
                    }
                  }
                  tt[0].render(tt[0], dSurface, cam);
                  break;
          // PAST PLAYER
          case 3: for(TechnoType t : tt) {
                    if(t.getLayer() == 3) {
                      t.render(tt[0], dSurface, cam);
                    }
                  }
                  break;
          // UI
          case 4: for(int j = 0; j < status.length; j++) {
                    dSurface.drawText(status[j], Color.white, 10, (j*15) + 40);
                  }
                  break;
        }
      }

      dSurface.dispose();
      buffer.show();
   }
   
   public void processInput() {
      if(up) {
        level.setSpeed(0.005f);
      } else {
        level.setSpeed(SPEED);
      }
      if(down) {
        level.setSpeed(0.25f);
      } else {
        level.setSpeed(SPEED);
      }
      if(right) {
        if(tt[0].getCoord3DX() + tt[0].getDimensionW() <= (LEVELWIDTH/2)) {
          tt[0].setCoord3DX(tt[0].getCoord3DX() + 1);
        }
      }
      if(left) {
        if(tt[0].getCoord3DX() >= -(LEVELWIDTH/2)) {
          tt[0].setCoord3DX(tt[0].getCoord3DX() - 1);
        }
      }
      if(pause)
      {
         PauseMenu.setIsPaused(true);
         //draw menu here 
         PauseMenu.drawPauseMenu(dSurface);
      }else
       {
         PauseMenu.setIsPaused(false);
         //dispose menu here
         PauseMenu.clearPauseMenu(dSurface);
       }
       
       //will also add here for new game button == pauseMenu.isPaused 
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
    if((k.getKeyCode() == KeyEvent.VK_CAPS_LOCK))
        if(pause)
       {
         pause = false;
       }
       else
       {
         pause = true;
       }

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
    if((k.getKeyCode() == KeyEvent.VK_CAPS_LOCK))
       if(pause)
       {
         pause = false;
       }
       else
       {
         pause = true;
       }

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