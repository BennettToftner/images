import javax.swing.JFrame;

public class ImageFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -860326443865178967L;

	public ImageFrame()
	{
		setSize(790,455);
		
		ImageComponent i = new ImageComponent();
		i.edges();
		add(i);
	}

}
