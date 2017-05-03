package com.aman.snippingtool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.*;


public class ScreenCaptureRectangle {

    Rectangle captureRect;
    JFrame  mainFrame;
    ScreenCaptureRectangle(final BufferedImage screen) {
        final BufferedImage screenCopy = new BufferedImage(
                screen.getWidth(),
                screen.getHeight(),
                screen.getType());
        final JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
        JScrollPane screenScroll = new JScrollPane(screenLabel);

        
        mainFrame = new JFrame("Java Swing Examples");
        mainFrame.setSize((int)(screen.getWidth()/1.2),
                (int)(screen.getHeight()/1.2));
        mainFrame.setLayout(new FlowLayout());
      
        screenScroll.setPreferredSize(new Dimension(
                (int)(screen.getWidth()/1.2),
                (int)(screen.getHeight()/1.2)));

        JPanel panel = new JPanel(){
        	 protected void paintComponent(Graphics g) {
        	        super.paintComponent(g);
        	        g.drawLine(0,0, 20, 35);
        	 }
        };
      panel.setLayout(new BorderLayout());
        panel.add(screenScroll, BorderLayout.CENTER);
        JPanel bottomPanel=new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
    
       
        final JLabel selectionLabel = new JLabel(
                "Drag a rectangle in the screen shot!");
        JButton jButton=new JButton("OK");
        bottomPanel.add(selectionLabel);
        bottomPanel.add(jButton);
        
        jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				   Robot robot;
			    	try {
			    		robot = new Robot();
			    		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
						try {
							
							/**
							 * currently we are using jpg format and default location of saved screenshot is D://screenshot
							 */
							
							ImageIO.write(screenFullImage, "jpg", new File("D://screenshot"));
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
					} catch (AWTException e1) {
						
						e1.printStackTrace();
					} 
			}
		});
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
       
        
        mainFrame.add(panel);
        
        mainFrame.setVisible(true); 
        
       
        repaint(screen, screenCopy);
        screenLabel.repaint();
     
        // create an instance of my custom mouse cursor component
        final AlsXYMouseLabelComponent alsXYMouseLabel = new AlsXYMouseLabelComponent();

        // add my component to the DRAG_LAYER of the layered pane (JLayeredPane)
        JLayeredPane layeredPane = mainFrame.getRootPane().getLayeredPane();
        layeredPane.add(alsXYMouseLabel, JLayeredPane.DRAG_LAYER);
         alsXYMouseLabel.setBounds(0, 0, screenLabel.getWidth(), screenLabel.getHeight());

      
        screenLabel.addMouseMotionListener(new MouseMotionAdapter() {

            Point start = new Point();

            @Override
            public void mouseMoved(MouseEvent me) {
            
            	 alsXYMouseLabel.x = (int) start.getX();
                 alsXYMouseLabel.y = (int) start.getY();
                 alsXYMouseLabel.repaint();
            	
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
 


    
}