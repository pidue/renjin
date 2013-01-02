package org.renjin.maven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.renjin.eval.Context;
import org.renjin.packaging.LazyLoadFrameBuilder;
import org.renjin.parser.RParser;
import org.renjin.primitives.io.serialization.RDataWriter;
import org.renjin.primitives.packaging.Namespace;
import org.renjin.primitives.packaging.NamespaceDef;
import org.renjin.sexp.Environment;
import org.renjin.sexp.SEXP;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * Compiles R sources into a serialized blob
 * 
 * @goal compile
 * @phase compile
 * @requiresProject true
 */
public class CompilerMojo extends AbstractMojo {

	/**
	 * Name of the R package
	 * @parameter expression="src/main/R"
	 * @required
	 */
	private File sourceDirectory;	

	/**
	 * Name of the R package
	 * @parameter expression="${project.build.outputDirectory}"
	 * @required
	 * @readonly
	 */
	private File outputDirectory;	
	
	/**
	 * @parameter expression="${project.groupId}.${project.artifactId}"
	 * @required
	 */
	private String packageName;

	/**
   * @parameter expression="${project.artifactId}"
   * @required
	 */
	private String namespaceName;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Context context = initContext();
		
		Namespace namespace = context.getNamespaceRegistry().createNamespace(new NamespaceDef(), namespaceName);
		evaluateSources(context, namespace.getNamespaceEnvironment());
		serializeEnvironment(context, namespace.getNamespaceEnvironment(), getEnvironmentFile());
		copyNamespace();
	}


	private void copyNamespace() throws MojoFailureException, MojoExecutionException {
		try {
			File namespaceSource = new File("NAMESPACE");
			if(!namespaceSource.exists()) {
				throw new MojoFailureException("Missing NAMESPACE file");
			}
			Files.copy(namespaceSource, getNamespaceOutput());
		} catch (IOException e) {
			throw new MojoExecutionException("Exception copying NAMESPACE file", e);
		}
	}


	private Context initContext() throws MojoExecutionException  {
		try {
			Context context = Context.newTopLevelContext();
			context.init();
			return context;
		} catch(IOException e) {
			throw new MojoExecutionException("Could not initialize R top level context", e);
		}
	}
	


	private List<File> getRSources() {
		List<File> list = Lists.newArrayList(sourceDirectory.listFiles());
		Collections.sort(list);
		return list;
	}

	private File getEnvironmentFile() {
		return new File(getPackageRoot(), "environment");
	}

	private File getNamespaceOutput() {
		return new File(getPackageRoot(), "NAMESPACE");
	}

	private File getPackageRoot() {
		File packageRoot = new File(outputDirectory.getAbsoluteFile() + File.separator + packageName.replace(".", File.separator));
		packageRoot.mkdirs();
		return packageRoot;
	}

	private void evaluateSources(Context context, Environment namespaceEnvironment)
			throws MojoExecutionException {
		for(File sourceFile : getRSources()) {
			getLog().debug("Evaluating '" + sourceFile + "'");
			try {
				FileReader reader = new FileReader(sourceFile);
				SEXP expr = RParser.parseAllSource(reader);
				reader.close();
				
				context.evaluate(expr, namespaceEnvironment);
				
			} catch (Exception e) {
				throw new MojoExecutionException("Exception evaluating " + sourceFile.getName(), e);
			}
		}
	}
	
	private void serializeEnvironment(Context context, Environment namespaceEnv, File environmentFile) throws MojoExecutionException  {
		
		getLog().info("Writing namespace environment to " + environmentFile);
		try {
		  LazyLoadFrameBuilder builder = new LazyLoadFrameBuilder(context);
		  builder.outputTo(environmentFile);
		  builder.build(namespaceEnv);
		} catch(IOException e) {
			throw new MojoExecutionException("Exception encountered serializing namespace environment", e);
		}
	}
}