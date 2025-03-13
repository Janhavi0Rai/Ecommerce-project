
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model;

import com.cleartrip.E_commerce.Product.and.Inventory.Management.System.model.Product;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int stockQuantity;
}
