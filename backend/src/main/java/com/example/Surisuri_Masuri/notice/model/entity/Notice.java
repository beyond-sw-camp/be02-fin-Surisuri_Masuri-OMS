package com.example.Surisuri_Masuri.notice.model.entity;

import com.example.Surisuri_Masuri.notice.model.request.PatchUpdateNoticeReq;
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
public class Notice {

    // TODO: 2024-03-10 user 테이블과 관계 맺어야 함 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeIdx;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, length = 1)
    private Boolean status;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    public void update(PatchUpdateNoticeReq patchUpdateNoticeReq) {
        if (patchUpdateNoticeReq.getCategory() != null) {
            this.category = patchUpdateNoticeReq.getCategory();
        }
        if (patchUpdateNoticeReq.getTitle() != null) {
            this.title = patchUpdateNoticeReq.getTitle();
        }
        if (patchUpdateNoticeReq.getContent() != null) {
            this.content = patchUpdateNoticeReq.getContent();
        }
        if (patchUpdateNoticeReq.getStatus() != null) {
            this.status = patchUpdateNoticeReq.getStatus();
        }
    }
}

