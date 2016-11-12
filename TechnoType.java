import java.awt.Color;

public class TechnoType{
   private Point3D dimensions;
   private Point3D coords3D;
   private Point2D coords2D;

   private int lives;
   
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
      
      color = new Color[rgb.length];
      for(int i = 0; i < color.length; i++) {
         color[i] = new Color(rgb[i][0], rgb[i][1], rgb[i][2]);
      }
      wireframe = i_wf;
   }
   
   public TechnoType(Type tType, Point3D p, Point3D dim, int[][] rgb, boolean i_wf) {
      this(tType, p.getX(), p.getY(), p.getZ(), dim.getX(), dim.getY(), dim.getZ(), rgb, i_wf);
   }

   public void update() {
      //TODO
   }

   public void render(Drawing dSurface, Camera cam) {
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