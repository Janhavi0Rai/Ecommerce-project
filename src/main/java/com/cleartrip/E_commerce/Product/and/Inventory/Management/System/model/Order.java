
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model;

import jakarta.persistence.*;
        import lombok.*;
        import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Date orderDate;
    private String status;
}
