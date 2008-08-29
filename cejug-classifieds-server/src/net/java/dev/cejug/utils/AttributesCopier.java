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
package net.java.dev.cejug.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.ws.WebServiceException;

public class AttributesCopier<S, T> {
  public static final String GET_PREFIX = "get";
  public static final String SET_PREFIX = "set";

  public void copyValuesByAttributeNames(S source, T target) throws SecurityException,
      NoSuchMethodException, IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    try {
      Method[] targetMethods = target.getClass().getMethods();
      Class<?> sourceType = source.getClass();
      Method[] m = sourceType.getMethods();
      for(Method mm : m) {
        System.out.println("M: " + mm.getName());
      }
      for (Method setter : targetMethods) {
        String setterName = setter.getName();
        if (setterName.startsWith(SET_PREFIX)) {
          String getterName = GET_PREFIX + setterName.substring(3);
          try{
            System.out.println("GETTER NAME = '" + getterName + "'");
            Method getter = 
              sourceType.getMethod(getterName, new Class[0]);
            if (getter != null) {
              Class<?> getType = getter.getReturnType();
              Class<?>[] setTypes = setter.getParameterTypes();
              if (setTypes.length == 1 && setTypes[0].isAssignableFrom(getType)) {
                Object sourceAttributeValue = getter.invoke(source, new Object[0]);
                setter.invoke(target, new Object[]{sourceAttributeValue});
                System.out.println(getterName + "() --> " + setterName + "() OK :)");
              }
            }
          } catch(NoSuchMethodException error) {
            System.out.println("NAO EXISTE: " + error.getMessage());
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new WebServiceException(e);
    }
  }
}
