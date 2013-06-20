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

import static java.util.Collections.list;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Examine a JAR and its contents.
 * 
 * @author Philip Aston
 */
class JarReflector {

    private final JarFile jar;

    public JarReflector(final File file) throws IOException {
	jar = new JarFile(file);
    }

    /**
     * Assert that a manifest entry exists and has the given value.
     * 
     * @param key
     *            Entry name.
     * @param value
     *            Value.
     * @throws IOException
     *             If the jar has been closed.
     */
    public void assertEntry(final String key, final String value)
	    throws IOException {
	assertEquals(value, jar.getManifest().getMainAttributes().getValue(key));
    }

    public void assertEntries(final Set<String> entries) {
	final Set<String> containedEntries = new HashSet<String>();

	for (final JarEntry i : list(jar.entries())) {
	    if (i.getName().startsWith("META-INF/")) {
		continue;
	    }

	    if (i.isDirectory()) {
		continue;
	    }

	    containedEntries.add(i.getName());
	}

	assertEquals(entries, containedEntries);
    }
}
