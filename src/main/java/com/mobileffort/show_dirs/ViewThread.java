package com.mobileffort.show_dirs;

import java.io.File;
import java.util.logging.Logger;

/**
 * This class implements the counting of files in the desired directory
 * 
 * @author Serhii
 *
 */
public class ViewThread extends Thread {
	private File mainDir;
	private long filesCount;
	private Long id;

	public ViewThread(String mainDirName) {
		this.mainDir = new File(mainDirName);
	}

	public ViewThread(String mainDirName, long id) {
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

	private String getDirName() {
		return mainDir.getName().length() != 0 ? mainDir.getName() : mainDir.getAbsolutePath();
	}

	private String getDirId() {
		return id != null ? id.toString() : "?";
	}

	public String toString() {
		this.interrupt();
		return String.format("%s;%s", getDirName(), filesCount) + System.getProperty("line.separator");
	}

	public String toString(String leftAlignFormat) {
		return String.format(leftAlignFormat, getDirId(), getDirName(), filesCount);
	}

	private Logger log = Logger.getLogger(getClass().getName());
}
