package engine.model;

import engine.util.Colour;

public class Material {
	
	public static final Material MATTE = new Material()
			.withAmbientLight(0.3F)
			.withDiffuseLight(1.0F)
			.withSpecularLight(0.0F)
			.withSpecularDamping(1);
	
	public static final Material SHINE = new Material()
			.withAmbientLight(0.3F)
			.withDiffuseLight(1.0F)
			.withSpecularLight(0.6F)
			.withSpecularDamping(64);
	
	private Texture texture = Texture.BLANK;
	
	private Colour colour = Colour.WHITE;
	
	private float ambientLight = 0.0F, diffuseLight = 1.0F, specularLight = 0.0F;
	private int specularDamping = 1;
	
	public Texture getTexture() { return texture; }
	
	public Material withTexture(Texture texture) {
		Material m = clone(); m.texture = texture; return m;
	}
	
	public Material withTexture(Texture texture, Colour colour) {
		Material m = clone(); m.texture = texture; m.colour = colour; return m;
	}
	
	public Colour getColour() { return colour; }
	
	public Material withColour(Colour colour) {
		Material m = clone(); m.colour = colour; return m;
	}
	
	public float getAmbientLight() { return ambientLight; }
	
	public Material withAmbientLight(float ambientLight) {
		Material m = clone(); m.ambientLight = ambientLight; return m;
	}
	
	public float getDiffuseLight() { return diffuseLight; }
	
	public Material withDiffuseLight(float diffuseLight) {
		Material m = clone(); m.diffuseLight = diffuseLight; return m;
	}
	
	public float getSpecularLight() { return specularLight; }
	
	public Material withSpecularLight(float specularLight) {
		Material m = clone(); m.specularLight = specularLight; return m;
	}
	
	public int getSpecularDamping() { return specularDamping; }
	
	public Material withSpecularDamping(int specularDamping) {
		Material m = clone(); m.specularDamping = specularDamping; return m;
	}
	
	@Override
	public Material clone() {
		
		Material m = new Material();
		m.texture = texture;
		m.colour = colour;
		m.ambientLight = ambientLight;
		m.diffuseLight = diffuseLight;
		m.specularLight = specularLight;
		m.specularDamping = specularDamping;
		return m;
	}
}