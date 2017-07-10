package com.mobileffort.show_dirs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.logging.Logger;

/**
 * This class for creating ViewThread.class threads and processing received data
 * 
 * @author Serhii
 * @see ViewThread
 */
public class ViewManager {
	private File inFile;
	private File outFile;

	private ArrayDeque<ViewThread> threadsQue = new ArrayDeque<ViewThread>();

	/**
	 * Constructor for ViewManager.class
	 * 
	 * @param inFileName
	 *            name of input file
	 * @param outFileName
	 *            name of output file
	 */
	public ViewManager(String inFileName, String outFileName) {
		this.inFile = new File(inFileName);
		this.outFile = new File(outFileName);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		/*
		 * get time of start app
		 */
		long time = (new Date()).getTime();
		/*
		 * start thread for input
		 */
		InputThread it = new InputThread(System.in);
		it.start();

		/*
		 * run threads for each line of input file
		 */
		try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
			// String line;
			long lineNumber = 1;

			for (String line = br.readLine(); line != null && !line.isEmpty(); line = br.readLine(), lineNumber++) {
				ViewThread th = new ViewThread(line, lineNumber);
				th.start();
				threadsQue.add(th);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * create data for out
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("Путь к папке;Количество вложенных файлов\n");

		for (ViewThread th : threadsQue) {
			for (Integer n = 0;; n++) {
				if (it.isPressed() || !th.isAlive()) {
					th.interrupt();
					sb.append(th.toString());
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		/*
		 * if user stoped app -> show results to System.out or return results to
		 * output file
		 */
		if (it.isPressed()) {
			sb = new StringBuilder();

			String leftAlignFormat = "| %-4s | %-20s | %-6d |\n";

			sb.append("+------+------------+-------------+\n");
			sb.append("|  ID  |    Name    |    Count    |\n");
			sb.append("+------+------------+-------------+\n");

			for (ViewThread th : threadsQue) {
				sb.append(th.toString(leftAlignFormat));
			}
			sb.append("+--------+------------+-------------+\n");

			System.out.println(sb.toString());
		} else {
			try (PrintWriter writer = new PrintWriter(outFile)) {
				writer.write(sb.toString());
			} catch (FileNotFoundException e) {
				log.warning("Resoults file not found");
			}

		}
		/*
		 * show total time
		 */
		log.info("Total time: " + (new Date((new Date()).getTime() - time)).getSeconds());

		/*
		 * close application
		 */
		System.exit(0);
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
