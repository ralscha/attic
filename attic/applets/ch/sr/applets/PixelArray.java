package ch.sr.applets;
import java.awt.*;
import java.awt.image.*;

class PixelArray {
	private int[] array;
	private int width;
	private int height;

	public PixelArray(int width, int height) {
		array = new int[width * height];
		this.width = width;
		this.height = height;
	}

	public synchronized void setPixel(int x, int y, Color color) {
		array[x + (y * width)] = color.getRGB();
	}

	public synchronized Color getPixel(int x, int y) {

		return (new Color(array[x + (y * width)]));
	}


	public synchronized int[]
		getArray() {
		return array;
	}


	public synchronized void setArray(int[] array) {
		this.array = array;
	}

	public synchronized MemoryImageSource overlay(int[] oarray) {
		int a[];
		a = new int[width * height];
		System.arraycopy(array, 0, a, 0, width * height);

		for (int i = 0; i < oarray.length; i++) {
			if (a[i] != Color.black.getRGB())
				a[i] = oarray[i];


		}
		return (new MemoryImageSource(width, height, a, 0, width));
	}

	public synchronized MemoryImageSource getMemoryImage() {
		int a[];
		a = new int[width * height];
		System.arraycopy(array, 0, a, 0, width * height);
		return (new MemoryImageSource(width, height, a, 0, width));
	}
}