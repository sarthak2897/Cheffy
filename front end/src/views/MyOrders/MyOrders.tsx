import React,{useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import axios from 'axios';
import { useLocation } from 'react-router';
import myOrders from '../../model/MyOrders';
import { Spinner } from 'react-bootstrap';
import './MyOrders.css';
import Logo from '../../components/Logo/Logo';
import DrawerLeft from '../../components/Drawer/Drawer';


const useStyles = makeStyles({
    root: {
      minWidth: 275,
      margin: '20px auto',
      backgroundColor : 'khaki'
    },
    bullet: {
      display: 'inline-block',
      margin: '0 2px',
      transform: 'scale(0.8)',
    },
    title: {
      fontSize: 14,
    },
    pos: {
      marginBottom: 12,
    },
  });
  
  interface customer {
      id : number,
      username : string
  }

const MyOrders = ()=> {
    const classes = useStyles();
    const state = useLocation().state as customer;
    console.log(state.id);
    const [data,setData] = useState<myOrders[]>();
    const [loading,setLoading] = useState(false);
    const [message,setMessage] = useState('');
    const bull = <span className={classes.bullet}>•</span>;
    useEffect(() => {
      setLoading(true);
        const fetchCustomerOrders = async () => {
          try{
            
            const response = await axios.get(`http://127.0.0.1:8080/api/customer/get-all-bookings?customerId=${state.id}`);
            console.log(response.data);
            if(response.status === 200){
              if(response.data.length === 0){
                setMessage('You have not made any orders');
                
              }
              else{
                setData(response.data);
                
              }  
            }
          }
          catch(error){
            console.log(error);
            
          }
        } 
        fetchCustomerOrders();  
        setLoading(false);   
    }, []);

   console.log(data);
   console.log(message);
    return loading ? <Spinner variant="primary" animation="border"/> : message.length === 0 ? 
        (<>
        <DrawerLeft id={state.id} user="customer" username={state.username}/>
        <Logo id={state.id}/>
        <div className="main">
          <h3 style={{textAlign : 'center'}}>MY ORDERS</h3>
          <div className="orderCards" >
         {data?.map(order =>{
       return ( 
      <Card className={classes.root}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom>
            ORDER ID : {order.orderId}
            <span className="price">Rs {order.amount}</span>
          </Typography>
          {order.name !== '' ?
          <Typography color="textPrimary">
            Chef Name : {order.name}<br/>
            Chef Email : {order.chefEmail}<br/>
            Chef's Contact Number : {order.chefPhoneNumber}<br/>
          </Typography> : null}
          <hr />
          <Typography className={classes.pos} color="textSecondary">
            ITEMS
          </Typography>
          <Typography color="textPrimary">
            Type : {order.mealType}<br />
            Ocassion : {order.occasion}<br />
            Number of people : {order.noOfPeople}<br />
          </Typography>
          <hr/>
          <Typography className={classes.pos} color="textSecondary">
            ALLOCATED ON
          </Typography>
          <Typography color="textPrimary">
            {new Date(order.dateOfOrder).toDateString()}
          </Typography>
          <hr />
          <Typography  className={classes.pos} color="textSecondary">
            STATUS
            <span className="price" style={order.orderStatus === 'Failed' ? {color : 'red'} : {color : 'green'}}>{order.orderStatus}</span>
          </Typography>
        </CardContent>
      </Card>
      
       )})}
       </div>
       </div></>) : <><Logo id={state.id}/>
        <h3 style={{textAlign : 'center'}}>{message}</h3></>;
  }

export default MyOrders;





  
