import React, {useState} from 'react';
import Carousel from 'react-bootstrap/Carousel'
import CouponCard from '../../components/CouponCard/CouponCard';
import './BookChef.css';
import axios from 'axios';
import SaveIcon from '@material-ui/icons/Save';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import breakfast from '.././../images/breakfast.png'
import lunch from '.././../images/lunch.png'
import dinner from '.././../images/dinner.png'
import femaleCook from '.././../images/femaleCook.png'
import maleCook from '.././../images/maleCook.png'
import nonveg from '.././../images/nonveg.png'
import veg from '.././../images/veg.png'
import both from '.././../images/both.png'
import any from '.././../images/any.png'
import DropdownOccassion from '../../components/Dropdown/DropdownOccassion';
import DropdownCuisine from '../../components/Dropdown/DropdownCuisine';
import ButtonPlusMinus from '../../components/IncrementButton/ButtonPlusMinus';
import DateSelect from './../../components/DatePicker/DateSelect'
import Divider from '@material-ui/core/Divider';
import DrawerLeft from '../../components/Drawer/Drawer';
import { ThemeProvider } from "@material-ui/core/styles";
import {  createMuiTheme, capitalize, Tooltip } from "@material-ui/core";
import { useHistory, useLocation} from 'react-router-dom'
import Order from '../../model/Order';
import { couldStartTrivia } from 'typescript';
import ChefRegister from '../ChefRegistration/ChefRegistration';
import { Spinner } from 'react-bootstrap';

interface customer{
  id : number,
  username : string
}
const BookChef = () => {

  const state = useLocation().state as customer;
  const initialstate = {
        "amount" : 0,
        "chefTier": 1,
        "cuisine": "Italian" ,
        "customerID": state.id,
        "date": "11-07-2021",   // date should not be past and after 2 months from today
        "foodPreference": "string",
        "genderPreference": "female", 
        "ingredients": true,
        "mealType": "Breakfast",
        "noOfHobs": 3,
        "noOfPeople": 5,
        "occasion": "string", // Occasion, Fullweek ,Month
        "oven": true,
        "time": "05:30:00",  // 30 mins or 00 and seconds should always be 00
        "typeOfHob": "Gas"
  }
  

  const [data,setData] = useState<Order>(initialstate);
  const [date,setDate] = useState<Date | null>(new Date());
  const [error,setError] = useState('');
  const [loading,setLoading] = useState(false);
  
  const placeOrder = async (event : React.MouseEvent) => {
    // const placeOrder = async (event : React.HTMLFormEve) => {
    event?.preventDefault();
    console.log(data);
  
    data.customerID=state.id;
    if(data.chefTier === 1)
      data.amount = 3000 * data.noOfPeople;
    else if(data.chefTier === 2)
      data.amount = 2250 * data.noOfPeople;
    else
      data.amount = 1100 * data.noOfPeople;
    
    try{
        setLoading(true);
        const response = await axios.post('http://127.0.0.1:8080/api/customer/place-order-for-chef/',data);
        console.log(response);
        setLoading(false);
        if(response.data){
          history.push({pathname : '/orderSummary',state : {orderId : response.data,id:state.id,username : state.username}});
        }

        //history.push({pathname : '/orderSummary',state : {orderId : 1}});
    }
    catch(error){
        console.log(error.response.data); 
        setLoading(false);
        setError(error.response.data);
        //history.push({'/bookChef',state:})   
    }  
   }

   const dateChangeHandler = (date : Date | null) => {
      console.log(date?.toLocaleString("sv-SE"));
      const dateSplit : string[] | undefined = date?.toLocaleString("sv-SE").split(' ');
      //console.log(dateSplit[0]+" "+dateSplit[1]);
      //setDate(date);
      if(dateSplit !== undefined){
        setData({...data,date : dateSplit[0],time : dateSplit[1]});
      }
        
      console.log(data);
      //setData({..data})
   }

   const handleDropdowns = (value : string,propertyName : string) => {
        console.log(value);
        setData({...data, [propertyName] : value});
        console.log(data);
   }

   const handlePersonCount = (count : number | undefined) => {
      console.log(count);
      if(count !== undefined)
        setData({...data,noOfPeople : count});
      console.log(data);
   }


  const history= useHistory();
  const viewChange=()=>{
    history.push({
      pathname: '/orderSummary'
    
    })
     
     }
     const inputChange = (event : React.ChangeEvent<HTMLInputElement>,propertyName : string) => {
       let value : string = event.target.value;
       let intValue : number;
       let booleanValue : boolean;
       if(propertyName === 'noOfHobs' || propertyName === 'chefTier'){
        if(propertyName === 'chefTier' && value === 'Any')
          value = "1";
        intValue = parseInt(value);
        setData({...data,[propertyName]: intValue});
       }
       else if(propertyName === 'ingredients' || propertyName === 'oven'){
        if(value === 'true')
          booleanValue = true;
        else
          booleanValue = false;
        setData({...data,[propertyName]: booleanValue});
       }
       else{
      setData({...data,[propertyName]: value});
       }
      console.log(data);
     }

     const inputImageChange = (event : React.MouseEvent<HTMLImageElement>,propertyName : string) =>{
       let value = event.currentTarget.attributes[1].value;
       if(propertyName === 'genderPreference' && value === 'Any'){
         value = 'Male';
       }
       setData({...data,[propertyName]: value});
       //event.target.style.border = '#000000 2px solid';
       //event.currentTarget.style.border = '#000000 2px solid';
      console.log(data);
     }
    



     console.log(state.username);
    return(
<>

<DrawerLeft id={state.id} user="customer" username={state.username}/>

<hr/>
<hr/>
<div className="container space-from-up">
    <div className="row">.
  <div className="col-md-4 ">
    <Carousel>
      
        <Carousel.Item interval={1000}>

       <CouponCard text="CODE26GUF" color="#3F51B5" />
       
        </Carousel.Item>
      
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#3F51B5" />
      
        </Carousel.Item>
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#3F51B5" />
      
        </Carousel.Item>
        <Carousel.Item>
        <CouponCard text="CODE26GUF" color="#3F51B5" />
    
        </Carousel.Item>
     
      </Carousel>
      </div>
      <div className="col-md-3 web-view">
    <Carousel>
      
        <Carousel.Item interval={1000}>

       <CouponCard text="CODE26GUF" color="#f50057" />
       
        </Carousel.Item>
      
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#f50057" />
      
        </Carousel.Item>
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#f50057" />
      
        </Carousel.Item>
        <Carousel.Item>
        <CouponCard text="CODE26GUF" color="#f50057" />
    
        </Carousel.Item>
     
      </Carousel>
      </div>
      <div className="col-md-4 web-view">
    <Carousel>
      
        <Carousel.Item interval={1000}>

       <CouponCard text="CODE26GUF" color="#3F51B5"/>
       
        </Carousel.Item>
      
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#3F51B5"/>
      
        </Carousel.Item>
        <Carousel.Item interval={500}>
        <CouponCard text="CODE26GUF" color="#3F51B5"/>
      
        </Carousel.Item>
        <Carousel.Item>
        <CouponCard text="CODE26GUF" color="#3F51B5"/>
    
        </Carousel.Item>
     
      </Carousel>
      </div>
      </div>
      {/* onSubmit={event => placeOrder(event)} */}
      <div className="col-md-12">
      {/* <form onSubmit={event => placeOrder(event)}> */}
      <Card >
      <CardContent>
          <h5>Find a suitable chef by choosing your preferences</h5>
          <h6>Select Meal Plan</h6>
          <RadioGroup color="primary" onChange={(event => inputChange(event,'occasion'))}>
          <FormControlLabel value="ocassion" control={<Radio />} label="Other/Special Ocassion" />
          <FormControlLabel value="fullweek"control={<Radio />} label="FullWeek" />
          <FormControlLabel value="monthly"control={<Radio />} label="Monthly" />
         
        </RadioGroup>

        <div className="row col-md-12 up-space">
        <div className="col-md-3">
        <h6>Select Meal Type</h6>
        <div className="meal-type">
            <Tooltip title="Breakfast" aria-label="add">
            <img src={breakfast} alt="breakfast" height="50px" width="50px" onClick={event => inputImageChange(event,'mealType')}/>
            </Tooltip>
            {/* <p>Breakfast</p> */}
            <Tooltip title="Lunch" aria-label="add">
            <img src={lunch} alt="lunch" height="50px" width="50px" onClick={event => inputImageChange(event,'mealType')}/>
            </Tooltip>
            {/* <p>Lunch</p> */}
            <Tooltip title="Dinner" aria-label="add">
            <img src={dinner} alt="dinner" height="50px" width="50px" onClick={event => inputImageChange(event,'mealType')}/>
            </Tooltip>
            {/* <p>Dinner</p> */}
        </div>
        </div>
        <div className="col-md-5">
        <h6>What type of food preference are you looking for?</h6>
        <div className="meal-type">
        <Tooltip title="Veg Food" aria-label="add">
          <img src={veg} alt="veg food" height="50px" width="50px" onClick={event => inputImageChange(event,'foodPreference')}/>
        </Tooltip>
            {/* <p>Breakfast</p> */}
            <Tooltip title="Non-veg Food" aria-label="add">
              <img src={nonveg} alt="nonveg food" height="50px" width="50px" onClick={event => inputImageChange(event,'foodPreference')}/>
            </Tooltip>
            {/* <p>Lunch</p> */}
            <Tooltip title="All Food" aria-label="add">
                <img src={both} alt="all food" height="50px" width="50px" onClick={event => inputImageChange(event,'foodPreference')}/>
            </Tooltip>
            {/* <p>Dinner</p> */}

        </div>
        </div>
        <div className="col-md-4">
        <h6>What is your cook gender preference?</h6>
        <div className="meal-type">
        <Tooltip title="Male" aria-label="add">
          <img src={maleCook} alt="Male" height="50px" width="50px" onClick={event => inputImageChange(event,'genderPreference')}/>
        </Tooltip>
        <Tooltip title="Female" aria-label="add">
          <img src={femaleCook} alt="Female" height="50px" width="50px" onClick={event => inputImageChange(event,'genderPreference')}/>
        </Tooltip>
        <Tooltip title="Any" aria-label="add">
            <img src={any} alt="Any" height="50px" width="50px" onClick={event => inputImageChange(event,'genderPreference')}/>
        </Tooltip>
            {/* <p>Dinner</p> */}
            </div>
            </div>
            </div>
            <div className="row col-md-12 down-space">
          <div className="col-md-3">
            <h6 className="cuisine-fit">Enter Cuisine</h6>
          <DropdownCuisine selectCuisine={handleDropdowns}/>
          </div>
          <div className="col-md-5">
          <h6 className="cuisine-fit">Select Ocassion</h6>
          <DropdownOccassion selectOccasion={handleDropdowns}/>
          </div>
          <div className="col-md-4">
          <h6>Number of people</h6>
          <ButtonPlusMinus peopleCount={handlePersonCount}/>
          </div>
          </div>
          <div className="row col-md-12 down-space">
          <div className="col-md-3">
          <h6>Type of Hob?</h6>
          <RadioGroup onChange={(event => inputChange(event,'typeOfHob'))}>
          <FormControlLabel value="Gas"control={<Radio />} label="Gas" />
          <FormControlLabel value="Induction"control={<Radio />} label="Induction" />
         
        </RadioGroup>
        </div>
        <div className="col-md-5">
        <h6>Number of Hobs?</h6>
          <RadioGroup onChange={(event => inputChange(event,'noOfHobs'))}>
          <FormControlLabel value="3" control={<Radio />} label="3" />
          <FormControlLabel value="4" control={<Radio />} label="4" />
          <FormControlLabel value="5" control={<Radio />} label="5" />
        </RadioGroup>
        </div>
        <div className="col-md-4">
        <h6>Do you have an oven?</h6>
          <RadioGroup onChange={(event => inputChange(event,'oven'))}>
          <FormControlLabel value="true" control={<Radio />} label="Yes" />
          <FormControlLabel value="false" control={<Radio />} label="No" />
         
        </RadioGroup>
        </div>
        </div>
        <div className="row col-md-12 down-space">
          <div className="col-md-4">
        <h6>Choose your chef?</h6>
          <RadioGroup onChange={(event => inputChange(event,'chefTier'))}>
          <FormControlLabel value="1" control={<Radio />} label="Tier 1" />
          <FormControlLabel value="2" control={<Radio />} label="Tier 2" />
          <FormControlLabel value="3" control={<Radio />} label="Tier 3" />
          <FormControlLabel value="Any" control={<Radio />} label="Any" />
        </RadioGroup>
        </div>
        <div className="col-md-8">
        <DateSelect dateChange={dateChangeHandler}/>
        </div>
        </div>
        <h6>Do you want us arrange ingredients?</h6>
          <RadioGroup  onChange={(event => inputChange(event,'ingredients'))}>
          <FormControlLabel value="true" control={<Radio />} label="Yes" />
          <FormControlLabel value="false" control={<Radio />} label="No" />
         
        </RadioGroup>

<Button
        variant="contained"
        color="primary"
        size="large"
        //onClick={()=>viewChange()}
        onClick={(event)=>placeOrder(event)}
        
        startIcon={<SaveIcon />}
      >
       Confirm
      </Button>
      <div className="resDiv">
        {error ? error : null} 
        {loading ? <Spinner animation="border" variant="primary" /> : null}       
      </div>
        </CardContent>
        </Card>
        {/* </form> */}
      </div>
     
      </div>
</>
   
      )
}

export default BookChef;