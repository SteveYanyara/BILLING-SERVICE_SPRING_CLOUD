package org.sid.billingservice;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.enums.BillStatus;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductIdemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.PagedModel;
import java.util.Date;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
        BillRepository billRepository,
        ProductIdemRepository productIdemRepository,
        CustomerRestClient customerRestClient,
        ProductItemRestClient productItemRestClient,
        RepositoryRestConfiguration restConfiguration
        ){
        restConfiguration.exposeIdsFor(Bill.class);
        return args->{
            Customer customer=customerRestClient.getCustomerById(1L);
           Bill bill1= billRepository.save(new Bill(null,new Date(),null, BillStatus.CREATED, customer.getId(),null));

            PagedModel<Product> productPageModel= productItemRestClient.pageProducts();
            productPageModel.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity((1+new Random().nextInt(100)));
                productItem.setBill(bill1);
                productItem.setProductID(p.getId());
                productIdemRepository.save(productItem);
            });

        };
    }

}
