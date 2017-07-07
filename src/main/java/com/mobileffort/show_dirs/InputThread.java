package com.mobileffort.show_dirs;

import java.io.InputStream;
import java.util.Scanner;

public class InputThread extends Thread {
	private InputStream is;
	private boolean buttonPressed;

	public InputThread(InputStream is) {
		this.is = is;
	}

	public void run() {
		try (Scanner reader = new Scanner(is)) {
			System.out.println("Для остановки нажмите Enter: ");
			String input;
			while (!buttonPressed) {
				input = reader.next();
				if (input != null) {
					System.out.println("input: " + input);
					buttonPressed = true;
					break;
				}
			}
		}
	}

	public boolean isPressed() {
		return buttonPressed;
	}
}
