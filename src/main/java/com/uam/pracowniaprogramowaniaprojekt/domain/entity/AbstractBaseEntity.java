package com.uam.pracowniaprogramowaniaprojekt.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // TODO: 1/10/2022 remove json ignore
    @JsonIgnore
    private LocalDateTime createdDate;

    @JsonIgnore
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
