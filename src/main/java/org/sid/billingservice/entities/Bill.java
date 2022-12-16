package org.sid.billingservice.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billingservice.enums.BillStatus;
import org.sid.billingservice.model.Customer;
//import org.sid.billingservice.model.Product;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    @OneToMany(mappedBy = "bill")
    private Collection<ProductItem> productItems;
    private BillStatus billStatus;
    private Long customerId;
    @Transient
    private Customer customer;
   // @Transient
   // private Product product;
public double getTotal(){
    double somme=0;
    for(ProductItem pi:productItems){
        somme+=pi.getAmount();
    }
    return somme;
}
}
