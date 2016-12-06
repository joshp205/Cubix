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

  // FRAME DATA
  public final String TITLE = "Cubix";
  public final int WIDTH = 960;
  public final int HEIGHT = WIDTH / 4 * 3;

  // LEVEL DATA
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
  private boolean running, inGame;
  private boolean left, right, up, down, space, enter, escape;

  private Camera cam;
  private Level level;
  private TechnoType[] tt;
  private Menu menu, pauseMenu, optionMenu;
  private Level.Difficulty difficulty = Level.Difficulty.EASY;

  private int[][] pColor = new int[][]{{182,176,238}, {124,80,169}, {91,68,172}, {182,176,238}, {124,80,169}, {91,68,172}};
  private int[][] eColor = new int[][]{{199,102,42}, {166,50,40}, {112,28,52}, {199,102,42}, {166,50,40}, {112,28,52}};
  private String[] status = new String[3];
  private String[][] menuItems =  {{"New Game", "Difficulty", "Exit"}, {"New Game", "Return To Game", "Exit To Main Menu"},
                                  {"Easy", "Medium", "Hard", "Extreme", "Go Back"}};

  // Testing variables
  public Random rand = new Random();
  public float rot = 0f;


  public static void main(String[] args) {
    Game game = new Game();
  }

  public Game() {
    thread = new Thread(this);
    
    setTitle(TITLE);
    setSize(WIDTH,HEIGHT);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setIgnoreRepaint(true);
    setVisible(true);

    addKeyListener(this);
    menu = new Menu(Menu.MenuType.MAIN, menuItems);

    start();
  }

  private synchronized void start() {
  	running = true;
    inGame = false;
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
    cam = new Camera(0f,65f,0f,1f);
    level = new Level(LEVELWIDTH, 0f, LEVELDEPTH, SPEED, difficulty, 25, LAYERS);
    tt = new TechnoType[5];
    tt[0] = new TechnoType(TechnoType.Type.PLAYER,0,0,45,15,15,15,0,0,0,pColor,false);

    // Placeholder enemies
    for(int i = 1; i < tt.length; i++) {
      tt[i] = new TechnoType(TechnoType.Type.ENEMY,rand.nextInt((int)LEVELWIDTH) - LEVELWIDTH/2f,0, i*100,15,15,15,0,0,0,eColor,false);
    }

    tt[0].setCoord3DZ(110);
    cam.setZ(1f);

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
    if(inGame) {
      // LEVEL
      level.update(cam);
      level.setScore(level.getScore() + 1);

      // OBJECTS
      for(TechnoType t : tt) {
        t.update(tt[0], level, cam);
      }

      // UI
      status[0] = String.format("Lives: %d", tt[0].getLives());
      status[1] = String.format("Score: %d", level.getScore());
    } else {

      // OBJECTS
      rot+=0.00025;
      if(rot >= 360) {
        rot = 0;
      }
      tt[0].setCoord3DX((float)Math.cos(Math.toDegrees(rot)) * (LEVELWIDTH/2));
      tt[0].update(tt[0], level, cam);

      // MENU
      menu.update(WIDTH, HEIGHT);
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

      // INGAME
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

      if(!inGame) {
        menu.render(dSurface);
      }

      dSurface.dispose();
      buffer.show();
   }

   public void newGame() {
    // LEVEL CREATION
    level = new Level(LEVELWIDTH, 0f, LEVELDEPTH, SPEED, difficulty, 25, LAYERS);

    // OBJECT CREATION
    tt = new TechnoType[difficulty.getEnemies() + 1];
    tt[0] = new TechnoType(TechnoType.Type.PLAYER,0,0,45,15,15,15,0,0,0,pColor,false);
    for(int i = 1; i < tt.length; i++) {
      tt[i] = new TechnoType(TechnoType.Type.ENEMY,rand.nextInt((int)LEVELWIDTH) - LEVELWIDTH/2f,0, i*100,15,15,15,0,0,0,eColor,false);
    }

    tt[0].setCoord3DZ(110);
    inGame = true;
   }

   public void endGame() {
    inGame = false;

   }
   
   public void processInput() {
    if(inGame) {
      // GAME CONTROLS
      if(up) {
        level.setSpeed(5f);
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
      if(escape) {
        escape = false;
        inGame = false;
        menu.setMenuType(Menu.MenuType.PAUSE);
      }
    } else {
      // MENU CONTROLS
      if(up) {
        menu.menuUp();
        up = false;
      }
      if(down) {
        menu.menuDown();
        down = false;
      }
      if(right) {
        right = false;
      }
      if(left) {
        left = false;
      }
      if(enter) {
        int i = menu.getCursorPos();
        if(menu.getMenuType() == Menu.MenuType.MAIN) {
          switch(i) {
            case 0:   newGame();
                      break;
            case 1:   menu.setMenuType(Menu.MenuType.OPTIONS);
                      break;
            case 2:   System.exit(0);
          }
        }
        if(menu.getMenuType() == Menu.MenuType.PAUSE) {
          switch(i) {
            case 0:   newGame();
                      break;
            case 1:   inGame = true;
                      menu.setMenuType(Menu.MenuType.NONE);
                      break;
            case 2:   menu.setMenuType(Menu.MenuType.MAIN);
                      endGame();
                      break;
          }
        }
        if(menu.getMenuType() == Menu.MenuType.OPTIONS) {
          switch(i) {
            case 0: difficulty = Level.Difficulty.EASY;
                    break;
            case 1: difficulty = Level.Difficulty.MEDIUM;
                    break;
            case 2: difficulty = Level.Difficulty.HARD;
                    break;
            case 3: difficulty = Level.Difficulty.EXTREME;
                    break;
            case 4: menu.setMenuType(Menu.MenuType.MAIN);
                    break;
          }
        }
        enter = false;
      }
      if(escape) {
        if(menu.getMenuType() == Menu.MenuType.MAIN) {
          System.exit(0);
        }
        if(menu.getMenuType() == Menu.MenuType.PAUSE) {
          inGame = true;
          menu.setMenuType(Menu.MenuType.NONE);
        }
        if(menu.getMenuType() == Menu.MenuType.OPTIONS) {
          if(menu.getParentMenu() == Menu.MenuType.MAIN) {
            menu.setMenuType(Menu.MenuType.MAIN);

          } else if(menu.getParentMenu() == Menu.MenuType.PAUSE) {
            menu.setMenuType(Menu.MenuType.PAUSE);
          }
        }
        escape = false;
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
       space = true;
    if((k.getKeyCode() == KeyEvent.VK_ENTER))
       enter = true;
    if((k.getKeyCode() == KeyEvent.VK_ESCAPE))
       escape = true;
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
       space = true;
    if((k.getKeyCode() == KeyEvent.VK_ENTER))
       enter = true;
    if((k.getKeyCode() == KeyEvent.VK_ESCAPE))
       escape = true;
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
       space = false;
    if((k.getKeyCode() == KeyEvent.VK_ENTER))
       enter = false;
    if((k.getKeyCode() == KeyEvent.VK_ESCAPE))
       escape = false;
   }
}