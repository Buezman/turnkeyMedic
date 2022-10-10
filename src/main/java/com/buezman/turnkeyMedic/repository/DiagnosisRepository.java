package com.buezman.turnkeyMedic.repository;

import com.buezman.turnkeyMedic.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
