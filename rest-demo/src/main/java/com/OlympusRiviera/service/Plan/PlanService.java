package com.OlympusRiviera.service.Plan;

import com.OlympusRiviera.model.Plan.Plan;

import java.util.List;

public interface PlanService {

    public String createPlan(Plan plan);
    public String updatePlan(Plan plan);
    public String deletePlan(String plan_id);
    public Plan getPlan(String plan_id);
    public List<Plan> getAllPlans();
}
