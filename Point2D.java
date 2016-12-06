public class Point2D {
   private float[] xy;
   
   public Point2D(float x, float y) {
      xy = new float[2];
      xy[0] = x;
      xy[1] = y;
   }

   public float getX() {
      return xy[0];
   }
   
   public float getY() {
      return xy[1];
   }
   
   public void setX(float x) {
      xy[0] = x;
   }
   
   public void setY(float y) {
      xy[1] = y;
   }

   public void setPoint(Point2D p) {
      setX(p.getX());
      setY(p.getY());
   }
   
   @Override
   public String toString() {
      return "(" + xy[0] + "," + xy[1] + ")";
   }
}