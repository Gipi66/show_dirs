package com.mobileffort.show_dirs;

import java.io.InputStream;
import static java.lang.System.out;
import java.util.Scanner;

/**
 * This class for check enter in a separate thread
 * 
 * @author pc2
 *
 */
public class InputThread extends Thread {
	private InputStream is;
	private boolean buttonPressed;

	/**
	 * Constructor for InputThread
	 * 
	 * @param is
	 *            the InputStream
	 */
	public InputThread(InputStream is) {
		this.is = is;
	}

	public void run() {
		try (Scanner reader = new Scanner(is)) {
			out.println("Для остановки нажмите Enter: ");
			String input;
			while (!buttonPressed) {
				input = reader.nextLine();
				if (input != null) {
					buttonPressed = true;
					break;
				}
			}
		}
	}

	/**
	 * Check for button pressed
	 * 
	 * @return buttonPressed indicator if the button was pressed
	 */
	public boolean isPressed() {
		return buttonPressed;
	}
}
