import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tetris extends Frame{
    public static void main(String[] args){
        new Tetris();
    }

    Tetris() {
        super("Tetris");
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
         });
        //sets the window size
        setSize(1920, 1080);

        CvTetris cvtetris = new CvTetris();
        add(cvtetris);
        CvOutsideArea cvoutsidearea = new CvOutsideArea();
        add(cvoutsidearea);


        int mainAreaX= (1920/2) - 500;
        int mainAreaY = 80;
        int mainAreaWidth = 400;
        int mainAreaLength = 800;
        cvtetris.setBounds(mainAreaX, mainAreaY, 400, 800);
        cvoutsidearea.setBounds(mainAreaX + mainAreaWidth + 20, mainAreaY, mainAreaWidth/2, mainAreaLength);

        System.out.println(getSize());

        setVisible(true);
    }

    class CvOutsideArea extends Canvas {
        public void paint(Graphics g){
            /* draw 'next shape' area */
            int mainAreaWidth = 400;
            int mainAreaLength = 800;

            int nextShapeAreaX = 0, nextShapeAreaY = 0;
            int nextShapeAreaWidth = mainAreaWidth/2, nextShapeAreaLength = mainAreaLength/5;

            g.drawRect(nextShapeAreaX, nextShapeAreaY, nextShapeAreaWidth, nextShapeAreaLength);

            int cellWidth = 40;
            int cellLength = 40;

            /* draw next L shape */
            g.setColor(Color.RED);
            g.fillRect(nextShapeAreaX + 40, nextShapeAreaY + 80, cellWidth, cellLength);
            g.fillRect(nextShapeAreaX + 80, nextShapeAreaY + 80, cellWidth, cellLength);
            g.fillRect(nextShapeAreaX + 120, nextShapeAreaY + 80, cellWidth, cellLength);
            g.fillRect(nextShapeAreaX + 120, nextShapeAreaY + 40, cellWidth, cellLength);
            g.setColor(Color.BLACK);
            g.drawRect(nextShapeAreaX + 40, nextShapeAreaY + 80, cellWidth, cellLength);
            g.drawRect(nextShapeAreaX + 80, nextShapeAreaY + 80, cellWidth, cellLength);
            g.drawRect(nextShapeAreaX + 120, nextShapeAreaY + 80, cellWidth, cellLength);
            g.drawRect(nextShapeAreaX + 120, nextShapeAreaY + 40, cellWidth, cellLength);

            /* draw levels, lines, score text */
            String levels = "Levels:    1";
            String lines = "Lines:  0";
            String score = "Score:  0";
            Font f = new Font("Serif", Font.BOLD, 30);
            g.setFont(f);
            g.drawString(levels, nextShapeAreaX, nextShapeAreaY + nextShapeAreaLength + 120);
            g.drawString(lines, nextShapeAreaX, nextShapeAreaY  + nextShapeAreaLength + 200);
            g.drawString(score, nextShapeAreaX, nextShapeAreaY  + nextShapeAreaLength + 280);

            /* draw 'quit' box */
            int quitAreaX = 0, quitAreaY = mainAreaLength - 80;
            int quitAreaWidth = nextShapeAreaWidth, quitAreaLength = nextShapeAreaLength/2;
            g.drawRect(quitAreaX, quitAreaY, quitAreaWidth, quitAreaLength);
            g.drawString("Quit", quitAreaX + 70, quitAreaY + 50);
        }
    }

    class CvTetris extends Canvas {
        public boolean mouseInsideMainArea = false;

        CvTetris(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e){
                    mouseInsideMainArea = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e){
                    mouseInsideMainArea = false;
                    repaint();
                }
            });
        }
        

        public void paint(Graphics g) {
            //gets the width and length of canvas

            int mainAreaWidth = 400;
            int mainAreaLength = 800;
            
            /* draw the main area */
            int mainAreaX = 0;
            int mainAreaY = 0;
            g.drawRect(mainAreaX, mainAreaY, mainAreaWidth, mainAreaLength);

            /* width and length of one cell */
            int cellWidth = 40;
            int cellLength = 40;

            /* draw a 2x2 box component */
            g.setColor(Color.GREEN);
            g.fillRect(160 + mainAreaX, 60 + mainAreaY, cellWidth, cellLength);
            g.fillRect(200 + mainAreaX, 60 + mainAreaY, cellWidth, cellLength);
            g.fillRect(160 + mainAreaX, 100 + mainAreaY, cellWidth, cellLength);
            g.fillRect(200 + mainAreaX, 100 + mainAreaY, cellWidth, cellLength);
            g.setColor(Color.BLACK);
            g.drawRect(160 + mainAreaX, 60 + mainAreaY, cellWidth, cellLength);
            g.drawRect(200 + mainAreaX, 60 + mainAreaY, cellWidth, cellLength);
            g.drawRect(160 + mainAreaX, 100 + mainAreaY, cellWidth, cellLength);
            g.drawRect(200 + mainAreaX, 100 + mainAreaY, cellWidth, cellLength);

            /* draw zig zag component */
            // x starts at 220  
            g.setColor(Color.YELLOW);
            g.fillRect(mainAreaX + 240, mainAreaY + 760, cellWidth, cellLength);    
            g.fillRect(mainAreaX + 280, mainAreaY + 760, cellWidth, cellLength);    
            g.fillRect(mainAreaX + 280, mainAreaY + 720, cellWidth, cellLength);    
            g.fillRect(mainAreaX + 320, mainAreaY + 720, cellWidth, cellLength);    
            g.setColor(Color.BLACK);   
            g.drawRect(mainAreaX + 240, mainAreaY + 760, cellWidth, cellLength);    
            g.drawRect(mainAreaX + 280, mainAreaY + 760, cellWidth, cellLength);    
            g.drawRect(mainAreaX + 280, mainAreaY + 720, cellWidth, cellLength);    
            g.drawRect(mainAreaX + 320, mainAreaY + 720, cellWidth, cellLength);    

            /* draw L component */
            g.setColor(Color.BLUE);
            g.fillRect(mainAreaX + 320, mainAreaY + 760, cellWidth, cellLength);
            g.fillRect(mainAreaX + 360, mainAreaY + 760, cellWidth, cellLength);
            g.fillRect(mainAreaX + 360, mainAreaY + 720, cellWidth, cellLength);
            g.fillRect(mainAreaX + 360, mainAreaY + 680, cellWidth, cellLength);
            g.setColor(Color.black);
            g.drawRect(mainAreaX + 320, mainAreaY + 760, cellWidth, cellLength);
            g.drawRect(mainAreaX + 360, mainAreaY + 760, cellWidth, cellLength);
            g.drawRect(mainAreaX + 360, mainAreaY + 720, cellWidth, cellLength);
            g.drawRect(mainAreaX + 360, mainAreaY + 680, cellWidth, cellLength);

            /* draw pause box if cursor in main area */
            Font f = new Font("Serif", Font.BOLD, 30);
            g.setFont(f);
            if (mouseInsideMainArea){
                int pauseAreaX = mainAreaX + 100, pauseAreaY = mainAreaY+mainAreaLength-200;
                g.drawRect(pauseAreaX, pauseAreaY, 400/2, 80);
                g.drawString("Pause", pauseAreaX + 60, pauseAreaY + 50);
            }
        }
     }
}