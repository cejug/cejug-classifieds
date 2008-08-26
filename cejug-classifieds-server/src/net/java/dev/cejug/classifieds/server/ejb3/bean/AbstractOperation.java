/*
 * @(#) $CVSHeader:  $
 *
 * Copyright (C) 2008 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Netcetera AG, Switzerland.  The program(s) may be used and/or copied
 * only with the written permission of Netcetera AG or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 *
 * @(#) $Id: codetemplates.xml,v 1.5 2004/06/29 12:49:49 hagger Exp $
 */
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

import org.junit.Ignore;

public abstract class AbstractOperation {

	protected CategoryEntity fillCategoryEntity(
			final AdvertisementCategory advCategory) {
		CategoryEntity category = new CategoryEntity();
		category.setId(advCategory.getId());
		category.setDescripton(advCategory.getDescription());
		category.setName(advCategory.getName());
		return category;
	}

	@Ignore(value = "under construction!")
	protected Object copyValuesByAttributeNames(Object source, Class target)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		try {
			Constructor defaultConstructor = target
					.getConstructor(new Class[0]);
			Object targetInstance = defaultConstructor
					.newInstance(new Object[0]);
			Method[] targetMethods = target.getMethods();
			Class sourceType = source.getClass();

			for (Method method : targetMethods) {
				String methodName = method.getName();
				if (method.isAccessible() && methodName.startsWith("set")) {
					String getterMethodName = "get"
							+ method.getName().substring(3);
					Method sourceMethod = sourceType.getMethod(
							getterMethodName, new Class[0]);
					System.out.println("ZZZZZZZ" + sourceMethod.getName());
					if (sourceMethod != null) {
						Object sourceAttributeValue = sourceMethod.invoke(
								source, new Object[0]);
						System.out.println("LLLLLL" + sourceAttributeValue);
						method.invoke(targetInstance,
								new Object[] { sourceAttributeValue });
					}
				}
			}
			return targetInstance;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebServiceException(e);
		}
	}

	protected AdvertisementCategory fillAdvertisementCategory(
			final CategoryEntity entity) {
		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setId(entity.getId());
		cat.setDescription(entity.getDescripton());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());
		return cat;
	}
}
