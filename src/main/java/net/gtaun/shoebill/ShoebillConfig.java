/**
 * Copyright (C) 2011 JoJLlmAn
 * Copyright (C) 2011-2012 MK124
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
import java.io.InputStream;

import net.gtaun.shoebill.util.config.YamlConfiguration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author JoJLlmAn, MK124
 */
public class ShoebillConfig
{
	private File shoebillDir;
	private File bootstrapDir;
	private File repositoryDir;
	private File librariesDir;
	private File pluginsDir;
	private File gamemodesDir;
	
	private boolean resolveDependencies;

	
	public ShoebillConfig(InputStream in)
	{
		YamlConfiguration config = new YamlConfiguration();
		config.read(in);

		shoebillDir = new File(config.getString("shoebill", "shoebill") + File.separator);
		bootstrapDir = new File(shoebillDir, config.getString("folder.bootstrap", "bootstrap") + File.separator);
		repositoryDir = new File(shoebillDir, config.getString("folder.repository", "repository") + File.separator);
		librariesDir = new File(shoebillDir, config.getString("folder.libraries", "libraries") + File.separator);
		pluginsDir = new File(shoebillDir, config.getString("folder.plugins", "plugins") + File.separator);
		gamemodesDir = new File(shoebillDir, config.getString("folder.gamemodes", "gamemodes") + File.separator);
		
		resolveDependencies = config.getBoolean("resolveDependencies", true);
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	public File getShoebillDir()
	{
		return shoebillDir;
	}
	
	public File getBootstrapDir()
	{
		return bootstrapDir;
	}
	
	public File getRepositoryDir()
	{
		return repositoryDir;
	}
	
	public File getLibrariesDir()
	{
		return librariesDir;
	}
	
	public File getPluginsDir()
	{
		return pluginsDir;
	}
	
	public File getGamemodesDir()
	{
		return gamemodesDir;
	}
	
	public boolean isResolveDependencies()
	{
		return resolveDependencies;
	}
}