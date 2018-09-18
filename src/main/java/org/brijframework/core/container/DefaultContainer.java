package org.brijframework.core.container;

import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;

import org.brijframework.container.Container;
import org.brijframework.group.Group;
import org.brijframework.util.asserts.AssertMessage;
import org.brijframework.util.asserts.Assertion;

public interface DefaultContainer extends Container{
	
	@Override
	public default Group add(Object groupKey, Group group) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(group,AssertMessage.arg_null_message+" -> group");
		Assertion.isTrue(getCache().get(groupKey) != null,AssertMessage.allready_contains+"->"+groupKey);
		return getCache().put(groupKey, group);
	}

	@Override
	public default Group update(Object groupKey, Group group) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.isTrue(getCache().get(groupKey)== null,AssertMessage.key_not_contains+"->"+groupKey);
		return getCache().replace(groupKey, group);
	}

	@Override
	public default Group remove(Object groupKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.isTrue(getCache().get(groupKey) != null,AssertMessage.key_not_contains+"->"+groupKey);
		return getCache().remove(groupKey);
	}

	@Override
	public  default Group get(Object groupKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		return getCache().get(groupKey);
	}
	
	@Override
	public default Group search(Object groupKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		if(getCache().get(groupKey)!=null){
			return getCache().get(groupKey);
		}
		Enumeration<Group> enumeration=getCache().elements();
		while(enumeration.hasMoreElements()){
			Group group=enumeration.nextElement();
			if(groupKey.toString().equalsIgnoreCase(""+group.getGroupKey())){
				return group;
			}
		}
		return null;
	}
	
	@Override
	public default <T> T add(Object groupKey, Object dataKey, T object) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(dataKey,AssertMessage.arg_null_or_empty_message+" -> dataKey");
		Assertion.isTrue(getCache().get(groupKey) == null,AssertMessage.key_not_contains+" "+groupKey);
		return getCache().get(groupKey).add(dataKey,object);
	}

	@Override
	public default <T> T remove(Object groupKey, Object dataKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(dataKey,AssertMessage.arg_null_or_empty_message+" -> dataKey");
		Assertion.isTrue(getCache().get(groupKey) != null,AssertMessage.key_not_contains);
		return getCache().get(groupKey).remove(dataKey);
	}

	@Override
	public default <T> T update(Object groupKey, Object dataKey, T object) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(dataKey,AssertMessage.arg_null_or_empty_message+" -> dataKey");
		Assertion.isTrue(getCache().get(groupKey) != null,AssertMessage.key_not_contains);
		return getCache().get(groupKey).update(dataKey,object);
	}

	@Override
	public default <T> T get(Object groupKey, Object dataKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(dataKey,AssertMessage.arg_null_or_empty_message+" -> dataKey");
		Assertion.isTrue(getCache().get(groupKey) != null,AssertMessage.key_not_contains);
		return getCache().get(groupKey).get(dataKey);
	}
	
	@Override
	public default Group merge(Object groupKey, Group group) {
		Group findGroup=get(groupKey);
		if(findGroup != null){
			return findGroup;
		}
		return this.add(groupKey, group);
	}

	
	@Override
	public default <T> T search(Object groupKey, Object dataKey) {
		Assertion.notNull(groupKey,AssertMessage.arg_null_or_empty_message+" -> groupKey");
		Assertion.notNull(dataKey,AssertMessage.arg_null_or_empty_message+" -> dataKey");
		Group group=search(groupKey);
		Assertion.notNull(group,AssertMessage.group_not_found_message+" -> groupKey");
		return group.find(dataKey);
	}
	
	@Override
	public default <T> T search(Object groupKey, String parentID, Class<?> type) {
		Group group=get(groupKey);
		if(group==null) {
			return null;
		}
		return group.find(parentID,type);
	}
	
	@SuppressWarnings("unchecked")
	public default <T> T find(Object find) {
		Assertion.notNull(find,AssertMessage.arg_null_or_empty_message+" -> find");
		Set<Entry<Object, Group>> enumeration=getCache().entrySet();
		for(Entry<Object, Group> entry:enumeration) {
			Object data=entry.getValue().find(find);
			if(data!=null){
				return (T) data;
			}
		}
		return null;
	}
	
	public default Container reLoadContainer() {
		clearContainer();
		return this.loadContainer();
	}

	public default Container clearContainer() {
		this.getCache().clear();
		return this;
	}
}
