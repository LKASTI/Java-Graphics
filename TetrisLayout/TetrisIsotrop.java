import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Cursor;

public class TetrisIsotrop extends Frame{
    public static void main(String[] args){
        new TetrisIsotrop();
    }

    TetrisIsotrop() {
        super("Tetris Isotrop");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
         });
        //sets the window size
        setSize(1000, 1000);
        CvTetrisIsotrop cvTetrisIsotrop = new CvTetrisIsotrop();
        add(cvTetrisIsotrop);


        setVisible(true);
    }

    class CvTetrisIsotrop extends Canvas {
        public boolean mouseInsideMainArea = false;
        private int xCenter, yCenter;
        float pixelSize, rWidth = 50.0F, rHeight = 100.0F;

        CvTetrisIsotrop(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    int quitLeft = iX(rWidth/10);
                    int quitTop = iY((-rHeight/2)*((float) 3/4));
                    int quitRight = iX(rWidth/2);
                    int quitBot = iY(-rHeight/2);
                    float xA = e.getX(), yA = e.getY();

                    if(xA >= quitLeft && xA <= quitRight && yA >= quitTop && yA <= quitBot){
                        System.exit(0);
                    }
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e){
                    int quitLeft = iX(rWidth/10);
                    int quitTop = iY((-rHeight/2)*((float) 3/4));
                    int quitRight = iX(rWidth/2);
                    int quitBot = iY(-rHeight/2);
                    float xA = e.getX(), yA = e.getY();

                    if(xA >= quitLeft && xA <= quitRight && yA >= quitTop && yA <= quitBot){
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                    else{
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }

                    int mainLeft = iX(-rWidth);
                    int mainTop = iY(rHeight/2);
                    int mainRight = iX(0);
                    int mainBot = iY(-rHeight/2);
                    // check if mouse in main area
                    if(xA >= mainLeft && xA <= mainRight && yA >= mainTop && yA <= mainBot){
                        mouseInsideMainArea = true;
                        repaint();
                    }
                    else{
                        mouseInsideMainArea = false;
                        repaint();
                    }
                } 
            });
        }
        
        void initgr(){
            Dimension mainAreaDim = getSize();
            int mainAreaMaxX = mainAreaDim.width - 1, mainAreaMaxY = mainAreaDim.height - 1;
            pixelSize = Math.max(rWidth / mainAreaMaxX, rHeight / mainAreaMaxY);
            xCenter = mainAreaMaxX/2; yCenter = mainAreaMaxY/2;
        }

        // x is the offset from the center
        int iX(float x) {return Math.round(xCenter + x / pixelSize);}
        int iY(float y) {return Math.round(yCenter - y / pixelSize);}
        float fx(int x) {return (x - xCenter) * pixelSize;}
        float fy(int y) {return (yCenter - y) * pixelSize;}

        
        public void paint(Graphics g) {
            initgr();
            
            /* draw the main area */
            int mainLeft = iX(-rWidth);
            int mainTop = iY(rHeight/2);
            int mainRight = iX(0);
            int mainBot = iY(-rHeight/2);
            
            g.drawRect(mainLeft, mainTop, mainRight - mainLeft, mainBot - mainTop);

            /* draw quit box */
            int quitLeft = iX(rWidth/10);
            int quitTop = iY((-rHeight/2)*((float) 3/4));
            int quitRight = iX(rWidth/2);
            int quitBot = iY(-rHeight/2);
            g.drawRect(quitLeft, quitTop, quitRight-quitLeft, quitBot-quitTop);

            // cell
            int cellLen = (mainRight - mainLeft)/8;

            /* draw next shape box and red L shape */
            int nextLeft = quitLeft;
            int nextRight = quitLeft + (cellLen * 5);
            int nextTop = mainTop;
            int nextBot = mainTop + (cellLen * 4);
            g.drawRect(nextLeft, nextTop, nextRight-nextLeft, nextBot-nextTop);
            drawRedL(g, nextLeft, nextRight, nextTop, nextBot, cellLen);

            /* draw quit string (TODO: isotropic font size) */
            Font f = new Font("Serif", Font.BOLD, 20);
            g.setFont(f);
            g.drawString("Quit", (int)(quitRight - ((quitRight-quitLeft)*(0.6F))), (int)(quitBot - ((quitBot-quitTop)*(0.4F))));


            /* draw a 2x2 box component */
            drawBox(g, mainLeft, mainRight, mainTop, mainBot, cellLen);
            
            /* draw zig zag component */
            drawZigZag(g, mainLeft, mainRight, mainTop, mainBot, cellLen);

            /* draw L component */
            drawBlueL(g, mainLeft, mainRight, mainTop, mainBot, cellLen);

            /* draw pause box if cursor in main area */
            f = new Font("Serif", Font.BOLD, 20);
            g.setFont(f);
            if(mouseInsideMainArea){
                int pauseLeft = mainLeft + (cellLen * 2);
                int pauseRight = mainRight - (cellLen * 2);
                int pauseTop = mainTop + (cellLen * 8);
                int pauseBot = mainTop + (cellLen * 10);
                g.drawRect(pauseLeft, pauseTop, pauseRight-pauseLeft, pauseBot-pauseTop);
                /* (TODO: isotropic font size) */
                g.drawString("Pause", (int)(pauseRight - ((pauseRight-pauseLeft)*(0.6F))), (int)(pauseBot - ((pauseBot-pauseTop)*(0.4F))));
            }
        }
        private void drawRedL(Graphics g, int nextLeft, int nextRight, int nextTop, int nextBot, int cellLen){
            g.setColor(Color.RED);
            g.fillRect(nextLeft + (cellLen * 1), nextTop + (cellLen * 2), cellLen, cellLen);
            g.fillRect(nextLeft + (cellLen * 2), nextTop + (cellLen * 2), cellLen, cellLen);
            g.fillRect(nextLeft + (cellLen * 3), nextTop + (cellLen * 2), cellLen, cellLen);
            g.fillRect(nextLeft + (cellLen * 3), nextTop + (cellLen * 1), cellLen, cellLen);
            g.setColor(Color.BLACK);
            g.drawRect(nextLeft + (cellLen * 1), nextTop + (cellLen * 2), cellLen, cellLen);
            g.drawRect(nextLeft + (cellLen * 2), nextTop + (cellLen * 2), cellLen, cellLen);
            g.drawRect(nextLeft + (cellLen * 3), nextTop + (cellLen * 2), cellLen, cellLen);
            g.drawRect(nextLeft + (cellLen * 3), nextTop + (cellLen * 1), cellLen, cellLen);
        }
        private void drawBox(Graphics g, int mainLeft, int mainRight, int mainTop, int mainBot, int cellLen){
            g.setColor(Color.GREEN);
            g.fillRect(mainLeft + (cellLen*3), mainTop + (cellLen * 2), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen*4), mainTop + (cellLen * 2), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen*3), mainTop + (cellLen * 3), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen*4), mainTop + (cellLen * 3), cellLen, cellLen);
            g.setColor(Color.BLACK);
            g.drawRect(mainLeft + (cellLen*3), mainTop + (cellLen * 2), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen*4), mainTop + (cellLen * 2), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen*3), mainTop + (cellLen * 3), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen*4), mainTop + (cellLen * 3), cellLen, cellLen);
        }
        private void drawZigZag(Graphics g, int mainLeft, int mainRight, int mainTop, int mainBot, int cellLen){
            g.setColor(Color.YELLOW);
            g.fillRect(mainLeft + (cellLen * 4), mainBot - (cellLen), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 5), mainBot - (cellLen), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 5), mainBot - (cellLen * 2), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 6), mainBot - (cellLen * 2), cellLen, cellLen);
            g.setColor(Color.BLACK);
            g.drawRect(mainLeft + (cellLen * 4), mainBot - (cellLen), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 5), mainBot - (cellLen), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 5), mainBot - (cellLen * 2), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 6), mainBot - (cellLen * 2), cellLen, cellLen);
        }
        private void drawBlueL(Graphics g, int mainLeft, int mainRight, int mainTop, int mainBot, int cellLen){
            g.setColor(Color.BLUE);
            g.fillRect(mainLeft + (cellLen * 6), mainBot - (cellLen * 1), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 1), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 2), cellLen, cellLen);
            g.fillRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 3), cellLen, cellLen);
            g.setColor(Color.BLACK);
            g.drawRect(mainLeft + (cellLen * 6), mainBot - (cellLen * 1), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 1), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 2), cellLen, cellLen);
            g.drawRect(mainLeft + (cellLen * 7), mainBot - (cellLen * 3), cellLen, cellLen);
        }
     }
}