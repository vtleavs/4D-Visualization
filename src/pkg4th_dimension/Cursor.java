/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4th_dimension;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Ben
 */
public class Cursor extends Cell
{
    //int xLocation = super.xLocation;
    //int yLocation = super.yLocation;
    //int zLocation = super.zLocation;
    //int kLocation = super.kLocation;
    //int cellWidth = super.cellWidth;
    boolean upLimit;
    boolean downLimit;
    boolean leftLimit;
    boolean rightLimit;
    boolean backLimit;
    boolean frontLimit;
    private String color = "gray";
    int currentLocationX;
    int currentLocationY;
    int currentLocationZ;
    int currentLocationK;
    private boolean showArrow;
    Graphics graphics;
    
    
    public Cursor(int x, int y, int z, int k, int width, Point tesLoc,String _colour)
    {
        super(x, y, z, k, tesLoc,width);
        color = _colour;
        
        currentLocationX = x;
        currentLocationY = y;
        currentLocationZ = z;
        currentLocationK = k;
    }
    
    public void checkWalls()
    {
        leftLimit = (xLocation == 0);
        rightLimit = (xLocation == 9);
        upLimit = (yLocation == 0);
        downLimit = (yLocation == 9);
        backLimit = (zLocation == 0);
        frontLimit = (zLocation == 9);
    }
    
    
    @Override
    public void paint(Graphics g)
    {
        graphics = g;
        
        double zTrans = 0.5 * zLocation*cellWidth;
        int aX = (int)(xLocation*cellWidth + zTrans + tesseractLocation.getX());
        int aY = (int)(yLocation*cellWidth + zTrans + tesseractLocation.getY());
        int bX = aX + cellWidth;
        int bY = aY;
        int cX = (int)(aX + .5 * cellWidth);
        int cY = (int)(aY + .5 * cellWidth);
        int dX = cX + cellWidth;
        int dY = cY;
        int eX = aX;
        int eY = aY + cellWidth;
        int fX = bX;
        int fY = eY;
        int gX = cX;
        int gY = cY + cellWidth;
        int hX = dX;
        int hY = gY;
        
        if(color.equals("gray"))
        {
            graphics.setColor(new Color((255/MainFrame.getTesseractWidth())*kLocation,
                (255/MainFrame.getTesseractWidth())*kLocation,(255/MainFrame.getTesseractWidth())*kLocation));
        }
        else if(color.equals("blue"))
        {
            graphics.setColor(new Color((255/MainFrame.getTesseractWidth())*kLocation,
                (255/MainFrame.getTesseractWidth())*kLocation, 255));
        }
        else if(color.equals("red"))
        {
            graphics.setColor(new Color(255,(255/MainFrame.getTesseractWidth())*kLocation,
                    (255/MainFrame.getTesseractWidth())*kLocation));
        }
        else if(color.equals("green"))
        {
            graphics.setColor(new Color((255/MainFrame.getTesseractWidth())*kLocation,255,
                    (255/MainFrame.getTesseractWidth())*kLocation));
        }
        
        graphics.drawRect(aX, aY, bX - aX, eY - aY);
        
        int[] X = {aX,bX,dX,hX,gX,eX,aX};
        int[] Y = {aY,bY,dY,hY,gY,eY,aY};
        graphics.fillPolygon(X, Y, 7);
        
        
        
        
        graphics.drawLine(fX, fY, hX, hY);
        graphics.fillRect(cX, cY, dX - cX, gY - cY);
        
        graphics.setColor(Color.BLACK);
        graphics.drawRect(cX, cY, dX - cX, gY - cY);
        graphics.drawLine(aX, aY, cX, cY);
        graphics.drawLine(bX, bY, dX, dY);
        graphics.drawLine(eX, eY, gX, gY);
        
        
        
        
        
        graphics.setColor(Color.black); 
        
        graphics.setFont(new Font("TEMPFONT", Font.BOLD, 40));
        
        graphics.drawString("" + (kLocation), (int)(cX+(cellWidth*.5))-10, (int)(cY+(cellWidth*.5))+10);
    }
    
    protected void drawArrow(int num)
    {
        //if(num < 255)
            //graphics.setColor(new Color(0, 0, 0, num));
        graphics.drawLine(xLocation*cellWidth, yLocation*cellWidth, currentLocationX, currentLocationY);
        graphics.drawLine(xLocation*cellWidth+1, yLocation*cellWidth+1, currentLocationX+1, currentLocationY+1);
        graphics.drawLine(xLocation*cellWidth+2, yLocation*cellWidth+2, currentLocationX+2, currentLocationY+2);
        graphics.drawLine(xLocation*cellWidth+3, yLocation*cellWidth+3, currentLocationX+3, currentLocationY+3);
        graphics.setColor(Color.black);
        
        currentLocationX = xLocation;
        currentLocationY = yLocation;
        currentLocationZ = zLocation;
        currentLocationK = kLocation;
    }
    
    public void showArrow(boolean arrow)
    {
        showArrow = arrow;
    }
    
}
