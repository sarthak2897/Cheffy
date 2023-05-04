import React from 'react'

import './Header.css';
import chefLogo from '../../images/chef-logo.png'
const Header = () => {
    return (
        <div className="header">
             <img className="logo" src={chefLogo} alt="logo"/>
            {/* <p>RANGE CALCULATOR</p> */}
      </div>
    )
}

export default Header
