package kr.ac.ks.app.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    @OneToMany (mappedBy = "student")
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    @Builder
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
