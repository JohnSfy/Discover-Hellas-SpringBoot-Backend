package com.DiscoverHellas.service.impl.Approval;


import com.DiscoverHellas.model.Approval.Approval;
import com.DiscoverHellas.repository.Approval.ApprovalRepository;
import com.DiscoverHellas.service.Approval.ApprovalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalServiceimpl implements ApprovalService {

    ApprovalRepository approvalRepository;

    public ApprovalServiceimpl(ApprovalRepository approvalRepository) {
        this.approvalRepository = approvalRepository;
    }

    public Approval getApproval(String approval_id) {

        return approvalRepository.findById(approval_id).get();
    }

    @Override
    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }

    @Override
    public String createApproval(Approval approval) {
        approvalRepository.save(approval);  // Save the approval in the database
        return "Successfully created";
    }

    public String updateApproval(Approval approval) {
//        more logic
        approvalRepository.save(approval);
        return "Update Success";
    }
}
