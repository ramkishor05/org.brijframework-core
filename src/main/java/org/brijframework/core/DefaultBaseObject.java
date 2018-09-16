package org.brijframework.core;

import static org.brijframework.util.builder.BuilderUtil.getCurrentInstance;

import org.brijframework.BaseObject;
import org.brijframework.meta.blueprint.ClassInfo;
import org.brijframework.util.asserts.AssertMessage;
import org.brijframework.util.asserts.Assertion;
import org.brijframework.util.builder.BuilderUtil;
import org.brijframework.util.meta.PointUtil;

public interface DefaultBaseObject extends BaseObject {

	@Override
	default <T> T setProperty(String _keyPath, T _value) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = getCurrentInstance(getInstance(), _keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message + " " + _keyPath);
		return setProperty(keyInstance, PointUtil.keyPoint(_keyPath), _value);
	}

	@Override
	default <T> T getProperty(String _keyPath) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = getCurrentInstance(getInstance(), _keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message + " " + _keyPath);
		return getProperty(keyInstance, PointUtil.keyPoint(_keyPath));
	}

	@Override
	default Boolean containsProperty(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.arg_null_message);
		String _key = PointUtil.keyPoint(_keyPath);
		Class<?> current = BuilderUtil.getCurrentClass(getInstance().getClass(), _keyPath);
		return BuilderUtil.getProperty(current, _key) != null;
	}

	@Override
	default Boolean containsValue(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.properties_null_message);
		if (!this.containsProperty(_keyPath)) {
			return false;
		}
		return getProperty(_keyPath) != null;
	}

	@Override
	default Class<?> typeOfProperty(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.arg_null_message);
		String _key = PointUtil.keyPoint(_keyPath);
		Class<?> current = BuilderUtil.getCurrentClass(getInstance().getClass(), _keyPath);
		return BuilderUtil.getProperty(current, _key);
	}

	default Object getInstance() {
		return this;
	}

	abstract ClassInfo getClassInfo(Object keyInstance) ;
	
	abstract <T> T setProperty(Object keyInstance, String keyPoint, T _value);

	abstract <T> T getProperty(Object keyInstance, String keyPoint);

}
