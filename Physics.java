public class Physics {
	private static Point3D center1 = new Point3D(0,0,0);
	private static Point3D center2 = new Point3D(0,0,0);

	public static boolean calculateCollision(TechnoType t1, TechnoType t2) {
		center1.setX(t1.getCoord3DX() + t1.getDimensionW()/2f);
		center1.setY(t1.getCoord3DY() + t1.getDimensionH()/2f);
		center1.setZ(t1.getCoord3DZ() + t1.getDimensionD()/2f);
		center2.setX(t2.getCoord3DX() + t2.getDimensionW()/2f);
		center2.setY(t2.getCoord3DY() + t2.getDimensionH()/2f);
		center2.setZ(t2.getCoord3DZ() + t2.getDimensionD()/2f);
		float holder1 = t1.getDimensionW() + t2.getDimensionW();
		float holder2 = xMath.getDistance3D(center1.getX(), center1.getY(), center1.getZ(), center2.getX(), center2.getY(), center2.getZ());
		holder2 = holder2 - holder1;
		if(holder2 <= 0) {
         return true;
      } else {
         return false;
      }
	}

}