/**
 * 
 */
package net.java.dev.cejug.classifieds.server.ejb3.interceptor;

import java.util.Date;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.xml.ws.WebServiceException;
import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.OperationTimeKeeperLocal;

/**
 * @author rodrigo
 * 
 */
public class TimerInterceptor {

    @EJB
    OperationTimeKeeperLocal timeKeeperFacade;

    /*
     * Intercepter method within the bean (the bean is the aspect)
     */
    @AroundInvoke
    public Object timerLog(InvocationContext ctx) throws Exception {

        // TODO: include timezone...
        Date start = new Date();
        try {
            return ctx.proceed();
        } finally {
            try {
                OperationTimestampEntity stamp = new OperationTimestampEntity();
                stamp.setOperationName(ctx.getMethod().getName());
                stamp.setStart(start);
                stamp.setFinish(new Date());
                stamp.setStatus(true);
                stamp.setClientId("TODO: get client ID");
                timeKeeperFacade.update(stamp);
            } catch (Exception error) {
                throw new WebServiceException(error);
            }
        }
    }
}
