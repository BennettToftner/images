import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Pixel extends JComponent{
	
	public int red;
	public int green;
	public int blue;
	
	public int x;
	public int y;
	
	public Pixel(int xLoc, int yLoc, int r, int g, int b)
	{
		x = xLoc;
		y = yLoc;
		red = r;
		green = g;
		blue = b;
	}
	
	public int getAvg()
	{
		return (red + green + blue) / 3;
	}
	
	public void setColor(Pixel p)
	{
		this.red = p.red;
		this.green = p.green;
		this.blue = p.blue;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(red, green, blue));
		g.drawRect(x, y, 1, 1);
	}
	
	
}
