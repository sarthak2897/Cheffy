import React from 'react';
import './Input.css';

const Input: React.FC<{type:string,placeholder:string,name:string,onChange : (event: React.ChangeEvent<HTMLInputElement>) => void,value : string | number | undefined}> = (props) =>{
    return (
        <React.Fragment>
            <input className="input" type={props.type} name={props.name} placeholder={props.placeholder} onChange={props.onChange} value={props.value ? props.value : ""}/>
        </React.Fragment>
    )
}

export default Input



