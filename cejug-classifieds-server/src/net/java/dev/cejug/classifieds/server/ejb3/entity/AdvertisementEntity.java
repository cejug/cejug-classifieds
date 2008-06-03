package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "advertisement")
@NamedQuery(name = "selectAdvertisementByFilter", query = "SELECT adv FROM AdvertisementEntity adv")
public class AdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String keywords;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<PublishingPeriodEntity> publishingPeriod;

    @OneToOne(mappedBy = "advertisement")
    private VoucherEntity voucher;

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSummary() {

        return summary;
    }

    public void setSummary(String summary) {

        this.summary = summary;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public VoucherEntity getVoucher() {

        return voucher;
    }

    public void setVoucher(VoucherEntity voucher) {

        this.voucher = voucher;
    }

    public String getKeywords() {

        return keywords;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
    }

    public Collection<PublishingPeriodEntity> getPublishingPeriod() {

        return publishingPeriod;
    }

    public void setPublishingPeriod(Collection<PublishingPeriodEntity> publishingPeriod) {

        this.publishingPeriod = publishingPeriod;
    }
}
