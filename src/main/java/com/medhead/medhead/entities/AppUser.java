package com.medhead.medhead.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity(name = "AppUser")

public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, columnDefinition = "nvarchar(60)", unique = true)
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "nvarchar(60)")
    private String password;

    @ManyToMany
    @JoinColumn(name = "id")
    private List<AppAuthorization> Authorizations;

    @Column(name = "validated", nullable = false)
    private boolean isValidated = false;

    @OneToOne(targetEntity = AccountType.class)
    @JoinColumn(nullable = false)
    private AccountType accountType;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public List<AppAuthorization> getAuthorizations() {
        return Authorizations;
    }

    public void setAuthorizations(List<AppAuthorization> authorizations) {
        Authorizations = authorizations;
    }

    public AppUser() {
    }

    public AppUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
