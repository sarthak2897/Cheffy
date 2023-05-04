import React from 'react';

import {BrowserRouter as Router,Route, Switch} from 'react-router-dom';
import './App.css';
import { useHistory } from 'react-router';
import Login from './views/Login/Login'
import CustomerRegister from './views/CustomerRegistration/CustomerRegistration'
import ChefRegister from './views/ChefRegistration/ChefRegistration'
import BookChef from './views/Order/BookChef';
import MyOrder from './views/MyOrders/MyOrders';
import MyAllocation from './views/MyAllocations/MyAllocations';
import PaymentProcess from './views/PaymentProcess/PaymentProcess';
import MyProfile from './views/MyProfile/MyProfile';
import OrderSummary from './views/OrderSummary/OrderSummary';
import CustomerProfile from './components/Profile/CustomerProfile';
import ChefProfile from './components/Profile/ChefProfile';

function App() {
  return (
    <Router>
    <div className="App">
      <Switch> 
          <Route path="/" exact component={Login}/>
          <Route path="/customerRegister" exact component={CustomerRegister}/>
          <Route path="/chefRegister" component={ChefRegister}/>
          <Route path="/bookChef" component={BookChef}/>
          <Route path="/myOrders" component={MyOrder}/>
          <Route path="/myAllocations" component={MyAllocation}/>
          <Route path="/paymentProcess" component={PaymentProcess}/>
          <Route path="/myProfile" component={ChefProfile}/>
          <Route path="/orderSummary" component={OrderSummary}/>
          <Route path="/customerProfile" component={CustomerProfile}/>
      </Switch>
    </div>
  </Router>
  );
}
//Akash
export default App;
