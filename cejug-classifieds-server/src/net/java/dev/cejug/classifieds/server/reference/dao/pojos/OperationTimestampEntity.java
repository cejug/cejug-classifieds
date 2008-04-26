package net.java.dev.cejug.classifieds.server.reference.dao.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@SuppressWarnings("unused")
@Entity
@Table(name = "response_time")
public class OperationTimestampEntity {
	public OperationTimestampEntity(OperationTimestamp stamp) {
		operationName = stamp.getOperationName();
		// start = stamp.getStart().toGregorianCalendar();
		// finish = stamp.getFinish().toGregorianCalendar();
		status = stamp.isStatus();
		clientId = stamp.getClientId();
		fault = stamp.getFault();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String operationName;
	/*
	 * @Column(nullable = false)
	 * 
	 * private GregorianCalendar start; @Column(nullable = false) private
	 * GregorianCalendar finish;
	 */
	@Column(nullable = false)
	private Boolean status;
	@Column(nullable = false)
	private String clientId;
	@Column(nullable = true)
	private String fault;
}
