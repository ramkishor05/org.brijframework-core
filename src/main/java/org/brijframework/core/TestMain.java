package org.brijframework.core;

import org.brijframework.meta.factories.ClassInfoFactory;
import org.brijframework.util.reflect.PackUtil;

public class TestMain {

	public static void main(String[] args) {
		for(Class<?> cls:PackUtil.getClassList()) {
			if(ClassInfoFactory.class.isAssignableFrom(cls))
			System.out.println(cls);
		}
	}
}
