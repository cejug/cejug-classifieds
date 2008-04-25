package net.java.dev.cejug.classifieds.server.reference.dao.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.datatype.XMLGregorianCalendar;

import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@SuppressWarnings("unused")
@Entity
@Table(name = "response_time")
public class ResponseTimeEntity {
	public ResponseTimeEntity(OperationTimestamp stamp) {
		operationName = stamp.getOperationName();
		start = stamp.getStart();
		finish = stamp.getFinish();
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
	private XMLGregorianCalendar start;
	@Column(nullable = false)
	private XMLGregorianCalendar finish;
	@Column(nullable = false)
	private Boolean status;
	@Column(nullable = false)
	private String clientId;
	@Column(nullable = true)
	private String fault;
}
