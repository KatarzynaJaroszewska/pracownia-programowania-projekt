package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractBaseEntity extends AbstractIdentityEntity {

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Version
    private Long version;

    @PrePersist
    public void createDate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = createdDate;
    }

    @PreUpdate
    public void updateDate() {
        this.updatedDate = LocalDateTime.now();
    }

}
