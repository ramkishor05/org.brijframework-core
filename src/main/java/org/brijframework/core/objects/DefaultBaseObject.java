package org.brijframework.core.objects;

import org.brijframework.BaseObject;
import org.brijframework.meta.reflect.ClassMetaInfo;
import org.brijframework.util.asserts.AssertMessage;
import org.brijframework.util.asserts.Assertion;
import org.brijframework.util.meta.PointUtil;

public interface DefaultBaseObject extends BaseObject {

	@Override
	public default <T> T setProperty(String _keyPath, T _value) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = this.getCurrentInstance(getInstance(), _keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message + " " + _keyPath);
		if(!this.equals(keyInstance) && BaseObject.class.isAssignableFrom(keyInstance.getClass())) {
			BaseObject baseObject=(BaseObject) keyInstance;
			return baseObject.setProperty(_keyPath, _value);
		}else {
			return setProperty(keyInstance, PointUtil.keyPoint(_keyPath), _value);
		}
	}

	@Override
	public default <T> T getProperty(String _keyPath) {
		Assertion.notEmpty(_keyPath, "Key should not be null or empty");
		Object keyInstance = this.getCurrentInstance(getInstance(),_keyPath);
		Assertion.notNull(keyInstance, AssertMessage.root_object_null_message + " " + _keyPath);
		if(BaseObject.class.isAssignableFrom(keyInstance.getClass()) &&!this.equals(keyInstance)) {
			BaseObject baseObject=(BaseObject) keyInstance;
			return baseObject.getProperty(_keyPath);
		}else {
		   return getProperty(keyInstance, PointUtil.keyPoint(_keyPath));
		}
	}

	@Override
	public default Boolean containsKey(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.arg_null_message);
		String _key = PointUtil.keyPoint(_keyPath);
		Class<?> current = this.getCurrentClass(getInstance().getClass(), _keyPath);
		return this.getProperty(current, _key) != null;
	}

	@Override
	public default Boolean containsValue(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.properties_null_message);
		if (!this.containsKey(_keyPath)) {
			return false;
		}
		return getProperty(_keyPath) != null;
	}

	@Override
	public default Class<?> typeOfProperty(String _keyPath) {
		Assertion.notNull(_keyPath, AssertMessage.arg_null_message);
		String _key = PointUtil.keyPoint(_keyPath);
		Class<?> current = this.getCurrentClass(getInstance().getClass(), _keyPath);
		return this.getProperty(current, _key);
	}

	public default Object getInstance() {
		return this;
	}
	
	public abstract Class<?> getCurrentClass(Class<? extends Object> class1, String _keyPath);

	public abstract Object getCurrentInstance(Object instance, String _keyPath);
	
	public abstract ClassMetaInfo getClassInfo(Object keyInstance) ;
	
	public abstract <T> T setProperty(Object keyInstance, String keyPoint, T _value);

	public abstract <T> T getProperty(Object keyInstance, String keyPoint);

}
