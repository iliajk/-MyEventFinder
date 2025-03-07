package com.MyEventFinder.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
    protected Boolean deleted = Boolean.FALSE;
    protected LocalDateTime deleted_at = null;
}
