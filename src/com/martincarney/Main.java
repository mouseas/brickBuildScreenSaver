package com.martincarney;

import java.awt.EventQueue;

import com.martincarney.view.AppFrame;

/**
 * Entry point for the screensaver. Takes command-line arguments expected for a Windows screensaver,
 * and also works with no arguments.
 * @author Martin Carney 2015
 */
public class Main {

	public static void main(String[] args) {
		// TODO handle screensaver args.
		// With no args, run as normal screensaver view, with a short delay before mouse move causes exit.
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// make the main frame
				new AppFrame();
			}
		});
	}

}
