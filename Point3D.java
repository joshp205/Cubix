public class Point3D {
   private float xyz[];
   
   public Point3D(float x, float y, float z) {
      xyz = new float[3];
      xyz[0] = x;
      xyz[1] = y;
      xyz[2] = z;
   }

   public Point3D(Point3D p) {
      this(p.getX(), p.getY(), p.getZ());
   }

   public void add(Point3D p) {
      xyz[0] += p.getX();
      xyz[1] += p.getY();
      xyz[2] += p.getZ();
   }

   public void sub(Point3D p) {
      xyz[0] -= p.getX();
      xyz[1] -= p.getY();
      xyz[2] -= p.getZ();
   }

   public float getX() {
      return xyz[0];
   }
   
   public float getY() {
      return xyz[1];
   }

   public float getZ() {
      return xyz[2];
   }
   
   public void setX(float x) {
      xyz[0] = x;
   }
   
   public void setY(float y) {
      xyz[1] = y;
   }

   public void setZ(float z) {
      xyz[2] = z;
   }

   public void setPoint(Point3D p) {
      this.setX(p.getX());
      this.setY(p.getY());
      this.setZ(p.getZ());
   }
   
   @Override
   public String toString() {
      return "(" + xyz[0] + "," + xyz[1] + "," + xyz[2] + ")";
   }
}