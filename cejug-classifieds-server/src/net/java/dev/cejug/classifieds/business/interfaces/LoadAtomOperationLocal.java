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
package net.java.dev.cejug.classifieds.business.interfaces;

import javax.ejb.Local;

import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

import org.w3._2005.atom.FeedType;

/**
 * @author $Author: felipegaucho $
 * @version $Rev $ ($Date: 2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
@Local
public interface LoadAtomOperationLocal {
	/**
	 * @return <a href=
	 *         "http://en.wikipedia.org/wiki/Atom_(standard)#Example_of_an_Atom_1.0_Feed"
	 *         >Example:</a>
	 * 
	 *         <pre>
	 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
	 * &lt;feed xmlns=&quot;http://www.w3.org/2005/Atom&quot;&gt;
	 *  
	 *  &lt;title&gt;Example Feed&lt;/title&gt;
	 *  &lt;subtitle&gt;A subtitle.&lt;/subtitle&gt;
	 *  &lt;link href=&quot;http://example.org/feed/&quot; rel=&quot;self&quot;/&gt;
	 *  &lt;link href=&quot;http://example.org/&quot;/&gt;
	 *  &lt;updated&gt;2003-12-13T18:30:02Z&lt;/updated&gt;
	 *  &lt;author&gt;
	 *    &lt;name&gt;John Doe&lt;/name&gt;
	 *    &lt;email&gt;johndoe@example.com&lt;/email&gt;
	 *  &lt;/author&gt;
	 *  &lt;id&gt;urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6&lt;/id&gt;
	 *  
	 *  &lt;entry&gt;
	 *    &lt;title&gt;Atom-Powered Robots Run Amok&lt;/title&gt;
	 *    &lt;link href=&quot;http://example.org/2003/12/13/atom03&quot;/&gt;
	 *    &lt;id&gt;urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a&lt;/id&gt;
	 *    &lt;updated&gt;2003-12-13T18:30:02Z&lt;/updated&gt;
	 *    &lt;summary&gt;Some text.&lt;/summary&gt;
	 *  &lt;/entry&gt;
	 *  
	 * &lt;/feed&gt;
	 * </pre>
	 */
	FeedType loadAtomOperation(SyndicationFilter filter);
}