import React, { useState } from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import ListSubheader from '@material-ui/core/ListSubheader';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';


const useStyles = makeStyles((theme: Theme) =>
createStyles({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
}),
);
//const DropdownCuisine:React.FC<{selectCuisine : (event : React.ChangeEvent<{name? : string | undefined, value : unknown}>) => void}> = (props) => {
  const DropdownCuisine:React.FC<{selectCuisine : (value : string,propertyName : string) => void}> = (props) => {
  const classes = useStyles();
  
  //const [cuisine,setCuisine] = useState<string>('');
  const selectCuisine = (event : React.ChangeEvent<{name? : string | undefined, value : unknown}>) => {
      console.log(event.target.value);
      const value : string = event.target.value as string;
      props.selectCuisine(value,'cuisine');
  }

    return(

      <>
      <FormControl className={classes.formControl}>
      <InputLabel htmlFor="grouped-select" >Select Cuisine</InputLabel>
      <Select defaultValue="" id="grouped-select" onChange={event => selectCuisine(event)}>
     
          <MenuItem value="">
            <em>None</em>
          </MenuItem> 
         
          <MenuItem value="Italian">Italian</MenuItem>
          <MenuItem value="Chinese">Chinese</MenuItem>
          <MenuItem value="South Indian">South Indian</MenuItem>
          <MenuItem value="North Indian">North Indian</MenuItem>
          <MenuItem value="Goan">Goan</MenuItem>
          <MenuItem value="Kashmiri">Kashmiri</MenuItem>
        </Select>
        </FormControl>
      </>
      )
}

export default DropdownCuisine;
 