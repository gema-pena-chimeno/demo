package com.agile.monkeys.demo.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"customers\"")
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private String photo;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @Column
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Setter(AccessLevel.NONE)
    private OffsetDateTime lastUpdated;

    @Column(nullable = false)
    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    // TODO:
    //@JoinColumn(name = "created_by_user_id", nullable = false, updatable = false)
    @JoinColumn(name = "created_by_user_id", updatable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id")
    private User updateBy;
}
