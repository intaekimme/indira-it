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
        window.sessionStorage.setItem("loginMember", response.data.memberNo);
        window.sessionStorage.setItem("accessToken", response.data.accessToken);
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
        alert("회원가입 되었습니다." + response.data);
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
        alert("사용 가능합니다." + response.data);
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
        alert("사용 가능합니다." + response.data);
      })
      .catch((error) => {
        alert("중복된 nickname입니다. : " + error);
      });
  },

  //팔로워 수 확인
  getFollowerCount: (profileMemberNo) => {
    return instance
      .get(`/profile/${profileMemberNo}/follow/fans/count`)
      .then((response) => {
        console.log("fanCount : " + response.data.fanCount);
        return response.data;
      })
      .catch((error) => {
        alert("FollowerCount 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //팔로우 여부 확인
  isFollowing: (data) => {
    const fanMemberNo = data.fanMemberNo ? data.fanMemberNo : -1;
    console.log(fanMemberNo);
    return instance
      .get(`profile/${data.profileMemberNo}/follow`, {
        headers: { accessToken: sessionStorage.getItem("accessToken") },
      })
      .then((response) => {
        console.log("isFollowing : " + response.data.isFollowing);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        alert("isFollowing 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //팔로우 언팔로우 클릭
  follow: (data) => {
    return data.isFollowing
      ? instance
          .post(`/profile/${data.profileMemberNo}/follow`, data.fanMemberNo)
          .then((response) => {
            alert("팔로우 하였습니다." + response.data);
            return true;
          })
          .catch((error) => {
            console.log(error);
            alert(" 팔로우실패 : " + error);
            return false;
          })
      : instance
          .delete(`/profile/${data.profileMemberNo}/follow`, data.fanMemberNo)
          .then((response) => {
            alert("팔로우 취소하였습니다" + response.data);
            return true;
          })
          .catch((error) => {
            console.log(error);
            alert(" 팔로우 취소실패 : " + error);
            return false;
          });
  },

  //회원정보 불러오기
  getMemberInfo: (memberNo) => {
    return instance
      .get(`/member/${memberNo}`)
      .then((response) => {
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        alert("Member 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //관심태그 불러오기
  getInterestTag: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/interest/tag`)
      .then((response) => {
        console.log(response.data);
        alert("category : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        alert("category 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //관심카테고리 불러오기
  getInterestCategory: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/interest/category`)
      .then((response) => {
        console.log(response.data);
        alert("category : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        alert("category 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //호감도 순위정보
  getLikeabilityData: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/likability/topstars/`)
      .then((response) => {
        alert("likability data : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        alert("likability data를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
};

export default apiClient;
