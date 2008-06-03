package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "domain")
@NamedQuery(name = "selectDomainByName", query = "SELECT d FROM DomainEntity d WHERE d.domain= :par1")
public class DomainEntity {

    @Column(nullable = false)
    private Boolean sharedQuota;

    @Id
    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String brand;

    public String getDomain() {

        return domain;
    }

    public void setDomain(String domain) {

        this.domain = domain;
    }

    public Boolean getSharedQuota() {

        return sharedQuota;
    }

    public void setSharedQuota(Boolean sharedQuota) {

        this.sharedQuota = sharedQuota;
    }

    public String getBrand() {

        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

}
