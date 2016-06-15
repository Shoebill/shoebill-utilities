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

package net.gtaun.shoebill.util.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * 
 * @author MK124
 */
public abstract class FilterConfiguration extends AbstractConfiguration implements Configuration
{
	public static Configuration pathPrefixConfiguration(final Configuration config, final String prefix)
	{
		final ConfigurationFilter filter = new ConfigurationFilter()
		{
			@Override
			String filterPath(String path)
			{
				return prefix + path;
			}
		};
		
		return new FilterConfiguration(config, filter)
		{
			@Override
			public Collection<String> getKeyList()
			{
				List<String> list = new ArrayList<>();
				Collection<String> parentList = config.getKeyList(prefix);
				for (String key : parentList) list.add(key.substring(prefix.length()));
				return list;
			}
			
			@Override
			public Collection<String> getKeyList(String path)
			{
				List<String> list = new ArrayList<>();
				Collection<String> parentList = config.getKeyList(filter.filterPath(path));
				for (String key : parentList) list.add(key.substring(prefix.length()));
				return list;
			}
		};
	}
	
	public static Configuration readonlyConfiguration(final Configuration config)
	{
		return new FilterConfiguration
		(
			config, new ConfigurationFilter()
			{
				@Override
				ConfigurationPair filterSetPair(String path, Object value)
				{
					return null;
				}
				
				@Override
				ConfigurationPair filterSetDefaultPair(String path, Object value)
				{
					return null;
				}
			}
		)
		{
			@Override
			public Collection<String> getKeyList()
			{
				return config.getKeyList();
			}
			
			@Override
			public Collection<String> getKeyList(String path)
			{
				return config.getKeyList(path);
			}
		};
	}
	
	
	public static abstract class ConfigurationFilter
	{
		String filterPath(String path)
		{
			return path;
		}
		
		Object filterValue(Object value)
		{
			return value;
		}
		
		ConfigurationPair filterSetPair(String path, Object value)
		{
			return new ConfigurationPair(filterPath(path), value);
		}
		
		ConfigurationPair filterSetDefaultPair(String path, Object value)
		{
			return new ConfigurationPair(filterPath(path), value);
		}
	}
	
	
	private Configuration config;
	private ConfigurationFilter filter;
	
	
	public FilterConfiguration(Configuration config, ConfigurationFilter filter)
	{
		this.config = config;
		this.filter = filter;
	}
	
	@Override
	public boolean contains(String path)
	{
		return config.contains(filter.filterPath(path));
	}
	
	@Override
	public Object get(String path)
	{
		return config.get(filter.filterPath(path));
	}
	
	@Override
	public void set(String path, Object value)
	{
		ConfigurationPair pair = filter.filterSetPair(path, value);
		if (pair == null) return;
		
		config.set(pair.getPath(), pair.getValue());
	}
	
	@Override
	public Configuration getSection(String path)
	{
		return config.getSection(filter.filterPath(path));
	}
	
	@Override
	public void setDefault(String path, Object value)
	{
		ConfigurationPair pair = filter.filterSetDefaultPair(path, value);
		if (pair == null) return;
		
		config.setDefault(pair.getPath(), pair.getValue());
	}
	
	@Override
	public Object getDefault(String path)
	{
		return config.getDefault(filter.filterPath(path));
	}
}
