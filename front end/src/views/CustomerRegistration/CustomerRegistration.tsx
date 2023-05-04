import React, { SyntheticEvent, useState } from 'react';
import "./CustomerRegistration.css";
import axios from 'axios';
import { stringify } from 'querystring';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import { useHistory } from 'react-router';
import Customer from '../../model/Customer';
import Input from '../../components/Input/Input';
import Map from '../../components/Map/Map';
import { LeafletMouseEvent } from 'leaflet';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import { Spinner } from 'react-bootstrap';
import Logo from '../../components/Logo/Logo';
const CustomerRegister=()=>{

const initialstate = {
  address:{
    "addressLine1":"",
    "addressLine2":"",
    "city":"",
    "pinCode":0,
    "latitude":0,
    "longitude":0,
    "state":""
},
userDetailDTO: {
    "email": "",
    "gender": "Female",
    "name": "",
    "phoneNumber": 0,
    "userType":"ROLE_CUSTOMER",
    "password":"",
    "verifyPassword":"",
    }
}
  const [data,setData] = useState<Customer>(initialstate);
  const [value, setValue] = React.useState<string>('Female');
  const history = useHistory();
  const [latitude,setLatitude] = useState<number>();
  const [longitude,setLongitude] = useState<number>();
  const [error,setError] = useState<string>();
  const [match, setMatch]= useState<boolean>(false);
  const [loading,setLoading] = useState<boolean>(false);

  const getCoordinates = (event:LeafletMouseEvent) =>{
    const {lat,lng} = event.latlng;
    console.log(lat+" "+lng);
    //setLatitude(lat);
    //setLongitude(lng);
    const {address} = data;
    const newAddress = {...address,latitude : lat,longitude : lng};
    setData({...data,address : newAddress});
}

const addUser = async (event : React.FormEvent<HTMLFormElement>) => {
 event?.preventDefault();
 console.log(data);
 try{
    setLoading(true);
     const response = await axios.post('http://127.0.0.1:8080/api/customer/register/',data);
     //const response = await axios.get('http://127.0.0.1:8080/api/user/dummy');
     console.log(response);
     if(response.status === 200 && response.data.includes('successfully registered')){
       //history.push('/',{state:{message : response.data}});// back to login page
       history.push({pathname : '/',state : {message : response.data}});
     }
     else{
       setError("Some error occured!");
       history.push('/customerRegister');
     }
 
 }
 catch(error){
     console.log(error.response.data);
     setError(error.response.data);
       history.push('/customerRegister');
 }
 setLoading(false);
}

const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  setValue(event.target.value);
  // setData({...data,  data.userDetailDTO.gender=value})  
};

 
const matchPassword = (event:React.ChangeEvent<HTMLInputElement>) => {
  const {address,userDetailDTO} = data;
  if(userDetailDTO.password===event.target.value){
    //console.log("I am inside matchPassword")
   setMatch(true);
    //console.log(match)

  }
  else{
    //console.log("I am inside matchPassword if it is false")
    setMatch(false);
    //console.log(match)
  }
}
const inputChange = (event : React.ChangeEvent<HTMLInputElement>,propertyName : string) => {
  const {address,userDetailDTO} = data;
  console.log(event.target.type);
  if(event.target.name.includes('address')){
    const newAddress = {...address,[propertyName] : event.target.value};
    console.log(newAddress);
    setData({...data,address : newAddress});
  }
  else{
   if(event.target.type === 'radio'){
     handleChange(event);
   }
  const newUserDetailDTO = {
    ...userDetailDTO,[propertyName] : event.target.value
  } 
  console.log(newUserDetailDTO);
  //setData((prevState)=>({...prevState,userDetailDTO : newUserDetailDTO}));
  setData({...data,userDetailDTO : newUserDetailDTO});
}
  console.log(data);
  if(propertyName === 'verifyPassword'){
    matchPassword(event);
  }
}
  return  (
    <div>
      <div className="row col-md-12">
    <div className="col-md-7">
    <div className="card card-mod">
    <div className="card-header">YOUR DETAILS</div>
    <div className="card-body">

        <form onSubmit={event => addUser(event)}>
          <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Enter Full Name</label>
            <input type="text" className="form-control" name="userDetailDTO.name" placeholder="Full Name" required value={data.userDetailDTO.name} onChange={event => inputChange(event,'name')}/>
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Email Id</label>
            <input type="email" className="form-control" name="userDetailDTO.email"   placeholder="Enter your email" required value={data.userDetailDTO.email} onChange={(event) => inputChange(event,'email')} />
          </div>
          </div>
          </div>
          <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Phone Number</label>
            <input type="tel" pattern="[0-9]{10}" required className="form-control" name="userDetailDTO.phoneNumber" placeholder="Enter your number" onChange={(event) => inputChange(event,'phoneNumber')} />
          </div>
          </div>
          <div className="col-md-6">
          <label className="label-text">Gender</label>
          <RadioGroup value={value} onChange={event => inputChange(event,'gender')}>
          <FormControlLabel name="gender" value="Female"control={<Radio />} label="Female" />
          <FormControlLabel  name="gender" value="Male"control={<Radio />} label="Male" />
          {/* <FormControlLabel   name="gender"value="other" control={<Radio />} label="Other" /> */}
         
        </RadioGroup>
        </div>
        </div>
        <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Password</label>
            <input type="password" className="form-control" name="userDetailDTO.password" placeholder="Password" required value={data.userDetailDTO.password} onChange={(event) => inputChange(event,'password')} />
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Confirm Password</label>
            <input type="password" name="userDetailDTO.verifyPassword" className="form-control"  placeholder="Verify Password" required  value={data.userDetailDTO.verifyPassword} onChange={(event) => inputChange(event,'verifyPassword')}/>
            { !match?(<p className="passwordMatch ">*password doesn't match</p>)
             :(null)}
            
          </div>
          </div>
          </div>
          <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Address Line 1</label>
            <input type="text" name="address.addressLine1" className="form-control"  placeholder="Address Line 1" required value={data.address.addressLine1} onChange={(event) => inputChange(event,'addressLine1')} />
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Address Line 2</label>  
            <input type="text" name="address.addressLine2" className="form-control"  placeholder="Address Line 2" required  value={data.address.addressLine2} onChange={(event) => inputChange(event,'addressLine2')} />
          </div>
          </div>
          </div>
          <div className="row col-md-12 down">
            <div className="col-md-4">
          <div className="form-group">
            <label className="label-text">City</label>
            <input type="text" name="address.city" className="form-control"  placeholder="City" required value={data.address.city} onChange={(event) => inputChange(event,'city')} />
          </div>
          </div>
          <div className="col-md-4">
          <div className="form-group">
            <label className="label-text">State</label>
            <input type="text" name="address.state" className="form-control"  placeholder="State" required value={data.address.state} onChange={(event) => inputChange(event,'state')} />
            </div>
          </div>
          <div className="col-md-4">
          <div className="form-group">
            <label className="label-text">Pincode</label>
            <input type="text"   name="address.pincode" className="form-control"  placeholder="Pincode" required onChange={(event) => inputChange(event,'pinCode')} />
         </div>
          </div>
          </div>
          <div className="customer_page_btn">
              <button type="submit" className="btn btn-block btn-style">CONTINUE</button>
          </div>
          <div className="resDiv">
        {error ? error : null} 
        {loading ? <Spinner animation="border" variant="primary" /> : null}       
      </div>
        </form>
      </div>
    </div>
    </div>
    <div className="col-md-5">
    <Map onClick={event => getCoordinates(event)} latitude={latitude} longitude={longitude}/>
            
    </div>
    </div>
    </div>
  );
};

export default CustomerRegister;
