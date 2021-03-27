package tenpinsbowling.components;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is responsable to read the input files from the resource folder.
 * 
 * @author pongelupe
 *
 */
public class FileInputReader implements Closeable {

	private static final String DEFAULT_DELIMITER = "\t";

	private final Scanner scanner;

	private final String delimiter;

	public FileInputReader(String path, String delimiter) throws FileNotFoundException, URISyntaxException {
		this.delimiter = delimiter;
		scanner = new Scanner(new FileInputStream(new File(getClass().getClassLoader().getResource(path).toURI())));
	}

	public FileInputReader(String path) throws FileNotFoundException, URISyntaxException {
		this(path, DEFAULT_DELIMITER);
	}

	@SuppressWarnings("unchecked")
	public <T extends FromString> T readLine(Class<T> clazz) {
		if (scanner.hasNextLine()) {
			var line = scanner.nextLine();
			try {
				return (T) clazz.getConstructor().newInstance().fromString(line.split(delimiter));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new IllegalArgumentException("The class informed must have a no Arguments constructor");
			}
		} else {
			return null;
		}
	}

	public <T extends FromString> List<T> readAllLines(Class<T> clazz) {
		var lines = new ArrayList<T>();
		
		var current = readLine(clazz);
		
		while (current != null) {
			lines.add(current);
			current = readLine(clazz);
		}
		
		return lines;
	}

	public void close() throws IOException {
		scanner.close();
	}
}
