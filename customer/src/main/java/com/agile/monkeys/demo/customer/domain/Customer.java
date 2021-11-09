package com.agile.monkeys.demo.customer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    //@GeneratedValue(generator="system-uuid")
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    //@NotNull // TODO: keep?
    @Column(name = "first_name", nullable = false)
    private String firstName;

    //@NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

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

    @Column
    @Version
    private Long version;

    // TODO: remove?
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
