import React from 'react';
import photu from '../../images/chef-logo.png';
import './CustomerProfile.css';
import axios from 'axios';
import Customer from '../../model/Customer';
import { useState } from 'react';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import OrderSummary from '../../views/OrderSummary/OrderSummary';
import { Spinner } from 'react-bootstrap';
import DrawerLeft from '../Drawer/Drawer';

interface customer {
    id : number,
    username : string
}

const CustomerProfile = () => {
    const state = useLocation().state as customer;
    const [data,setData] = useState<Customer>();
    const [loading,setLoading] = useState(false);
    const [message,setMessage] = useState('');

    useEffect(() => {
        setLoading(true);
          const fetchCustomerData = async () => {
            try{
              
              const response = await axios.get(`http://127.0.0.1:8080/api/customer/get-profile?customerId=${state.id}`);
              console.log(response.data);
              if(response.status === 200){
                if(response.data.length === 0){
                  setMessage('There is no data to display');
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
          fetchCustomerData();
          setLoading(false);  
      }, []);

    //   const {address, userDetailDTO} = data;
    
    return loading ? <Spinner variant="primary" animation="border"/> : (
        <>
        <DrawerLeft id={state.id} user="customer" username={state.username}/>
        <div className="row col-md-12 sp">
            <div className="col-md-4"></div>
            <div className="col-md-4">
                <div className="card" style={{backgroundColor: 'khaki'}}>
                    <div className="profile_pic">
                        <img src={photu} height="40%" width="40%"></img>
                    </div>
                    <hr />
                    <div className="profile_info">
                        <div className="profile_info_line">
                            <h6 className="profile_info_data1">Name : </h6>
                            <p className="data_txt">{data?.userDetailDTO.name}</p>
                        </div>
                        <div className="profile_info_line">
                            <h6 className="profile_info_data2">Email : </h6>
                            <p className="data_txt">{data?.userDetailDTO.email}</p>
                        </div>
                        <div className="profile_info_line">
                            <h6 className="profile_info_data3">Phone : </h6>
                            <p className="data_txt">{data?.userDetailDTO.phoneNumber}</p>
                        </div>
                        <div className="profile_info_line">
                            <h6 className="profile_info_data3">Gender : </h6>
                            <p className="data_txt">{data?.userDetailDTO.gender}</p>
                        </div>
                        <div className="profile_info_line">
                            <h6 className="profile_info_data4">Address : </h6>
                            <p className="data_txt">{data?.address.addressLine1}<br/>{data?.address.addressLine2}<br/>{data?.address.city}, {data?.address.state}<br/>{data?.address.pinCode}</p>
                        </div>
                    </div>
                    {/* <button type="button" className="btn btn-primary btnedit">Edit</button> */}
                </div>
            </div>
            <div className="col-md-4"></div>
        </div></>
    )
}

export default CustomerProfile;