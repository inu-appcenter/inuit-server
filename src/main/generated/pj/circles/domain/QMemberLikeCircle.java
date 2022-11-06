package pj.circles.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberLikeCircle is a Querydsl query type for MemberLikeCircle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberLikeCircle extends EntityPathBase<MemberLikeCircle> {

    private static final long serialVersionUID = -1106174388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberLikeCircle memberLikeCircle = new QMemberLikeCircle("memberLikeCircle");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCircle circle;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public QMemberLikeCircle(String variable) {
        this(MemberLikeCircle.class, forVariable(variable), INITS);
    }

    public QMemberLikeCircle(Path<? extends MemberLikeCircle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberLikeCircle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberLikeCircle(PathMetadata metadata, PathInits inits) {
        this(MemberLikeCircle.class, metadata, inits);
    }

    public QMemberLikeCircle(Class<? extends MemberLikeCircle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.circle = inits.isInitialized("circle") ? new QCircle(forProperty("circle"), inits.get("circle")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

