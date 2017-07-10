package com.mobileffort.show_dirs;

import java.io.File;
import java.util.logging.Logger;

public class ViewThread extends Thread {
	private File mainDir;
	private long filesCount;
	private Integer id;

	public ViewThread(String mainDirName) {
		this.mainDir = new File(mainDirName);
	}

	public ViewThread(String mainDirName, int id) {
		this(mainDirName);
		this.id = id;
	}

	@Override
	public void run() {
		if (mainDir.isDirectory()) {
			try {
				showDir(mainDir);
			} catch (NullPointerException e) {
				// pass
			}
		}
	}

	private void showDir(File dir) throws NullPointerException {
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

	public String toString() {
		this.interrupt();
		return String.format("%s;%s", mainDir, filesCount) + System.getProperty("line.separator");
	}

	public String toString(String leftAlignFormat) {
		return String.format(leftAlignFormat, id != null ? id : "?", mainDir.getName(), filesCount);
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
