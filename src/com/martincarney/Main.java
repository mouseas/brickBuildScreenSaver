package com.martincarney;

import java.awt.EventQueue;

import com.martincarney.view.AppFrame;


public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// make the main frame
				AppFrame mainFrame = new AppFrame();
			}
		});
	}

}
