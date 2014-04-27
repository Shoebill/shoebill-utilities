/**
 * Copyright (C) 2012 JoJLlmAn
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.gtaun.shoebill.util.config.MapConfiguration;
import net.gtaun.shoebill.util.config.YamlConfiguration;

/**
 * 
 * 
 * @author JoJLlmAn, MK124
 */
public class ResourceConfig
{
	public static final class RepositoryEntry
	{
		private String id;
		private String type;
		private String url;
		private String username;
		private String password;
		
		RepositoryEntry(MapConfiguration section)
		{
			id = section.getString("id");
			url = section.getString("url");
			type = section.getString("type", "default");
			username = section.getString("username", null);
			password = section.getString("password", null);
		}
		
		public String getId()
		{
			return id;
		}
		
		public String getType()
		{
			return type;
		}
		
		public String getUrl()
		{
			return url;
		}
		
		public String getUsername()
		{
			return username;
		}
		
		public String getPassword()
		{
			return password;
		}
	}
	
	
	private List<RepositoryEntry> repositories;
	private String cacheUpdatePolicy;
	private boolean offlineMode;
	
	private List<String> runtimes;
	private List<String> plugins;
	private String gamemode;
	
	
	@SuppressWarnings("unchecked")
	public ResourceConfig(InputStream in)
	{
		YamlConfiguration config = new YamlConfiguration();
		config.setDefault("cacheUpdatePolicy", "never");
		
		config.read(in);
		
		List<Map<String, Object>> repoMaps = (List<Map<String, Object>>) config.getList("repositories");
		repositories = new ArrayList<>(repoMaps.size());
		cacheUpdatePolicy = config.getString("cacheUpdatePolicy");
		offlineMode = config.getBoolean("offlineMode");
		
		for (Map<String, Object> map : repoMaps)
		{
			repositories.add(new RepositoryEntry(new MapConfiguration(map)));
		}
		
		runtimes = (List<String>) config.getList("runtimes");
		plugins = (List<String>) config.getList("plugins");
		gamemode = config.getString("gamemode");
	}
	
	public List<RepositoryEntry> getRepositories()
	{
		return repositories;
	}
	
	public String getCacheUpdatePolicy()
	{
		return cacheUpdatePolicy;
	}
	
	public boolean isOfflineMode()
	{
		return offlineMode;
	}
	
	public List<String> getRuntimes()
	{
		return Collections.unmodifiableList(runtimes);
	}
	
	public List<String> getPlugins()
	{
		return Collections.unmodifiableList(plugins);
	}
	
	public String getGamemode()
	{
		return gamemode;
	}
}
