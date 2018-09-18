package org.brijframework.core.objects;

import java.util.Map;

import org.brijframework.BaseModel;

public interface DefaultBaseModel extends BaseModel, DefaultBaseObject{

	default Map<String, ?> setProperties(String[] _keys, Object... _values){
		
		return null;
	}
}
