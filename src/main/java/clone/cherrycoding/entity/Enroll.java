package clone.cherrycoding.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    public Enroll (User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
    }
}
