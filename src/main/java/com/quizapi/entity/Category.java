package com.quizapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;
    @Column(nullable = false,unique = true)
    private String categoryName;
    private String categoryDescription;

    private String categoryImageUrl;
    private String categoryImagePublicId;

    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Quiz> quizList = new ArrayList<>();
}
