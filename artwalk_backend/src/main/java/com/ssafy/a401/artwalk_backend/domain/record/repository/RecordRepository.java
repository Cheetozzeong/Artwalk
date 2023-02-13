package com.ssafy.a401.artwalk_backend.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	long countByRecordId(int recordId);

	long countByUserId(String userId);

	Record findByLink(String link);

	Record findByEditLink(String editLink);

	List<Record> findByUserIdOrderByRecordIdDesc(String userId);

	List<Record> findByUserIdContainingOrderByRecordIdDesc(String userId);

	List<Record> findByDetailContainingOrderByRecordIdDesc(String detail);
}
