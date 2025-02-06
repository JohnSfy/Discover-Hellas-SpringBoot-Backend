package com.DiscoverHellas.service.Plan;

import com.DiscoverHellas.model.Plan.Plan;

import java.util.List;

public interface PlanService {

    public String createPlan(Plan plan);
    public String updatePlan(Plan plan);
    public String deletePlan(String plan_id);
    public Plan getPlan(String plan_id);
    public List<Plan> getAllPlans();
}
