package com.ghost.erp.common.base_class;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at",nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    protected LocalDateTime updatedAt;

    @Column(nullable = false)
    protected boolean active = true;

    // =================== JPA LIFECYCLE ===================

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // =================== GETTERS ===================

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    // =================== SETTERS ===================

    public void setActive(boolean active) {
        this.active = active;
    }
}
