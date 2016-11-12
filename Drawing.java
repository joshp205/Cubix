import java.awt.geom.*;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Drawing {
   private int w;
   private int h;
   private Point2D c;
   private Graphics g;
   private Graphics2D g2d;

   private Point2D calc2D;
   private Point3D calc3D;
   private Path2D[] polyRect;
   private Line2D line = new Line2D.Float(0f, 0f, 0f, 0f);
   private Rectangle2D rect = new Rectangle2D.Float(0f, 0f, 0f, 0f);
   
   private Color xBG = new Color(4,2,4);
   private Color xHiCyan = new Color(71,234,253);
   private Color xHiPink = new Color(231,37,83);
   private Color xBlack1 = new Color(0x280327);
   private Color xBlack2 = new Color(0x3F0737);
   
   public enum Facing {
      UP, DOWN, LEFT, RIGHT, FORWARD, BACKWARD
   }

   public Drawing(int w, int h, Graphics g, Graphics2D g2d) {
      this.w = w;
      this.h = h;
      this.g = g;
      this.g2d = g2d;
      c = new Point2D(w/2,h/2);
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
      float scale = cam.getDistance() / (z - cam.getZ());
      float xProj = (x - cam.getX()) * scale;
      float yProj = (y - cam.getY()) * scale;
      
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

   public void drawLine(Color color, float x1, float y1, float x2, float y2) {
      line.setLine(x1, y1, x2, y2);
      g2d.setColor(color);
      g2d.draw(line);
   }

   public void drawPlane(Camera cam, float x, float y, float z, float w, float h, float d, Facing f, Color c, boolean i_wf) {
      polyRect = new Path2D[1];
      polyRect[0] = new Path2D.Double();
      drawRect3D(x, y, z, w, h, d, f, c, i_wf, polyRect[0], cam);     
   }

   public void drawCube(Camera cam, float x, float y, float z, float w, float h, float d, Color c1, Color c2, Color c3, boolean i_wf) {
      polyRect = new Path2D[3];

      for(int i = 0; i < 3; i++) {
         polyRect[i] = new Path2D.Double();
      }
   
      if(y + h < cam.getY()) {
         drawRect3D(x, y, z, w, h, d, Facing.UP, c1, i_wf, polyRect[0], cam);
      } else if(y > cam.getY()){
         drawRect3D(x, y, z, w, h, d, Facing.DOWN, c1, i_wf, polyRect[0], cam);
      }
      
      if(x + w < cam.getX()) {
         drawRect3D(x, y, z, w, h, d, Facing.RIGHT, c2, i_wf, polyRect[1], cam);

      } else if(x > cam.getX()){
         drawRect3D(x, y, z, w, h, d, Facing.LEFT, c2, i_wf, polyRect[1], cam);
      }
      
      if(z > cam.getZ()) {
         drawRect3D(x, y, z, w, h, d, Facing.FORWARD, c3, i_wf, polyRect[2], cam);
      }
   }
   
   public void drawCube(TechnoType p, Camera cam, float w, float h, float d) {
      drawCube(cam, p.getCoord3DX(), p.getCoord3DY(), p.getCoord3DZ(), w, h, d, p.getColor(0), p.getColor(1), p.getColor(2), p.isWireframe());
   }
   
   public void drawRect3D(float x, float y, float z, float w, float h, float d, Facing f, 
                           Color color, boolean wire, Path2D poly, Camera cam) {
      // Camera Clipping              
      if((z-d) < cam.getZ()) {
         if(z < cam.getZ()) {
            return;
         } else {
            d = z - (cam.getZ() + cam.getDistance());
         }
      }
      
      // TODO implement cleaner function
      calc2D = new Point2D(0,0);

      if(f == Facing.UP || f == Facing.DOWN) {
         if(f == Facing.UP) {
            y+=h;
         }

         for(int i = 0; i < 4; i++) {
            switch(i) {
               case 0:  project(x,y,z,cam,calc2D);
                        poly.moveTo(calc2D.getX(),calc2D.getY());  
                        break;
               case 1:  project(x + w,y,z,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 2:  project(x + w,y,z - d,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 3:  project(x,y,z - d,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
            }
         }
         
         poly.closePath();
         g2d.setColor(color);
         if(wire != true) {
            g2d.fill(poly);
         } else {
            g2d.draw(poly);
         }
         
         return;
      }
      
      if(f == Facing.LEFT || f == Facing.RIGHT) {
         if(f == Facing.RIGHT) {
            x+=w;
         }
         for(int i = 0; i < 4; i++) {
            switch(i) {
               case 0:  project(x,y,z,cam,calc2D);
                        poly.moveTo(calc2D.getX(),calc2D.getY());  
                        break;
               case 1:  project(x,y + h,z,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 2:  project(x,y + h,z - d,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 3:  project(x,y,z - d,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
            }
         }
         
         poly.closePath();
         g2d.setColor(color);
         if(wire != true) {
            g2d.fill(poly);
         } else {
            g2d.draw(poly);
         }
         
         return;
      }
      
      if(f == Facing.FORWARD || f == Facing.BACKWARD) {
         if(f == Facing.FORWARD) {
            z-=d;
         }
         for(int i = 0; i < 4; i++) {
            switch(i) {
               case 0:  project(x,y,z,cam,calc2D);
                        poly.moveTo(calc2D.getX(),calc2D.getY());  
                        break;
               case 1:  project(x,y + h,z,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 2:  project(x + w,y + h,z,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
               case 3:  project(x + w,y,z,cam,calc2D);
                        poly.lineTo(calc2D.getX(),calc2D.getY());
                        break;
            }
         }
         
         poly.closePath();
         g2d.setColor(color);
         if(wire != true) {
            g2d.fill(poly);
         } else {
            g2d.draw(poly);
         }
         
         return;
      }
   }
}