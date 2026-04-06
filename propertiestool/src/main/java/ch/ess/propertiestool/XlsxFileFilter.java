package ch.ess.propertiestool;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XlsxFileFilter extends FileFilter {

	@Override
	public String getDescription() {
		return ".xlsx (Excel 2007 File)";
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		return f.getName().toLowerCase().endsWith(".xlsx");
	}

}
