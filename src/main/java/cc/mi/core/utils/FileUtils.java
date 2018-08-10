package cc.mi.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public enum FileUtils {
	INSTANCE;
	
	private static final String BINLOG_EXT_NAME = ".bl";
	private FileUtils() {}
	
	public boolean loadPlayerBinlog(final String dir, final String fileName, List<String> lines) {
		String path = dir + "/" + fileName + "." + BINLOG_EXT_NAME;
		return this.loadFile(path, lines);
	}
	
	public boolean loadFile(final String path, List<String> lines) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path));) {
			String content = null;
			while ((content = reader.readLine()) != null) {
				lines.add(content);
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public boolean loadFile(final File file, List<String> lines) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
			String content = null;
			while ((content = reader.readLine()) != null) {
				lines.add(content);
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public boolean append(final String fileName, String str) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));) {
			out.write(str);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public boolean append(final File file, String str) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true));) {
			out.write(str);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public boolean writeContent(final File file, List<String> list) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(file));) {
			for (String content : list) {
				out.write(content);
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public boolean writeContent(final String fileName, List<String> list) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName));) {
			for (String content : list) {
				out.write(content);
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public List<File> listFilesInCurrentDirectory(String path) {
		List<File> ret = new LinkedList<>();
		
		File[] files = new File(path).listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				ret.add(file);
			}
		}
		
		return ret;
	}
}
