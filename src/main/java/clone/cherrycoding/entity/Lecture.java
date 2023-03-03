package clone.cherrycoding.entity;

import clone.cherrycoding.dto.LectureRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Lecture extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String imageUrl;

    private int price;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "lecture", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Enroll> enrollList = new ArrayList<>();

    public Lecture(LectureRequestDto requestDto, String imageUrl, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = imageUrl;
        this.price = requestDto.getPrice();
        this.user = user;
    }

    public void update(LectureRequestDto requestDto, String imageUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.imageUrl = imageUrl;
        this.price = requestDto.getPrice();
    }
}
