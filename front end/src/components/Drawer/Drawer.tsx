import React from 'react';
import clsx from 'clsx';
import { ThemeProvider } from "@material-ui/core/styles";
import {

  Link, useHistory
} from "react-router-dom";
import { makeStyles, useTheme, Theme, createStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import MailIcon from '@material-ui/icons/Mail';
import {  createMuiTheme, capitalize } from "@material-ui/core";
import Logo from '../Logo/Logo';
import './Drawer.css';

const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
    },
    appBar: {
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
    },
    appBarShift: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth,
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    hide: {
      display: 'none',
    },
    drawer: {
      width: drawerWidth,
      flexShrink: 0,
    },
    drawerPaper: {
      width: drawerWidth,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: theme.spacing(0, 1),
      // necessary for content to be below app bar
      ...theme.mixins.toolbar,
      justifyContent: 'flex-end',
    },
    content: {
      flexGrow: 1,
      padding: theme.spacing(3),
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      marginLeft: -drawerWidth,
    },
    contentShift: {
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
      marginLeft: 0,
    },
  }),
);
const styles = {
  // sideNav: {
  //   marginTop: '-60px',
  //   zIndex: 3,
  //   marginLeft: '0px',
  //   position: 'fixed',
  // },
  link: {
    color: 'black',
    textDecoration: 'none',
  }
};
const DrawerLeft:React.FC<{id : number,user : string, username : string}> = (props) =>{
  const classes = useStyles();
  const theme = useTheme();
  const history = useHistory();
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const logout=() =>{
    localStorage.clear();
    window.location.href = '/';
}

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const goToMyOrders=()=>{
    console.log(props.id);
    history.push({pathname : '/myOrders',state : {id : props.id}});
  }

  return (
    <div className={classes.root}>
      <CssBaseline />
      
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, open && classes.hide)}
          >
            <MenuIcon />
          </IconButton>
          <Logo id={props.id}/>
          <h6>Welcome {props.username.split(' ')[0]}</h6>
          {/* <Typography variant="h6" noWrap>
            Persistent drawer
          </Typography> */}
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <div className={classes.drawerHeader}>
        {/* <h2 style={{fontSize: 38, fontFamily: "cursive", fontStyle: "italic", fontVariant: "all-petite-caps", marginRight: 40}}>Cheffy</h2> */}
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </div>
        <Divider />
        {/* <List>
          {['My Profile', 'My Order'].map((text, index) => (
            <ListItem button key={text}>
              <ListItemIcon>{index % 2 === 0 ? <InboxIcon /> : <MailIcon />}</ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List> */}
        <Link to={props.user === 'chef' ? {pathname : '/myProfile', state : {id : props.id,username : props.username}} : {pathname : '/customerProfile',state : {id : props.id,username : props.username}}} style={styles.link}>
            <List>
              <ListItem button key='My Profile'>
                <ListItemIcon><AccountCircleIcon/>
                </ListItemIcon>
                <ListItemText primary='My Profile' />
              </ListItem>
            </List>
          </Link>
          <Link to={props.user === 'chef' ? {pathname : '/myAllocations',state : {id : props.id,username : props.username}} :{pathname : '/myOrders',state : {id : props.id,username : props.username}}} style={styles.link}>
          <List>
            <ListItem button key={ props.user === 'chef' ? 'My Allocations' : 'My Orders'}>
              <ListItemIcon><AccountCircleIcon/>
              </ListItemIcon>
              <ListItemText primary={ props.user === 'chef' ? 'My Allocations' : 'My Orders'} />
            </ListItem>
            </List>
          </Link>
        <Divider />
        { props.user === 'customer' ?
        <Link to={{pathname : '/bookChef',state : {id : props.id,username : props.username}}} style={styles.link} >
          <List>
            <ListItem button key='Book Chef'>
              <ListItemIcon><AccountCircleIcon/>
              </ListItemIcon>
              <ListItemText primary='Book Chef' />
            </ListItem>
            </List>
          </Link> : null
            }
          <List>
          {/* <button onClick={logout} style={styles.link}>Logout</button> */}
          <ListItem button key='Logout' style={styles.link} onClick={logout}>
              <ListItemIcon><AccountCircleIcon/>
              </ListItemIcon>
              <ListItemText primary='Logout'/>
            </ListItem>
          </List>
      </Drawer>
     
    </div>
  );
}


export default DrawerLeft;