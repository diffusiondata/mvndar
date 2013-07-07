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

import static com.pushtechnology.mvndar.DARMojo.or;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.archiver.util.DefaultFileSet;

/**
 * Package the provided resources.
 * 
 * @author Philip Aston
 */
class AddDiffusionResourcesTask implements PackagingTask {

    private static final String[] DEFAULT_INCLUDES = new String[] {
	    "data/**",
	    "etc/**",
	    "html/**", };

    private static final String[] DEFAULT_EXCLUDES = new String[] {};

    @Override
    public void perform(final DARMojoContext context) throws IOException {
	final File diffusionDirectory = context.getDiffusionResourceDirectory();

	if (diffusionDirectory.exists()) {
	    final DefaultFileSet fileSet = new DefaultFileSet();

	    fileSet.setDirectory(diffusionDirectory);
	    fileSet.setIncludes(
		    or(context.getDiffusionIncludes(), DEFAULT_INCLUDES));
	    fileSet.setExcludes(
		    or(context.getDiffusionExcludes(), DEFAULT_EXCLUDES));

	    fileSet.setPrefix(
		    context.getPrefixDirectoryName() + File.separator);

	    context.getArchiver().addFileSet(fileSet);
	}
	else {
	    context.getLog().warn(
		    diffusionDirectory + " does not exist, skipping");
	}
    }
}