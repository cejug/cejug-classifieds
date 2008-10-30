package net.java.dev.cejug.classifieds.view.jsf;

import net.java.dev.cejug.classifieds.model.service.adapter.WSFactoryHelper;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;

import org.apache.myfaces.trinidad.context.RequestContext;

/**
 * An utility class to access the <code>PageFlowScope</code>.
 * 
 * @author Tarso Bessa
 * 
 */

public class RequestContextUtils {
	/**
	 * Put the object <code>value</code> in the <code>PageFlowScope</code> under
	 * the name <code>name</code>.
	 * 
	 * @param name
	 *            the name of the object
	 * @param value
	 *            the value to be stored
	 */
	public static void put(String name, Object value) {
		CejugClassifiedsBusiness service = WSFactoryHelper.getWebService();
		BundleRequest request = new BundleRequest();
		CategoryCollection categories = service
				.readCategoryBundleOperation(request);
		RequestContext.getCurrentInstance().getPageFlowScope().put(
				name,
				"the server has " + categories.getAdvCategory().size()
						+ " categories");
	}

	/**
	 * Returna the object from <code>PageFlowScope</code> under the name
	 * <code>name</code>.
	 * 
	 * @param name
	 *            the name to retrieve the object
	 * @return
	 */
	public static Object get(String name) {
		return RequestContext.getCurrentInstance().getPageFlowScope().get(name);
	}
}
