package org.immutables.samples.free;

public class Main {

	public static void main(String[] args) {
		FreeDocument fd = new FreeDocument.Builder().setId(1).setName("name").build();
		System.out.println(fd);
	}

}
