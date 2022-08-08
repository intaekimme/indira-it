import React from "react";
import Button from "@mui/material/Button";
export default function EmailSend() {
  const login = () => {
    window.location.href = "/login";
  };

  return (
    <div
      style={{
        position: "absolute",
        top: "40%",
        left: "25%",
        fontSize: "40px",
        width: "600px",
        height: "300px",
        backgroundColor: "#AAAAAA",
        textAlign: "center",
      }}
    >
      <div>
        <br />
        인증메일이 전송되었습니다.
        <br />
        확인 후 로그인을 시도해주세요.
      </div>
      <div>
        <br />
        <Button onClick={login} style={{ fontSize: "20px", width: "50%" }}>
          로그인페이지로 이동
        </Button>
      </div>
    </div>
  );
}