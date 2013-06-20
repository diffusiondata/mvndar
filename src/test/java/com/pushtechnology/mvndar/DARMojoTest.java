/*
 * Copyright 2013 Push Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pushtechnology.mvndar;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import com.pushtechnology.mvndar.stubs.DARMavenProjectStub;

/**
 * Unit tests for {@link DARMojo}.
 * 
 * @author Philip Aston
 */
public class DARMojoTest extends AbstractMojoTestCase {

    private File testBaseDirectory;

    @Override
    protected void setUp() throws Exception {
	super.setUp();

	testBaseDirectory = new File(getBasedir(), "target/mojo-test");
	testBaseDirectory.mkdirs();
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

    private File getTestBuildDirectory(final String name) {
	return new File(testBaseDirectory, name);
    }

    public void testBasic() throws Exception {
	final File pom = getTestFile("src/test/resources/unit/basic-test/pom.xml");
	assertTrue(pom.exists());

	final DARMojo mojo = (DARMojo) lookupMojo("dar", pom);

	final File buildDirectory = getTestBuildDirectory("basic");

	setVariableValueToObject(mojo, "project", new DARMavenProjectStub(pom));
	setVariableValueToObject(mojo, "buildDirectory",
		buildDirectory.getAbsolutePath());
	setVariableValueToObject(mojo, "finalName", "mydar");

	setVariableValueToObject(mojo, "outputDirectory",
		new File("some missing directory"));
	setVariableValueToObject(mojo, "diffusionResourceDirectory",
		new File("some missing directory"));
	setVariableValueToObject(mojo, "minimumDiffusionVersion", "1.23");

	mojo.execute();

	final JarReflector jar = new JarReflector(new File(buildDirectory,
		"mydar.dar"));
	jar.assertEntry("Diffusion-Version", "1.23");
    }
}
