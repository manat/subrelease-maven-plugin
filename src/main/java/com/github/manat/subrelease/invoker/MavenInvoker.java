/**
 * 
 */
package com.github.manat.subrelease.invoker;

import java.nio.file.Path;
import java.util.Arrays;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.github.manat.subrelease.model.Artifact;

/**
 * @author dt77850
 *
 */
public class MavenInvoker implements com.github.manat.subrelease.invoker.Invoker {

	private Path pomPath;

	public MavenInvoker(Path pomPath) {
		this.pomPath = pomPath;
	}

	@Override
	public boolean resolveDependency(Path output) {
		InvocationRequest req = new DefaultInvocationRequest();
		req.setPomFile(pomPath.toFile());
		req.setGoals(Arrays.asList("dependency:resolve", "-DoutputFile=" + output.toString()));

		Invoker invoker = new DefaultInvoker();
		InvocationResult result = null;
		try {
			result = invoker.execute(req);
		} catch (MavenInvocationException e) {
			e.printStackTrace();
			return false;
		}

		return result.getExitCode() == 0;
	}

	@Override
	public boolean unpackArtifact(Artifact artifact) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.manat.subrelease.invoker.Invoker#checkout(com.github.manat.
	 * subrelease.model.Artifact, java.lang.String)
	 */
	@Override
	public boolean checkout(Artifact artifact, String connection) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.manat.subrelease.invoker.Invoker#release()
	 */
	@Override
	public boolean release() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.manat.subrelease.invoker.Invoker#release(java.nio.file.Path)
	 */
	@Override
	public boolean release(Path projectPath) {
		// TODO Auto-generated method stub
		return false;
	}

}
