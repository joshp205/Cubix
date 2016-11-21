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
      
   public static boolean getIsPaused()
   {
      return isPaused;
   }
   
   public static void setIsPaused(boolean state)
   {
      isPaused = state;
   }
   
    
   public static void newGame(Level myLevel, TechnoType[] myPlayer)
   {
      myLevel.setScore(0);
      myPlayer[0].setLives(5);
      //as well as any other variables that need to be reset 
   }
   
   public static void changeDificulty(Level myLevel)
   {
      //changing the speed and factor for score
   }
   
      
}