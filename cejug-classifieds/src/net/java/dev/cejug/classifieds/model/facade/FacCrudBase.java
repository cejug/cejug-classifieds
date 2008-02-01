package net.java.dev.cejug.classifieds.model.facade;

import java.util.List;

/**
*
* Façade base to all CRUD.
* 
* @author Rafael Carneiro [rafaelcarneirob@gmail.com]
* @since 02/01/2008
*/
public interface FacCrudBase<Entity> {
	
	public Entity create(Entity entity);
	
	public Entity update(Entity entity);
	
	public Entity delete(Entity entity);
	
	public Entity find(Entity entity);
	
	public List<Entity> list(Entity entity);
	
}
