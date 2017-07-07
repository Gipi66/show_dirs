package com.mobileffort.show_dirs;

import java.io.File;
import java.util.logging.Logger;

public class ViewThread extends Thread {
	private File mainDir;
	private int filesCount;

	public ViewThread(String mainDirName) {
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
			if (entry == null || interrupted()) {
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

	public void getResults(StringBuilder sb) {
		this.interrupt();
		sb.append(String.format("%s;%s", mainDir, filesCount) + System.getProperty("line.separator"));
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
