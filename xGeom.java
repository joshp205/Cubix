public class xGeom {
	private static Point3D calc3D = new Point3D(0,0,0);

	public static void traceRect3D(Point3D[][] matrix, int index, Point3D p, Point3D dim, Drawing.Facing f) {
		calc3D.setPoint(p);

		switch(f) {
			case UP:		calc3D.setY(p.getY() + dim.getY());
			case DOWN:		matrix[index][0].setX(calc3D.getX());
							matrix[index][0].setY(calc3D.getY());
							matrix[index][0].setZ(calc3D.getZ());
							matrix[index][1].setX(calc3D.getX() + dim.getX());
							matrix[index][1].setY(calc3D.getY());
							matrix[index][1].setZ(calc3D.getZ());
							matrix[index][2].setX(calc3D.getX() + dim.getX());
							matrix[index][2].setY(calc3D.getY());
							matrix[index][2].setZ(calc3D.getZ() - dim.getZ());
							matrix[index][3].setX(calc3D.getX());
							matrix[index][3].setY(calc3D.getY());
							matrix[index][3].setZ(calc3D.getZ() - dim.getZ());
							break;
			case RIGHT: 	calc3D.setX(p.getX() + dim.getX());
			case LEFT:		matrix[index][0].setX(calc3D.getX());
							matrix[index][0].setY(calc3D.getY());
							matrix[index][0].setZ(calc3D.getZ());
							matrix[index][1].setX(calc3D.getX());
							matrix[index][1].setY(calc3D.getY() + dim.getY());
							matrix[index][1].setZ(calc3D.getZ());
							matrix[index][2].setX(calc3D.getX());
							matrix[index][2].setY(calc3D.getY() + dim.getY());
							matrix[index][2].setZ(calc3D.getZ() - dim.getZ());
							matrix[index][3].setX(calc3D.getX());
							matrix[index][3].setY(calc3D.getY());
							matrix[index][3].setZ(calc3D.getZ() - dim.getZ());
							break;
			case FORWARD: 	calc3D.setZ(p.getZ() - dim.getZ());
			case BACKWARD:	matrix[index][0].setX(calc3D.getX());
							matrix[index][0].setY(calc3D.getY());
							matrix[index][0].setZ(calc3D.getZ());
							matrix[index][1].setX(calc3D.getX());
							matrix[index][1].setY(calc3D.getY() + dim.getY());
							matrix[index][1].setZ(calc3D.getZ());
							matrix[index][2].setX(calc3D.getX() + dim.getX());
							matrix[index][2].setY(calc3D.getY() + dim.getY());
							matrix[index][2].setZ(calc3D.getZ());
							matrix[index][3].setX(calc3D.getX() + dim.getX());
							matrix[index][3].setY(calc3D.getY());
							matrix[index][3].setZ(calc3D.getZ());
							break;
		}
	}
}