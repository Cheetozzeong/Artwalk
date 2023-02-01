package com.ssafy.a401.artwalk_backend.domain.record.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.a401.artwalk_backend.domain.record.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	int countByRecordId(int recordId);

	List<Record> findByUserId(String userId);
}
