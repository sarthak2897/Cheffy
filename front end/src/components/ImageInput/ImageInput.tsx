import React from 'react'
import './ImageInput.css'
const ImageInput : React.FC<{src : string,onClick : () => void,alt : string}> = (props) => {
 
    return (
        <>
          <input type="image"  className="input imgbtton" src={props.src} onClick={props.onClick} alt={props.alt}/>  
        </>
    )
}

export default ImageInput