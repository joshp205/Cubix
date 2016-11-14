import java.awt.Color;

public class TechnoType{
   private Point3D dimensions;
   private Point3D coords3D;
   private Point2D coords2D;

   private int lives;
   private int hitCoolDown;
   public boolean collided;

   private int layer;
   
   private Color[] color;
   private boolean wireframe;
   
   Type tType;
   
   
   public enum Type {
      PLAYER, ENEMY, POWERUP
   }
   
   public TechnoType(Type tType, float x, float y, float z, float w, float h, float d, int[][] rgb, boolean i_wf) {
      this.tType = tType;
      dimensions = new Point3D(w, h, d);
      coords3D = new Point3D(x, y, z);
      coords2D = new Point2D(0,0);

      if(tType == Type.PLAYER) {
         layer = 2;
         lives = 5;
         hitCoolDown = 50;
      }

      if(tType == Type.ENEMY || tType == Type.POWERUP) {
         collided = false;
      }
      
      color = new Color[rgb.length];
      for(int i = 0; i < color.length; i++) {
         color[i] = new Color(rgb[i][0], rgb[i][1], rgb[i][2]);
      }
      wireframe = i_wf;
   }
   
   public TechnoType(Type tType, Point3D p, Point3D dim, int[][] rgb, boolean i_wf) {
      this(tType, p.getX(), p.getY(), p.getZ(), dim.getX(), dim.getY(), dim.getZ(), rgb, i_wf);
   }

   public void update(TechnoType player, Level level, Camera cam) {
      if(tType == Type.ENEMY || tType == Type.POWERUP) {
         coords3D.setZ(coords3D.getZ() - level.getTravelDistance());
         if(coords3D.getZ() < cam.getZ()) {
            coords3D.setZ(level.getDepth());
         }

         if(coords3D.getZ() > level.getSegZGrid(level.getSegments() - level.getDarkSegments())) {
            layer = 1;
         } else if(coords3D.getZ() < player.getCoord3DZ() + player.getDimensionD()){
            layer = 3;
            collided = false;
         } else if(!collided){
            layer = 2;
            if(Physics.calculateCollision(this, player)) {
               player.setLives(player.getLives() - 1);
               collided = true;
               player.collided = true;
            }
         }
      }

      // TESTING CODE - COOLDOWN
      if(tType == Type.PLAYER) {
         if(collided == true) {
            hitCoolDown--;
            if(hitCoolDown%3 == 0) {
               wireframe = false;
            } else {
               wireframe = true;
            }
            if(hitCoolDown == 0) {
               collided = false;
               hitCoolDown = 50;
            }
         }
      }
   }

   public void render(TechnoType player, Drawing dSurface, Camera cam) {
      dSurface.drawCube(this, cam, dimensions.getX(), dimensions.getY(), dimensions.getZ());
   }

   public float getDimensionW() {
      return dimensions.getX();
   }

   public float getDimensionH() {
      return dimensions.getY();
   }

   public float getDimensionD() {
      return dimensions.getZ();
   }
   
   public float getCoord3DX() {
      return coords3D.getX();
   }
   
   public float getCoord3DY() {
      return coords3D.getY();
   }
   
   public float getCoord3DZ() {
      return coords3D.getZ();
   }
   
   public Point3D getCoords3D() {
      return coords3D;
   }
   
   public float getCoord2DX() {
      return coords2D.getX();
   }
   
   public float getCoord2DY() {
      return coords2D.getY();
   }
   
   public Point2D getCoords2D() {
      return coords2D;
   }

   public int getLives() {
      return lives;
   }

   public int getLayer() {
      return layer;
   }

   public Color getColor(int index) {
      return color[index];
   }

   public int getColorR(int index) {
      return color[index].getRed();
   }

   public int getColorG(int index) {
      return color[index].getGreen();
   }

   public int getColorB(int index) {
      return color[index].getBlue();
   }
   
   public boolean isWireframe() {
      return wireframe;
   }

   public void setDimensionW(float w) {
      dimensions.setX(w);
   }

   public void setDimensionH(float h) {
      dimensions.setY(h);
   }

   public void setDimensionD(float d) {
      dimensions.setZ(d);
   }
   
   public void setCoord3DX(float x) {
      coords3D.setX(x);
   }
   
   public void setCoord3DY(float y) {
      coords3D.setY(y);
   }
   
   public void setCoord3DZ(float z) {
      coords3D.setZ(z);
   }
   
   public void setCoords3D(float x, float y, float z) {
      coords3D.setX(x);
      coords3D.setY(y);
      coords3D.setZ(z);
   }
   
   public void setCoords3D(Point3D p) {
      setCoords3D(p.getX(),p.getY(),p.getZ());
   }
   
   public void setCoord2DX(float x) {
      coords2D.setX(x);
   }
   
   public void setCoords2DY(float y) {
      coords2D.setY(y);
   }
   
   public void setCoords2D(float x, float y) {
      coords2D.setX(x);
      coords2D.setY(y);
   }
   
   public void setCoords2D(Point2D p) {
      setCoords2D(p.getX(), p.getY());
   }

   public void setLives(int lives) {
      this.lives = lives;
   }

   public void setColor(int index, Color c) {
      color[index] = c;
   }

   public void setColor(int index, int r, int g, int b) {
      Color c = new Color(r, g, b);
      setColor(index, c);
   }
   
   public void setWireframe(boolean i_wf) {
      wireframe = i_wf;
   }
}