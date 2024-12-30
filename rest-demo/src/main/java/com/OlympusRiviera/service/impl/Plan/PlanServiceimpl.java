package com.OlympusRiviera.service.impl.Plan;

import com.OlympusRiviera.model.Plan.Plan;
import com.OlympusRiviera.repository.Plan.PlanRepository;
import com.OlympusRiviera.service.Plan.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceimpl implements PlanService {

    PlanRepository planRepository;

    public PlanServiceimpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // Implement the methods for interacting with the plan repository
    @Override
    public String createPlan(Plan plan) {
        planRepository.save(plan);
        return "Success";
    }

    @Override
    public String updatePlan(Plan plan) {
//        more logic
        planRepository.save(plan);
        return "Update Success";
    }

    @Override
    public String deletePlan(String plan_id) {
        planRepository.deleteById(plan_id);
        return "Deleted Success";
    }

    @Override
    public Plan getPlan(String plan_id) {

        return planRepository.findById(plan_id).get();
    }

    @Override
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
}
