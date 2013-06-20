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
package com.pushtechnology.mvndar.stubs;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Properties;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;

/**
 * Model stub.
 * 
 * @author Philip Aston
 */
public class DARModelStub extends Model {

    private final Properties properties = new Properties();

    @Override
    public String getVersion() {
	return "0.0-TEST";
    }

    @Override
    public String getModelVersion() {
	return "0.0-TEST";
    }

    @Override
    public String getName() {
	return "Test Model";
    }

    @Override
    public String getGroupId() {
	return "com.pushtechnology.mvndar.test";
    }

    @Override
    public String getPackaging() {
	return "dar";
    }

    @Override
    public Parent getParent() {
	return new Parent();
    }

    @Override
    public String getArtifactId() {
	return "maven-test-plugin";
    }

    @Override
    public Properties getProperties() {
	return properties;
    }

    @Override
    public List<String> getProfiles() {
	return emptyList();
    }

    @Override
    public List<String> getModules() {
	return emptyList();
    }
}
