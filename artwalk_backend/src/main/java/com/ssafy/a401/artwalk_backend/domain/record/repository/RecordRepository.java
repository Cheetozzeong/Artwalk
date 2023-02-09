package com.ssafy.a401.artwalk_backend.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	long countByRecordId(int recordId);

	long countByUserId(String userId);

	Record findByLink(String link);

	Record findByEditLink(String editLink);

	List<Record> findByUserId(String userId);

	List<Record> findByUserIdContaining(String userId);

	List<Record> findByDetailContaining(String detail);
}
