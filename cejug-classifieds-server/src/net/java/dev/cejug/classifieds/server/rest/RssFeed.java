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
package net.java.dev.cejug.classifieds.server.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsBusinessLocal;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
import net.java.dev.cejug_classifieds.rss.Rss;

/**
 * REST rss feed, to allow other applications to consume the RSS feed directly
 * on the wire, without the need of soap envelopes. READ-ONLY operations are
 * suitable for REST protocol (this is experimental, eventually we will replace
 * this servlet by a jersey code).
 */
public class RssFeed extends HttpServlet {

	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	@EJB
	private transient ClassifiedsBusinessLocal business;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SyndicationFilter filter = new SyndicationFilter();
		Rss rss = business.loadRssOperation(filter);

		// Little trick: http://www.petefreitag.com/item/381.cfm
		String agent = request.getHeader("User-Agent");
		if (agent != null && agent.toUpperCase().contains("MOZILLA")) {
			response.setContentType("text/xml");
		} else {
			response.setContentType("application/rss+xml");
		}

		PrintWriter out = response.getWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(
					"net.java.dev.cejug_classifieds.rss", Thread
							.currentThread().getContextClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller
					.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
							"https://cejug-classifieds.dev.java.net/files/documents/8128/108281/rss.xsd");
			marshaller.marshal(rss, out);
		} catch (Exception ee) {
			ee.printStackTrace(out);
			out.print("<error>" + ee.getMessage() + "</error>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		super.doPost(req, resp);
	}
}
