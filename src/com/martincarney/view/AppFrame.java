package com.martincarney.view;

import javax.swing.JFrame;


public class AppFrame extends JFrame {

	public AppFrame() {
		super("Brick Build Screen Saver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
	}
	
}
