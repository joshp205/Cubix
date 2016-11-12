public class Camera extends Point3D{
   private float x;
   private float y;
   private float z;
   private float distance;

	public Camera(float x, float y, float z, float d) {
      super(x,y,z);
      distance = d;
	}
   
   public float getDistance() {
      return distance;
   }
   
   public void setDistance(float d) {
      distance = d;
   }
}