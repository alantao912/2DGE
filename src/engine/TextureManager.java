package engine;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public final class TextureManager {
	
	private class Texture {
		String name;
		BufferedImage texture;
	}
	
	/**
	 * 	The TextureManager class maintains a set of classes and their associated textures.
	 */
	
	private static ArrayList<Object> classes = new ArrayList<>();
	private static ArrayList<Texture[]> associatedTextures = new ArrayList<>();
	
	/**
	 * 
	 * Reads textures from disc and stored them with an associated class.
	 * 
	 * @param o : class which the textures are associated with
	 * @param texturesPath : parent directory where the texture files are stored
	 * @throws IOException
	 */
	
	public static void loadTextures(Object o, String texturesPath) throws IOException {
		
		/* Returns if textures for the class has already been loaded*/
		
		for(int i = 0; i < classes.size(); ++i)
			if(classes.get(i).equals(o))
				return;
		
		/* Parent directory of where the textures are stored */
		File texturesFolder = new File(texturesPath);
		
		
		/* Checks if the file exists, and that it is a directory */
		if(texturesFolder.exists() && texturesFolder.isDirectory()) {
			/* Lists all the files within the specified parent directory */
			File[] textureFiles = texturesFolder.listFiles();
			
			/* Checks that all of the files are not directories */
			for(File texture : textureFiles)
				if(texture.isDirectory())
					throw new IOException("Game textures must not be a folder");
			
			Texture[] textures = new Texture[textureFiles.length];
			
			/* Converts the files into BufferedImage objects to store */
			
			for(int i = 0; i < textures.length; ++i) {
				textures[i].texture = ImageIO.read(textureFiles[i]);
				textures[i].name = textureFiles[i].getName();
			}
			classes.add(o);
			associatedTextures.add(textures);
		}
	}
	
	public static BufferedImage getTexture(Object o, String textureName) {
		for(int i = 0; i < classes.size(); ++i)
			if(o.equals(classes.get(i)))
				for(Texture texture : associatedTextures.get(i))
					if(texture.name.equals(textureName))
						return texture.texture;
		return null;
			
	}
	
}
