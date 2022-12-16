package org.sid.billingservice.feign;

import org.sid.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//permet de communiquer avec un micro service via Rest dans ce cas CUSTOMER-SERVICE
// annotation @FeignClient avec name= nom de notre micro service en Majuscule car c'est enregistrer en majiscule dans l'annuaire
@FeignClient(name="CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable(name="id") Long id);

}
