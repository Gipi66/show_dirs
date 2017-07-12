package com.mobileffort.show_dirs;

import static java.lang.System.out;

public class Main {
	public static void main(String[] args) {
		if (args.length == 2) {
			ViewManager vm = new ViewManager(args[0], args[1]);
			vm.run();
		} else if (args.length == 1 && args[0].contains("-help")) {
			out.println("The arguments must match the paths to the input file and the output file\n"
					+ "For example:\njava -jar show_dirs.jar /home/in.txt /home/out.csv");
		} else {
			out.println("Argument error: To show help, use \"-help\"");
		}
	}
}
