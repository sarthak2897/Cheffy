package com.cooks.demo.service;

import com.cooks.demo.model.Order;
import com.cooks.demo.model.Transaction;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.repository.TransactionRepository;
import com.cooks.demo.util.DTOS.PaymentRequestDTO;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.cooks.demo.model.Transaction.Status.SUCCESS;
import static com.cooks.demo.model.Transaction.Status.FAILED;

@Slf4j
@Service
public class StripeService {

    private String secretKey;
    @Autowired
    ValidatorService validatorService;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;


    @PostConstruct
    /**
     * initializes the api-key
     */
    public void init() {
        Stripe.apiKey = "sk_test_51IyfarSFE9juX3b0cuSSXkvw93vRDsW3P3ClDvafNlpacHqYKKKP1YZcM4ol8U9I6xsg9xxDpyb7nxZua9fM5OWL00uBFVJ0wQ";
    }

    /**
     * based on the payment mode money is charged to the end user
     * @param paymentRequestDTO
     * @return
     * @throws Exception
     */
    @Transactional
    public Charge charge(PaymentRequestDTO paymentRequestDTO) throws Exception {
        String validationResult = validatorService.validateCardDetails(paymentRequestDTO.getCardDigits());
        if(validationResult.equals("Card validated successfully!")){
            Map<String,Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", paymentRequestDTO.getAmount());
            chargeParams.put("currency",paymentRequestDTO.Currency.INR);
            chargeParams.put("source", paymentRequestDTO.getToken());
            Charge charge = Charge.create(chargeParams);
            performTransaction(paymentRequestDTO, SUCCESS,charge.getId(),"Completed");
            log.info("Transaction saved to DB successfully! Status : SUCCESS");
            return charge;
        }
        else{
            performTransaction(paymentRequestDTO, FAILED,null,"Failed");
            log.info("Transaction saved to DB successfully! Status : FAILED");
            throw new Exception(validationResult);
        }
    }

    /**
     * gets the transaction object from DTO
     * @param paymentRequestDTO
     * @return
     * @throws Exception
     */
    private Transaction getTransactionFromDTO(PaymentRequestDTO paymentRequestDTO) throws  Exception{
        Order order = orderService.getGetOderById(paymentRequestDTO.orderId);
            return Transaction.builder().order(order).amount(paymentRequestDTO.getAmount()/100).build();
    }

    /**
     * actually performs the transaction and persists the same
     * @param paymentRequestDTO
     * @param status
     * @param transactionId
     * @throws Exception
     */
    private void performTransaction(PaymentRequestDTO paymentRequestDTO, Transaction.Status status,String transactionId,String orderStatus) throws Exception{
        Order order = orderService.getGetOderById(paymentRequestDTO.getOrderId());
        Transaction transaction = getTransactionFromDTO(paymentRequestDTO);
        transaction.setStatus(status);
        transaction.setTransactionConfirmationId(transactionId);
        transaction.setDate(new Date());
        transaction.setOrder((order));
        transactionRepository.save(transaction);
        order.setStatus(orderStatus);
        orderRepository.save(order);
        String customerMessage = "Your payment has been successful. We would be sharing the details of Chef shortly. thank you for shopping with us. Please feel free to reach out to our customer care center for help";
        mailService.sendEmail(order.getCustomer().getUser().getEmail(),"Registration Successful",customerMessage);
        String chefMessage = "A customer had booked you as Chef. We will be sharing the details shortly. thank you for being our valued partner. Please feel free to reach out to our customer care center for help";

    }


}
