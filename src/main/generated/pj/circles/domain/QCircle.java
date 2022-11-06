package pj.circles.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCircle is a Querydsl query type for Circle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCircle extends EntityPathBase<Circle> {

    private static final long serialVersionUID = 2090783579L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCircle circle = new QCircle("circle");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath cafeLink = createString("cafeLink");

    public final EnumPath<pj.circles.domain.enumType.CircleCategory> circleCategory = createEnum("circleCategory", pj.circles.domain.enumType.CircleCategory.class);

    public final EnumPath<pj.circles.domain.enumType.CircleDivision> circleDivision = createEnum("circleDivision", pj.circles.domain.enumType.CircleDivision.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath information = createString("information");

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath link = createString("link");

    public final QMember member;

    public final ListPath<MemberLikeCircle, QMemberLikeCircle> memberLikeCircles = this.<MemberLikeCircle, QMemberLikeCircle>createList("memberLikeCircles", MemberLikeCircle.class, QMemberLikeCircle.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath oneLineIntroduce = createString("oneLineIntroduce");

    public final StringPath openKakaoLink = createString("openKakaoLink");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<Photo, QPhoto> photos = this.<Photo, QPhoto>createList("photos", Photo.class, QPhoto.class, PathInits.DIRECT2);

    public final BooleanPath recruit = createBoolean("recruit");

    public final DateTimePath<java.time.LocalDateTime> recruitEndDate = createDateTime("recruitEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> recruitStartDate = createDateTime("recruitStartDate", java.time.LocalDateTime.class);

    public QCircle(String variable) {
        this(Circle.class, forVariable(variable), INITS);
    }

    public QCircle(Path<? extends Circle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCircle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCircle(PathMetadata metadata, PathInits inits) {
        this(Circle.class, metadata, inits);
    }

    public QCircle(Class<? extends Circle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

