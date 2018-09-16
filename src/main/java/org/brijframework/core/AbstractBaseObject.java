package org.brijframework.core;

import static org.brijframework.util.builder.BuilderUtil.getCurrentInstance;

import org.brijframework.BaseObject;
import org.brijframework.meta.blueprint.ClassInfo;
import org.brijframework.util.asserts.AssertMessage;
import org.brijframework.util.asserts.Assertion;
import org.brijframework.util.meta.PointUtil;

public abstract class AbstractBaseObject implements BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T setProperty(String _keyPath, T _value) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = getCurrentInstance(getInstance(),_keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message+" "+_keyPath);
		return setProperty(keyInstance,PointUtil.keyPoint(_keyPath), _value);
	}

	@Override
	public <T> T getProperty(String _keyPath) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = getCurrentInstance(getInstance(),_keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message+" "+_keyPath);
		return getProperty(keyInstance,PointUtil.keyPoint(_keyPath));
	}

	@Override
	public Boolean containsProperty(String _key) {
		return null;
	}

	@Override
	public Boolean containsValue(String _key) {
		return null;
	}

	@Override
	public Class<?> typeOfProperty(String _key) {
		return null;
	}
	
	protected Object getInstance() {
		return this;
	}
	
	protected abstract ClassInfo getClassInfo(Object keyInstance);
	
	protected abstract <T> T setProperty(Object keyInstance, String keyPoint, T _value);
	
	protected abstract <T> T getProperty(Object keyInstance, String keyPoint) ;

}
