/**
 * Copyright (C) 2012 MK124
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gtaun.shoebill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author MK124
 */
public class ShoebillArtifactLocator
{
	public static final String COORD_TOKEN_FLAT_SEPARATOR = "#";
	private static final String JAR_EXTENSION = ".jar";
	
	
	private ShoebillConfig shoebillConfig;
	private ResourceConfig resourceConfig;
	
	
	public ShoebillArtifactLocator(ShoebillConfig shoebillConfig, ResourceConfig resourceConfig)
	{
		this.shoebillConfig = shoebillConfig;
		this.resourceConfig = resourceConfig;
	}

	public File getRuntimeFile()
	{
		return getArtifaceJarFile(resourceConfig.getRuntime(), shoebillConfig.getLibrariesDir());
	}
	
	public File getLibraryFile(String coord)
	{
		return getArtifaceJarFile(coord, shoebillConfig.getLibrariesDir());
	}
	
	public File getPluginFile(String coord)
	{
		return getArtifaceJarFile(coord, shoebillConfig.getPluginsDir());
	}
	
	public List<File> getPluginFiles()
	{
		List<String> pluginCoords = resourceConfig.getPlugins();
		List<File> result = new ArrayList<>(pluginCoords.size());
		
		File pluginsDir = shoebillConfig.getPluginsDir();
		
		for (String coord : pluginCoords)
		{
			File file = getArtifaceJarFile(coord, pluginsDir);
			if (file != null) result.add(file);
		}
		
		return result;
	}
	
	public File getGamemodeFile()
	{
		return getArtifaceJarFile(resourceConfig.getGamemode(), shoebillConfig.getGamemodesDir());
	}
	
	public File getOverrideFile(String coord)
	{
		File file = null;
		file = getArtifactJarFileFromFlatRepo(coord, shoebillConfig.getLibrariesDir());
		if (file == null) file = getArtifactJarFileFromFlatRepo(coord, shoebillConfig.getPluginsDir());
		if (file == null) file = getArtifactJarFileFromFlatRepo(coord, shoebillConfig.getGamemodesDir());
		return file;
	}
	
	private File getArtifaceJarFile(String coord, File dir)
	{
		File file = null;
		file = getArtifactJarFileFromFlatRepo(coord, dir);
		if (file == null) file = getArtifactJarFileFromMavenLocalRepo(coord, shoebillConfig.getRepositoryDir());
		return file;
	}
	
	private File getArtifactJarFileFromFlatRepo(String coord, File dir)
	{
		String[] tokens = coord.split(":");
		if (tokens.length != 3)
		{
			String filename = coord + JAR_EXTENSION;
			File file = new File(dir, filename);
			
			if (file.exists()) return file;
			return null;
		}
		
		String groupId = tokens[0];
		String artifactId = tokens[1];
		String version = tokens[2];
		
		String filename = groupId + COORD_TOKEN_FLAT_SEPARATOR + artifactId + "-" + version + JAR_EXTENSION;
		File file = new File(dir, filename);
		
		if (file.exists()) return file;
		
		if (shoebillConfig.isAllowArtifactOverrideIgnoreGroupId())
		{
			filename = artifactId + "-" + version + JAR_EXTENSION;
			file = new File(dir, filename);
			
			if (file.exists()) return file;
		}
	
		return null;
	}
	
	private File getArtifactJarFileFromMavenLocalRepo(String coord, File dir)
	{
		String[] tokens = coord.split(":");
		if (tokens.length != 3) return null;
		
		String groupId = tokens[0];
		String artifactId = tokens[1];
		String version = tokens[2];
		
		String path = dir.getPath() + File.separator;
		String[] groupTokens = StringUtils.split(groupId, '.');
		for (String child : groupTokens)
		{
			path += child + File.separator;
		}

		path += artifactId + File.separator + version + File.separator;
		path += artifactId + "-" + version + JAR_EXTENSION;
		
		File file = new File(path);
		
		if (file.exists() == false) return null;
		return file;
	}
}
