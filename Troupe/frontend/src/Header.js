import styled from "./css/a.module.css"
import React from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@mui/icons-material/Menu';
import MovieFilterIcon from '@mui/icons-material/MovieFilter';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/Logout';

const useStyles = makeStyles({
  list: {
    width: 250,
  },
  fullList: {
    width: 'auto',
  },
});

export default function Header() {
  const classes = useStyles();
  const [state, setState] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });

  const toggleDrawer = (anchor, open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }

    setState({ ...state, [anchor]: open });
  };

  const list = (anchor, repIcon, listObject) => (
    <div
      style={{ height: "100%", float: anchor, background: "#EAC88E"}}
      className={clsx(classes.list, {
        [classes.fullList]: anchor === 'top' || anchor === 'bottom',
      })}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List style={{height: "50px"}}>
        <div style={{ float: anchor }}>
          <ListItem button key="headerMenu" style={{ float: anchor }}>
            <ListItemIcon>{repIcon}</ListItemIcon>
            <ListItemText primary=''/>
          </ListItem>
        </div>
      </List>
      <Divider />
      <List>
        <ListItem><Divider /></ListItem>
        {listObject.map((object, index) => (
          <ListItem button key={object.text}>
            <ListItemIcon>{object.icon}</ListItemIcon>
            <ListItemText primary={object.element} />
          </ListItem>
        ))}
      </List>
      <Divider />
    </div>
  );

  return (
    <div id="header" style={{ width: "100%", background: "#EEE3D0", textAlign: "center", fontSize: "50px" }}>
      <div style={{ float: "left", width: "100px"}}>
        {[{ anchor: 'left', icon: <MenuIcon fontSize="large"/> },
          // { anchor: 'right', icon: <MenuIcon fontSize="large"/> },
          // { anchor: 'top', icon: <MenuIcon fontSize="large"/> },
          // { anchor: 'bottom', icon: <MenuIcon fontSize="large" /> }
        ].map((object) => (
          <React.Fragment key={object.anchor}>
            <Button onClick={toggleDrawer(object.anchor, true)}>{object.icon}</Button>
            <Drawer anchor={object.anchor} open={state[object.anchor]} onClose={toggleDrawer(object.anchor, false)}>
              {list(object.anchor, <MenuIcon fontSize="large" />,
                [{
                  text: "공연/전시 목록", element: <a href="/perf/list" className={ styled.header }>공연/전시 목록</a>, icon: <MovieFilterIcon fontSize="large" /> },
                { text: "피드 목록", element: <a href="/feed/list" className={ styled.header }>피드 목록</a>, icon: <CreditCardIcon fontSize="large" /> }])
              }
            </Drawer>
          </React.Fragment>
          ))}
      </div>
      <a href="/" className={ styled.header }>Troupe</a>
      <div style={{ float: "right", width: "100px", background: "#FFFFFF" }}>
        <div style={{float: "right"}}>
          <React.Fragment key='right'>
            <Button onClick={toggleDrawer('right', true)}><AccountCircleIcon fontSize="large" /></Button>
            <Drawer anchor='right' open={state['right']} onClose={toggleDrawer('right', false)}>
              {list("right", <AccountCircleIcon fontSize="large" />,
                [{ text: "마이페이지", element: <a href="/profile" className={ styled.header }>마이페이지</a>, icon: <AccountCircleIcon fontSize="large" /> },
                { text: "로그아웃", element: <a href="/test" className={ styled.header }>로그아웃</a>, icon: <LogoutIcon fontSize="large" /> }])
              }
            </Drawer>
          </React.Fragment>
        </div>
      </div>
    </div>
  );
}
