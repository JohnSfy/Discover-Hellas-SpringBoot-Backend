package com.OlympusRiviera.service.Approval;

import com.OlympusRiviera.model.Approval.Approval;

import java.util.List;

public interface ApprovalService {

    public Approval getApproval(String approval_id);
    public List<Approval> getAllApprovals();

    public String createApproval(Approval approval);

}
