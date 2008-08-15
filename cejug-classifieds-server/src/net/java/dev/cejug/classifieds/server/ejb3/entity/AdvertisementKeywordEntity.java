/**
 * 
 */
package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "KEYWORD")
public class AdvertisementKeywordEntity extends AbstractEntity {

  @Column(name = "NAME", nullable = false)
  private String name;

  /**
   * @return the name
   */
  public String getName() {

    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(final String name) {
    this.name = name;
  }

}
