import React from "react";

import './CouponCard.css'

type CardProps = {
    text: String,
    color:String
  
    // color:String
    
}

const CouponCard=({text, color}: CardProps)=> {

 

  return (
    <div className="card mb-2" style={{backgroundColor:`${color}`}}>
    <div className="card-body">
       <h5 className="card-title">Flat 25% Off</h5>
       <p className="card-text">Code: {text}</p>
       <p className="card-text">Only on Daily Meals Plan.</p>
     
     </div>
   </div>
  );
}
export default CouponCard
