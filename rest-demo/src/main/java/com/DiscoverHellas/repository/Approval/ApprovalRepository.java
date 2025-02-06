package com.DiscoverHellas.repository.Approval;

import com.DiscoverHellas.model.Approval.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, String> {
}
