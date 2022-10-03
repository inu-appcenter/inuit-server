package pj.circles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pj.circles.domain.enumType.PhotoType;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Photo {
    @Id
    @GeneratedValue
    @Column(name="photo_id")
    private Long id;
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
        this.photoType= PhotoType.서브;
    }
    public void setCircle(Circle circle){
        this.circle=circle;
    }
    public void setMain(){
        this.photoType=PhotoType.메인;
    }
    public void setSub(){this.photoType=PhotoType.서브;}}
