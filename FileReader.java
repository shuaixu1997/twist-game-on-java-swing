package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class FileReader implements Iterator<String>{
	
	private Scanner _scan;
	
	public FileReader(String filename) {
		_scan = null;
		try {
			_scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override public boolean hasNext() {
		if (_scan == null) { return false; }
		return _scan.hasNextLine();
	}

	@Override public String next() {
		if (_scan == null) { return null; }
		return _scan.nextLine();
	}
	
}
