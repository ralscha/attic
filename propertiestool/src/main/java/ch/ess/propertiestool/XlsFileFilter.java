package ch.ess.propertiestool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XlsFileFilter extends FileFilter {

	@Override
	public String getDescription() {
		return ".xls (Excel File)";
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		return f.getName().toLowerCase().endsWith(".xls");
	}

}
