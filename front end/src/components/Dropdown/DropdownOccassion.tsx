import React from 'react';
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
const DropdownOccassion : React.FC<{selectOccasion : (value : string,propertyName : string) => void}> = (props) => {
  const classes = useStyles();

  const selectOccasion = (event : React.ChangeEvent<{name? : string | undefined, value : unknown}>) => {
    console.log(event.target.value);
    const value : string = event.target.value as string;
    props.selectOccasion(value,'occasion');
}
 

    return(

      <>
      <FormControl className={classes.formControl}>
      <InputLabel htmlFor="grouped-select">Ocassion</InputLabel>
      <Select defaultValue="" id="grouped-select"  onChange={event => selectOccasion(event)}>
     
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
         
          <MenuItem value="Party">Party</MenuItem>
          <MenuItem value="Dinner Date">Dinner Date</MenuItem>
          <MenuItem value="Family Meetup">Family Meetup</MenuItem>
          <MenuItem value="Function">Function</MenuItem>
          <MenuItem value="Formal">Formal</MenuItem>
          {/* <MenuItem value={6}>Kashmiri</MenuItem> */}
        </Select>
        </FormControl>
      </>
      )
}

export default DropdownOccassion;
 