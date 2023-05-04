import React, { useReducer, useEffect, useState } from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import { useHistory, useLocation} from 'react-router-dom'
import TextField from '@material-ui/core/TextField';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import CardHeader from '@material-ui/core/CardHeader';
import Button from '@material-ui/core/Button';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormLabel from '@material-ui/core/FormLabel';
import Header from '../../components/Header/Header';
import axios from 'axios';
import './Login.css'


// import chefLogo from './../../../public/chef-logo.png'

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
      width: 400,
      margin: `${theme.spacing(0)} auto`
    },
    loginBtn: {
      marginTop: theme.spacing(2),
      flexGrow: 1
    },
    header: {
      textAlign: 'center',
      background: '#212121',
      color: '#fff'
    },
    card: {
      marginTop: theme.spacing(10)
    }
  
  })
);

//state type

type State = {
  username: string
  password:  string
  isButtonDisabled: boolean
  helperText: string
  isError: boolean
};

const initialState:State = {
  username: '',
  password: '',
  isButtonDisabled: true,
  helperText: '',
  isError: false
};

type Action = { type: 'setUsername', payload: string }
  | { type: 'setPassword', payload: string }
  | { type: 'setIsButtonDisabled', payload: boolean }
  | { type: 'loginSuccess', payload: string }
  | { type: 'loginFailed', payload: string }
  | { type: 'setIsError', payload: boolean };

const reducer = (state: State, action: Action): State => {
   
  switch (action.type) {
    case 'setUsername': 
      return {
        ...state,
        username: action.payload
      };
    case 'setPassword': 
      return {
        ...state,
        password: action.payload
      };
    case 'setIsButtonDisabled': 
      return {
        ...state,
        isButtonDisabled: action.payload
      };
    case 'loginSuccess': 
      return {
        ...state,
        helperText: action.payload,
        isError: false
      };
    case 'loginFailed': 
      return {
        ...state,
        helperText: action.payload,
        isError: true
      };
    case 'setIsError': 
      return {
        ...state,
        isError: action.payload
      };
  }
}

interface postloginMessage {
  message : string;
}

const Login = () => {
  const classes = useStyles();
  const [value, setValue] = React.useState<String>('customer');
  const [state, dispatch] = useReducer(reducer, initialState);

  const history = useHistory();
  const [error,setError] = useState('');
  const location = useLocation().state as postloginMessage;



  useEffect(() => {
    if (state.username.trim() && state.password.trim()) {
     dispatch({
       type: 'setIsButtonDisabled',
       payload: false
     });
    } else {
      dispatch({
        type: 'setIsButtonDisabled',
        payload: true
      });
    }
  }, [state.username, state.password]);

//   handleTwoLoginFun: function(){
//     viewChange();
//     handleLogin();
//  },


// const handleChange = (event) => {
//   setValue(value);
// };
  const viewChangeAsPerUser=()=>{
    if(value==='chef'){
      history.push({
        pathname: '/chefRegister',
       
      })
    }else{
      history.push({
        pathname: '/customerRegister',
       
      })
    }
  }
  const viewChange=()=>{
    history.push({
      pathname: '/bookChef',
     
    })
     
     }

  const handleLogin = () => {
    if (state.username === 'abc@email.com' && state.password === 'password') {
      dispatch({
        type: 'loginSuccess',
        payload: 'Login Successfully'
      });
     
    } else {
      dispatch({
        type: 'loginFailed',
        payload: 'Incorrect username or password'
      });
    }
  };

  const loginHandler = async () => {
      try{
      const response = await axios.post('http://127.0.0.1:8080/api/user/login',{username : state.username,password : state.password});
      if(response.status === 200){
        console.log(response.data);
        //if(value==='customer'){
          if(response.data.role === 'ROLE_CUSTOMER'){
          history.replace({pathname : '/bookChef',state:{'id': response.data.accountId,'username':response.data.username}}); //URL for customers
        }
        // else if( value==='chef'){
        //   history.push('/myAllocations'); // URL for chefs
        // }
        else{
          history.replace({pathname : '/myAllocations',state:{'id' : response.data.accountId,'username': response.data.username}});  // IF user is a chef
        }
        
      }
      else{
        setError('Invalid username/password! Try again!');
      }
    }
    catch(error){
      console.log(error.response.data);
      if(error.response.data === 'Bad credentials')
        setError('Invalid username/password! Try again!');
      else
        setError(error.response.data);
    }
  }

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.keyCode === 13 || event.which === 13) {
      state.isButtonDisabled || loginHandler();
    }
  };

  const handleUsernameChange: React.ChangeEventHandler<HTMLInputElement> =
    (event) => {
      dispatch({
        type: 'setUsername',
        payload: event.target.value
      });
    };

  const handlePasswordChange: React.ChangeEventHandler<HTMLInputElement> =
    (event) => {
      dispatch({
        type: 'setPassword',
        payload: event.target.value
      });
    }
    
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
      setValue((event.target as HTMLInputElement).value);
    };
  return (
    <form className={classes.container} noValidate autoComplete="off">
      <Card className={classes.card}>
        {/* <CardHeader className={classes.header} title="Login App" /> */}
      <Header/>
      <RadioGroup  value={value} onChange={handleChange}>
          <FormControlLabel value="customer" control={<Radio />} label="I'm looking for a chef" className="cust_radio_1" />
          <FormControlLabel value="chef" control={<Radio />} label="I'm a chef" className="chef_radio_2" />
        </RadioGroup>
        <CardContent>
          <div>
            <TextField
              error={state.isError}
              fullWidth
              id="username"
              type="email"
              label="Email"
              placeholder="email"
              margin="normal"
              onChange={handleUsernameChange}
              onKeyPress={handleKeyPress}
            />
            <TextField
              error={state.isError}
              fullWidth
              id="password"
              type="password"
              label="Password"
              placeholder="Password"
              margin="normal"
              helperText={state.helperText}
              onChange={handlePasswordChange}
              onKeyPress={handleKeyPress}
            />
          </div>
        </CardContent>
        <CardActions>
          <Button
            variant="contained"
            size="large"
            color="primary"
            className={classes.loginBtn}
            //onClick={() => { viewChange(); handleLogin();}}
            onClick={loginHandler}
            disabled={state.isButtonDisabled}>
            Login
          </Button>
        </CardActions>
        <h4 className="heading_or">OR</h4>
        <CardActions>
          <Button
            variant="contained"
            size="large"
            color="primary"
            className={classes.loginBtn}
            onClick={viewChangeAsPerUser}
            // disabled={state.isButtonDisabled}
            >
            Register
          </Button>
        </CardActions>
        <p style={{textAlign : 'center',color: 'green'}}>{error ? error : (location?.message ? location?.message : null)}</p>
      </Card>
    </form>
    
  );
}

export default Login;
 