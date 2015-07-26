package com.martincarney.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

/**
 * Main window for the screensaver. Automatically starts itself in full-screen mode.
 * @author Martin Carney 2015
 */
public class AppFrame extends JFrame {

	private AppPanel appPanel;
	
	private MouseMotionListener mouseMovedListener;
	private KeyListener keyPressedListener;
	
	public AppFrame() {
		super("Brick Build Screen Saver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		
		appPanel = new AppPanel();
		getContentPane().add(appPanel);
		
		// TODO hide cursor
		
		setVisible(true);
	}
	
	public AppPanel getAppPanel() {
		return appPanel;
	}
	
	public void enableExitUponUserInput() {
		if (mouseMovedListener == null) {
			mouseMovedListener = new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					super.mouseMoved(e);
					dispose();
					System.exit(0);
				}
			};
			addMouseMotionListener(mouseMovedListener);
		}
		
		if (keyPressedListener == null) {
			keyPressedListener = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					super.keyPressed(e);
					dispose();
					System.exit(0);
				}
			};
			addKeyListener(keyPressedListener);
		}
	}
	
	public void disableExitUponUserInput() {
		if (mouseMovedListener != null) {
			removeMouseMotionListener(mouseMovedListener);
			mouseMovedListener = null;
		}
		
		if (keyPressedListener != null) {
			removeKeyListener(keyPressedListener);
			keyPressedListener = null;
		}
	}
	
}
