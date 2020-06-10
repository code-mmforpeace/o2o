package com.ouver.o2o.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	//需要加密的对象
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

    /**
     * 关键属性进行转换
     * @param propertyName
     * @param propertyValue
     * @return
     */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
		    //对已经加密的对象进行解密
			String decryptValue = DESUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

    /**
     * 判断是不是已经加密
     * @param propertyName
     * @return
     */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}
