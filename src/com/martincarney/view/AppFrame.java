package com.martincarney.view;

import javax.swing.JFrame;

/**
 * Main window for the screensaver. Automatically starts itself in full-screen mode.
 * @author Martin Carney 2015
 */
public class AppFrame extends JFrame {

	public AppFrame() {
		super("Brick Build Screen Saver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
	}
	
}
