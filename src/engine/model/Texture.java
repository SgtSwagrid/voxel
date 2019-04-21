package engine.model;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.opengl.TextureLoader;

public class Texture {
	
	public static final Texture BLANK = new Texture("res/texture/blank.png");
	
	private static Map<String, Texture> textures = new HashMap<>();
	
	private String fileName;
	
	private int textureId = -1;
	
	private Texture(String fileName) {
		this.fileName = fileName;
	}
	
	public static Texture getTexture(String fileName) {
		if(!textures.containsKey(fileName))
			textures.put(fileName, new Texture(fileName));
		return textures.get(fileName);
	}
	
	public int getTextureId() {
		if(textureId == -1) textureId = loadPng(fileName);
		return textureId;
	}
	
	public boolean isOpaque() { return true; }
	
	private static int loadPng(String fileName) {
		
		int textureId = 0;
		
		try {
			textureId = TextureLoader
					.getTexture("PNG", new FileInputStream(fileName))
					.getTextureID();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0);
		
		if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
			float filtering = Math.min(4F, glGetFloat(
					EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			glTexParameterf(GL_TEXTURE_2D,
					EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, filtering);
		}
		return textureId;
	}
}