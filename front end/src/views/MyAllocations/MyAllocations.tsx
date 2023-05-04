import React,{useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import axios from 'axios';
import { useLocation } from 'react-router';
import { Spinner } from 'react-bootstrap';
import './MyAllocations.css';
import Logo from '../../components/Logo/Logo';
import myAllocations from '../../model/MyAllocations';
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

const MyAllocations = ()=> {
    const classes = useStyles();
    const state = useLocation().state as customer;
    console.log(state.id);
    const [data,setData] = useState<myAllocations[]>();
    const [loading,setLoading] = useState(false);
    const [message,setMessage] = useState('');
    const bull = <span className={classes.bullet}>•</span>;
    useEffect(() => {
      setLoading(true);
        const fetchCustomerOrders = async () => {
          try{
            
            const response = await axios.get(`http://127.0.0.1:8080/api/chef/get-all-bookings?chefId=${state.id}`);
            console.log(response.data);
            if(response.status === 200){
              if(response.data.length === 0){
                setMessage('You have not been allocated to any customer.');
                
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
        <DrawerLeft id={state.id} user="chef" username={state.username}/>
        <Logo id={state.id}/>
        <div className="main">
          <h3 style={{textAlign : 'center'}}>MY ALLOCATIONS</h3>
          <div className="orderCards" >
         {data?.map(allocation =>{
       return ( 
      <Card className={classes.root}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom>
            ORDER ID : {allocation.orderId}
            <span className="price">Rs {allocation.amount}</span>
          </Typography>
          <Typography color="textPrimary">
            Customer's Name : {allocation.customerName}<br/>
            Customer's Email : {allocation.customerEmail}<br/>
            Customer's Contact Number : {allocation.customerPhoneNumber}<br/>
          </Typography>
          <hr />
          <Typography className={classes.pos} color="textSecondary">
            ITEMS
          </Typography>
          <Typography color="textPrimary">
            Type : {allocation.mealType}<br />
            Food Preference : {allocation.foodPreference}<br/>
            Cuisine : {allocation.cuisine}<br/>
            {/* Ocassion : {allocation.occasion}<br /> */}
            Number of people : {allocation.noOfPeople}<br />
          </Typography>
          <hr/>
          <Typography className={classes.pos} color="textSecondary">
            ALLOCATED ON
          </Typography>
          <Typography color="textPrimary">
            {new Date(allocation.serviceDate).toDateString()}
          </Typography>
          <hr />
          <Typography  className={classes.pos} color="textSecondary">
            STATUS
            <span className="price" style={allocation.status === 'Failed' ? {color : 'red'} : {color : 'green'}}>{allocation.status}</span>
          </Typography>
        </CardContent>
      </Card>
      
       )})}
       </div>
       </div></>) : <><Logo id={state.id}/>
        <h3 style={{textAlign : 'center'}}>{message}</h3></>;
  }

export default MyAllocations;





  
