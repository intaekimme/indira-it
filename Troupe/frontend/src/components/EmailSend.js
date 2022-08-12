import React from "react";
import { useParams } from "react-router-dom";
import Button from "@mui/material/Button";
import apiClient from "../apiClient";
export default function EmailSend() {

  const { token } = useParams();
  const login = () => {
    if (token) {
      apiClient.confirmEmail(token);
    }
    window.location.href = "/login";
  };

  React.useEffect(() => {
    if (token) {
      apiClient.confirmEmail(token);
    }
  }, [token]);

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
      {token ? (
        <div>
          <br />
          메일 인증이 완료되었습니다.
        </div>
      ) : (
        <div>
          <br />
          인증메일이 전송되었습니다.
          <br />
          확인 후 로그인을 시도해주세요.
        </div>
      )}
      <div>
        <br />
        <Button onClick={login} style={{ fontSize: "20px", width: "50%" }}>
          로그인페이지로 이동
        </Button>
      </div>
    </div>
  );
}