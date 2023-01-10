package utilities;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ParallelArrayDictionary<Key, Value> implements Map<Key, Value>
{
    protected ArraySet<Key>    _keys;
    protected ArrayList<Value> _values;
	
	public ParallelArrayDictionary()
	{
		_keys = new ArraySet<Key>();
		_values = new ArrayList<Value>();
	}

	@Override
	public int size() { return _keys.size(); }

	@Override
	public boolean isEmpty() { return _keys.isEmpty(); }

	@Override
	public boolean containsKey(Object key) { return _keys.contains(key); }

	@Override
	public boolean containsValue(Object value) { return _values.contains(value); }

	@Override
	public Value get(Object key)
	{
		int index = _keys.indexOf(key);
		
		return index == -1 ? null : _values.get(index);
	}

	@Override
	public Value put(Key key, Value value)
	{
		int index = _keys.indexOf(key);
	
		// Overwrite previous Value and return that previous value
		if (index != -1)
		{
			Value oldValue = _values.get(index);
			
			_values.set(index, value);
			
			return oldValue;
		}
		
		_keys.add(key);
		_values.add(value);
		return null;
	}

	@Override
	public Value remove(Object key)
	{
		int index = _keys.indexOf(key);

		if (index == -1) return null;
		
		_keys.remove(index);
		
		Value oldValue = _values.get(index);
		_values.remove(index);
		
		return oldValue;
	}

	@Override
	public void putAll(Map<? extends Key, ? extends Value> m)
	{
		for (Map.Entry<? extends Key, ? extends Value> pair : m.entrySet())
		{
			put(pair.getKey(), pair.getValue());
		}
	}

	@Override
	public void clear()
	{
		_keys.clear();
		_values.clear();
	}

	@Override
	public Set<Key> keySet() { return _keys; }

	@Override
	public Collection<Value> values() { return _values; }

	@Override
	public Set<Entry<Key, Value>> entrySet()
	{
		Set<Entry<Key, Value>> set = new ArraySet<Entry<Key, Value>>();
		
		for (int i = 0; i < _keys.size(); i++)
		{
			set.add(new AbstractMap.SimpleEntry<Key, Value>(_keys.get(i), _values.get(i)));
		}
		
		return set;
	}
}