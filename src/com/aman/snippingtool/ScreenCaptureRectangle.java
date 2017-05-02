package com.aman.snippingtool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;


public class ScreenCaptureRectangle {

    Rectangle captureRect;

    ScreenCaptureRectangle(final BufferedImage screen) {
        final BufferedImage screenCopy = new BufferedImage(
                screen.getWidth(),
                screen.getHeight(),
                screen.getType());
        final JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
        JScrollPane screenScroll = new JScrollPane(screenLabel);

        screenScroll.setPreferredSize(new Dimension(
                (int)(screen.getWidth()/1.2),
                (int)(screen.getHeight()/1.2)));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(screenScroll, BorderLayout.CENTER);

        final JLabel selectionLabel = new JLabel(
                "Drag a rectangle in the screen shot!");
        panel.add(selectionLabel, BorderLayout.SOUTH);

        repaint(screen, screenCopy);
        screenLabel.repaint();

        screenLabel.addMouseMotionListener(new MouseMotionAdapter() {

            Point start = new Point();

            @Override
            public void mouseMoved(MouseEvent me) {
                start = me.getPoint();
                repaint(screen, screenCopy);
                selectionLabel.setText("Start Point: " + start);
                screenLabel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                Point end = me.getPoint();
                captureRect = new Rectangle(start,
                        new Dimension(end.x-start.x, end.y-start.y));
                repaint(screen, screenCopy);
                screenLabel.repaint();
                selectionLabel.setText("Rectangle: " + captureRect);
            }
        });
      
        JOptionPane.showMessageDialog(null, panel);
        Robot robot;
    	try {
    		robot = new Robot();
    		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
			try {
				
				/**
				 * currently we are using jpg format and default location of saved screenshot is D://screenshot
				 */
				
				ImageIO.write(screenFullImage, "jpg", new File("D://screenshot"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
            
        System.out.println("Rectangle of interest: " + captureRect);
        
      
    }
   
    public void repaint(BufferedImage orig, BufferedImage copy) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(orig,0,0, null);
        if (captureRect!=null) {
            g.setColor(Color.RED);
            g.draw(captureRect);
            g.setColor(new Color(255,255,255,150));
            g.fill(captureRect);
        }
        g.dispose();
    }

    public void callSmartScreenCapture(){
    	 Robot robot=null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         final Dimension screenSize = Toolkit.getDefaultToolkit().
                 getScreenSize();
         final BufferedImage screen = robot.createScreenCapture(
                 new Rectangle(screenSize));

         SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 new ScreenCaptureRectangle(screen);
             }
         });
    }
    public static void main(String[] args) throws Exception {
    	
    	
    	new SwingContainerDemo().showJFrameDemo();
    
    	
    	
    	
    /*   */
    }
    
}