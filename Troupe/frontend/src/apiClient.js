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
        window.sessionStorage.setItem("loginMember", response);
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

  //팔로워 수 확인
  getFollowerCount: (profileMemberNo) => {
    instance
      .get(`/profile/${profileMemberNo}/follow/fans/count`)
      .then((response) => {
        alert("FollowerCount : " + response.fanCount);
        return response.fanCount;
      })
      .catch((error) => {
        alert("FollowerCount 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //팔로우 여부 확인
  isFollowing: (data) => {
    instance
      .get(`profile/${data.profileMemberNo}/follow`, data.fanMemberNo)
      .then((response) => {
        alert("isFollowing : " + response.isFollowing);
        return response.isFollowing;
      })
      .catch((error) => {
        alert("isFollowing 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //팔로우 언팔로우 클릭
  follow: (data) => {
    data.isFollowing
      ? instance
          .post(`/profile/${data.profileMemberNo}/follow`, data.fanMemberNo)
          .then((response) => {
            alert("팔로우 하였습니다." + response);
            return true;
          })
          .catch((error) => {
            alert(data + " 팔로우실패 : " + error);
            return false;
          })
      : instance
          .delete(`/profile/${data.profileMemberNo}/follow`, data.fanMemberNo)
          .then((response) => {
            alert("팔로우 취소하였습니다" + response);
            return true;
          })
          .catch((error) => {
            alert(data + " 팔로우 취소실패 : " + error);
            return false;
          });
  },

  //회원정보 불러오기
  getMemberInfo: (userNo) => {
    instance
      .get(`/member/${userNo}`)
      .then((response) => {
        alert("Member : " + response);
        return response;
      })
      .catch((error) => {
        alert("Member 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //관심카테고리 불러오기
  getInterestCategory: (userNo) => {
    instance
      .get(`/profile/${userNo}/interest/category`)
      .then((response) => {
        alert("category : " + response);
        return response;
      })
      .catch((error) => {
        alert("category 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //호감도 순위정보
  getLikeabilityData: (userNo) => {
    instance
      .get(`/profile/${userNo}/likability/topstars/`)
      .then((response) => {
        alert("likability data : " + response);
        return response;
      })
      .catch((error) => {
        alert("likability data를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
};

export default apiClient;
