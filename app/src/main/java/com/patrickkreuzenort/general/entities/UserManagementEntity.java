package com.patrickkreuzenort.general.entities;

import jakarta.persistence.*;

public class UserManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Version
    private int modificationCounter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getModificationCounter() {
        return modificationCounter;
    }

    public void setModificationCounter(int modificationCounter) {
        this.modificationCounter = modificationCounter;
    }
}
