package engine.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class Files {
	
	public static void forEachLine(String fileName, Consumer<String> action) {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			reader.lines().forEach(action);
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}