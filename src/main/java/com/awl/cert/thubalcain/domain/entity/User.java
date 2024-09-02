package com.awl.cert.thubalcain.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_user")
    private Long seqUser;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "enum ('ADMIN', 'USER', 'SPECIAL')")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(nullable = false)
    private LocalDateTime createDtm;
    @Column(nullable = false)
    private LocalDateTime updateDtm;
    private LocalDateTime deleteDtm;

    public void changeDeleteDtm(LocalDateTime deleteDtm) {
        this.deleteDtm = deleteDtm;
    }
}
