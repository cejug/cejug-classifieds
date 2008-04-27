package net.java.dev.cejug.classifieds.server.reference.dao.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@Entity
@Table(name = "response_time")
public class OperationTimestampEntity {
	public OperationTimestampEntity(OperationTimestamp stamp) {
		operationName = stamp.getOperationName();
		start = stamp.getStart().toGregorianCalendar().getTime();
		finish = stamp.getFinish().toGregorianCalendar().getTime();
		status = stamp.isStatus();
		clientId = stamp.getClientId();
		fault = stamp.getFault();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String operationName;
	@Column(nullable = false)
	private Date start;
	@Column(nullable = false)
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
		return finish;
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
