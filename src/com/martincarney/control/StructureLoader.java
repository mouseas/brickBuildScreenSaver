package com.martincarney.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.martincarney.model.structure.BrickStructure;

/**
 * Tool to load a brick structure from file and create a {@link BrickStructure} from it, which can
 * then be used by the screensaver.
 * @author Martin Carney 2015
 */
public class StructureLoader {
	
	private static List<File> structureFiles = new ArrayList<File>();
	
	public BrickStructure loadStructureFromJSONFile(File file) {
		// TODO implement structure file loading
		throw new NotImplementedException();
	}
	
	public BrickStructure loadStructureFromBinaryFile(File file) {
		// TODO implement structure file loading
		throw new NotImplementedException();
	}
	
	public static void findStructureFiles() {
		// TODO determine where structure files should be stored
		// TODO implement finding structure files
		throw new NotImplementedException();
	}
	
	public static List<File> getStructureFileList() {
		return structureFiles;
	}
}
