import React, { Fragment } from "react";
import { Button } from "@mui/material";
import apiClient from "../apiClient";
import { SvgIcon } from "@mui/material";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import DeleteIcon from "@mui/icons-material/Delete";
export default function ModifyDeleteButton(props) {
  const [user, setUser] = React.useState(false);

  React.useEffect(() => {
    setUser(parseInt(sessionStorage.getItem("loginMember")) === props.memberNo);
  }, [sessionStorage.getItem("loginMember"), props.memberNo]);
  function handleUser() {
    setUser(!user);
  }
  function onRemove() {
    apiClient.feedRemove(props.feedNo);
  }

  return (
    <div style={{ float: "right", backgroundColor: "#FFF" }}>
      <Button
        href="/feed/list/all/0"
        style={{
          margin: "5px",
          backgroundColor: "transparent",
        }}
      >
        <SvgIcon component={FormatListBulletedIcon} color="action"></SvgIcon>
      </Button>
      {user ? (
        <Fragment>
          <Button
            href={"/feed/modify/" + props.feedNo}
            style={{
              margin: "5px",
              backgroundColor: "transparent",
            }}
          >
            <SvgIcon component={ModeEditIcon} color="action"></SvgIcon>
          </Button>
          <Button
            style={{
              margin: "5px",
              backgroundColor: "transparent",
            }}
            onClick={onRemove}
          >
            <SvgIcon component={DeleteIcon} color="action"></SvgIcon>
          </Button>
        </Fragment>
      ) : (
        ""
      )}
    </div>
  );
}
