/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4th_dimension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Ben
 */
public class Cell
{
    public int xLocation;
    public int yLocation;
    public int zLocation;
    public int kLocation;
    public int cellWidth;
    int tCounter = 0;
    public Point tesseractLocation;
    
    
    public Cell(int x, int y, int z, int k, Point tesLoc,int width)
    {
        xLocation = x;
        yLocation = y;
        zLocation = z;
        kLocation = k;
        cellWidth = width;
        tesseractLocation = tesLoc;
    }  

    @Override
    public String toString()
    {
        return "Cell @ [" + xLocation + ", " + yLocation + ", " + zLocation + ", " + kLocation + "]";
    }

    public void setxLocation(int xLocation)
    {
        this.xLocation = xLocation;
    }

    public void setyLocation(int yLocation)
    {
        this.yLocation = yLocation;
    }

    public void setzLocation(int zLocation)
    {
        this.zLocation = zLocation;
    }

    public void setkLocation(int kLocation)
    {
        this.kLocation = kLocation;
    }
    
    public void setLocation(int x, int y, int z, int k)
    {
        xLocation = x;
        yLocation = y;
        zLocation = z;
        kLocation = k;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.lightGray);
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
        
        g.drawRect(aX, aY, bX - aX, eY - aY);
        g.drawLine(fX, fY, hX, hY);
        g.drawRect(cX, cY, dX - cX, gY - cY);
        g.drawLine(aX, aY, cX, cY);
        g.drawLine(bX, bY, dX, dY);
        g.drawLine(eX, eY, gX, gY);
        g.setColor(Color.black);
    } 
     
}
