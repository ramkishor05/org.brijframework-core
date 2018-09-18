package org.brijframework.core.group;

import java.util.Map.Entry;
import java.util.Set;

import org.brijframework.group.Group;
import org.brijframework.util.asserts.AssertMessage;
import org.brijframework.util.asserts.Assertion;
import org.brijframework.util.validator.ValidationUtil;

public interface DefaultGroup extends Group{
	
	public static final String DEFAULT = "default";
	
	@Override
	default boolean containsKey(Object dataKey) {
		return getCache().containsKey(dataKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T add(Object dataKey, T value) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message + " left");
		Assertion.notNull(value, AssertMessage.arg_null_or_empty_message + " right");
		Assertion.isTrue(getCache().get(dataKey) != null, AssertMessage.allready_contains + " " + dataKey);
		getCache().put(dataKey, value);
		return (T) value;
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T marge(Object dataKey, T value) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message + " left");
		Assertion.notNull(value, AssertMessage.arg_null_or_empty_message + " right");
		getCache().put(dataKey, value);
		return (T) value;
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T remove(Object dataKey) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message);
		return (T) getCache().remove(dataKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T get(Object dataKey) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message);
		dataKey = ValidationUtil.isEmptyObject(dataKey) ? DEFAULT : dataKey;
		return (T) getCache().get(dataKey);
	}
	@SuppressWarnings("unchecked")
	@Override
	default <T> T getIgnoreCase(Object dataKey) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message);
		dataKey = ValidationUtil.isEmptyObject(dataKey) ? DEFAULT : dataKey;
		Set<Entry<Object, Object>> entries= getCache().entrySet();
		for(Entry<Object, Object> entry:entries){
			if(entry.getKey().toString().equalsIgnoreCase(dataKey.toString())){
				return (T) entry.getValue();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	default <T> T update(Object dataKey, T value) {
		Assertion.notNull(dataKey, AssertMessage.arg_null_or_empty_message);
		return (T) getCache().replace(dataKey, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T find(Object dataKey) {
		dataKey = ValidationUtil.isEmptyObject(dataKey) ? DEFAULT : dataKey;
		Object search = get(dataKey);
		if (search != null) {
			return (T) search;
		}
		Set<Entry<Object, Object>> enumeration = getCache().entrySet();
		for(Entry<Object, Object>entry: enumeration) {
			if (entry.getKey().equals(dataKey)) {
				return (T) entry.getValue();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T indexOfValue(int i) {
		return (T) getCache().values().toArray()[i];
	}

	@SuppressWarnings("unchecked")
	@Override
	default <T> T indexOfKey(int i) {
		return (T) getCache().keySet().toArray()[i];
	}
	
	

}
