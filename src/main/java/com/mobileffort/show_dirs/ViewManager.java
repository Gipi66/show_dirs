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

public class ViewManager {
	private File inFile;
	private File outFile;

	private ArrayDeque<ViewThread> threadsQue = new ArrayDeque<ViewThread>();

	public ViewManager(String inFileName, String outFileName) {
		this.inFile = new File(inFileName);
		this.outFile = new File(outFileName);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
			String line;
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				ViewThread th = new ViewThread(line);
				th.start();
				threadsQue.add(th);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long time = (new Date()).getTime();

		StringBuilder sb = new StringBuilder();
		sb.append("Путь к папке;Количество вложенных файлов\n");

		InputThread it = new InputThread(System.in);
		it.start();

		for (ViewThread th : threadsQue) {
			for (Integer n = 0;; n++) {
				if (it.isPressed() || !th.isAlive()) {
					th.interrupt();
					th.getResults(sb);
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		log.info(sb.toString());
		log.info("Seconds passed: " + (new Date((new Date()).getTime() - time)).getSeconds());

		try (PrintWriter writer = new PrintWriter(outFile)) {
			writer.write(sb.toString());
		} catch (FileNotFoundException e) {
			log.warning("Resoults file not found");
		}
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
