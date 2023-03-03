package clone.cherrycoding.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String imageUrl;

    private int price;

    private boolean isEnrolled;

    @OneToMany(mappedBy = "lecture", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "lecture", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Enroll> enrollList = new ArrayList<>();
}
