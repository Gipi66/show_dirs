package com.mobileffort.show_dirs;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Logger;

public class ViewManager {
	private File inFile;
	private File outFile;

	private ArrayDeque<ViewRunnable> threadsQue = new ArrayDeque<ViewRunnable>();

	public ViewManager(String inFileName, String outFileName) {
		this.inFile = new File(inFileName);
		this.outFile = new File(outFileName);
	}

	public void run() {
		try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
			String line;
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				ViewRunnable th = new ViewRunnable(line);
				th.start();
				threadsQue.add(th);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long time = (new Date()).getTime();

		for (ViewRunnable th : threadsQue) {
			 try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println(th.getResults());
		}

		System.out.println("Seconds passed: " + (new Date((new Date()).getTime() - time)).getSeconds());

		Console con = System.console();


	}

	private Logger log = Logger.getLogger(getClass().getName());
}
