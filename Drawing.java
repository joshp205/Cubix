import java.awt.geom.*;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

public class Drawing {
   private int w;
   private int h;
   private Point2D c;
   private Graphics g;
   private Graphics2D g2d;

   private float scale;
   private float xProj;
   private float yProj;

   private Point2D calc2D;
   private Point2D calc2D2;
   private Point3D calc3D;
   private Point3D[][] rectMatrix;
   private Path2D[] polyRect;
   private Line2D line;
   private Rectangle2D rect;
   private Facing f;
   private float[] zDepth;
   
   private Color xBG = new Color(57,14,64);
   
   public enum Facing {
      UP (0), DOWN (1), RIGHT (2), LEFT (3), FORWARD (4), BACKWARD (5);

      private int id;

      Facing(int id) {
         this.id = id;
      }

      public static Facing getFacing(int index) {
         for(Facing f : Facing.values()) {
            if(f.getID() == index) {
               return f;
            }
         }
         return Facing.UP;
      }

      public int getID() {
         return id;
      }
   }

   public Drawing(int w, int h, Graphics g, Graphics2D g2d) {
      this.w = w;
      this.h = h;
      this.g = g;
      this.g2d = g2d;
      c = new Point2D(w/2,h/2);

      calc2D = new Point2D(0,0);
      calc2D2 = new Point2D(0,0);
      calc3D = new Point3D(0,0,0);

      zDepth = new float[6];

      rectMatrix = new Point3D[6][4];
      for(int i = 0; i < rectMatrix.length; i++) {
         for(int j = 0; j < rectMatrix[i].length; j++) {
            rectMatrix[i][j] = new Point3D(0,0,0);
         }
      }

      polyRect = new Path2D[6];
      for(int i = 0; i < polyRect.length; i++) {
         polyRect[i] = new Path2D.Double();
      }

      line = new Line2D.Float(0f, 0f, 0f, 0f);
      rect = new Rectangle2D.Float(0f, 0f, 0f, 0f);
   }

   public float getScreenWidth() {
      return w;
   }

   public float getScreenHeight() {
      return h;
   }

   public float getScreenCenterX() {
      return c.getX();
   }

   public float getScreenCenterY() {
      return c.getY();
   }

   public void dispose() {
      g.dispose();
      g2d.dispose();
   }
   
   public void clearScreen() {
      g.setColor(xBG);
      g.fillRect(0,0,w,h);
   }

   public Point2D project(float x, float y, float z, Camera cam, Point2D screen) {
      scale = cam.getDistance() / (z - cam.getZ());
      xProj = (x - cam.getX()) * scale;
      yProj = (y - cam.getY()) * scale;
      
      screen.setX(c.getX() + c.getX()*xProj);
      screen.setY(c.getY() - c.getY()*yProj);
      
      return screen;
   }

   public Point2D project(Point3D p, Camera cam, Point2D screen) {
      return project(p.getX(), p.getY(), p.getZ(), cam, screen);
   }

   public void drawText(String text, Color color, int x, int y) {
      g.setColor(color);
      g.drawString(text, x, y);
   }

   public void drawText(String text, Color color, float x, float y) {
      g2d.setColor(color);
      g2d.drawString(text, x, y);
   }

   public void drawLine2D(Color color, float x1, float y1, float x2, float y2) {
      line.setLine(x1, y1, x2, y2);
      g2d.setColor(color);
      g2d.draw(line);
   }

   public void drawLine3D(Camera cam, Color color, float x1, float y1, float z1, float x2, float y2, float z2) {
      project(x1,y1,z1,cam,calc2D);
      project(x2,y2,z2,cam,calc2D2);
      line.setLine(calc2D.getX(), calc2D.getY(), calc2D2.getX(), calc2D2.getY());
      g2d.setColor(color);
      g2d.draw(line);
   }

   public void drawLine3D(Camera cam, Color color, Point3D p1, Point3D p2) {
      drawLine3D(cam, color, p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ());
   }

   public void drawPlane(Camera cam, Point3D p, Point3D dim, Point3D rot, Point3D piv, Facing f, Color c, boolean i_wf) {
      // Camera Clipping 
      calc3D.setPoint(dim);             
      if((p.getZ()-dim.getZ()) < cam.getZ()) {
         if(p.getZ() < cam.getZ()) {
            return;
         } else {
            calc3D.setZ(p.getZ() - (cam.getZ() + cam.getDistance()));
         }
      }

      polyRect[0].reset();
      xGeom.traceRect3D(rectMatrix, 0, p, calc3D, f);
      drawPoly3D(rectMatrix[0], polyRect[0], c, i_wf, cam);
   }

   public void drawCube(Camera cam, Point3D p, Point3D dim, Point3D rot, Point3D piv, Color[] color, boolean i_wf) {
      // Camera Clipping       
      calc3D.setPoint(dim);        
      if((p.getZ()-dim.getZ()) < cam.getZ()) {
         if(p.getZ() < cam.getZ()) {
            return;
         } else {
            calc3D.setZ(p.getZ() - (cam.getZ() + cam.getDistance()));
         }
      }

      // Flush poly path
      for(int i = 0; i < polyRect.length; i++) {
         polyRect[i].reset();
      }

      // Generate coordinates, rotate, and draw
      for(int i = 0; i < rectMatrix.length; i++) {
         f = Facing.getFacing(i);
         xGeom.traceRect3D(rectMatrix, i, p, calc3D, f);
         if(rot.getX() != 0 || rot.getY() != 0 || rot.getZ() != 0) {
            for(int j = 0; j < rectMatrix[i].length; j++) {
               rectMatrix[i][j].sub(piv);
               rectMatrix[i][j].setPoint(xMath.rotateX(rectMatrix[i][j], rot.getX()));
               rectMatrix[i][j].setPoint(xMath.rotateY(rectMatrix[i][j], rot.getY()));
               rectMatrix[i][j].setPoint(xMath.rotateZ(rectMatrix[i][j], rot.getZ()));
               rectMatrix[i][j].add(piv);
            }
         }
         drawPoly3D(rectMatrix[i], polyRect[i], color[i], i_wf, cam);
      }
   }
   
   public void drawCube(TechnoType p, Camera cam) {
      drawCube(cam, p.getCoords3D(), p.getDimensions(), p.getRotation(), p.getCenter(), p.getColors(), p.isWireframe());
   }

   public void drawRect2D(Color c, int x, int y, int w, int h) {
      g.setColor(c);
      g.fillRect(x, y, w, h);
   }

   public void drawPoly3D(Point3D[] p, Path2D poly, Color color, boolean wire, Camera cam) {
      for(int i = 0; i < p.length; i++) {
         project(p[i], cam, calc2D);
         if(i == 0) {
            poly.moveTo(calc2D.getX(),calc2D.getY());
         } else {
            poly.lineTo(calc2D.getX(),calc2D.getY());
         }
      }

      poly.closePath();
      g2d.setColor(color);
      if(wire) {
         g2d.draw(poly);
      } else {
         g2d.fill(poly);
      }

   }
}