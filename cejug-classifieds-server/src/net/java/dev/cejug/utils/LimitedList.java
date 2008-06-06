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

import java.util.ArrayList;

/**
 * @author $Author: mar nufelipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
public final class LimitedList extends ArrayList<Long> {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_LIMIT = 10;
	private final int LIMIT;

	public LimitedList(int limit) {
		LIMIT = limit;
	}

	public LimitedList() {
		this(DEFAULT_LIMIT);
	}

	@Override
	public synchronized boolean add(Long e) {
		if (size() == LIMIT) {
			super.remove(0);
		}
		return super.add(e);
	}

	public synchronized double getAverage() {
		long count = 0L;
		for (Long value : this) {
			count += value;
		}
		return Double.longBitsToDouble(count) / size();
	}
}
