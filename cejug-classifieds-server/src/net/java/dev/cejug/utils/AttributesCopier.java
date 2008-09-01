/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class to copy the values from an object to another - using the
 * attributes names and types matching as criteria of copying.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 520 $ ($Date: 2008-08-25 20:11:50 +0200 (Mon, 25 Aug 2008) $)
 */
public class AttributesCopier<S, T> {

	public static final String IS_PREFIX = "is";
	public static final String GET_PREFIX = "get";
	public static final String SET_PREFIX = "set";
	public static final int PREFIX_LENGTH = 3;
	boolean identicalTypes = false;
	private Logger logger = null;

	public AttributesCopier(boolean identicalTypes) {
		this.identicalTypes = identicalTypes;
	}

	public AttributesCopier() {
		this(false);
	}

	/*
	 * TODO: check the getter methods with return type List on the target
	 * object. such lists have no getter methods if generated by JAXB, so we
	 * need to check if the return is assignable by List interface and also if
	 * the object exist. If it exists then we should use Collections.copy()
	 * instead of calling a setter that probably doesn't exist.
	 */
	public void copyValuesByAttributeNames(S source, T target)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		logger = Logger.getLogger("AttributesCopier<" + source + ", " + target
				+ ">", "i18n/log");
		Method[] setterCandidates = target.getClass().getMethods();

		for (Method setter : setterCandidates) {
			String setterName = setter.getName();
			if (setterName.startsWith(SET_PREFIX)) {
				Class<?>[] setterParamTypes = setter.getParameterTypes();
				if (setterParamTypes.length == 0) {
					// nothing to copy - WARNING: no setBoolean pattern
					// not supported, it requires external mapping (not yet
					// implemented).
					throw new IllegalArgumentException(
							"empty setter method not supported: "
									+ setter.toString());
				} else if (setterParamTypes[0].isArray()) {
					// array copy - iteration required.
					arrayCopy(source, target, setter);
				} else if (setterParamTypes[0].isAssignableFrom(List.class)) {
					// Collection requires recursvive deep copy.
					listCopy(source, target, setter);
				} else { // if (setterParamTypes[0].isPrimitive() ||
					// setterParamTypes
					// [0].isAssignableFrom(String.class)) {
					// shallow copy attempt
					shallowCopy(source, target, setter);
				}
			}
		}
	}

	private void arrayCopy(S source, T target, Method setter) {
		// TODO Auto-generated method stub
		logger.severe("Arrays not yet implemented");
		throw new IllegalArgumentException("Arrays copy not yet implemented"
				+ setter.toString());
	}

	private void listCopy(S source, T target, Method setter) {
		logger.severe("Arrays not yet implemented");
		throw new IllegalArgumentException("List copy not yet implemented"
				+ setter.toString());
	}

	/**
	 * If the identicalTypes flag is set to false and if the objects are not
	 * instances of identical types (i.e., same number and types of attributes),
	 * it will ignore attributes without equivalence between the
	 * <code>source</code> and <code>target</code> objects.
	 * 
	 * @param source
	 * @param target
	 * @param setter
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void shallowCopy(S source, T target, Method setter)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class<?>[] setterParamTypes = setter.getParameterTypes();
		Class<?> sourceType = source.getClass();
		String getterName;

		if (setterParamTypes[0].isAssignableFrom(boolean.class)
				|| setterParamTypes[0].isAssignableFrom(Boolean.class)) {
			getterName = IS_PREFIX + setter.getName().substring(PREFIX_LENGTH);
		} else {
			getterName = GET_PREFIX + setter.getName().substring(PREFIX_LENGTH);
		}

		try {
			Method getter = sourceType.getMethod(getterName, new Class[0]);
			Class<?> getType = getter.getReturnType();
			System.out.println("Class<?> getType = " + getType + " : "
					+ setterParamTypes[0]);
			if (setterParamTypes[0].isAssignableFrom(getType)) {
				Object sourceAttributeValue = getter.invoke(source,
						new Object[0]);
				setter.invoke(target, new Object[] { sourceAttributeValue });
			}
		} catch (NoSuchMethodException ignorable) {
			if (identicalTypes) {
				throw ignorable;
			}
			// else, it will copy only the equivalent attributes.
		}
	}
}