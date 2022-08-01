import instance from "axios";

// const instance = axios.create({
//   baseURL: process.env.REACT_APP_MAGAZINE_API_BASE_URL,
// });

const apiClient = {
  //로그인
  login: (loginInfo) => {
    instance
      .post("/member/login", loginInfo)
      .then((response) => {
        window.sessionStorage.setItem("loginCheck", true);
        window.sessionStorage.setItem("loginUser", response);
        alert("로그인 되었습니다.");
        window.location.href = "/";
      })
      .catch((error) => {
        alert("로그인 실패 : " + error);
      });
  },
  //회원가입
  signup: (data) => {
    instance
      .post("/member/signup", data)
      .then((response) => {
        alert("회원가입 되었습니다." + response);
      })
      .catch((error) => {
        alert("회원가입 실패 : " + error);
      });
  },

  //이메일 중복체크
  existEmail: (data) => {
    instance
      .post("/member/signup/email", data)
      .then((response) => {
        alert("사용 가능합니다." + response);
      })
      .catch((error) => {
        alert("중복된 email입니다. : " + error);
      });
  },

  //닉네임 중복체크
  existNickname: (data) => {
    instance
      .post("/member/signup/nickname", data)
      .then((response) => {
        alert("사용 가능합니다." + response);
      })
      .catch((error) => {
        alert("중복된 nickname입니다. : " + error);
      });
  },

  //팔로우 언팔로우 클릭
  follow: (data) => {
    data.follow
      ? instance
          .post(`/profile/${data.userNo}/follow`, data.currentUser)
          .then((response) => {
            alert("팔로우 하였습니다." + response);
          })
          .catch((error) => {
            alert(data + " 팔로우실패 : " + error);
          })
      : instance
          .delete(`/profile/${data.userNo}/follow/del`, data.currentUser)
          .then((response) => {
            alert("팔로우 취소하였습니다" + response);
          })
          .catch((error) => {
            alert(data + " 팔로우 취소실패 : " + error);
          });
  },

  //회원정보 불러오기
  getProfile: (userNo) => {
    instance
      .get(`/profile/${userNo}`)
      .then((response) => {
        alert("User : " + response);
        return response;
      })
      .catch((error) => {
        alert("User 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
};

export default apiClient;
