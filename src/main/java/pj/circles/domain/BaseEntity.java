package pj.circles.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Slf4j
public abstract class BaseEntity {

    public static final String COLUMN_NAME_ID = "id";

    public static final String COLUMN_NAME_CREATED_DATE = "created_date";

    public static final String COLUMN_NAME_LAST_MODIFIED_DATE = "last_modified_date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_NAME_ID, nullable = false, insertable = false, updatable = false)
    private Long id;

    @CreatedDate
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_CREATED_DATE, nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_MODIFIED_DATE, updatable = false)
    private LocalDateTime lastModifiedDate;
}
