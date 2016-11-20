/*******************************************
* Authors : dustin shropshire
* class : PauseMenu
*  - a menu that allows the player to 
* step away from the game, start a new game
* and adjust dificulity 
********************************************/


 
public class PauseMenu
{
      //fields
  public static boolean isPaused = false;
  
      
      //constructors
   private PauseMenu()
   {
      //so no instance can be created 
   }
      
      //methods 
   
   public static void drawPauseMenu(Drawing dSurface)
   {
      //will draw pause menu from this method in process input 
      if(!isPaused)
      {
         //will clear screen here in process input 
         clearPauseMenu(dSurface);
      }
     
   }
   
   public static boolean getIsPaused()
   {
      return isPaused;
   }
   
   public static void setIsPaused(boolean state)
   {
      isPaused = state;
   }
   
   public static void clearPauseMenu(Drawing dSurface)
   {
      //think issue may come from here
      dSurface.clearScreen();
   }
   
   public static void newGame(Level myLevel, TechnoType myPlayer)
   {
      myLevel.setScore(0);
      myPlayer.setLives(5);
      //as well as any other variables that need to be reset 
   }
   
   public static void changeDificulty(Level myLevel)
   {
      //changing the speed and factor for score
   }
   
      
}