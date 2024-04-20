package com.datn.monolithic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 100)
    private String name;

    @Column(length = 1000)
    private String path;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Picture(String name, String path, User user) {
        this.name = name;
        this.path = path;
        this.user = user;
    }
}
