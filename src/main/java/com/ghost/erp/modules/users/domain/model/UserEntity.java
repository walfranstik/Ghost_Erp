package com.ghost.erp.modules.users.domain.model;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.sales.domain.model.SaleEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email")
    },
    indexes = {
        // 1. Para filtrar usuarios por rol rápidamente
        @Index(name = "idx_user_role", columnList = "role"),
        
        // 2. Para el borrado lógico heredado de BaseEntity
        @Index(name = "idx_user_active", columnList = "active")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class UserEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private int failedLoginAttempts;

    @Column
    private LocalDateTime accountLockedUntil;


    // ================= RELATIONSHIPS =================
    @Builder.Default
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<SaleEntity> sales = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<SellerCommissionEntity> commissions = new ArrayList<>();

    public enum Role {
        ADMIN,
        SALES_PERSON
    }

    // ================= business methods =================
    
    public boolean isAccountLocked() {
    return accountLockedUntil != null &&
           accountLockedUntil.isAfter(LocalDateTime.now());
    }

    public void resetLoginAttempts() {
        this.failedLoginAttempts = 0;
        this.accountLockedUntil = null;
    }

    public void increaseLoginAttempts(int maxAttempts, int lockMinutes) {
        this.failedLoginAttempts++;

        if (this.failedLoginAttempts >= maxAttempts) {
            this.accountLockedUntil =
                LocalDateTime.now().plusMinutes(lockMinutes);
        }
    }

}
