package pkg4th_dimension;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *
 * @author Ben
 */
public class Button
{
    private boolean clicked;
    private boolean cursorOverButton;
    
    private boolean enabled = true;
    private String text;
    private Dimension size;
    private Point location;
    private Color currentColor;
    private Color originalColor;
    private Frame frame;
    
    
    public Button(int x, int y, int w, int h, String title, Color color_, Frame f)
    {
        location = new Point(x, y);
        size = new Dimension(w, h);
        text = title;
        originalColor = color_;
        currentColor = originalColor;
        frame = f;
    }
    
    public Button(int x, int y, int w, int h, String title, Frame f)
    {
        location = new Point(x, y);
        size = new Dimension(w, h);
        text = title;
        originalColor = Color.LIGHT_GRAY;
        currentColor = originalColor;
        frame = f;
        //clickedChecker.start();
        //System.out.println(clickedChecker.isAlive());
    }
    
    public void repaint()
    {
        frame.repaint((int)location.getX()-2, (int)location.getY()-2, 
                (int)size.getWidth()+4, (int)size.getHeight()+4);
    }
    
    public void paint(Graphics g)
    {
        g.setFont(new Font("Button", Font.PLAIN, 24));
        
        if(cursorOverButton)
        {
            
            if(originalColor.getRed()+10 <= 255 && originalColor.getGreen()+10 <= 255 && originalColor.getBlue()+10 <= 255)
            {
                //HISystem.out.println("Changing Hover Colour");
                currentColor = new Color(originalColor.getRed()+10, originalColor.getGreen()+10, originalColor.getBlue()+10);
            }
            else currentColor = originalColor;
        }
        else
        {
            currentColor = new Color(originalColor.getRed()-10, originalColor.getGreen()-10, originalColor.getBlue()-10);
        }
        
        if(enabled)
        {
            if(!clicked)
                paintEnabled(g);
            else if(clicked)
                paintClicked(g);
        }
        else paintDisabled(g);
        
        g.setColor(Color.BLACK);
    }
    
    protected void paintEnabled(Graphics g)
    {
        if(originalColor.getRed()+50 <= 255 && originalColor.getGreen()+50 <= 255 && originalColor.getBlue()+50 <= 255)
            g.setColor(new Color(originalColor.getRed()+50, originalColor.getGreen()+50, originalColor.getBlue()+50));
        else
            g.setColor(currentColor);
        
        g.fillRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);
        g.setColor(Color.BLACK);
        g.drawRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);
        g.setColor(currentColor);
        g.fillRoundRect((int)location.getX(), (int)location.getY(), (int)size.getWidth(), (int)size.getHeight(), 60, 60);
        g.setColor(Color.BLACK);
        g.drawRoundRect((int)location.getX(), (int)location.getY(), (int)size.getWidth(), (int)size.getHeight(), 60, 60);

        g.drawString(text, (int)(location.getX() + size.getWidth()*.5 - g.getFontMetrics().stringWidth(text)*.5), 
            (int)(location.getY() + size.getHeight()*.5 + 5));
    }
    
    protected void paintDisabled(Graphics g)
    {
        g.setColor(currentColor);
        g.fillRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);
        g.setColor(Color.BLACK);
        g.drawRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);

        g.drawString(text, (int)(location.getX() + size.getWidth()*.5 - g.getFontMetrics().stringWidth(text)*.5) - 5, 
            (int)(location.getY() + size.getHeight()*.5 + 5)-5);
    }
    
    protected void paintClicked(Graphics g)
    {
        g.setColor(currentColor);
        g.fillRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);
        g.setColor(Color.BLACK);
        g.drawRoundRect((int)location.getX()-5, (int)location.getY()-5, (int)size.getWidth(), (int)size.getHeight(), 60, 60);

        g.drawString(text, (int)(location.getX() + size.getWidth()*.5 - g.getFontMetrics().stringWidth(text)*.5) - 5, 
            (int)(location.getY() + size.getHeight()*.5 + 5)-5);
    }
    
    public void mouseClicked(MouseEvent me)
    {
    }

    public void mousePressed(MouseEvent me)
    {
        if(enabled)
        {
            setClicked(checkCursorLoc());
            repaint();
        }
    }
    
    public boolean checkCursorLoc()
    {
        if(frame.getMousePosition().getX() > location.getX() 
                && frame.getMousePosition().getX() < location.getX() + size.getWidth()
                && frame.getMousePosition().getY() > location.getY() 
                && frame.getMousePosition().getY() < location.getY() + size.getHeight())
        {
            return true;
        }
        return false;
        
    }

    public void mouseReleased(MouseEvent me)
    {
        if(enabled)
        {
            clicked = false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent me)
    {
        if(enabled)
        { 
        }
    }

    public void mouseExited(MouseEvent me)
    {
        if(enabled)
        { 
        }
    }
    
    public void mouseDragged(MouseEvent me)
    {
        if(enabled)
        { 
        }
    }

    public void mouseMoved(MouseEvent me)
    {
        if(enabled)
        { 
            cursorOverButton = checkCursorLoc();
        }
        repaint();
    }
    
    

    public void setClicked(boolean clicked)
    {
        this.clicked = clicked;
    }

    public void setEnabled(boolean enabled)
    {
        if(!enabled)
        {
            setClicked(false);
        }
        this.enabled = enabled;
    }

    public void setLocation(Point location)
    {
        this.location = location;
    }

    public void setSize(Dimension size)
    {
        this.size = size;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Point getLocation()
    {
        return location;
    }

    public Dimension getSize()
    {
        return size;
    }

    public String getText()
    {
        return text;
    }

    boolean isClicked()
    {
        return clicked;
    }

    boolean isEnabled()
    {
        return enabled;
    }

    public boolean isCursorOverButton()
    {
        return cursorOverButton;
    }
}
