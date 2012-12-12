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
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 
 * 
 * @author MK124
 */
public abstract class AbstractConfiguration implements Configuration
{
	public AbstractConfiguration()
	{
		
	}
	
	@Override
	public String getString(String path)
	{
		Object obj = getDefault(path);
		String def = obj != null ? getDefault(path).toString() : "";
		return getString(path, def);
	}
	
	@Override
	public String getString(String path, String def)
	{
		Object obj = get(path);
		return (obj == null) ? def : obj.toString();
	}
	
	@Override
	public void setString(String path, Object value)
	{
		set(path, value.toString());
	}
	
	@Override
	public boolean isString(String path)
	{
		return get(path) instanceof String;
	}
	
	@Override
	public int getInt(String path)
	{
		return getInt(path, 0);
	}
	
	@Override
	public int getInt(String path, int def)
	{
		Object obj = get(path);
		if (obj instanceof Integer) return (Integer)obj;
		return NumberUtils.toInt(obj.toString(), def);
	}
	
	@Override
	public void setInt(String path, int value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isInt(String path)
	{
		return get(path) instanceof Integer;
	}
	
	@Override
	public long getLong(String path)
	{
		return getLong(path, 0L);
	}
	
	@Override
	public long getLong(String path, long def)
	{
		Object obj = get(path);
		if (obj instanceof Long) return (Long)obj;
		return NumberUtils.toLong(obj.toString(), def);
	}
	
	@Override
	public void setLong(String path, long value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isLong(String path)
	{
		return get(path) instanceof Long;
	}
	
	@Override
	public float getFloat(String path)
	{
		return getFloat(path, 0.0f);
	}
	
	@Override
	public float getFloat(String path, float def)
	{
		Object obj = get(path);
		if (obj instanceof Float) return (Float)obj;
		return NumberUtils.toFloat(obj.toString(), def);
	}
	
	@Override
	public void setFloat(String path, float value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isFloat(String path)
	{
		return get(path) instanceof Float;
	}
	
	@Override
	public double getDouble(String path)
	{
		return getDouble(path, 0.0);
	}
	
	@Override
	public double getDouble(String path, double def)
	{
		Object obj = get(path);
		if (obj instanceof Double) return (Double)obj;
		return NumberUtils.toDouble(obj.toString(), def);
	}
	
	@Override
	public void setDouble(String path, double value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isDouble(String path)
	{
		return get(path) instanceof Double;
	}
	
	@Override
	public boolean getBoolean(String path)
	{
		return getBoolean(path, false);
	}
	
	@Override
	public boolean getBoolean(String path, boolean def)
	{
		Object obj = get(path);
		if (obj instanceof Boolean) return (Boolean)obj;
		return BooleanUtils.toBooleanDefaultIfNull(BooleanUtils.toBooleanObject(obj.toString()), def);
	}
	
	@Override
	public void setBoolean(String path, boolean value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isBoolean(String path)
	{
		return get(path) instanceof Boolean;
	}
	
	@Override
	public List<?> getList(String path)
	{
		return getList(path, null);
	}
	
	@Override
	public List<?> getList(String path, List<?> def)
	{
		Object o = get(path);
		if (o instanceof List) return (List<?>) o;
		if (def == null) def = new ArrayList<>(0);
		return def;
	}
	
	@Override
	public void setList(String path, List<?> value)
	{
		set(path, value);
	}
	
	@Override
	public boolean isList(String path)
	{
		return get(path) instanceof List;
	}
	
	@Override
	public List<String> getStringList(String path)
	{
		return getStringList(path, null);
	}
	
	@Override
	public List<String> getStringList(String path, List<String> def)
	{
		List<?> raw = getList(path);
		if (raw == null) return (def != null) ? def : new ArrayList<String>();
		
		List<String> list = new ArrayList<>();
		for (Object o : raw)
		{
			if (o != null) list.add(o.toString());
		}
		
		return list;
	}
	
	@Override
	public List<Integer> getIntList(String path)
	{
		return getIntList(path, null);
	}
	
	@Override
	public List<Integer> getIntList(String path, List<Integer> def)
	{
		List<?> raw = getList(path);
		if (raw == null) return (def != null) ? def : new ArrayList<Integer>();
		
		List<Integer> list = new ArrayList<>();
		for (Object o : raw)
		{
			list.add(NumberUtils.toInt(o.toString(), 0));
		}
		
		return list;
	}
	
	@Override
	public List<Float> getFloatList(String path)
	{
		return getFloatList(path, null);
	}
	
	@Override
	public List<Float> getFloatList(String path, List<Float> def)
	{
		List<?> raw = getList(path);
		if (raw == null) return (def != null) ? def : new ArrayList<Float>();
		
		List<Float> list = new ArrayList<>();
		for (Object o : raw)
		{
			list.add(NumberUtils.toFloat(o.toString(), 0.0f));
		}
		
		return list;
	}
	
	@Override
	public List<Double> getDoubleList(String path)
	{
		return getDoubleList(path, null);
	}
	
	@Override
	public List<Double> getDoubleList(String path, List<Double> def)
	{
		List<?> raw = getList(path);
		if (raw == null) return (def != null) ? def : new ArrayList<Double>();
		
		List<Double> list = new ArrayList<>();
		for (Object o : raw)
		{
			list.add(NumberUtils.toDouble(o.toString(), 0.0));
		}
		
		return list;
	}
	
	@Override
	public List<Boolean> getBooleanList(String path)
	{
		return getBooleanList(path, null);
	}
	
	@Override
	public List<Boolean> getBooleanList(String path, List<Boolean> def)
	{
		List<?> raw = getList(path);
		if (raw == null) return (def != null) ? def : new ArrayList<Boolean>();
		
		List<Boolean> list = new ArrayList<>();
		for (Object o : raw)
		{
			list.add(BooleanUtils.toBoolean(o.toString()));
		}
		
		return list;
	}
}
