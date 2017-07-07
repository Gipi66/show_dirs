package com.mobileffort.show_dirs;

public class Main {
	public static void main(String[] args) {
		if (args.length == 2) {
			ViewManager vm = new ViewManager(args[0], args[1]);
			vm.run();
		}
	}
}
