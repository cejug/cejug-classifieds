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

@Entity
@Table(name = "response_time")
public class OperationTimestampEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String operationName;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date start;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date finish;
	@Column(nullable = false)
	private Boolean status;
	@Column(nullable = false)
	private String clientId;
	@Column(nullable = true)
	private String fault;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return (Date) finish.clone();
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

}
