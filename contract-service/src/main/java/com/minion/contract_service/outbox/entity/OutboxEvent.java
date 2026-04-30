package com.minion.contract_service.outbox.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outbox_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String aggregateType;

	@Column(nullable = false, length = 50)
	private String aggregateId;

	@Column(nullable = false, length = 50)
	private String eventType;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String payload;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OutboxStatus status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime publishedAt;

	public enum OutboxStatus {
		PENDING,
		PUBLISHED
	}

	@Builder
	public OutboxEvent(String aggregateType, String aggregateId,
		String eventType, String payload) {
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.status = OutboxStatus.PENDING;
		this.createdAt = LocalDateTime.now();
	}

	public void markPublished() {
		this.status = OutboxStatus.PUBLISHED;
		this.publishedAt = LocalDateTime.now();
	}
}