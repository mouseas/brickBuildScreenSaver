package com.martincarney;

import java.awt.EventQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.martincarney.view.AppFrame;

/**
 * Entry point for the screensaver. Takes command-line arguments expected for a Windows screensaver,
 * and also works with no arguments.
 * @author Martin Carney 2015
 */
public class Main {
	
	/**
	 * The number of milliseconds after the screensaver starts before user input will cause the screensaver
	 * to exit.
	 */
	public static final int USER_INPUT_EXIT_DELAY = 1500;
	
	private static AppFrame appFrame;
	
	public static void main(String[] args) {
		// TODO handle screensaver args.
		// With no args, run as normal screensaver view, with a short delay before user input causes exit.
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// make the main frame
				appFrame = new AppFrame();
			}
		});
		
		enableUserInputExitAfterDelay();
	}
	
	/**
	 * After a delay, this enables user input (mouse movement or keyboard press) to exit the application.
	 */
	private static void enableUserInputExitAfterDelay() {
		Runnable exitTask = new Runnable() {
			@Override
			public void run() {
				appFrame.enableExitUponUserInput();
			}
		};
		
		ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
		worker.schedule(exitTask, USER_INPUT_EXIT_DELAY, TimeUnit.MILLISECONDS);
	}

}
