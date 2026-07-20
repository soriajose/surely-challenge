package com.surely.challenge.user.model;

import com.surely.challenge.user.exception.UserException;
import java.time.LocalDate;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String email;
    private boolean vip;

    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;
    private String deletedBy;
    private LocalDate deletedAt;

    private User(String firstName, String lastName, String documentNumber, String email, boolean vip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.email = email;
        this.vip = vip;
    }

    public static User factoryUser(String firstName, String lastName, String documentNumber, String email, boolean vip) {

        if (firstName == null || firstName.isBlank()) {
            throw new UserException("El nombre no puede ser nulo");
        }

        if (lastName == null || firstName.isBlank()) {
            throw new UserException("El apellido no puede ser nulo");
        }

        if (documentNumber == null || documentNumber.isBlank()) {
            throw new UserException("El documento no puede ser nulo");
        }

        if (email == null || email.isBlank()) {
            throw new UserException("El email no puede ser nulo");
        }

        return new User(firstName, lastName, documentNumber, email, vip);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }
}
