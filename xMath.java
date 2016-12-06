public class xMath {

	private static Point3D calc3D = new Point3D(0,0,0);
	private static float[] temp = new float[2];

	public static void rotateX(Point3D p, float theta) {
		p.setZ(p.getZ() * (float)Math.cos(Math.toDegrees(theta)) - p.getY() * (float)Math.sin(Math.toDegrees(theta)));
		p.setY(p.getZ() * (float)Math.sin(Math.toDegrees(theta)) + p.getY() * (float)Math.cos(Math.toDegrees(theta)));
	}

	public static void rotateY(Point3D p, float theta) {
		p.setX(p.getX() * (float)Math.cos(Math.toDegrees(theta)) - p.getZ() * (float)Math.sin(Math.toDegrees(theta)));
		p.setZ(p.getX() * (float)Math.sin(Math.toDegrees(theta)) + p.getZ() * (float)Math.cos(Math.toDegrees(theta)));
	}

	public static void rotateZ(Point3D p, float theta) {
		p.setX(p.getX() * (float)Math.cos(Math.toDegrees(theta)) - p.getY() * (float)Math.sin(Math.toDegrees(theta)));
		p.setY(p.getX() * (float)Math.sin(Math.toDegrees(theta)) + p.getY() * (float)Math.cos(Math.toDegrees(theta)));
	}

	public static Point3D getCenter3D(float x, float y, float z, float w, float h, float d) {
		calc3D.setX(x + w/2f);
		calc3D.setY(y + h/2f);
		calc3D.setZ(z + d/2f);
		return calc3D;
	}

	public static Point3D getCenter3D(Point3D p, Point3D dim) {
		return getCenter3D(p.getX(), p.getY(), p.getZ(), dim.getX(), dim.getY(), dim.getZ());
	}

	public static Point3D getCenter3D(TechnoType t) {
		return getCenter3D(t.getCoords3D(), t.getDimensions());
	}

	public static float getDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
	}

}