import java.awt.Color;

public class Level {
   private float w;
   private float h;
   private float d;
   private float distance;
   private float travelDistance;
   private float speed;

   private int score;
   private Difficulty difficulty;

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
   private Point3D pZero, calc3D, calc3D2;

   public enum Difficulty {
      EASY (0, 5, 15, 5, 0.25f), MEDIUM (1, 8, 15, 5, 0.25f), HARD (2, 10, 15, 5, 0.25f), EXTREME (3, 15, 15, 5, 0.25f);

      private int id;
      private int enemies, powerups, powerdowns;
      private float speedMod;

      Difficulty(int id, int enemies, int powerups, int powerdowns, float speedMod) {
         this.id = id;
         this.enemies = enemies;
         this.powerups = powerups;
         this.powerdowns = powerdowns;
         this.speedMod = speedMod;
      }
      public int getID() {
         return id;
      }

      public int getEnemies() {
         return enemies;
      }

      public int getPowerups() {
         return powerups;
      }

      public int getPowerdowns() {
         return powerdowns;
      }

      public float getSpeedMod() {
         return speedMod;
      }
   }

   public Level(float w, float h, float d, float speed, Difficulty difficulty, int segments, int layers) {
      this.w = w;
      this.h = h;
      this.d = d;
      this.distance = 0;
      this.speed = speed;

      this.score = 0;
      this.difficulty = difficulty;

      this.layers = layers;

      this.segments = segments;
      this.darkSegments = segments/2;
      this.segmentSize = d/(float)segments;
      this.segPos = new Point3D[segments];
      this.segZGrid = new float[segments];
      calc3D = new Point3D(0,0,0);
      calc3D2 = new Point3D(0,0,0);
      pZero = new Point3D(0,0,0);

      for(int i = 0; i < segments; i++) {
         segPos[i] = new Point3D(-w/2,h,(float)i * segmentSize);
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
      calc3D = new Point3D(w,h,segmentSize);
      calc3D2 = new Point3D(-w/2, h, segmentSize);
      // BACKGROUND
      if(layer == 0) {
         for(int i = 0; i < segments; i++) {
            dSurface.drawPlane(cam, segPos[i], calc3D, pZero, pZero, Drawing.Facing.UP,
                                 xColor.lilShift(color[i].getRed(), color[i].getGreen(), color[i].getBlue(), color[i].getAlpha()), false);
         }
         for(int i = 0; i < darkness.length; i++) {
            calc3D2.setZ(segZGrid[segments - (i+1)]);
            dSurface.drawPlane(cam, calc3D2, calc3D, pZero, pZero, Drawing.Facing.UP, darkness[darkness.length - (i+1)], false);
         }
         return;
      }

      // FOREGROUND
      if(layer == 2) {
         for(int i = 0; i < darkness.length; i++) {
            calc3D2.setZ(segZGrid[segments - (i+1)]);
            calc3D.setY(150);
            dSurface.drawPlane(cam, calc3D2, calc3D, pZero, pZero, Drawing.Facing.BACKWARD, darkness[darkness.length - (i+1)], false);
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