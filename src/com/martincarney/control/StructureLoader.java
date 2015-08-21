package com.martincarney.control;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.martincarney.model.brick.Brick;
import com.martincarney.model.brick.BrickInstance;
import com.martincarney.model.brick.BrickPrototype;
import com.martincarney.model.brick.RandomColor;
import com.martincarney.model.shared.Dimension;
import com.martincarney.model.structure.BrickStructure;
import com.martincarney.view.renderer.RendererConstants;

/**
 * Tool to load a brick structure from file and create a {@link BrickStructure} from it, which can
 * then be used by the screensaver.
 * @author Martin Carney 2015
 */
public class StructureLoader {
	
	private static List<File> structureFiles = new ArrayList<File>();
	
	/**
	 * Loads a {@link BrickStructure} from the provided JSON file
	 * @param file Reference to a JSON file ("*.json") containing data from which to construct a
	 * brick structure.
	 * @return the completed {@link BrickStructure} object if leading was successful, null if not.
	 */
	public BrickStructure loadStructureFromJSONFile(File file) {
		if (file.exists() && file.canRead()) {
			JSONObject document = new JSONObject(loadFileToString(file));
			JSONArray prototypesJson = document.getJSONArray("prototypes");
			JSONArray instancesJson = document.getJSONArray("instances");
			
			List<BrickPrototype> prototypes = loadJsonBrickPrototypes(prototypesJson);
			List<BrickInstance> instances = loadJsonBrickInstances(prototypes, instancesJson);
			
			BrickStructure result = new BrickStructure();
			result.provideBricks(instances);
			return result;
		}
		return null;
	}
	
	/**
	 * Loads the {@link BrickPrototype}s specified in the JSON file, which will then be used to
	 * generate individual {@link BrickInstance}s.
	 */
	private List<BrickPrototype> loadJsonBrickPrototypes(JSONArray prototypesJson) {
		List<BrickPrototype> result = new ArrayList<BrickPrototype>();
		
		for (int i = 0; i < prototypesJson.length(); i++) {
			JSONObject prototypeJson = prototypesJson.getJSONObject(i);
			try {
				Class<? extends BrickPrototype> clazz = Class.forName(Brick.class.getPackage().getName() +
						"." + prototypeJson.getString("type")).asSubclass(BrickPrototype.class);
				BrickPrototype prototype = clazz.newInstance();
				
				loadDimension(prototypeJson.getJSONArray("size"), prototype.getSize());
				
				if (prototypeJson.has("color")) {
					String colorStr = prototypeJson.getString("color");
					if ("random".equals(colorStr)) {
						prototype.setBaseColor(new RandomColor());
					} else if (colorStr.startsWith("C")) {
						int colorIndex = Integer.parseInt(colorStr.substring(1));
						prototype.setBaseColor(RendererConstants.BRICK_COLORS[colorIndex]);
					} else {
						prototype.setBaseColor(Color.decode(colorStr));
					}
				}
				
				result.add(prototype);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * Generates each of the {@link BrickInstance}s using the previously-loaded prototypes
	 */
	private List<BrickInstance> loadJsonBrickInstances(List<BrickPrototype> prototypes, JSONArray instancesJson) {
		List<BrickInstance> result = new ArrayList<BrickInstance>();
		
		for (int i = 0; i < instancesJson.length(); i++) {
			JSONObject instanceJson = instancesJson.getJSONObject(i);
			int prototypeIndex = instanceJson.getInt("prototype");
			BrickInstance instance = prototypes.get(prototypeIndex).createInstance();
			loadDimension(instanceJson.getJSONArray("loc"), instance.getLocation());
			result.add(instance);
		}
		
		return result;
	}

	/**
	 * Loads a set of 3D coordinates into a {@link Dimension} object.
	 * @param target If not null, its values are set to those in the JSONArray. If null, a new
	 * {@link Dimension} object is created & returned.
	 */
	private Dimension loadDimension(JSONArray dimensionJson, Dimension target) {
		if (target != null) {
			target.x = dimensionJson.getInt(0);
			target.y = dimensionJson.getInt(1);
			target.z = dimensionJson.getInt(2);
			return target;
		} else {
			return new Dimension(dimensionJson.getInt(0), dimensionJson.getInt(1), dimensionJson.getInt(2));
		}
	}

	public BrickStructure loadStructureFromBinaryFile(File file) {
		// TODO implement structure file loading
		throw new NullPointerException();
	}
	
	/**
	 * Find all the Structure files within the defined structure file directory.
	 */
	public static void findStructureFiles() {
		// TODO determine where structure files should be stored -> "structures" directory for now
		// TODO implement finding structure files
		throw new NullPointerException();
	}
	
	/**
	 * Get the list of Structure files previously found using {@link #findStructureFiles()}.
	 */
	public static List<File> getStructureFileList() {
		return structureFiles;
	}
	
	private String loadFileToString(File file) {
		StringBuilder sb = new StringBuilder();
		try {
			List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
			for (String line : lines) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
