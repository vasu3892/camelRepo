package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileCopier {

	public static void main(String args[]) throws Exception {
		File inboxDirectory = new File("input");
		File outboxDirectory = new File("output");
		outboxDirectory.mkdir();
		File[] files = inboxDirectory.listFiles();
		for (File source : files) {
			if (source.isFile()) {
				File dest = new File(outboxDirectory.getPath() + File.separator + source.getName());
				copyFile(source, dest);
				System.out.println("Copied file : " + dest.getName());
			}
		}

		System.out.println("done");

	}

	private static void copyFile(File source, File dest) throws IOException {
		OutputStream out = new FileOutputStream(dest);
		byte[] buffer = new byte[(int) source.length()];
		FileInputStream in = new FileInputStream(source);
		in.read(buffer);
		try {
			out.write(buffer);
		} finally {
			out.close();
			in.close();
		}
	}

}
