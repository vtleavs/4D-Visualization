/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4th_dimension;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.JFrame;

/**
 *
 * @author Ben
 */
public class MainFrame extends JFrame implements KeyListener, ActionListener, 
        MouseListener, MouseMotionListener
{

    private static Cursor cursorCell;
    private static Cell[][][][] cellTesseract;
    private static int cellWidth = 100;
    private static int tesseractWidth = 10;
    private Timer animationTimer = new Timer(1, this);
    private int animationCounter = 0;
    private Color cursorAnimationColor = new Color(200, 0, 0);
    private Point tesseractLocation;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private static Cursor advCell;
    
    private Button resetButton;
    private Button exitButton;
    private Button randMovButton;
    
    private Image dbImage;
    private Graphics dbGraphics;

    public MainFrame()
    {
        setSize(screenSize);
        setUndecorated(true);
        setVisible(true);
        tesseractLocation = new Point((int)((screenSize.getWidth()*.5) - tesseractWidth*cellWidth*.75), 
                (int)((screenSize.getHeight()*.5) - tesseractWidth*cellWidth*.75));
        
        cellTesseract = generateMatrix();
        cursorCell = new Cursor(4, 9, 4, 4, cellWidth, tesseractLocation,"green");
        advCell = new Cursor(4, 0, 4, 4, cellWidth, tesseractLocation,"blue");
        
        resetButton = new Button(500, 500, 200, 100, "Reset", new Color(10, 200, 200), this);
        exitButton =  new Button(500, 650, 200, 100, "Exit", Color.PINK, this);
        randMovButton = new Button(500, 800, 200, 100, "Random Move", new Color(10, 200, 10), this);
        resetButton.setEnabled(false);
        exitButton.setEnabled(true);
        randMovButton.setEnabled(true);
        
        dbImage = createImage(getWidth(), getWidth());
        dbGraphics = dbImage.getGraphics();
        
        animationTimer.start();
        
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        repaint();
    }

    public Cell[][][][] generateMatrix()
    {
        Cell[][][][] tempTesseract = new Cell[tesseractWidth][tesseractWidth][tesseractWidth][tesseractWidth];
        
        for (int x = 0; x < tesseractWidth; x++)
        {
            for (int y = 0; y < tesseractWidth; y++)
            {
                for (int z = 0; z < tesseractWidth; z++)
                {
                    for (int k = 0; k < tesseractWidth; k++)
                    {
                        tempTesseract[x][y][z][k] = new Cell(x, y, z, k, tesseractLocation,cellWidth);
                    }
                }
            }
        }
        return tempTesseract;
    }

    @Override
    public void paint(Graphics g)
    {
        cursorCell.checkWalls();
        
        if(dbGraphics != null)
        {
            super.paint(dbGraphics);
            dbGraphics.setColor(Color.white);
            dbGraphics.fillRect(0, 0, getWidth(), getHeight());
            dbGraphics.setColor(Color.black);
            
            drawTesseractOutline(dbGraphics);
            drawTesseract(dbGraphics);
            
            
            if(cursorCell.xLocation < advCell.xLocation 
                    && cursorCell.yLocation < advCell.yLocation 
                    && cursorCell.zLocation > advCell.zLocation)
            {
                advCell.paint(dbGraphics);
                cursorCell.paint(dbGraphics);
            }
            else
            {
                cursorCell.paint(dbGraphics);
                advCell.paint(dbGraphics);
            }
            
            drawText(dbGraphics);
            
            resetButton.paint(dbGraphics);
            exitButton.paint(dbGraphics);
            randMovButton.paint(dbGraphics);
            
            g.drawImage(dbImage, 0, 0, this);
        }
    }
    
    public void drawText(Graphics g)
    {
        g.setFont(new Font("TextFont", Font.BOLD, 50));
        g.setColor(Color.BLUE);
        g.drawString("Blue Location", (int)(tesseractWidth*cellWidth + 135 + tesseractLocation.getX()), 
                (int)(50 + tesseractLocation.getY()));
        g.drawString(advCell.xLocation + ", " + advCell.yLocation + ", " 
                + advCell.zLocation + ", " + advCell.kLocation + ", ", 
                (int)(tesseractWidth*cellWidth + 200 + tesseractLocation.getX()), 
                (int)(100 + tesseractLocation.getY()));
        
        g.setColor(Color.GREEN);
        g.drawString("Green Location", (int)(tesseractLocation.getX()-20), 
                (int)(tesseractWidth*cellWidth + 400 + tesseractLocation.getY()));
        g.drawString(cursorCell.xLocation + ", " + cursorCell.yLocation + ", " 
                + cursorCell.zLocation + ", " + cursorCell.kLocation + ", ", 
                (int)(50 + tesseractLocation.getX()), 
                (int)(tesseractWidth*cellWidth + 450 + tesseractLocation.getY()));
    }
    
    public void drawTesseract(Graphics g)
    {
        for (int x = 0; x < tesseractWidth; x++)
        {
            for (int y = 0; y < tesseractWidth; y++)
            {
                for (int z = 0; z < tesseractWidth; z++)
                {
                    for (int k = 0; k < tesseractWidth; k++)
                    {
                        cellTesseract[x][y][z][k].paint(g);
                    }
                }
            }
        }
        
    }
    
    public void drawTesseractOutline(Graphics g)
    {
        int aX = (int)tesseractLocation.getX(); //
        int aY = (int)tesseractLocation.getY(); //
        int bX = (int)(tesseractWidth*cellWidth  + tesseractLocation.getX()); //
        int bY = aY; //
        int cX = (int)(tesseractWidth*.5*cellWidth + tesseractLocation.getX()); //
        int cY = (int)(tesseractWidth*.5*cellWidth + tesseractLocation.getY()); //
        int dX = (int)(tesseractWidth*cellWidth*1.5 + tesseractLocation.getX()); //
        int dY = cY; //
        int eX = aX; //
        int eY = (int)(tesseractWidth*cellWidth + tesseractLocation.getY()); //
        int fX = bX;
        int fY = eY;
        int gX = cX; //
        int gY = (int)(tesseractWidth*cellWidth*1.5 + tesseractLocation.getY()); //
        int hX = dX; //
        int hY = gY; //
        
        int[] xTop = {aX, bX, dX, cX, aX};
        int[] yTop = {aY, bY, dY, cY, aY};
        
        int[] xBottom = {eX, fX, hX, gX, eX};
        int[] yBottom = {eY, fY, hY, gY, eY};
        
        int[] xLeft = {aX, cX, gX, eX, aX};
        int[] yLeft = {aY, cY, gY, eY, aY};
        
        int[] xRight = {bX, dX, hX, fX, bX};
        int[] yRight = {bY, dY, hY, fY, bY};
        
        
        if(cursorCell.leftLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillPolygon(xLeft, yLeft, 5);
        }
        if(cursorCell.rightLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillPolygon(xRight, yRight, 5);
        }
        if(cursorCell.upLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillPolygon(xTop, yTop, 5);
        }
        if(cursorCell.downLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillPolygon(xBottom, yBottom, 5);
        }
        if(cursorCell.backLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillRect(aX, aY, bX - aX, eY - aY);
            g.fillRect(aX + 1, aY + 1, bX - aX, eY - aY);
        }
        if(cursorCell.frontLimit)
        {
            g.setColor(cursorAnimationColor);
            g.fillRect(cX, cY, dX - cX, gY - cY);
            g.fillRect(cX + 1, cY + 1, dX - cX, gY - cY);
        }
        g.setColor(Color.BLACK);
        
        
        g.drawRect(aX, aY, bX - aX, eY - aY);
        g.drawRect(aX + 1, aY + 1, bX - aX, eY - aY);
        
        g.drawRect(cX, cY, dX - cX, gY - cY);
        g.drawRect(cX + 1, cY + 1, dX - cX, gY - cY);
        
        g.drawLine(bX, bY, dX, dY);
        g.drawLine(bX+1, bY, dX+1, dY);
        
        g.drawLine(aX, aY, cX, cY);
        g.drawLine(aX+1, aY, cX+1, cY);
        
        g.drawLine(gX, gY, eX, eY);
        g.drawLine(gX+1, gY, eX+1, eY);
        
        g.drawLine(fX, fY, hX, hY);
        g.drawLine(fX+1, fY, hX+1, hY);
    }

    public static int getTesseractWidth()
    {
        return tesseractWidth;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(animationCounter < 255)
        {
            cursorAnimationColor = new Color(255, animationCounter, animationCounter);
            animationCounter+=40;
            cursorCell.drawArrow(animationCounter);
        }
        else
        {
            animationTimer.stop();
            animationCounter = 200;
            cursorAnimationColor = new Color(255, 255, 255);
        }
        if(animationCounter % 10 == 0)
        {
            repaint();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        resetButton.setEnabled(true);
        
        animationTimer.start();
        
        if(ke.getKeyCode() == 27)
        {
            this.dispose();
        }
        if(ke.getKeyCode() == 65 && cursorCell.xLocation > 0)
        {
            //Left
            cursorCell.setLocation(cursorCell.xLocation-1, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 68 && cursorCell.xLocation < tesseractWidth-1)
        {
            //Right
            cursorCell.setLocation(cursorCell.xLocation+1, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 83 && cursorCell.yLocation < tesseractWidth-1)
        {
            //Down
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation+1, cursorCell.zLocation, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 87 && cursorCell.yLocation > 0)
        {
            //Up
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation-1, cursorCell.zLocation, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 40 && cursorCell.zLocation > 0)
        {
            //Out
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation-1, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 38 && cursorCell.zLocation < tesseractWidth-1)
        {
            //In
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation+1, cursorCell.kLocation);
        }
        if(ke.getKeyCode() == 37 && cursorCell.kLocation > 0)
        {
            //Kata
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation-1);
        }
        if(ke.getKeyCode() == 39 && cursorCell.kLocation < tesseractWidth-1)
        {
            //Ana
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation+1);
        }
        
        moveAdversary();
        
        animationCounter = 100;
        //System.out.println(cursorCell);
        repaint((int)(cursorCell.xLocation*cellWidth-10), (int)(cursorCell.yLocation*cellWidth-10), 
                (int)(cursorCell.xLocation*cellWidth + cellWidth*1.5 + 10), (int)(cursorCell.yLocation*cellWidth + cellWidth*1.5 + 10));
        repaint((int)(advCell.xLocation*cellWidth-10), (int)(advCell.yLocation*cellWidth-10), 
                (int)(advCell.xLocation*cellWidth + cellWidth*1.5 + 10), (int)(advCell.yLocation*cellWidth + cellWidth*1.5 + 10));
    }
    
    public void moveAdversary()
    {
        int rand = (int)(Math.random()*8);
        
        if(rand == 0 && advCell.xLocation > 0)
            advCell.setLocation(advCell.xLocation-1, advCell.yLocation, advCell.zLocation, advCell.kLocation);
        if(rand == 1 && advCell.xLocation < tesseractWidth-1)
            advCell.setLocation(advCell.xLocation+1, advCell.yLocation, advCell.zLocation, advCell.kLocation);
        if(rand == 2 && advCell.yLocation < tesseractWidth-1)
            advCell.setLocation(advCell.xLocation, advCell.yLocation+1, advCell.zLocation, advCell.kLocation);
        if(rand == 3 && advCell.yLocation > 0)
            advCell.setLocation(advCell.xLocation, advCell.yLocation-1, advCell.zLocation, advCell.kLocation);
        if(rand == 4 && advCell.zLocation > 0)
            advCell.setLocation(advCell.xLocation, advCell.yLocation, advCell.zLocation-1, advCell.kLocation);
        if(rand == 5 && advCell.zLocation < tesseractWidth-1)
            advCell.setLocation(advCell.xLocation, advCell.yLocation, advCell.zLocation+1, advCell.kLocation);
        if(rand == 6 && advCell.kLocation > 0)
            advCell.setLocation(advCell.xLocation, advCell.yLocation, advCell.zLocation, advCell.kLocation-1);
        if(rand == 7 && advCell.kLocation < tesseractWidth-1)
            advCell.setLocation(advCell.xLocation, advCell.yLocation, advCell.zLocation, advCell.kLocation+1);
    }
    
    public void randMove()
    {
        int rand = (int)(Math.random()*7);
        
        if(rand == 0 && cursorCell.xLocation > 0)
            cursorCell.setLocation(cursorCell.xLocation-1, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation);
        if(rand == 1 && cursorCell.xLocation < tesseractWidth-1)
            cursorCell.setLocation(cursorCell.xLocation+1, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation);
        if(rand == 2 && cursorCell.yLocation < tesseractWidth-1)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation+1, cursorCell.zLocation, cursorCell.kLocation);
        if(rand == 3 && cursorCell.yLocation > 0)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation-1, cursorCell.zLocation, cursorCell.kLocation);
        if(rand == 4 && cursorCell.zLocation > 0)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation-1, cursorCell.kLocation);
        if(rand == 5 && cursorCell.zLocation < tesseractWidth-1)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation+1, cursorCell.kLocation);
        if(rand == 6 && cursorCell.kLocation > 0)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation-1);
        if(rand == 7 && cursorCell.kLocation < tesseractWidth-1)
            cursorCell.setLocation(cursorCell.xLocation, cursorCell.yLocation, cursorCell.zLocation, cursorCell.kLocation+1);
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        resetButton.mousePressed(me);
        {
            if(resetButton.isClicked())
            {
                cursorCell.setLocation(4, 9, 4, 4);
                advCell.setLocation(4, 0, 4, 4);
                resetButton.setEnabled(false);
            }
        }
        
        randMovButton.mousePressed(me);
        {
            if(randMovButton.isClicked())
            {
                animationTimer.start();
                randMove();
                moveAdversary();
                animationCounter = 100;
            }
        }
        
        exitButton.mousePressed(me);
        
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        resetButton.mouseReleased(me);
        randMovButton.mouseReleased(me);
        
        if(exitButton.isClicked())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.dispose();
        }
        exitButton.mouseReleased(me);
        
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me)
    {
        resetButton.mouseMoved(me);
        exitButton.mouseMoved(me);
        randMovButton.mouseMoved(me);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        
    }

    

}
