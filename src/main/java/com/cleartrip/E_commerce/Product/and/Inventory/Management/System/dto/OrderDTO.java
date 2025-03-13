
package com.cleartrip.E_commerce.Product.and.Inventory.Management.System.dto;

import lombok.*;
        import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private Date orderDate;
    private String status;
}