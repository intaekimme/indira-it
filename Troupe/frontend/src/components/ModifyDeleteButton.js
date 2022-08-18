import React, { Fragment } from "react";
import { Button } from "@mui/material";
import apiClient from "../apiClient";
import { SvgIcon } from "@mui/material";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import DeleteIcon from "@mui/icons-material/Delete";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
const MySwal = withReactContent(Swal)
export default function ModifyDeleteButton(props) {
  const [user, setUser] = React.useState(false);

  React.useEffect(() => {
    setUser(parseInt(sessionStorage.getItem("loginMember")) === props.memberNo);
  }, [sessionStorage.getItem("loginMember"), props.memberNo]);
  function handleUser() {
    setUser(!user);
  }
  function onRemove() {
    Swal.fire({
      title: '삭제하시겠습니까?',
      showCancelButton: true,
      confirmButtonText: '예',
      cancelButtonText: '아니오',
      confirmButtonColor: 'red',
    }).then((result)=>{
      if(result.isConfirmed){
        apiClient.feedRemove(props.feedNo);

      }
    })
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
