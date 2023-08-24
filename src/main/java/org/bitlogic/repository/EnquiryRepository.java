package org.bitlogic.repository;

import java.util.List;

import org.bitlogic.model.EnquiryForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryForm, Integer>{
	public List<EnquiryForm> findAllByStatus(String status);
}
