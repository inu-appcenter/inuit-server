package pj.circles.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmail is a Querydsl query type for Email
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmail extends EntityPathBase<Email> {

    private static final long serialVersionUID = 1870510001L;

    public static final QEmail email1 = new QEmail("email1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final SimplePath<Object> checked = createSimple("checked", Object.class);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isCheck = createBoolean("isCheck");

    public final BooleanPath isJoin = createBoolean("isJoin");

    public final BooleanPath joined = createBoolean("joined");

    //inherited
    public final NumberPath<Long> lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public QEmail(String variable) {
        super(Email.class, forVariable(variable));
    }

    public QEmail(Path<? extends Email> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmail(PathMetadata metadata) {
        super(Email.class, metadata);
    }

}

