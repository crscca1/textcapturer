package textcapturer;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
/**
 * 
 * @author Raghuraman Ramaswamy
 *
 */


public class TextCapturer extends JFrame {
	
	private static final int ALPHA = 30;
	private static final int SCALE_UP_FACTOR = 3;

	static
	{
		//System.setProperty("java.library.path", System.getProperty("user.dir") + "\\lib\\win32-x86");
		
	}
	int oldX;
	int oldY;
	int y;
	int x;
	
	private boolean ctrlClicked;
	private boolean ctrlCClicked;
	 Tesseract instance = Tesseract.getInstance();
	
	 private  JButton button;

	public  void addComponentsToPane(Container pane) {
	        pane.setLayout(null);

	        button = new JButton("restart capture");
	       

	        pane.add(button);
	       

	        Insets insets = pane.getInsets();
	        Dimension size = button.getPreferredSize();
	        button.setBounds(0 + insets.left, 0 + insets.top,
	                     size.width, size.height);
	       // button.addActionListener(buttonListener);
	        button.setVisible(false);
	        button.addMouseListener(mouseListener);
	      
	        
	       
	    }
	
private void extraPaint(Graphics g) {
		//else will get dot or line not rectangle
		if(oldX!=x && oldY!=y  && !ctrlClicked)
		{
			 Graphics2D g2 = (Graphics2D) g;
		        g2.setColor(Color.RED);
		        Rectangle lin = getRectangle();
		        g2.draw(lin);
		        
		        
		}
	}

private Rectangle getRectangle() {
	int width=Math.abs(oldX-x);
	int height=Math.abs(oldY-y);
     /* if((x*x+y*y)>(oldX*oldX+oldY*oldY))//is this right?
      {
	   Rectangle lin = new Rectangle(oldX, oldY, width, height);
	    g2.draw(lin);
      }
      else
      {
	   Rectangle lin = new Rectangle(x, y, width, height);
	    g2.draw(lin);
      }*/
	int minX=Math.min(x, oldX);
	int minY=Math.min(y,  oldY);
	Rectangle lin = new Rectangle(minX, minY, width, height);
	return lin;
}

private boolean paintCheck;

    public TextCapturer() {
        super("Text Capturer");

        setBackground(new Color(0,0,0,0));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        //setUndecorated(true);

        panel = new JPanel() {
          @Override
            protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             
             extraPaint(g);
             
             if(ctrlCClicked && !paintCheck)
             {
            	 new TextToClibboard().process();
             }
             paintCheck=true;
            }
        };
        panel.addMouseListener(mouseListener);
        this.addKeyListener(keyListener);
        panel.setBackground(new Color(0,0,0,ALPHA));
       
        setContentPane(panel);
//        setLayout(new GridBagLayout());
//        add(new JButton("I am a Button"));
        setLayout(null);
        addComponentsToPane(getContentPane());

    }

    public static void main(String[] args) {
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge = 
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isPerPixelTranslucencySupported = 
            gd.isWindowTranslucencySupported(PERPIXEL_TRANSLUCENT);

        //If translucent windows aren't supported, exit.
        if (!isPerPixelTranslucencySupported) {
            System.out.println(
                "Per-pixel translucency is not supported");
                System.exit(0);
        }

        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextCapturer gtw = new TextCapturer();

                // Display the window.
                gtw.setVisible(true);
            }
        });
    }
    private KeyListener keyListener= new KeyListener(){

    	@Override
    	public void keyTyped(KeyEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void keyPressed(KeyEvent e) {
    		//int key=(int)e.getKeyChar();
    		int key = e.getKeyCode();
    		boolean controlDown = e.isControlDown();
    		boolean shiftDown = e.isShiftDown();
    	
    		//67 is ctrlc
    		//24 is ctrlx
    		//22 is ctrlv
    		//17 is ctrlq
    		//65535 is up
    		if(controlDown)
    		{
    			if(key=='Q')
        		{
        			System.exit(0);
        		}
        		else if(key=='C')
        		{
        			;
       			 panel.setBackground(new Color(0,0,0,0));
       			 ctrlClicked=true;
       			ctrlCClicked=true;
       			 paintCheck=false;
       			 
         			
         		}
        		else if(key=='X')
        		{
        			
       			 panel.setBackground(new Color(0,0,0,0));
       			 ctrlClicked=true;
       			 paintCheck=false;
       			 
         			new ImageToClibboard().process();
          		}
    		}
    		
    		if(shiftDown)
    		{
    			switch( key ) { 
    	        case KeyEvent.VK_UP:
    	            if(oldY<y)
    	            {
    	            	y--;
    	            }
    	            else if(y<oldY)
    	            {
    	            	oldY--;
    	            }
    	            break;
    	        case KeyEvent.VK_DOWN:
    	        	if(oldY>y)
    	            {
    	            	y++;
    	            }
    	            else if(y>oldY)
    	            {
    	            	oldY++;
    	            }
    	            break;
    	        case KeyEvent.VK_LEFT:
    	        	if(oldX<x)
    	            {
    	            	x--;
    	            }
    	            else if(x<oldX)
    	            {
    	            	oldX--;
    	            }
    	            break;
    	        case KeyEvent.VK_RIGHT :
    	        	if(oldX>x)
    	            {
    	            	x++;
    	            }
    	            else if(x>oldX)
    	            {
    	            	oldX++;
    	            }
    	            break;
    	     }
    			
    		}
    		else
    		{
    			switch( key ) { 
    	        case KeyEvent.VK_UP:
    	            if(oldY<y)
    	            {
    	            	oldY--;
    	            }
    	            else if(y<oldY)
    	            {
    	            	y--;
    	            }
    	            break;
    	        case KeyEvent.VK_DOWN:
    	        	if(oldY>y)
    	            {
    	            	oldY++;
    	            }
    	            else if(y>oldY)
    	            {
    	            	y++;
    	            }
    	            break;
    	        case KeyEvent.VK_LEFT:
    	        	if(oldX<x)
    	            {
    	            	oldX--;
    	            }
    	            else if(x<oldX)
    	            {
    	            	x--;
    	            }
    	            break;
    	        case KeyEvent.VK_RIGHT :
    	        	if(oldX>x)
    	            {
    	            	oldX++;
    	            }
    	            else if(x>oldX)
    	            {
    	            	x++;
    	            }
    	            break;
    	     }
    		}
    		 TextCapturer.this.repaint();
    		    		
    	}

	

    	@Override
    	public void keyReleased(KeyEvent e) {
    		// TODO Auto-generated method stub
    		
    	}};
    
    private MouseListener mouseListener= new MouseListener(){

    	@Override
    	public void mouseClicked(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mousePressed(MouseEvent e) {
    		if(e.getSource()==button)
    		{
    			button.setVisible(false);
    			ctrlClicked=false;
    			ctrlCClicked=false;
    			
    			panel.setBackground(new Color(0,0,0,ALPHA));
    		}
    		else
    		{
    			oldX=x;
        		oldY=y;
        		x=e.getX();
        		y=e.getY();
        		
        		
    		}
    		repaint();
    		
    		
    		
    		
    	}

    	@Override
    	public void mouseReleased(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseEntered(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void mouseExited(MouseEvent e) {
    		// TODO Auto-generated method stub
    		
    	}};
    	
    
    
	private JPanel panel;
	
	abstract class ToClipBoard
	{
		public void process()
		{
			
			 //how to wait for pain
			
			 Robot robot;
			try {
				robot = new Robot();
			} catch (AWTException e2) {
				throw new RuntimeException("unexpected", e2);
			}
			 Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
			 Rectangle lin = getRectangle();
			Point locationOnScreen = panel.getLocationOnScreen();
			Rectangle rect= new Rectangle(locationOnScreen.x+lin.x, locationOnScreen.y+lin.y, lin.width, lin.height);
			
			 
			 BufferedImage sc = robot.createScreenCapture(rect);
			
			 useImage(defaultToolkit, sc);
			
			//setAlwaysOnTop(false);
			//button.addActionListener(buttonListener);
			
			button.setVisible(true);
		}
		
		protected abstract void useImage(Toolkit defaultToolkit, BufferedImage sc) ;

		
		
		protected void saveScreenshot(BufferedImage sc) {
			try {
				ImageIO.write(sc, "png", new File("test.png"));
			} catch (IOException e1) {
				throw new RuntimeException("unexpected", e1);
			}
		}
	}
	
	class TextToClibboard extends ToClipBoard
	{
	

		protected void useImage(Toolkit defaultToolkit, BufferedImage sc) {

			 BufferedImage newImage = new BufferedImage(sc.getWidth()*SCALE_UP_FACTOR, sc.getHeight()*SCALE_UP_FACTOR, BufferedImage.TYPE_BYTE_GRAY);

			 Graphics2D g = newImage.createGraphics();

			 g.setColor(Color.white);
			 g.fillRect(0,0,newImage.getWidth(),newImage.getHeight());
			 g.setComposite(AlphaComposite.Src);
			 g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			 g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			 g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			 g.drawImage(sc, 0, 0, newImage.getWidth(),newImage.getHeight(), null);
			 g.dispose();
			//captureMinMax(newImage);
			
			 saveScreenshot(newImage);
			 String myString = "This text will be copied into clipboard when running this code!";
			 try {
				 myString = instance.doOCR(newImage);
			} catch (TesseractException e1) {
				throw new RuntimeException("unexpected", e1);
			}
			
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clpbrd = defaultToolkit.getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
		}

		private void captureMinMax(BufferedImage newImage) {
			int min=-1,max=-1;
			 for (int y = 0, height=newImage.getHeight(),start=0; y <height ; y++,start=1) {
				    for (int x = 0, width=newImage.getWidth(); x <width ; x++,start=1) {
				          int  clr   = newImage.getRGB(x, y); 
				          int  red   = (clr & 0x00ff0000) >> 16;
				          int  green = (clr & 0x0000ff00) >> 8;
				          int  blue  =  clr & 0x000000ff;
				          if(start==0)
				          {
				        	 min=clr;
				        	 max=clr;
				          }
				          else
				          {
				        	  min=Math.min(min, clr);
				        	  max=Math.max(max, clr);
				          }
				        
				    }
				}
			 System.out.println("min="+min+",max="+max);
		}
	}
	
	class ImageToClibboard extends ToClipBoard
	{
		protected void useImage(Toolkit defaultToolkit, BufferedImage sc) {
			
			
			
			 Transferable trans = new TransferableImage( sc );
			Clipboard clpbrd = defaultToolkit.getSystemClipboard();
			clpbrd.setContents(trans, null);
		}
	}
	
	private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
        throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}