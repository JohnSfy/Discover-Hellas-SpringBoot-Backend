//package com.OlympusRiviera.model.Plan;
//
//import com.OlympusRiviera.JsonListConverter;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Table(name = "Plan")
//@Data
//public class Plan {
//
//    @Id
//    private String plan_id;
//
//    private String title;
//
//    // Use a JSON field and store it as a list
//    @Column(columnDefinition = "jsonb")
//    @Convert(converter = JsonListConverter.class) // Custom converter for JSON<->List conversion
//    private List<PlanDetails> plan; // List instead of String
//
//    private String user_id;
//
//    @Column(updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//
//    public Plan() {}
//
//    public Plan(String title, List<PlanDetails> plan, String user_id) {
//        this.plan_id = generateId();
//        this.title = title;
//        this.plan = plan;
//        this.user_id = user_id;
//        this.createdAt = new Date();
//        this.updatedAt = new Date();
//    }
//
//    @PrePersist
//    @PreUpdate
//    public void prePersistAndUpdate() {
//        if (this.createdAt == null) {
//            this.createdAt = new Date();
//        }
//        if (this.plan_id == null) {
//            this.plan_id = generateId();
//        }
//        this.updatedAt = new Date();
//    }
//
//    private String generateId() {
//        return "pln_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
//    }
//}
