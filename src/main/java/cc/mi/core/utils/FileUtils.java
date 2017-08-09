package cc.mi.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public enum FileUtils {
	INSTANCE;
	
	private static final String BINLOG_EXT_NAME = ".blg";
	private FileUtils() {}
	
	public boolean loadPlayerBinlog(final String dir, final String fileName, List<String> lines) {
		String path = dir + "/" + fileName + "." + BINLOG_EXT_NAME;
		return this.syncLoadFile(path, lines);
	}
	
	public boolean syncLoadFile(final String path, List<String> lines) {
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
}
