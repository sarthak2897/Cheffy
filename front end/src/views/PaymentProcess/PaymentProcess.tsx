import React, { useState } from 'react';
import { useHistory, useLocation} from 'react-router-dom'
import Button from '@material-ui/core/Button';
import StripeCheckout from 'react-stripe-checkout';
import axios from 'axios';
import { Spinner } from 'react-bootstrap';
import './PaymentProcess.css';
import Logo from '../../components/Logo/Logo';

interface amount {
  orderId : number, 
    amount : number,
    amountToBePaid : number,
    email : string,
    id : number,
    username : string
}


const PaymentProcess=()=>{
    const history= useHistory();
    const [paymentStatus,setPaymentStatus] = useState(false);
    const [message,setMessage] = useState('');
    const [loading,setLoading] = useState(false);
    const state = useLocation().state as amount;
    const [successPay,setSuccessPay] = useState(false);
    const publishableKey = "pk_test_51IyfarSFE9juX3b0DLyKrpDHe9WfsKbAUJvGRtUhMYWYsr6J5TIbhVREB8eSyKXf8Iu9iOkyL7Pqs7V7ev0wlVtt00yTyQ3PcP";
    const goToBookChef=()=>{
      history.push({pathname: '/bookChef',state: {id : state.id,username : state.username}});
    }
      const goToMyOrders=() =>{
        history.push({pathname : '/myOrders',state : {id : state.id,username : state.username}});
      }
       
       const handleToken = async (token: any) =>{
         try{
           setLoading(true);
          console.log(token);
          const response = await axios.post("http://127.0.0.1:8080/api/customer/payment",{amount : state.amountToBePaid * 100,token : token.id, email:token.email, cardDigits : token.card.last4 , orderId : state.orderId});
          console.log(response)
          setLoading(false);
          if(response.status === 200 && response.data.status === "succeeded"){
            setPaymentStatus(true);
            setMessage(`Payment successful!\n Transaction ID : ${response.data.id}\n`);
            setSuccessPay(true);
          }
          else{
            setLoading(false);
          }
          
         }
         catch(error){
            console.error(error.response.data);
            setMessage(error.response.data);
            setLoading(false);
         }
         
       }
    return(
      <>
      <Logo id={state.id}/>
      <div className="main-section">
        <div className="heading"><span className="heading-content">CONFIRM BOOKING </span></div>
      <div>
          <div className="info">
            <div className="confirm-booking-main">
                <div className ="addressMain">Pay 20% of total amount, to confirm your booking </div>
            </div > 
            <div className="tamount">
                <div>TOTAL <span className="total">Rs {state.amount}</span></div>
                <div>20% of total <span className="total-tax">Rs {state.amountToBePaid}</span></div>
            </div>
            <div className="tamount">
                To be paid <span className='famount'>Rs {state.amountToBePaid}</span>
            </div>
          </div>
          {!paymentStatus ? <div className="stripeCheckoutButton"><StripeCheckout
                  name="Chef Order"
                  stripeKey={publishableKey}
                  token={handleToken}
                  amount={state.amountToBePaid*100}
                  description={`Your total price is Rs. ${state.amountToBePaid}`}
                  panelLabel="Pay Now"
                  currency="INR"
                  email={state.email}>
                    <Button style={{textAlign : 'center'}} variant="contained" color="primary" size="large">PAY WITH CARD</Button>
                  </StripeCheckout></div> : null}
                {successPay ? <div className="buttonView">
                <Button variant="contained" color="primary" size="large" onClick={()=>goToBookChef()}>Order another chef</Button>
                <Button className="buttonView2" variant="contained" color="primary" size="large" onClick={() => goToMyOrders()}>View Orders</Button>
                </div> : null}
                <div>
                {!loading ? <p>{message ? message : null}</p> : <Spinner animation="border" variant="primary" />}
                </div>
          </div>
          </div>
          
            </>
          
    

    )
}

export default PaymentProcess;