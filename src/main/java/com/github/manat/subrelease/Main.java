/**
 * 
 */
package com.github.manat.subrelease;

import java.nio.file.Paths;

import com.github.manat.subrelease.invoker.CliInvoker;
import com.github.manat.subrelease.invoker.Invoker;

/**
 * @author dt77850
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Invoker invoker = new CliInvoker("C:/dev/maven/test-maven-embedder");
		invoker.resolveDependency(Paths.get("C:/dev/maven/test-maven-embedder/resolvedDependency.txt"));

	}

}
