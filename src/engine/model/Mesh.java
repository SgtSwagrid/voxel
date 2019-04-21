package engine.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Mesh {
	
	public enum Format { OBJ, COLLADA }
	
	private final int vaoId, numVertices;
	
	
	
	public Mesh(String fileName, Format fileFormat) {
		vaoId = 0;
		numVertices = 0;
	}
	
	public Mesh(int vaoId, int numVertices) {
		this.vaoId = vaoId;
		this.numVertices = numVertices;
	}
	
	public int getVaoId() { return vaoId; }
	
	public int getNumVertices() { return numVertices; }
	
	private static void loadObj(String fileName) {
		
		List<Vector3f> vertexList = new ArrayList<>();
		List<Vector2f> texCoordList = new ArrayList<>();
		List<Vector3f> normalList = new ArrayList<>();
		List<int[][]> faceList = new ArrayList<>();
		
		int lineNum = 0;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			
			String line;
			while((line = reader.readLine()) != null) {
				
				//Remove comments and whitespace.
				line = line.split("#", -1)[0].replaceAll("\\s+", " ").trim();
				
				//Skip empty lines.
				if(line.isEmpty()) return;
				
				//Split line into space-delimited tokens.
				String[] tokens = line.split(" ");
				
				//The first token determines what is represented by this line.
				switch(tokens[0]) {
					
					//Vertices
					case "v":
						vertexList.add(toVector3(tokens, 1));
						break;
					
					//Texture Coordinates
					case "vt":
						texCoordList.add(toVector2(tokens, 1));
						break;
					
					//Normals
					case "vn":
						normalList.add((Vector3f) toVector3(tokens, 1).normalise());
						break;
					
					//Faces
					case "f":
						int[][] face = new int[tokens.length - 1][3];
						for(int i = 1; i < tokens.length; i++)
							face[i - 1] = toIntegers(tokens[i].split("/", -1), 0);
						faceList.add(face);
						break;
						
					default:
						throw new FileFormatException("Unknown token on line "
								+ lineNum + " in " + fileName + ".");
				}
				lineNum++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new FileFormatException("Insufficient number of parameters on line "
					+ lineNum + " in " + fileName + ".");
			
		} catch(NumberFormatException e) {
			throw new FileFormatException("Incorrect parameter type on line "
					+ lineNum + " in " + fileName + ".");
		}
		
		float[] indexedVertices, indexedTexCoords, indexedNormals;
		
		List<Integer> indices = new ArrayList<>();
		Map<int[], Integer> indexMap = new HashMap<>();
	}
	
	private static Vector2f toVector2(String[] array, int start) {
		return new Vector2f(
				Float.parseFloat(array[start]),
				Float.parseFloat(array[start + 1]));
	}
	
	private static Vector3f toVector3(String[] array, int start) {
		return new Vector3f(
				Float.parseFloat(array[start]),
				Float.parseFloat(array[start + 1]),
				Float.parseFloat(array[start + 2]));
	}
	
	private static int[] toIntegers(String[] array, int start) {
		int[] integers = new int[array.length - start];
		for(int i = 0; i < integers.length; i++)
			integers[i] = Integer.parseInt(array[i + start]);
		return integers;
	}
	
	@SuppressWarnings("serial")
	public static class FileFormatException extends RuntimeException {
		public FileFormatException() { super(); }
		public FileFormatException(String message) { super(message); }
	}
}