import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ImageComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6322168028761924498L;
	private BufferedImage img;
	private Pixel[][] grid;
	
	public ImageComponent()
	{
		img = null;
		try
		{
			img = ImageIO.read(new File("/Users/21toftnerb/images/colors.png"));
		}
		catch(Exception e)
		{
			System.out.println("Image not found.");
		}
		//img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		grid = new Pixel[img.getHeight()][img.getWidth()];
		for (int r = 0; r < img.getHeight(); r++)
		{
			for (int c = 0; c < img.getWidth(); c++)
			{
				int clr = img.getRGB(c,r); 
				int  red = (clr & 0x00ff0000) >> 16;
			  	int  green = (clr & 0x0000ff00) >> 8;
			  	int  blue = clr & 0x000000ff;
				grid[r][c] = new Pixel(c, r, red, green, blue);
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[r].length; c++)
			{
				grid[r][c].paintComponent(g);
			}
		}
	}
	
	public void edges()
	{
		Pixel[][] newGrid = new Pixel[grid.length][grid[0].length];
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[r].length; c++)
			{
				newGrid[r][c] = grid[r][c];
			}
		}
		int tolerance = 128;
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 1; c < grid[r].length; c++)
			{
				if (Math.abs(newGrid[r][c - 1].getAvg() - newGrid[r][c].getAvg()) > tolerance)
				{
					grid[r][c].red = 255;
					grid[r][c].green = 255;
					grid[r][c].blue = 255;
				}
				else
				{
					grid[r][c].red = 0;
					grid[r][c].green = 0;
					grid[r][c].blue = 0;
				}
			}
		}
	}
	
	public void noRed()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.red = 0;
			}
		}
	}
	
	public void noGreen()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.green = 0;
			}
		}
	}
	
	public void noBlue()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.blue = 0;
			}
		}
	}
	
	public void blur()
	{
		//make new grid and take averages from surrounding pixels
	}
	
	//from 0 to 100
	public void randomNoise(int strength)
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				int rand = (int)(Math.random() * 100) + 1;
				if (rand <= strength)
				{
					p.red = (int)(Math.random() * 256);
					p.green = (int)(Math.random() * 256);
					p.blue = (int)(Math.random() * 256);
				}
			}
		}
	}
	
	//Tolerance goes from 0 to 256
	public void blackWhite(int tolerance)
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				int avg = (int)((p.red + p.green + p.blue) / 3);
				if (avg < tolerance)
				{
					p.red = 0;
					p.green = 0;
					p.blue = 0;
				}
				else
				{
					p.red = 255;
					p.green = 255;
					p.blue = 255;
				}
			}
		}
	}
	
	public void grayscale()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				int avg = (int)((p.red + p.green + p.blue) / 3);
				p.red = avg;
				p.green = avg;
				p.blue = avg;
			}
		}
	}
	
	public void bluescale()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.red = 0;
				p.green = 0;
			}
		}
	}
	
	public void yellowscale()
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.blue = 0;
				int avg = (int)((p.red + p.green) / 2);
				p.red = avg;
				p.green = avg;
			}
		}
	}
	
	public void lateralBlur(int tolerance)
	{
		for (Pixel[] pArr: grid)
		{
			for (int i = 1; i < pArr.length - 1; i++)
			{
				int beforeAvg = pArr[i - 1].getAvg();
				int afterAvg = pArr[i + 1].getAvg();
				if (Math.abs(afterAvg - beforeAvg) > tolerance)
				{
					pArr[i].red = (pArr[i - 1].red + pArr[i + 1].red) / 2;
					pArr[i].green = (pArr[i - 1].green + pArr[i + 1].green) / 2;
					pArr[i].blue = (pArr[i - 1].blue + pArr[i + 1].blue) / 2;
				}
			}
		}
	}
	
	public void gradient()
	{
		this.fade(1);
		for (int x = 0; x < grid[0].length; x++)
		{
			double upper = Math.PI;
			double random = Math.random() * upper;
			double result = Math.sin(random);
			int y = (int)((result * grid.length));
			grid[y][x].red = 0;
			grid[y][x].green = 0;
			grid[y][x].blue = 0;
		}
	}
	
	//0 to 1, with 1 fading completely to white
	public void fade(double factor)
	{
		for (Pixel[] pArr: grid)
		{
			for (Pixel p: pArr)
			{
				p.red = (p.red + (int)((double)(255 - p.red) * factor));
				p.green = (p.green + (int)((double)(255 - p.green) * factor));
				p.blue = (p.blue + (int)((double)(255 - p.blue) * factor));
			}
		}
	}
	
	public void reflectLeft()
	{
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[r].length / 2; c++)
			{
				grid[r][grid[r].length - c - 1].setColor(grid[r][c]);
			}
		}
	}
	
	public void reflectRight()
	{
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[r].length / 2; c++)
			{
				grid[r][c].setColor(grid[r][grid[r].length - c - 1]);
			}
		}
	}
	
	public void reflectTop()
	{
		for (int r = 0; r < grid.length / 2; r++)
		{
			for (int c = 0; c < grid[r].length; c++)
			{
				grid[grid.length - r - 1][c].setColor(grid[r][c]);
			}
		}
	}
	
	public void reflectBottom()
	{
		for (int r = 0; r < grid.length / 2; r++)
		{
			for (int c = 0; c < grid[r].length; c++)
			{
				grid[r][c].setColor(grid[grid.length - r - 1][c]);
			}
		}
	}

}
