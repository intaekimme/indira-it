import styled from "./css/a.module.css";
import React from "react";
import clsx from "clsx";
import Drawer from "@mui/material/Drawer";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Link from "@mui/material/Link";
import apiClient from "./apiClient";
import MenuIcon from "@mui/icons-material/Menu";
import MovieFilterIcon from "@mui/icons-material/MovieFilter";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import LogoutIcon from "@mui/icons-material/Logout";
import logo from "./img/logo4.png";
import AddCircleIcon from "@mui/icons-material/AddCircle";
const useStyles = () => {
  return {
    list: {
      width: 250,
    },
    fullList: {
      width: "auto",
    },
  };
};

export default function Header() {
  const classes = useStyles();
  const [drawer, setDrawer] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });
  const [login, setLogin] = React.useState(
    window.sessionStorage.getItem("loginCheck") === "true",
  );

  const [profileImageUrl, setProfileImageUrl] = React.useState(
    "https://s3.ap-northeast-2.amazonaws.com/hongjoo.troupe.project/profile/defalut.png",
  );
  React.useEffect(() => {
    if (login) {
      apiClient.getMyinfo().then((data) => {
        if (
          data.profileImageUrl &&
          data.profileImageUrl !== "" &&
          data.profileImageUrl !== null
        ) {
          setProfileImageUrl(data.profileImageUrl);
        }
      });
    }
  }, []);
  const logout = () => {
    window.sessionStorage.removeItem("loginCheck");
    window.sessionStorage.removeItem("loginMember");
    window.sessionStorage.removeItem("accessToken");
    setLogin(false);
    alert("로그아웃 되었습니다.");
    console.log(login);
    window.location.href = window.location.href;
  };
  const toggleDrawer = (anchor, open) => (event) => {
    if (
      event.type === "keydown" &&
      (event.key === "Tab" || event.key === "Shift")
    ) {
      return;
    }

    setDrawer({ ...drawer, [anchor]: open });
  };

  const saveCurrentHref = () => {
    sessionStorage.setItem("currentHref", window.location.href);
  };

  const list = (anchor, repIcon, listObject) => (
    <div
      style={{ height: "100%", float: anchor, background: "#EAC88E" }}
      className={clsx(classes.list, {
        [classes.fullList]: anchor === "top" || anchor === "bottom",
      })}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List style={{ height: "50px" }}>
        <div style={{ float: anchor }}>
          <ListItem button key="headerMenu" style={{ float: anchor }}>
            <ListItemIcon>{repIcon}</ListItemIcon>
            <ListItemText primary="" />
          </ListItem>
        </div>
      </List>
      <Divider />
      <List>
        <ListItem>
          <Divider />
        </ListItem>
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
    <div
      id="header"
      style={{
        width: "100%",
        height: "150px",
        background: "#EEE3D0",
        textAlign: "center",
        fontSize: "50px",
      }}
    >
      <div style={{ float: "left", width: "100px", height: "100px" }}>
        {[
          {
            anchor: "left",
            icon: (
              <MenuIcon
                fontSize="large"
                sx={{ fontSize: "60px" }}
                style={{ verticalAlign: "middle" }}
              />
            ),
          },
        ].map((object) => (
          <React.Fragment key={object.anchor}>
            <Button
              onClick={toggleDrawer(object.anchor, true)}
              className={styled.header}
            >
              {object.icon}
            </Button>
            <Drawer
              anchor={object.anchor}
              open={drawer[object.anchor]}
              onClose={toggleDrawer(object.anchor, false)}
            >
              {list(object.anchor, <MenuIcon fontSize="large" />, [
                {
                  text: "공연/전시 목록",
                  element: (
                    <Link href="/perf/list/0" className={styled.header}>
                      공연/전시 목록
                    </Link>
                  ),
                  icon: (
                    <Link href="/perf/list/0" className={styled.header}>
                      <MovieFilterIcon fontSize="large" />
                    </Link>
                  ),
                },
                {
                  text: "피드 목록",
                  element: (
                    <Link href="/feed/list/all/0" className={styled.header}>
                      피드 목록
                    </Link>
                  ),
                  icon: (
                    <Link href="/feed/list/all/0" className={styled.header}>
                      <CreditCardIcon fontSize="large" />
                    </Link>
                  ),
                },
              ])}
            </Drawer>
          </React.Fragment>
        ))}
      </div>
      <div style={{ float: "right", width: "100px" }}>
        {login ? (
          [
            {
              anchor: "right",
              icon: (
                <img
                  src={profileImageUrl}
                  alt="random"
                  style={{
                    borderRadius: "70%",
                    objectFit: "cover",
                    height: "60px",
                    width: "60px",
                    border: "3px white solid",
                    boxShadow:
                      "0 10px 35px rgba(0, 0, 0, 0.05), 0 6px 6px rgba(0, 0, 0, 0.2)",
                  }}
                ></img>
                //   <AccountCircleIcon
                //   fontSize="large"
                //   sx={{ fontSize: "60px" }}
                //   style={{ verticalAlign: "middle" }}
                // />
              ),
            },
          ].map((object) => (
            <React.Fragment key="right">
              <Button
                onClick={toggleDrawer("right", true)}
                className={styled.header}
              >
                {object.icon}
              </Button>
              <Drawer
                anchor="right"
                open={drawer["right"]}
                onClose={toggleDrawer("right", false)}
              >
                {list("right", <AccountCircleIcon fontSize="large" />, [
                  {
                    text: "마이페이지",
                    element: (
                      <Link
                        href={`/profile/${sessionStorage.getItem(
                          "loginMember",
                        )}`}
                        className={styled.header}
                      >
                        마이페이지
                      </Link>
                    ),
                    icon: <AccountCircleIcon fontSize="large" />,
                  },

                  {
                    text: "공연 등록",
                    element: (
                      <Link href="/perf/register" className={styled.header}>
                        공연 등록
                      </Link>
                    ),
                    icon: (
                      <Link href="/perf/register" className={styled.header}>
                        <AddCircleIcon fontSize="large" color="action" />
                      </Link>
                    ),
                  },
                  {
                    text: "피드 등록",
                    element: (
                      <Link href="/feed/register" className={styled.header}>
                        피드 등록
                      </Link>
                    ),
                    icon: (
                      <Link href="/feed/register" className={styled.header}>
                        <AddCircleIcon fontSize="large" color="action" />
                      </Link>
                    ),
                  },
                  {
                    text: "로그아웃",
                    element: (
                      <Link className={styled.header} onClick={logout}>
                        로그아웃
                      </Link>
                    ),
                    icon: <LogoutIcon fontSize="large" />,
                  },
                ])}
              </Drawer>
            </React.Fragment>
          ))
        ) : (
          <Link
            href="/login"
            className={styled.header}
            onClick={saveCurrentHref}
          >
            <AccountCircleIcon
              fontSize="large"
              sx={{ fontSize: "60px" }}
              style={{ verticalAlign: "middle" }}
            />
          </Link>
        )}
      </div>
      <div style={{ width: "100%" }}>
        <Link
          href="/"
          className={styled.header}
          style={{ verticalAlign: "middle" }}
        >
          <img width={"270px"} src={logo}></img>
        </Link>
      </div>
    </div>
  );
}
