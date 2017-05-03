package com.aman.snippingtool;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class SwingContainerDemo {
	private JFrame mainFrame;
	// private JLabel headerLabel;
	// private JLabel statusLabel;
	private JPanel controlPanel;
	private JLabel msglabel;

	public SwingContainerDemo() {

		prepareGUI();
	}

	public void prepareGUI() {
		mainFrame = new JFrame("Java Swing Examples");
		mainFrame.setSize(100, 50);
		mainFrame.setLayout(new FlowLayout());

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		msglabel = new JLabel("Welcome to TutorialsPoint SWING Tutorial.", JLabel.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		// mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		// mainFrame.add(statusLabel);

		mainFrame.setUndecorated(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		mainFrame.setVisible(true);

		centreWindow(mainFrame);
	}

	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, 0);
	}

	public void showJFrameDemo() {
		/* headerLabel.setText("Container in action: JFrame"); */
		final JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setLayout(new FlowLayout());
		frame.add(msglabel);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}
		});
		JButton okButton = new JButton("Capture");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				mainFrame.setState(Frame.ICONIFIED);
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new ScreenCaptureRectangle(screen);
					}
				});
				mainFrame.setState(Frame.NORMAL);
			}
		});
		controlPanel.add(okButton);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) throws Exception {

		new SwingContainerDemo().showJFrameDemo();

		/*   */
	}
}