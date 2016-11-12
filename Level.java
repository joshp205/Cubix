import java.awt.Color;

public class Level {
   private float w;
   private float h;
   private float d;
   private float distance;
   private float speed;

   private Color xHiPink = new Color(231,37,83);
   private Color xBlack1 = new Color(255,0,0);
   private Color xBlack2 = new Color(0,0,255);

   private int segments;
   private float segmentSize;
   private Point3D[] segPos;
   private float segmentBackPosition;

   public Level(float w, float h, float d, float speed, int segments) {
      this.w = w;
      this.h = h;
      this.d = d;
      this.distance = 0;
      this.speed = speed;

      this.segments = segments;
      this.segmentSize = d/(float)segments;
      this.segPos = new Point3D[segments];

      for(int i = 0; i < segments; i++) {
         segPos[i] = new Point3D(0,0,0);
         segPos[i].setX(-w/2);
         segPos[i].setY(h);
         segPos[i].setZ((float)i * segmentSize);
         if(i + 1 == segments) {
            segmentBackPosition = segPos[i].getZ();
         }
      }
   }

   public void update(Camera cam) {
      for(int i = 0; i < segments; i++) {
         segPos[i].setZ(segPos[i].getZ() - segmentSize/speed);

         if(segPos[i].getZ() <= 0) {
            segPos[i].setZ(segmentBackPosition);
         }
      }
   }

   public void render(Drawing dSurface, Camera cam) {
      dSurface.drawLine(xHiPink, 0f, dSurface.getScreenCenterY(), dSurface.getScreenWidth(), dSurface.getScreenCenterY());

      Color c = xColor.rainbowShift(xBlack1.getRed(), xBlack1.getGreen(), xBlack1.getBlue());
      Color d = xColor.rainbowShift(xBlack2.getRed(), xBlack2.getGreen(), xBlack2.getBlue());
      xBlack1 = c;
      xBlack2 = d;

      for(int i = 0; i < segments; i++) {
         if(i%2 == 0) {
            dSurface.drawPlane(cam, getSegmentX(i), getSegmentY(i), getSegmentZ(i), w, h, segmentSize, Drawing.Facing.UP, xBlack1, false);
         } else {
            dSurface.drawPlane(cam, getSegmentX(i), getSegmentY(i), getSegmentZ(i), w, h, segmentSize, Drawing.Facing.UP, xBlack2, false);
         }
      }  
   }

   public float getWidth() {
      return w;
   }

   public float getHeight() {
      return h;
   }

   public float getDepth() {
      return d;
   }

   public float getTravelDistance() {
      return distance;
   }

   public float getSpeed() {
      return speed;
   }

   public int getSegments() {
      return segments;
   }

   public float getSegmentSize() {
      return segmentSize;
   }

   public float getSegmentX(int i) {
      return segPos[i].getX();
   }

   public float getSegmentY(int i) {
      return segPos[i].getY();
   }

   public float getSegmentZ(int i) {
      return segPos[i].getZ();
   }

   public void setWidth(float w) {
      this.w = w;
   }

   public void setHeight(float h) {
      this.h = h;
   }

   public void setDepth(float d) {
      this.d = d;
   }

   public void setTravelDistance(float distance) {
      this.distance = distance;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public void setSegments(int segments) {
      this.segments = segments;
   }

   public void setSegmentX(int i, float x) {
      this.segPos[i].setX(x);
   }

   public void setSegmentY(int i, float y) {
      this.segPos[i].setY(y);
   }

   public void setSegmentZ(int i, float z) {
      this.segPos[i].setZ(z);
   }

}