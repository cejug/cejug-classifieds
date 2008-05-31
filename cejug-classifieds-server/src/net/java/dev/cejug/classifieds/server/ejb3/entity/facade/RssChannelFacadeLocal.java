package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.generated.contract.Channel;

@Local
public interface RssChannelFacadeLocal extends DomainModelFacade<Channel> {

}
