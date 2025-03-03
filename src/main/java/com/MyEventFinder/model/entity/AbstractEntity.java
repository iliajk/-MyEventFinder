package com.MyEventFinder.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public abstract class AbstractEntity {
    protected Boolean deleted;
    protected LocalDateTime deleted_at;
}
