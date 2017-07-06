package com.mobileffort.show_dirs;

import java.io.File;
import java.util.ArrayDeque;
import java.util.logging.Logger;

public class ViewRunnable extends Thread {
	private File mainDir;
	private int filesCount;

	public ViewRunnable(String mainDirName) {
		this.mainDir = new File(mainDirName);
	}

	@Override
	public void run() {
		if (mainDir.isDirectory()) {
			showDir(mainDir);
		}
	}

	private void showDir(File dir) {
		for (File entry : dir.listFiles()) {

			if (this.interrupted()) {
				log.info("for inter!");
				break;
				// throw new InterruptedException();
			} else if (entry.isFile()) {
				filesCount++;
				continue;
			} else if (entry.isDirectory()) {
				showDir(entry);
			}
		}
	}

	int getResults() {
		this.interrupt();
		return filesCount;
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
