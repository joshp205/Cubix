import java.awt.Color;

public class TechnoType{
   private Point3D[] coords3D;
   private Point2D coords2D;

   private int lives;
   private int hitCoolDown;
   public boolean collided;

   private int layer;
   
   private Color[] color;
   private boolean wireframe;
   
   Type tType;
   
   
   public enum Type {
      PLAYER, ENEMY, POWERUP, NONE
   }
   
   public TechnoType(Type tType, float x, float y, float z, float w, float h, float d, float rotX, float rotY, float rotZ, int[][] rgb, boolean i_wf) {
      Point3D[] temp = {new Point3D(x, y, z), new Point3D(x, y, z), new Point3D(w, h, d), new Point3D(rotX, rotY, rotZ), new Point3D(0,0,0)};
      temp[4] = xMath.getCenter3D(temp[0], temp[2]);
      coords3D = temp;
      coords2D = new Point2D(0,0);

      this.tType = tType;

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
         color[i] = new Color(rgb[i][0], rgb[i][1], rgb[i][2], rgb[i][3]);
      }
      wireframe = i_wf;
   }
   
   public TechnoType(Type tType, Point3D p, Point3D dim, Point3D rot, int[][] rgb, boolean i_wf) {
      this(tType, p.getX(), p.getY(), p.getZ(), dim.getX(), dim.getY(), dim.getZ(), rot.getX(), rot.getY(), rot.getZ(), rgb, i_wf);
   }

   public void update(TechnoType player, Level level, Camera cam) {
      setPrevCoords3D(getCoords3D());
      setCenter(xMath.getCenter3D(getCoords3D(), getDimensions()));

      if(tType == Type.ENEMY || tType == Type.POWERUP) {
         setCoord3DZ(getCoord3DZ() - level.getTravelDistance());

         if(getCoord3DZ() > level.getSegZGrid(level.getSegments() - level.getDarkSegments())) {
            layer = 1;
         } else if(getCoord3DZ() < player.getCoord3DZ() + player.getDimensionD()){
            layer = 3;
            collided = false;
            if(getCoord3DZ() < cam.getZ()) {
               layer = 1;
               setCoord3DZ(level.getDepth());
            }
         } else if(!collided && !player.collided){
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
      dSurface.drawCube(this, cam);
   }

   public float getCoord3DX() {
      return coords3D[0].getX();
   }
   
   public float getCoord3DY() {
      return coords3D[0].getY();
   }
   
   public float getCoord3DZ() {
      return coords3D[0].getZ();
   }
   
   public Point3D getCoords3D() {
      return coords3D[0];
   }

   public float getPrevCoord3DX() {
      return coords3D[1].getX();
   }

   public float getPrevCoord3DY() {
      return coords3D[1].getY();
   }

   public float getPrevCoord3DZ() {
      return coords3D[1].getZ();
   }

   public Point3D getPrevCoords3D() {
      return coords3D[1];
   }

   public float getDimensionW() {
      return coords3D[2].getX();
   }

   public float getDimensionH() {
      return coords3D[2].getY();
   }

   public float getDimensionD() {
      return coords3D[2].getZ();
   }

   public Point3D getDimensions() {
      return coords3D[2];
   }

   public float getRotX() {
      return coords3D[3].getX();
   }

   public float getRotY() {
      return coords3D[3].getY();
   }

   public float getRotZ() {
      return coords3D[3].getZ();
   }

   public Point3D getRotation() {
      return coords3D[3];
   }

   public float getCenterX() {
      return coords3D[4].getX();
   }

   public float getCenterY() {
      return coords3D[4].getY();
   }

   public float getCenterZ() {
      return coords3D[4].getZ();
   }

   public Point3D getCenter() {
      return coords3D[4];
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

   public Color[] getColors() {
      return color;
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

   public void setCoord3DX(float x) {
      coords3D[0].setX(x);
   }
   
   public void setCoord3DY(float y) {
      coords3D[0].setY(y);
   }
   
   public void setCoord3DZ(float z) {
      coords3D[0].setZ(z);
   }
   
   public void setCoords3D(float x, float y, float z) {
      coords3D[0].setX(x);
      coords3D[0].setY(y);
      coords3D[0].setZ(z);
   }

   public void setCoords3D(Point3D p) {
      setCoords3D(p.getX(),p.getY(),p.getZ());
   }

   public void setPrevCoord3DX(float x) {
      coords3D[1].setX(x);
   }
   
   public void setPrevCoord3DY(float y) {
      coords3D[1].setY(y);
   }
   
   public void setPrevCoord3DZ(float z) {
      coords3D[1].setZ(z);
   }
   
   public void setPrevCoords3D(float x, float y, float z) {
      coords3D[1].setX(x);
      coords3D[1].setY(y);
      coords3D[1].setZ(z);
   }

   public void setPrevCoords3D(Point3D p) {
      setCoords3D(p.getX(),p.getY(),p.getZ());
   }

   public void setDimensionW(float w) {
      coords3D[2].setX(w);
   }

   public void setDimensionH(float h) {
      coords3D[2].setY(h);
   }

   public void setDimensionD(float d) {
      coords3D[2].setZ(d);
   }

   public void setRotX(float x) {
      coords3D[3].setX(x);
   }

   public void setRotY(float y) {
      coords3D[3].setY(y);
   }

   public void setRotZ(float z) {
      coords3D[3].setZ(z);
   }

   public void setRotation(float x, float y, float z) {
      coords3D[3].setX(x);
      coords3D[3].setY(y);
      coords3D[3].setZ(z);
   }

   public void setRotation(Point3D p) {
      setRotation(p.getX(),p.getY(),p.getZ());
   }

   public void setCenterX(float x) {
      coords3D[4].setX(x);
   }

   public void setCenterY(float y) {
      coords3D[4].setY(y);
   }

   public void setCenterZ(float z) {
      coords3D[4].setZ(z);
   }

   public void setCenter(float x, float y, float z) {
      coords3D[4].setX(x);
      coords3D[4].setY(y);
      coords3D[4].setZ(z);
   }

   public void setCenter(Point3D p) {
      setCenter(p.getX(),p.getY(),p.getZ());
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