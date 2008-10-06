package net.java.dev.cejug.classifieds.service.http;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * The expires filter adds the expires HTTP header based on the deployment
 * policy. Many sites have a fixed deployment schedule where deployments take
 * place based on timed regular intervals. This filter adds the expires header
 * of the next possible deployment time, to support browser caching.
 * 
 * @author Chris Webster
 * @see <a href='http://blogs.sun.com/cwebster/entry/caching_static_resources_in_glassfish'>Cachi
 *      n g static resources in glassfish</a>
 */
public class ExpiresFilter implements Filter {

	private transient FilterConfig filterConfig;
	private transient final String expires;
	private transient long nextDeploymentTime;

	public ExpiresFilter() {
		// expires = nextDeploymentTime();
		// TODO: re-think the best way to configure expire header, including the
		// split between the real static and the other expirable contents.
		expires = "Thu, 15 Apr 2010 20:00:00 GMT";
	}

	/*
	 * private String nextDeploymentTime() { // assume next deployment is M-F at
	 * 09:45 Calendar c = Calendar.getInstance(); c.set(Calendar.DAY_OF_MONTH,
	 * 31); c.set(Calendar.MONTH, Calendar.AUGUST); c.set(Calendar.MONTH,
	 * Calendar.AUGUST);
	 * 
	 * c.set(Calendar.DAY_OF_MONTH, 31);
	 * 
	 * int dayOffset = 1;
	 * 
	 * if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) { dayOffset+=2; }
	 * 
	 * if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) { dayOffset++; }
	 * 
	 * c.add(Calendar.DAY_OF_MONTH, dayOffset); c.set(c.get(Calendar.YEAR),
	 * c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 9, 45);
	 * 
	 * nextDeploymentTime = c.getTimeInMillis();
	 * 
	 * String pattern = "EEE, dd MMM yyyy HH:mm:ss z"; SimpleDateFormat sdf =
	 * new SimpleDateFormat(pattern);
	 * sdf.setTimeZone(TimeZone.getTimeZone("GMT")); return
	 * sdf.format(c.getTime()); }
	 */

	private void addCacheHeaders(ServletResponse response) throws IOException,
			ServletException {

		HttpServletResponse sr = (HttpServletResponse) response;
		sr.setHeader("Expires", expires);

		long now = (new Date()).getTime();

		long expireTime = nextDeploymentTime - now;
		expireTime %= 1000;
		sr.setHeader("Cache-Control", "max-age=" + Long.toString(expireTime)
				+ ";public;must-revalidate;");

	}

	/**
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * @param chain
	 *            The filter chain we are processing
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		addCacheHeaders(response);
		chain.doFilter(request, response);
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	private FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * Set the filter configuration object for this filter.
	 * 
	 * @param filterConfig
	 *            The filter configuration object
	 */
	private void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 * 
	 */
	public void destroy() {

	}

	/**
	 * Init method for this filter
	 * 
	 */
	public void init(FilterConfig filterConfig) {
		setFilterConfig(filterConfig);
	}

	/**
	 * Return a String representation of this object.
	 */
	@Override
	public String toString() {
		if (getFilterConfig() == null) {
			return ("ExpiresFilter()");
		}
		StringBuffer sb = new StringBuffer("ExpiresFilter(");
		sb.append(getFilterConfig());
		sb.append(')');
		return (sb.toString());

	}
}
