package com.matheusjfa.Digibank.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class auditory {
	@CreatedBy
	@Column(name = "created_by", nullable = false, updatable = false)
	protected UUID createdBy;

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Instant createdDate;

	@LastModifiedBy
	@Column(name = "last_modified_by", nullable = false)
	protected UUID lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Instant lastModifiedDate;
}

