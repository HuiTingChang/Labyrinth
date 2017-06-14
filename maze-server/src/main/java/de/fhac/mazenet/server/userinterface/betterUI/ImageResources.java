package de.fhac.mazenet.server.userinterface.betterUI;

import de.fhac.mazenet.server.config.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ImageResources {
	private static HashMap<String, Image> images = new HashMap<String, Image>();

	private ImageResources() {
	}

	public static Image getImage(String name) {
		if (images.containsKey(name)) {
			return images.get(name);
		}
		URL u = ImageResources.class.getResource(Settings.IMAGEPATH + name + Settings.IMAGEFILEEXTENSION);
		Image img = null;
		try {
			img = ImageIO.read(u);
			images.put(name, img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static void reset() {
		images = new HashMap<String, Image>();
	}

	public static void treasureFound(String treasure) {
		if (!treasure.startsWith("Start")) { //$NON-NLS-1$
			URL u = ImageResources.class.getResource(Settings.IMAGEPATH + "found" //$NON-NLS-1$
					+ Settings.IMAGEFILEEXTENSION);
			try {
				images.put(treasure, ImageIO.read(u));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}