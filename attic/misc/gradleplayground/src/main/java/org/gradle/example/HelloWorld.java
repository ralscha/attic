package org.gradle.example.simple;

import org.apache.commons.codec.digest.*;

public class HelloWorld {
  public static void main(String... args) {
    for (String arg : args) {
	  System.out.println(DigestUtils.md5Hex(arg));
	}
  }
}