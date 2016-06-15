/**
 * Copyright (C) 2012 MK124
 * Copyright (C) 2012 JoJLlmAn
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author MK124, JoJLlmAn
 */
public class MapConfiguration extends AbstractConfiguration implements Configuration
{
	@SuppressWarnings("unchecked")
	private static void set(Map<String, Object> map, String path, Object value)
	{
		String[] childs = StringUtils.split(path, '.');
		
		Map<String, Object> node = map;
		for (int i = 0; i < childs.length - 1; i++)
		{
			Object obj = node.get(childs[i]);
			if (obj instanceof Map<?, ?> == false)
			{
				obj = new HashMap<>();
				node.put(childs[i], obj);
			}
			
			node = (Map<String, Object>) obj;
		}
		
		node.put(childs[childs.length - 1], value);
		return;
	}
	
	@SuppressWarnings("unchecked")
	public static Object get(Map<String, Object> map, String path)
	{
		String[] childs = StringUtils.split(path, '.');
		if (childs.length == 0) return map;
		
		Map<String, Object> node = map;
		for (int i = 0; i < childs.length - 1; i++)
		{
			Object obj = node.get(childs[i]);
			if (obj instanceof Map<?, ?> == false) return null;
			node = (HashMap<String, Object>) obj;
		}
		
		return node.get(childs[childs.length - 1]);
	}
	
	@SuppressWarnings("unchecked")
	public static void getKeyList(Collection<String> collection, String path, Map<String, Object> map)
	{
		if (map == null) return;
		try
		{
			for (Entry<String, Object> entry : map.entrySet())
			{
				Object obj = entry.getValue();
				String curPath = path.isEmpty() ? entry.getKey() : path + "." + entry.getKey();
				if (obj instanceof Map<?, ?> == false) collection.add(curPath);
				else getKeyList(collection, curPath, (Map<String, Object>) obj);
			}
		}
		catch (ClassCastException e)
		{
			return;
		}
	}
	
	
	private Map<String, Object> root;
	private Map<String, Object> defRoot;
	
	
	public MapConfiguration()
	{
		this(null);
	}

	public MapConfiguration(Map<String, Object> root)
	{
		this(root, null);
	}
	
	public MapConfiguration(Map<String, Object> root, Map<String, Object> def)
	{
		setRoot((root != null) ? root : new HashMap<String, Object>());
		setDefaultRoot((def != null) ? def : new HashMap<String, Object>());
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	public void setRoot(Map<String, Object> root)
	{
		this.root = root;
	}
	
	public void setDefaultRoot(Map<String, Object> def)
	{
		this.defRoot = def;
	}
	
	public Map<String, Object> getRoot()
	{
		return root;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String path)
	{
		return (Map<String, Object>) get(path);
	}
	
	public void setMap(String path, Map<String, Object> value)
	{
		set(path, value);
	}
	
	@Override
	public boolean contains(String path)
	{
		return get(path) != null;
	}
	
	@Override
	public Object get(String path)
	{
		return get(root, path);
	}
	
	@Override
	public void set(String path, Object value)
	{
		set(root, path, value);
		return;
	}
	
	@Override
	public Configuration getSection(String path)
	{
		return FilterConfiguration.pathPrefixConfiguration(this, path + '.');
	}
	
	@Override
	public Collection<String> getKeyList()
	{
		return getKeyList("");
	}
	
	@Override
	public Collection<String> getKeyList(String path)
	{
		List<String> list = new ArrayList<>();
		getKeyList(list, path, getMap(path));
		return list;
	}
	
	@Override
	public void setDefault(String path, Object value)
	{
		set(defRoot, path, value);
	}
	
	@Override
	public Object getDefault(String path)
	{
		return get(defRoot, path);
	}
}
