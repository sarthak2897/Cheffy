import React from 'react'
import { useHistory } from 'react-router';
import logo from '../../images/cheffy-logo.png';
import './Logo.css';
const Logo:React.FC<{id : number}> = (props) => {
const history = useHistory();

    const navigateToHomePage = () => {
           // history.push({pathname : '/bookChef',state:{id : props.id}});
    }
    return (
        <>
            <img className="cheffyLogo" src={logo} alt="logo" onClick={navigateToHomePage}/>
        </>
    )
}

export default Logo
