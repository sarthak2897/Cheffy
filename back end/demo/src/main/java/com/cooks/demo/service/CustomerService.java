package com.cooks.demo.service;

import com.cooks.demo.model.Address;
import com.cooks.demo.model.Customer;
import com.cooks.demo.model.Order;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.CustomerRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.util.DTOS.AddressDTO;
import com.cooks.demo.util.DTOS.RegisterCustomerDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.ChefResponseDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.CustomerResponseDTO;
import com.cooks.demo.util.DTOS.ResponseDTOs.InterimOrderResponseDTO;
import com.cooks.demo.util.DTOS.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    ValidatorService validatorService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;

    public RegisterCustomerDTO getCustomerProfile(Integer customerId) throws Exception{
        Customer customer = getCustomerById(customerId);
        RegisterCustomerDTO registerCustomerDTO = getRegisterCustomerDTOFromCustomer(customer);
        return  registerCustomerDTO;
    }

    private RegisterCustomerDTO getRegisterCustomerDTOFromCustomer(Customer customer) {
        RegisterCustomerDTO registerCustomerDTO = new RegisterCustomerDTO();
        registerCustomerDTO.setAddress(getAddressDTOAddress(customer.getAddress()));
        registerCustomerDTO.setUserDetailDTO(userService.getUserDetailDTOFromUser(customer.getUser()));
        return  registerCustomerDTO;
    }
    public AddressDTO getAddressDTOAddress(Address address) {
        return AddressDTO.builder()
                .addressLine1(address
                        .getAddressLine1()).
                        addressLine2(address.
                                getAddressLine2()).
                        city(address.getCity()).
                        state(address.getState()).pinCode(address.getPinCode()).latitude(address.getLatitude()).longitude(address.getLongitude()).build();
    }


    public Customer getCustomerById(Integer customerId) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return customer.get();
        }else {
            throw new Exception("customer not found");
        }
    }

    /**
     * returns list of customer
     * @return
     */
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * converts order object to DTO object
     * @param orders
     * @return
     */
    private List<CustomerResponseDTO> convertOrderToCustomerOrdersDTO(List<Order> orders) {
        List<CustomerResponseDTO> customerOrders = new ArrayList<>();
        return customerOrders = orders.stream()
                .map(x -> new CustomerResponseDTO(
                        x.getId(),
                        x.getChef() != null ? x.getChef().getUser().getName() : "",
                        x.getChef() != null ? x.getChef().getUser().getEmail() : "",
                        x.getChef() != null ? x.getChef().getUser().getPhoneNumber() : null,
                        x.getChef() != null ? x.getChef().getUser().getGender() : "",
                        x.getChef() != null ? x.getChef().getTier() : null,
                        x.getStatus(),
                        x.getAmount(),
                        x.getCuisine(),
                        x.getMealType(),
                        x.getNoOfPeople(),
                        x.getFoodPreference(),
                        x.getOccasion(),
                        x.getDateOfSupply()
                )).sorted(new Comparator<CustomerResponseDTO>() {
                    @Override
                    public int compare(CustomerResponseDTO o1, CustomerResponseDTO o2) {
                        return o2.getOrderId() - o1.getOrderId();
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * returns all orders of customer
     * @param customerId
     * @return
     */
    public List<CustomerResponseDTO> getAllOrdersOfCustomer(Integer customerId) {
        List<Order> orders = orderRepository.getAllOrdersOfCustomer(customerId);
        return convertOrderToCustomerOrdersDTO(orders);
    }

    /**
     * rolls back all the transaction if the registration process is is not completed fully
     * @param registerCustomerDTO
     * @return
     * @throws SQLIntegrityConstraintViolationException
     * @throws Exception
     */

    @Transactional(rollbackFor = Exception.class)
    public String registerCustomerUser(RegisterCustomerDTO registerCustomerDTO) throws SQLIntegrityConstraintViolationException, Exception {
        try {
            if (validatorService.validatePasswordAndVerifyPassword(registerCustomerDTO.getUserDetailDTO().getPassword(), registerCustomerDTO.getUserDetailDTO().getVerifyPassword())) {
                User user = userService.getUserFromDTO(registerCustomerDTO.getUserDetailDTO());
                user.setPassword(passwordEncoder.encode(registerCustomerDTO.getUserDetailDTO().getPassword()));
                userService.registerBasicUserDetails(user);
                String message = "Your registration with cooks as a customer has been successful. We would be delighted to serve you. Please feel free to reach out to our customer care center for help";
                mailService.sendEmail(user.getEmail(), "Registration Successful", message);
                return registerCustomer(registerCustomerDTO, user);
            } else {
                log.error("passwords do not match exception");
                throw new Exception("Passwords do not match. please try again");
            }
        } catch (DataIntegrityViolationException exception){
            log.error(exception.getMessage());
            throw new Exception("User with this email_id or phone_number already exists");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            throw new Exception("something went wrong. please contact admin");
        }
    }

    /**
     * registers customer
     * @param registerCustomerDTO
     * @param user
     * @return
     */
    public String registerCustomer(RegisterCustomerDTO registerCustomerDTO, User user) {
        Customer customer = getCustomerFromDTO(registerCustomerDTO, user);
        customerRepository.save(customer);
        return "You have been successfully registered as customer.";
    }


    /**
     * gets Customer object from DTO object
     * @param registerCustomerDTO
     * @param user
     * @return
     */
    public Customer getCustomerFromDTO(RegisterCustomerDTO registerCustomerDTO, User user) {
        return Customer.builder().user(user).address(getAddressFromAddressDTO(registerCustomerDTO.getAddress())).build();
    }

//    public RegisterCustomerDTO getCustomerRegisterDTOFromCustomer(RegisterCustomerDTO registerCustomerDTO, User user) {
//        return Customer.builder().user(user).address(getAddressFromAddressDTO(registerCustomerDTO.getAddress())).build();
//    }

    /**
     * gets Address from DTO object
     * @param addressDTO
     * @return
     */
    public Address getAddressFromAddressDTO(AddressDTO addressDTO) {
        return Address.builder()
                .addressLine1(addressDTO
                        .getAddressLine1()).
                        addressLine2(addressDTO.
                                getAddressLine2()).
                        city(addressDTO.getCity()).
                        state(addressDTO.getState()).pinCode(addressDTO.getPinCode()).latitude(addressDTO.getLatitude()).longitude(addressDTO.getLongitude()).build();
    }

    /**
     * gets Address DTO from DTO object
     * @param address
     * @return
     */



    /**
     * gets interim object for display without giving out the details of Chef
     * @param orderId
     * @return
     * @throws Exception
     */
    public InterimOrderResponseDTO getInterimResponseForDisplay(Integer orderId) throws Exception{
        Order order = orderService.getGetOderById(orderId);
        AddressDTO addressDTO = getAddressDTOAddress(order.getCustomer().getAddress());
        InterimOrderResponseDTO interimOrderResponseDTO = InterimOrderResponseDTO.builder()
                .amountToBePaid((order.getAmount()/5))
                .totalAmount(order.getAmount())
                .cuisine(order.getCuisine())
                .mealType(order.getMealType())
                .foodPreference(order.getFoodPreference())
                .addressDTO(addressDTO)
                .occasion(order.getOccasion())
                .noOfPeople(order.getNoOfPeople())
                .chefTier(order.getChefTier())
                .orderDate(order.getDateOfSupply())
                .email(order.getCustomer().getUser().getEmail())
                .build();

            return interimOrderResponseDTO;
    }
}

