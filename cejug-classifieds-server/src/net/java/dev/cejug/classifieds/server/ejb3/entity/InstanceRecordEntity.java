package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("unused")
@Entity
@Table(name = "instance_record")
public class InstanceRecordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	@Temporal(TemporalType.DATE)
	private Date start;
	@Column
	@Temporal(TemporalType.DATE)
	private Date finish;
	@Column
	private String obs;
}
