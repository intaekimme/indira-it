/*global FB*/
/*global statusChangeCallback*/
import React from "react";
import apiClient from "../apiClient";

import Button from "@mui/material/Button";
import FacebookIcon from "@mui/icons-material/Facebook";
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'
const MySwal = withReactContent(Swal)
export default function SNSLoginFacebook() {
  window.fbAsyncInit = function () {
    FB.init({
      appId: 1231400157620627,
      cookie: true,
      xfbml: true,
      version: "v9.0",
    });

    FB.AppEvents.logPageView();
  };

  (function (d, s, id) {
    var js,
      fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
      return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = document.location.protocol + "//connect.facebook.net/ko_KR/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  })(document, "script", "facebook-jssdk");

  //로그인 상태 확인
  function checkLoginState() {
    FB.getLoginStatus(function (response) {
      // statusChangeCallback(response);
      if (response.status === "connected") {
        //로그인상태
        //DB 회원가입 되있을 시 로그인
        //없으면 새롭게 생성 후 로그인
        //apiClient.login();
        MySwal.fire({
          icon: 'warning',
          title: '로그인 상태입니다',
          confirmButtonColor: '#66cc66',
          confirmButtonBorder: '#66cc66',
        })
      } else {
        const loginData = FB.login((response) => {
          // alert("로그인시도");
          if (response.status === "connected") {
            //로그인상태
            //DB 회원가입 되있을 시 로그인
            //없으면 새롭게 생성 후 로그인
            //apiClient.login();
          }
        });
      }
    });
  }
  // status는 앱 사용자의 로그인 상태를 지정합니다. 상태는 다음 중 하나일 수 있습니다.
  // connected - 사용자가 Facebook에 로그인하고 앱에 로그인했습니다.
  // not_authorized - 사용자가 Facebook에는 로그인했지만 앱에는 로그인하지 않았습니다.
  // unknown - 사용자가 Facebook에 로그인하지 않았으므로 사용자가 앱에 로그인했거나 FB.logout()이 호출되었는지 알 수 없어, Facebook에 연결할 수 없습니다.

  // connected 상태인 경우 authResponse가 포함되며 다음과 같이 구성되어 있습니다.
  // accessToken - 앱 사용자의 액세스 토큰이 포함되어 있습니다.
  // expiresIn - 토큰이 만료되어 갱신해야 하는 UNIX 시간을 표시합니다.
  // signedRequest - 앱 사용자에 대한 정보를 포함하는 서명된 매개변수입니다.
  // userID - 앱 사용자의 ID입니다.

  return (
    <div style={{ position: "relative" }}>
      <Button
        fullWidth
        variant="contained"
        sx={{ mt: 0, mb: 1 }}
        style={{ backgroundColor: "#365899", fontFamily: "SBAggroB" }}
        onClick={checkLoginState}
        className="fb-login-button"
        data-max-rows="1"
        data-size="large"
        data-button-type="continue_with"
        data-use-continue-as="true"
        data-width="1000"
        data-layout="default"
        data-auto-logout-link="false"
      >
        <FacebookIcon
          style={{
            marginRight: "10px",
            color: "white",
            backgroundColor: "#365899",
          }}
        ></FacebookIcon>
        FaceBook으로 계속하기
      </Button>

      {/* <div
        style={{ marginBottom: "10px" }}
        className="fb-login-button"
        data-max-rows="1"
        data-size="large"
        data-button-type="continue_with"
        data-use-continue-as="true"
        data-width="1000"
        data-layout="default"
        data-auto-logout-link="false"
        onlogin={checkLoginState}
      ></div> */}
    </div>
  );
}
