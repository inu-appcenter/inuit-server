package pj.circles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import pj.circles.domain.enumType.PhotoType;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Slf4j
public class Photo extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private PhotoType photoType;
    private String uploadFileName;
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circle_id")
    private Circle circle;

    public Photo(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.photoType = PhotoType.서브;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setMain() {
        this.photoType = PhotoType.메인;
    }

    public void setSub() {
        this.photoType = PhotoType.서브;
    }
}
