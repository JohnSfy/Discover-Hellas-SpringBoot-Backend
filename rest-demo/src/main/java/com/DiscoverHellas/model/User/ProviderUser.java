package com.DiscoverHellas.model.User;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "provider_user")
@Data
public class ProviderUser extends User {

    private String company_name;
    private String tin;

    @Column(columnDefinition = "TEXT") // Allow larger content
    private String legal_document_tin; // pistopoiitiko afm
    @Column(columnDefinition = "TEXT") // Allow larger content
    private String legal_document_id; //taftotita

    private String status;


    public ProviderUser() {
    }

    public ProviderUser(String username, String firstname, String lastname, String email, Role role, String password, String googleId, String photo, String phone, String address,
                        String company_name, String tin, String legal_document_tin, String legal_document_id, String status) {
        super(username, firstname, lastname, email, role, password, googleId, photo, phone, address);  // Calls the User constructor
        this.company_name = company_name;
        this.tin = tin;
        this.legal_document_tin = legal_document_tin;
        this.legal_document_id = legal_document_id;
        this.status = status;

    }


}
