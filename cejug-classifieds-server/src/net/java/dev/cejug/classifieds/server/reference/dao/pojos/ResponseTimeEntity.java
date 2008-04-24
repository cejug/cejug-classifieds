package net.java.dev.cejug.classifieds.server.reference.dao.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.java.dev.cejug.classifieds.server.generated.contract.ResponseTime;

@Entity
@Table(name = "response_time")
@SuppressWarnings("unused")
public class ResponseTimeEntity {
	public ResponseTimeEntity(ResponseTime source) {
		operationName = source.getOperationName();
		average = source.getAverage();
		calls = source.getCalls();
	}

	public ResponseTimeEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String operationName;
	@Column
	private Long average;
	@Column
	private Long calls;
}
