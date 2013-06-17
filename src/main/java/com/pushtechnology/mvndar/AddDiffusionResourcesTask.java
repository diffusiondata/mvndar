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
	final File diffusionDirectory = context.getDiffusionDirectory();

	if (diffusionDirectory.exists()) {
	    context.getArchiver().addDirectory(
		    diffusionDirectory,
		    or(context.getDiffusionIncludes(), DEFAULT_INCLUDES),
		    or(context.getDiffusionExcludes(), DEFAULT_EXCLUDES)
		    );
	}
	else {
	    context.getLog().warn(
		    diffusionDirectory + " does not exist, skipping");
	}
    }
}