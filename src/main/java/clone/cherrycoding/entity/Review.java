package clone.cherrycoding.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewTitle;

    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
