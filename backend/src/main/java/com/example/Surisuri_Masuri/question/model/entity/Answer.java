package com.example.Surisuri_Masuri.question.model.entity;

import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerIdx;

    private String answerContent;

    private Date createdAt;
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "Question_idx")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "Manager_idx")
    private Manager manager;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
