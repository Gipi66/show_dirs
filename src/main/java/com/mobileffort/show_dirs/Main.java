package com.mobileffort.show_dirs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) {
		if (args.length == 2) {
			ViewManager vm = new ViewManager(args[0], args[1]);
			vm.run();
		}
	}
}
