import React, { useState,useEffect } from 'react';
import Button from '@material-ui/core/Button';
import { useHistory, useLocation} from 'react-router-dom'
import './OrderSummary.css';
import axios from 'axios';
import OrderSummaryModel from '../../model/OrderSummary';
import { Spinner } from 'react-bootstrap';
import Logo from '../../components/Logo/Logo';

interface Order {
  orderId : number;
  id : number,
  username : string
}

const OrderSummary=()=>{
    const history= useHistory();
    const state = useLocation().state as Order;
    console.log(state.orderId);
    const [data,setData] = useState<OrderSummaryModel>();
    const [loading,setLoading] = useState(false);
    //const [finalAmount,setFinalAmount] = useState<number>();
    useEffect(() => {
      const fetchOrderDetails = async () => {
          try{
            setLoading(true);
          const response = await axios.get(`http://127.0.0.1:8080/api/customer/interim-response?orderId=${state.orderId}`);
          console.log(response.data);
          setData(response.data);
          //setFinalAmount(data?.totalAmount+450);
          console.log(data);
          setLoading(false);
          }
          catch(error){
            console.error(error.response.data);
            setLoading(false);
          }
      }
      fetchOrderDetails();
    }, [state.orderId]);
  
  const viewChange=()=>{
    history.push({
      pathname: '/paymentProcess',state: {orderId : state.orderId, amount : data?.totalAmount,amountToBePaid : data?.amountToBePaid,email : data?.email,id:state.id , username : state.username}
    })}
    return loading ? <Spinner animation="border" variant="primary" /> : (
      <>
      <Logo id={state.id}/>
      <div className="main-section1">
        <div className="heading1"><span className="heading-content1">ORDER SUMMARY</span></div>
      <div>
        <div className="info1">
        <div className="address-main">
            <div className ="addressMain1">Address</div>
           <div className="addr1">{data?.addressDTO.addressLine1},{data?.addressDTO.addressLine2} {data?.addressDTO.city}</div>
           
        </div > 
        <div className="address-main">
            <div className='addressMain1'>Date & time</div>
          <div className="addr1">{data?.orderDate}</div> 
        </div>
        <div>
            <div className='addressMain1'>Type:<span className="chefInfo1">{data?.foodPreference} {data?.mealType}</span></div>
            <div className='addressMain1'>Ocassion:<span className="chefInfo1">{data?.occasion}</span></div>
            <div className='addressMain1'>Number of people<span className="chefInfo1">{data?.noOfPeople}</span></div>
            <div className='addressMain1'>Chef Type<span className="chefInfo1">Tier {data?.chefTier}</span></div>
        </div>
        <hr/>
        {/* <div  className="disclaimer1">
          <input type="checkbox" id="input1"/>
         <span className='addressMain' id="disc1"> Require help to clean dishes and cooking area post cooking, extra charges will be applicable.</span>
        </div> */}
        
        <div className="address-main">
          <div  className='addressMain'>Sub total <span id="subTotal">Rs {data?.totalAmount}</span></div>
          <div  className='addressMain'>Tax <span id="tax">Rs {data?.totalAmount !== undefined ? data?.totalAmount*0.1 : null}</span></div>
        </div>
        <hr/>
        <div className="tamount">
        <div className="addressMain">TOTAL <span id="total">Rs {data?.totalAmount !== undefined ? data?.totalAmount+data?.totalAmount*0.1 : null}</span></div>
        </div>
        </div>
        
        <Button
            variant="contained"
            color="primary"
            size="large"
            onClick={()=>viewChange()}
            id="submit1"
          >Confirm Order</Button>
      </div>
      </div>
      </>
    )
}

export default OrderSummary;