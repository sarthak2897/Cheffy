import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import photu from '../../images/chef-logo.png';
import Chef from '../../model/Chef';
import DrawerLeft from '../Drawer/Drawer';
import './CustomerProfile.css'

interface chef {
    id : number,
    username : string
}

const ChefProfile = () => {

    const state = useLocation().state as chef;
    const [data,setData] = useState<Chef>();
    const [loading,setLoading] = useState(false);
    const [message,setMessage] = useState('');

    useEffect(() => {
        setLoading(true);
          const fetchChefData = async () => {
            try{
              
              const response = await axios.get(`http://127.0.0.1:8080/api/chef/get-profile?chefId=${state.id}`);
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
          fetchChefData();
          setLoading(false);  
      }, []);

    return(
        <>
        <DrawerLeft id={state.id} user="chef" username={state.username}/>
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
                            <h6 className="profile_info_data4">Qualification : </h6>
                            <p className="data_txt">{data?.chefDetailDTO.qualification}</p>
                        </div>
                        <div className="profile_info_line">
                            <h6 className="profile_info_data5">Experience : </h6>
                            <p className="data_txt">{data?.chefDetailDTO.experience} years</p>
                        </div>
                    </div>
                    {/* <button type="button" className="btn btn-primary btnedit">Edit</button> */}
                </div>
            </div>
            <div className="col-md-4"></div>
        </div></>
    )
}

export default ChefProfile;