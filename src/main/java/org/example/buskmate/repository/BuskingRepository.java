package org.example.buskmate.repository;

import org.example.buskmate.domain.Busking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuskingRepository extends JpaRepository<Busking, String> {
}
