// track how many current points are in canvas
// if 4 points, connect the first three together

import java.util.Vector;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefTriangle extends Frame{
    public static void main(String[] args){
        new DefTriangle();
    }

    DefTriangle() {
        super("DefTriangle");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
         });
        //sets the window size
        setSize(600, 600);
        add("Center", new CvDefTriangle());
        setVisible(true);
    }

    class CvDefTriangle extends Canvas{
        Vector<Point2D> v = new Vector<Point2D>();
        float xCenter, yCenter, pixelSize;
        // Copied from Section 1.5 of
        // Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
        // Chichester: John Wiley.
        class Point2D {
            float x, y;
            Point2D(float x, float y) {this.x = x; this.y = y;}
        }
        float area2(Point2D a, Point2D b, Point2D c) {
            return (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
        }

        CvDefTriangle(){
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                   //currX and currY are the coordinates of the mouses position when pressed
                   float currX = fx(e.getX()), currY = fy(e.getY());
                    
                   // which point is this?
                   // when v size is 0-3, add point to vector
                   int numPoints = v.size();

                   if(numPoints >= 0 && numPoints <= 3){
                    v.addElement(new Point2D(currX, currY));
                   }
                   else{
                    v = new Vector<Point2D>();
                   }

                   repaint();
                
                }
            });
        }

        void initgr() {
            Dimension d = getSize();
            int maxX = d.width - 1, maxY = d.height - 1;
            xCenter = maxX / 2; yCenter = maxY / 2;
            pixelSize = 1;
        }
      
        int iX(float x) {return Math.round(xCenter + x / pixelSize);}
        int iY(float y) {return Math.round(yCenter - y / pixelSize);}
        float fx(int x) {return (x - xCenter) * pixelSize;}
        float fy(int y) {return (yCenter - y) * pixelSize;}

        public void paint(Graphics g){
            initgr();
            g.drawString("Click a 5th time to reset", 20, 20);

            int numPoints = v.size();

            if(numPoints == 0){
                return;
            }

            // draw first point
            Point2D firstPoint = (Point2D) (v.elementAt(0));
            g.drawRect(iX(firstPoint.x), iY(firstPoint.y), 4, 4);
            
            for(int i = 1; i < numPoints; i++){
                // draw point
                Point2D newPoint = (Point2D) (v.elementAt(i));
                if(i == 3){
                    //draw cross 
                    g.drawRect(iX(newPoint.x), iY(newPoint.y), 1, 4);
                    g.drawRect(iX(newPoint.x), iY(newPoint.y+3), 1, 4);
                    g.drawRect(iX(newPoint.x), iY(newPoint.y), 4, 1);
                    g.drawRect(iX(newPoint.x-3), iY(newPoint.y), 4, 1);

                    break;
                }
                g.drawRect(iX(newPoint.x), iY(newPoint.y), 4, 4);

                
        
                // draw edge to all old points from new point
                for(int j = 0; j < i; j++){
                    Point2D oldPoint = (Point2D) (v.elementAt(j));
                    g.drawLine(iX(newPoint.x) + 2, iY(newPoint.y) + 2, iX(oldPoint.x) + 2, iY(oldPoint.y) + 2);
                }
            }

            if(numPoints == 4)
            {
                // determine if point p is outside, inside, or on the triangle
                // a,b,p    b,c,p   c,a,p
                Point2D pointA = v.elementAt(0);
                Point2D pointB = v.elementAt(1);
                Point2D pointC = v.elementAt(2);
                Point2D pointP = v.elementAt(3);
                
                float abArea2 = area2(pointA,pointB,pointP);
                float bcArea2 = area2(pointB,pointC,pointP);
                float caArea2 = area2(pointC,pointA,pointP);

                float toleranceAmt = 700;

                System.out.println(abArea2 + " " + bcArea2 + " " + caArea2);

                if(Math.abs(abArea2) < toleranceAmt || Math.abs(bcArea2) < toleranceAmt || Math.abs(caArea2) < toleranceAmt){
                    //on edge
                    System.out.println("on edge");
                }
                else if (abArea2 >= 0 && bcArea2 >= 0 && caArea2 >= 0){
                    //inside
                    System.out.println("inside");
                }
                else{
                    //outside
                    System.out.println("outside");
                }
            }
        }
    }
}