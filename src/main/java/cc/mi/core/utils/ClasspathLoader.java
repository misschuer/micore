package cc.mi.core.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cc.mi.core.ex.UndefinedProtocolException;

public final class ClasspathLoader {
	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final String PROTOCOL_FILE = "file";
	private static final String PROTOCOL_JAR = "jar";
	private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	/**
	 * 查询classPkg目录下, 包含有annotationClass的类
	 * @param classPkg
	 * @param annotationClass
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> list(String classPkg, Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
		ensureObject(classPkg);
		ensureAnnotation(annotationClass);
		
		String path = classPkg.replaceAll("\\.", "/");
		List<Class<?>> classList = new LinkedList<>();
		
		Enumeration<URL> urlEnum = loader.getResources(path);
		while (urlEnum.hasMoreElements()) {
			URL url = urlEnum.nextElement();
			switch (url.getProtocol()) {
				case PROTOCOL_FILE:
					findClass(classList, classPkg, url.getPath(), annotationClass);
					break;
				case PROTOCOL_JAR:
					findJarClass(classList, url, annotationClass);
					break;
				default:
					throw new UndefinedProtocolException(String.format("未定义的protocol:%s", url.getProtocol()));
			}
		}
		
		return classList;
	}
	
	private static void findClass(List<Class<?>> classList, 
									String classPkg, 
									String dir, 
									Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
		File[] files = new File(dir).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(CLASS_FILE_SUFFIX);
			}
		});
		
		if (files != null) {
			for (File file : files) {
				String fullName = classPkg + "." + file.getName().replaceAll("\\"+CLASS_FILE_SUFFIX, "");
				addClass(classList, fullName, annotationClass);
			}
		}
		
		files = null;
	}
	
	
	private static void findJarClass(List<Class<?>> classList, URL url, Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntryEnum = jarFile.entries();
		
		while (jarEntryEnum.hasMoreElements()) {
			JarEntry jarEntry = jarEntryEnum.nextElement();
			if (jarEntry.getName().endsWith(CLASS_FILE_SUFFIX)) {
				String fullName = jarEntry.getName().replaceAll("\\"+CLASS_FILE_SUFFIX, "").replaceAll("/", "\\.");
				addClass(classList, fullName, annotationClass);
			}
		}
	}
	
	private static void addClass(List<Class<?>> classList, String fullName, Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
		Class<?> clazz = loader.loadClass(fullName);
		if (clazz.isAnnotationPresent(annotationClass))
			classList.add(clazz);
	}
	
	private static <T> void ensureObject(T obj) {
		if (obj == null)
			throw new IllegalArgumentException("对象不能为空");
	}
	
	private static void ensureAnnotation(Class<? extends Annotation> annotationClass) {
		if (annotationClass == null)
			throw new IllegalArgumentException("参数annotationClass不能为空");
	}
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		/**
		 * 对于jar包, 打包的时候要勾选add directory entries
		 * 这样才能遍历到, 而且遍历的annotation必须是jar包里面的
		 */
	}
}
