public class Point3D extends Point2D {
   private float z;
   
   public Point3D(float x, float y, float z) {
      super(x,y);
      this.z = z;
   }
   
   public void setZ(float z) {
      this.z = z;
   }
   
   public float getZ() {
      return z;
   }

   @Override
   public String toString() {
      return "(" + getX() + "," + getY() +  "," + z + ")";
   }
}