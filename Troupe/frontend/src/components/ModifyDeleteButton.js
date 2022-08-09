import React, { Fragment } from "react";
import { Button } from "@mui/material";
import apiClient from "../apiClient";
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
        variant="outlined"
        href="/feed/list"
        style={{
          margin: "8px",
          backgroundColor: "beige",
          fontFamily: "IBM Plex Sans KR",
        }}
      >
        피드 목록
      </Button>
      {user ? (
        <Fragment>
          <Button
            variant="outlined"
            href={"/feed/modify/" + props.feedNo}
            style={{
              margin: "8px",
              backgroundColor: "skyblue",
              fontFamily: "IBM Plex Sans KR",
            }}
          >
            수정
          </Button>
          <Button
            variant="outlined"
            style={{
              margin: "8px",
              backgroundColor: "pink",
              fontFamily: "IBM Plex Sans KR",
            }}
            onClick={onRemove}
          >
            삭제
          </Button>
        </Fragment>
      ) : (
        ""
      )}
    </div>
  );
}
