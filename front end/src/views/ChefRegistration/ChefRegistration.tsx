import React, { SyntheticEvent, useState } from 'react';
import axios from 'axios';
import "../CustomerRegistration/CustomerRegistration.css";
import { useHistory} from 'react-router-dom'
import Chef from '../../model/Chef';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import { Spinner } from 'react-bootstrap';



const ChefRegister = () => {
  const initialState ={
    "chefDetailDTO": {
      "experience": 5,
      "qualification": "Diploma"
    },
    "userDetailDTO": {
      "email": "ankita1@email.com",
      "gender": "Female",
      "name": "Ankita",
      "password": "ank",
      "phoneNumber": 987654321,
      "userType": "ROLE_CHEF",
      "verifyPassword": "ank"
    }
}
  const [data,setData] = useState<Chef>(initialState);
  const [match, setMatch]= useState<boolean>(false);
  const [value, setValue] = React.useState<String>('Female');
  const [error,setError] = useState('');
  const [loading,setLoading] = useState(false);
  const history= useHistory();
  const viewChange=()=>{
    history.push({
      pathname: '/myAllocations'
    
    })
     
     }
     const addChef = async (event : React.FormEvent<HTMLFormElement>) => {
      event?.preventDefault();
      console.log(data);
      try{
          setLoading(true);
          const response = await axios.post('http://127.0.0.1:8080/api/chef/register/',data);
          //const response = await axios.get('http://127.0.0.1:8080/api/user/dummy');
          console.log(response);
          if(response.status === 200 && response.data.includes('successfully registered')){
            //history.push('/',{state:{message : response.data}});// back to login page
            history.push({pathname : '/',state : {message : response.data}});
          }
          else{
            history.push('/chefRegister');
            setError("Some error occurred!");
          }
      
      }
      catch(error){
          console.log(error.message);
          setError(error.response.data);
          
      }
      setLoading(false);
     }
     const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
       setValue(event.target.value);
       // setData({...data,  data.userDetailDTO.gender=value})  
     };

     const matchPassword = (event:React.ChangeEvent<HTMLInputElement>) => {
      const {chefDetailDTO,userDetailDTO} = data;
      if(userDetailDTO.password===event.target.value){

       setMatch(true);
        //console.log(match)
    
      }
      else{
        setMatch(false);
        //console.log(match)
      }
    }


     const inputChange = (event : React.ChangeEvent<HTMLInputElement>,propertyName : string) => {
       const {chefDetailDTO,userDetailDTO} = data;
       console.log(event.target.type);
       if(event.target.name.includes('chefDetailDTO')){
         const newChefDetailDTO = {...chefDetailDTO,[propertyName] : event.target.value};
         console.log(newChefDetailDTO);
         setData({...data,chefDetailDTO : newChefDetailDTO});
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
// const ChefRegister = () => {
  return (
      <div className="row col-md-12">
    <div className="col-md-2"></div>
    <div className="col-md-8">
    <div className="card card-mod">
    <div className="card-header">YOUR DETAILS</div>
    <div className="card-body">
        <form onSubmit={event => addChef(event)}>
        <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Enter Full Name</label>
            <input type="text" className="form-control" name="userDetailDTO.name" placeholder="Full Name" required onChange={event => inputChange(event,'name')} />
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Email Id</label>
            <input type="email" className="form-control" name="userDetailDTO.email" placeholder="Email" required onChange={(event) => inputChange(event,'email')} />
          </div>
          </div>
          </div>
          <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Phone Number</label>
            <input type="tel" pattern="[0-9]{10}" required className="form-control" id="tel" placeholder="Enter the number" name="userDetailDTO.phoneNumber" onChange={(event) => inputChange(event,'phoneNumber')}/>
          </div>
          </div>
          <div className="col-md-6">
          <label className="label-text">Gender</label>
          <RadioGroup  value={value} onChange={event => inputChange(event,'gender')}>
          <FormControlLabel name="gender" value="Female"control={<Radio />} label="Female" />
          <FormControlLabel  name="gender" value="Male"control={<Radio />} label="Male" />
          {/* <FormControlLabel   name="gender"value="other" control={<Radio />} label="Other" /> */}
         
        </RadioGroup>
        </div>
        </div>
        <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Qualification</label>
            <input type="text" className="form-control" name="chefDetailDTO.qualification" required onChange={(event) => inputChange(event,'qualification')} placeholder="Enter Qualification" />
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Total years of experience</label>
            <input type="number" className="form-control" name="chefDetailDTO.experience" min="0" required onChange={(event) => inputChange(event,'experience')} placeholder="Enter Experience" />
          </div>
          </div>
          </div>
          <div className="row col-md-12 down">
            <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Password</label>
            <input type="password" className="form-control" id="pwd" name="userDetailDTO.password" required onChange={(event) => inputChange(event,'password')} placeholder="Password" />
          </div>
          </div>
          <div className="col-md-6">
          <div className="form-group">
            <label className="label-text">Confirm Password</label>
            <input type="password" className="form-control" id="cpwd" name="userDetailDTO.verifyPassword" placeholder="Verify Password" required onChange={(event) => inputChange(event,'verifyPassword')} />
            { !match?(<p className="passwordMatch ">*password doesn't match</p>)
             :(null)}
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
    <div className="col-md-2"></div>
    </div>
  );
};

export default ChefRegister;
