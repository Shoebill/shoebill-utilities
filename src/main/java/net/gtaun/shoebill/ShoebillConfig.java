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

import org.apache.commons.lang3.StringUtils;
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
	private File repositoryDir;
	private File librariesDir;
	private File pluginsDir;
	private File gamemodesDir;
	private File dataDir;

	private boolean resolveDependencies;
	private boolean allowArtifactOverrideIgnoreGroupId;
	private int serverCodepage;


	public ShoebillConfig(InputStream in)
	{
		YamlConfiguration config = new YamlConfiguration();
		config.setDefault("shoebillPath", "shoebill");
		config.setDefault("folder.repository", "repository");
		config.setDefault("folder.libraries", "libraries");
		config.setDefault("folder.plugins", "plugins");
		config.setDefault("folder.gamemodes", "gamemodes");
		config.setDefault("folder.data", "data");
		config.setDefault("resolveDependencies", true);
		config.setDefault("allowArtifactOverrideIgnoreGroupId", true);
		config.setDefault("serverCodepage", 1252);

		config.read(in);

		shoebillDir = new File(config.getString("shoebillPath") + File.separator);

        String currentUserRepository = System.getProperty("user.home") + File.separator + ".m2" +
                File.separator + "repository";


		String customRepositoryPath = config.getString("customRepositoryPath");
		File customRepositoryDir = new File(customRepositoryPath + File.separator);
		repositoryDir = (!StringUtils.isBlank(customRepositoryPath)) && customRepositoryDir.isDirectory() ?
				customRepositoryDir : new File(currentUserRepository + File.separator);

		librariesDir = new File(shoebillDir, config.getString("folder.libraries") + File.separator);
		pluginsDir = new File(shoebillDir, config.getString("folder.plugins") + File.separator);
		gamemodesDir = new File(shoebillDir, config.getString("folder.gamemodes") + File.separator);
		dataDir = new File(shoebillDir, config.getString("folder.data") + File.separator);

		if (!librariesDir.isDirectory()) librariesDir.mkdirs();
		if (!pluginsDir.isDirectory()) pluginsDir.mkdirs();
		if (!gamemodesDir.isDirectory()) gamemodesDir.mkdirs();
		if (!dataDir.isDirectory()) dataDir.mkdirs();

		resolveDependencies = config.getBoolean("resolveDependencies");
		allowArtifactOverrideIgnoreGroupId = config.getBoolean("allowArtifactOverrideIgnoreGroupId");
		serverCodepage = config.getInt("serverCodepage");
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

	public File getDataDir()
	{
		return dataDir;
	}

	public boolean isResolveDependencies()
	{
		return resolveDependencies;
	}

	public boolean isAllowArtifactOverrideIgnoreGroupId()
	{
		return allowArtifactOverrideIgnoreGroupId;
	}

	public int getServerCodepage()
	{
		return serverCodepage;
	}
}