package com.cooks.demo.controller;
import com.cooks.demo.service.BookSlotService;
import com.cooks.demo.service.CustomerService;
import com.cooks.demo.service.StripeService;
import com.cooks.demo.util.DTOS.PaymentRequestDTO;
import com.cooks.demo.util.DTOS.RegisterCustomerDTO;
import com.cooks.demo.util.DTOS.RequestOrderDTO;
import com.stripe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    BookSlotService bookedSlotService;

    @Autowired
    StripeService stripeService;
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     *
     * @param registerCustomerDTO all chefs registered with the Application
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity registerCustomerUser(@RequestBody RegisterCustomerDTO registerCustomerDTO){
        log.info("register user initiated");
        System.out.println("End point called");
        try {
            String message = customerService.registerCustomerUser(registerCustomerDTO);
            log.info("User registered successfully");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @return gets all customers
     */

    @GetMapping("/get-all")
    public ResponseEntity getAllCustomers(){
        log.info("Request initiated to get all customers");
        return new ResponseEntity(customerService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/get-profile")
    public ResponseEntity getCustomerProfile(@RequestParam Integer customerId) throws Exception{
        try {
            log.info("Requested for customer with customer id" + customerId);
            return new ResponseEntity(customerService.getCustomerProfile(customerId), HttpStatus.OK);
        }catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        }

    /**
     * gets all bookings of a customer
     * @param customerId unique Id within system
     * @return
     */

    @GetMapping("/get-all-bookings")
    public ResponseEntity getAllBookingsCustomers(@RequestParam  Integer customerId){
        log.info("Request initiated to get all bookings for customer with Id" +customerId);
        try {
            return new ResponseEntity<>(customerService.getAllOrdersOfCustomer(customerId),HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param requestDTO places order for cook
     * @return
     * @throws Exception
     */
    @PostMapping("/place-order-for-cook")
    public ResponseEntity placeOrderForCook(@RequestBody RequestOrderDTO requestDTO) throws Exception {
        log.info("request initiated to place order for cook");
        try {
            return new ResponseEntity(bookedSlotService.getAvailableChefsForDifferentSlots(requestDTO),HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * places order for a chef
     * @param requestDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/place-order-for-chef")
    public ResponseEntity placeOrderForChef(@RequestBody RequestOrderDTO requestDTO) throws Exception {
        try {
            log.info("requested to place order for chef on" + requestDTO.getDate());
            return new ResponseEntity<>(bookedSlotService.acceptOrderAndAllocateChef(requestDTO),HttpStatus.OK);
        } catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param paymentRequest
     * @return
     * @throws APIConnectionException
     * @throws APIException
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws CardException
     */
    @PostMapping(path="/payment")
    public ResponseEntity processPayment(@RequestBody PaymentRequestDTO paymentRequest) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
         try{
            log.info("Payment initiated for order with Id : " + paymentRequest.getOrderId());
            return new ResponseEntity<>(stripeService.charge(paymentRequest),HttpStatus.OK);
        }
        catch(Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * returns interim response for display without showing the details of chef
     * @param orderId
     * @return
     */
    @GetMapping("/interim-response")
    public ResponseEntity getInterimResponseForDisplay(@RequestParam Integer orderId){
        try {
            log.info("interim response requested");
            return new ResponseEntity<>(customerService.getInterimResponseForDisplay(orderId),HttpStatus.OK);
        }catch (Exception exception){
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.OK);
        }
    }

}
