
import React, { FunctionComponent, useState,useEffect } from 'react';
import './Counter.css'
import Fab from '@material-ui/core/Fab';

import AddIcon from '@material-ui/icons/Add';
import RemoveIcon from '@material-ui/icons/Remove';


const ButtonPlusMinus:FunctionComponent<{ initial?: number, peopleCount : (count : number | undefined) => void}> = (props) => {
  const[count, setCount]=useState(1);

  useEffect(() => {
    props.peopleCount(count);
  }, [count]);

    

    return(
<div className="counter">

<Fab size="medium" color="primary" aria-label="Add" onClick={ () => setCount(count>1 ? (count - 1) : count)}>
          <RemoveIcon />
        </Fab>
    {/* <button className='dec' onClick={ () => setCount(count>1 ? (count - 1) : count)}>_</button> */}
    <button className='reset' >{count}</button>
    {/* <button className='inc' onClick={() => setCount(count + 1)}>+</button> */}
    <Fab size="medium" color="primary" aria-label="Add" onClick={() => setCount(count + 1)} >
          <AddIcon />
        </Fab>
   
</div>
      )
}

export default ButtonPlusMinus;
