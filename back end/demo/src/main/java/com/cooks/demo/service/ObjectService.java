package com.cooks.demo.service;

import com.cooks.demo.model.*;
import com.cooks.demo.util.DTOS.*;
import com.cooks.demo.util.DTOS.ResponseDTOs.ChefResponseDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.CustomerResponseDTO;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ObjectService {
    User user = new User(1, "name", "name@gmail.com", "Male", 9164560226L, "password", "ROLE_CHEF");
    Chef chef = new Chef(1, "BCA", 5.0D, 1, false, user);
    UserDetailDTO userDetailDTO = new UserDetailDTO("Name", "name@gmail.com", "Male", 9164560226L, "ROLE_CHEF", "password", "password");
    ChefDetailDTO chefDetailDTO = new ChefDetailDTO("BCA",5.0D);
    RegisterChefDTO registerChefDTO = new RegisterChefDTO(userDetailDTO,chefDetailDTO);
    AddressDTO addressDTO = new AddressDTO("address line 1","address line 2","Bang","Karnataka",560037L,"lat","long");
    RegisterCustomerDTO registerCustomerDTO = new RegisterCustomerDTO(userDetailDTO, addressDTO);
    Customer customer = new Customer(1, user, new Address());
    RequestOrderDTO requestOrderDTO = new RequestOrderDTO("Lunch",
            "Veg", "Male", "NorthIndian",
            "Occasion", 5, "manual", 1,
            true, 1, true, 1, "",
            3000L, LocalDate.now(), LocalTime.now());

    Order order = new Order(1,"","","","","",1,"",1, false,1 ,false, LocalDate.now(),LocalDateTime.now(), false,customer, chef, "", 1236L);

    ChefResponseDTO chefResponseDTO = new ChefResponseDTO(1,"Akash","akash.m.konnur@gmail.com",9164560226l,LocalDate.now(),"completed",5000l,"Italian","non-veg",7,"spicy");

    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(1,"Akash","akash.m.konnur@gmail.com",9164560226l,"Male",1,"Allocated",5000l,"Italian","non-veg",7,"spicy","party",LocalDate.now());

    Address address = new Address(1,"","","","",12L,"","");

    public Chef getChefObject(){
        return chef;
    }
    public User getUserObject(){
        return user;
    }

    public ChefDetailDTO getChefDetailDTO(){
        return chefDetailDTO;
    }

    public UserDetailDTO getUserDetailDTO(){
      return userDetailDTO;
    }

    public RegisterChefDTO getRegisterChefDTO(){
      return registerChefDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
    public RegisterCustomerDTO getRegisterCustomerDTO(){
        return registerCustomerDTO;
    }

    public Customer getCustomer(){
        return customer;
    }

    public  RequestOrderDTO getRequestOrderDTO(){
        return requestOrderDTO;
    }

    public Order getOrder(){
        return order;
    }

    public ChefResponseDTO chefResponseDTO() {
        return chefResponseDTO;
    }
    public  CustomerResponseDTO customerResponseDTO(){
        return  customerResponseDTO;
    }

    public Address getAddress(){
        return address;
    }
}
