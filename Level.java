import java.awt.Color;

public class Level {
   private float w;
   private float h;
   private float d;
   private float distance;
   private float travelDistance;
   private float speed;

   private int score;

   private int layers;

   private Color xBG = new Color(57,14,64);
   private Color xHorizon = new Color(231,37,83);
   private Color[] color;
   private Color[] darkness;

   private int segments;
   private int darkSegments;
   private float segmentSize;
   private Point3D[] segPos;
   private float[] segZGrid;

   public Level(float w, float h, float d, float speed, int segments, int layers) {
      this.w = w;
      this.h = h;
      this.d = d;
      this.distance = 0;
      this.speed = speed;

      this.score = 0;

      this.layers = layers;

      this.segments = segments;
      this.darkSegments = segments/2;
      this.segmentSize = d/(float)segments;
      this.segPos = new Point3D[segments];
      this.segZGrid = new float[segments];

      for(int i = 0; i < segments; i++) {
         segPos[i] = new Point3D(0,0,0);
         segPos[i].setX(-w/2);
         segPos[i].setY(h);
         segPos[i].setZ((float)i * segmentSize);
         segZGrid[i] = segPos[i].getZ();
      }
      this.travelDistance = segmentSize / (segmentSize * speed);

      darkness = new Color[darkSegments];
      color = new Color[segments];
      for(int i = 0; i < color.length; i++) {
         if(i < darkness.length) {
            darkness[i] = new Color(57,14,64, (255/darkness.length)*i);
         }
         color[i] = new Color(166,50,40,(255/segments)*i);
      }
   }

   public void update(Camera cam) {
      travelDistance = segmentSize / (segmentSize * speed);

      for(int i = 0; i < segments; i++) {
         segPos[i].setZ(segPos[i].getZ() - travelDistance);

         if(segPos[i].getZ() <= -segmentSize) {
            segPos[i].setZ(segmentSize * (segments - 1));
         }
      }
   }

   public void render(Drawing dSurface, Camera cam, int layer) {
      // BACKGROUND
      if(layer == 0) {
         for(int i = 0; i < segments; i++) {
            dSurface.drawPlane(cam, getSegmentX(i), getSegmentY(i), getSegmentZ(i), w, h, segmentSize, 0, 0, 0, Drawing.Facing.UP,
                                 xColor.lilShift(color[i].getRed(), color[i].getGreen(), color[i].getBlue(), color[i].getAlpha()), false);
         }
         for(int i = 0; i < darkness.length; i++) {
            dSurface.drawPlane(cam, -w/2, h, segZGrid[segments - (i+1)], w, h, segmentSize, 0, 0, 0, Drawing.Facing.UP,
                                 darkness[darkness.length - (i+1)], false);
         }
         return;
      }

      // FOREGROUND
      if(layer == 2) {
         for(int i = 0; i < darkness.length; i++) {
            dSurface.drawPlane(cam, -w/2, h, segZGrid[segments - (i+1)], w, 150, segmentSize, 0, 0, 0,  Drawing.Facing.BACKWARD,
               darkness[darkness.length - (i+1)], false);
         }
         return;
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
      return travelDistance;
   }

   public float getSpeed() {
      return speed;
   }

   public int getScore() {
      return score;
   }

   public int getLayers() {
      return layers;
   }

   public int getSegments() {
      return segments;
   }

   public int getDarkSegments() {
      return darkSegments;
   }

   public float getSegmentSize() {
      return segmentSize;
   }

   public float getSegmentX(int index) {
      return segPos[index].getX();
   }

   public float getSegmentY(int index) {
      return segPos[index].getY();
   }

   public float getSegmentZ(int index) {
      return segPos[index].getZ();
   }

   public float getSegZGrid(int index) {
      return segZGrid[index];
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
      this.distance = travelDistance;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public void setLayers(int layers) {
      this.layers = layers;
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

   public void setColor(int index, Color c) {
      color[index] = c;
   }

   public void setColor(int index, int r, int g, int b) {
      Color c = new Color(r, g, b);
      setColor(index, c);
   }

}