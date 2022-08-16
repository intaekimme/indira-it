import { type } from "@testing-library/user-event/dist/type";
import instance from "axios";

// const instance = axios.create({
//   baseURL: process.env.REACT_APP_MAGAZINE_API_BASE_URL,
// });

const apiClient = {
  //accessToken Refresh
  refreshAccessToken: () => {
    console.log(window.sessionStorage.getItem("refreshToken"));
    console.log("refreshAccessToken");
    const memberNo = window.sessionStorage.getItem("loginMember");
    const refreshToken = window.sessionStorage.getItem("refreshToken");
    return instance
      .post(`/refresh`, {
        memberNo: parseInt(memberNo),
        refreshToken: refreshToken,
      })
      .then((response) => {
        console.log(response.data);
        alert("토큰 새로고침 성공 : " + response.data);
        if (!response.data || response.data === "") {
          return false;
        } else {
          sessionStorage.setItem("accessToken", response.data.accessToken);
          return true;
        }
      })
      .catch((error) => {
        console.log(error);
        alert("토큰 새로고침 실패하였습니다");
        return false;
      });
  },
  //로그인
  login: (loginInfo) => {
    return instance
      .post("/member/login", loginInfo)
      .then((response) => {
        window.sessionStorage.setItem("loginCheck", true);
        window.sessionStorage.setItem("loginMember", response.data.memberNo);
        window.sessionStorage.setItem("accessToken", response.data.accessToken);
        window.sessionStorage.setItem(
          "refreshToken",
          response.data.refreshToken,
        );
        console.log("로그인 되었습니다.");
        // alert("로그인 되었습니다.");
        const href = sessionStorage.getItem("currentHref");
        sessionStorage.removeItem("currentHref");
        window.location.href = href;
        return true;
      })
      .catch((error) => {
        console.log(error);
        console.log("로그인 실패 error");
        // alert("로그인 실패 : " + error);
        // alert(error.response.status);
        if (error.response.status === 401) {
          window.location.href = "/email";
        }
        return false;
      });
  },
  //회원가입
  signup: (data) => {
    console.log("회원가입 진행중입니다 잠시만 기다려주세요");
    // alert("회원가입 진행중입니다 잠시만 기다려주세요");
    return instance
      .post("/member/signup", data)
      .then((response) => {
        console.log(response.data);
        console.log("회원가입 되었습니다.");
        // alert("회원가입 되었습니다." + response.data);
        window.location.href = "/email";
        return true;
      })
      .catch((error) => {
        console.log(error);
        console.log("회원가입 실패");
        // alert("회원가입 실패 : " + error);
        return false;
      });
  },
  //이메일 중복체크
  existEmail: (data) => {
    return instance
      .post("/member/signup/email", data)
      .then((response) => {
        console.log(response.data);
        console.log("사용 가능한 이메일");
        // alert("사용 가능합니다." + response.data);
        return false;
      })
      .catch((error) => {
        const status = error.response.status;
        if (status === 500) {
          console.log(error);
          // alert("server Error : " + error);
          return error;
        } else if (status === 409) {
          console.log(error);
          console.log("중복된 e-mail 입니다");
          // alert("중복된 e-mail 입니다 : " + error);
          return true;
        }
      });
  },
  //닉네임 중복체크
  existNickname: (data) => {
    return instance
      .post("/member/signup/nickname", data)
      .then((response) => {
        console.log(response.data);
        console.log("사용 가능한 닉네임");
        // alert("사용 가능합니다." + response.data);
        return false;
      })
      .catch((error) => {
        const status = error.response.status;
        if (status === 500) {
          console.log(error);
          // alert("server Error : " + error);
          return error;
        } else if (status === 409) {
          console.log(error);
          console.log("중복된 nickname 입니다");
          // alert("중복된 nickname 입니다 : " + error);
          return true;
        }
      });
  },
  //이메일 인증
  confirmEmail: (token) => {
    return instance
      .get(`/confirm-email/${token}`)
      .then((response) => {
        console.log(response.data);
        console.log("이메일 인증 되었습니다.");
        // alert("이메일 인증 되었습니다." + response.data);
        window.location.href = "/login";
        return true;
      })
      .catch((error) => {
        console.log(error);
        console.log("이메일 인증 실패");
        // alert("이메일 인증 실패 : " + error);
        window.location.href = "/email";
        return false;
      });
  },
  //request pw
  requestPassword: (email) => {
    console.log(
      "비밀번호 초기화를 위해 이메일을 전송중입니다 잠시만 기다려주세요",
    );
    // alert("비밀번호 초기화를 위해 이메일을 전송중입니다 잠시만 기다려주세요");
    return instance
      .post("/member/request-password", { email: email })
      .then((response) => {
        console.log(response);
        console.log("비밀번호 초기화를 위해 이메일을 전송하였습니다.");
        // alert("비밀번호 초기화를 위해 이메일을 전송하였습니다." + response.data);
        return true;
      })
      .catch((error) => {
        console.log(error);
        console.log("이메일 전송 실패");
        // alert("이메일 전송 실패 : " + error);
        return false;
      });
  },
  //reset pw
  resetPassword: (token, password) => {
    return instance
      .post(`/member/reset-password/${token}`, { password: password })
      .then((response) => {
        console.log(response);
        console.log("비밀번호가 초기화 되었습니다.");
        // alert("비밀번호가 초기화되었습니다." + response.data);
        window.location.href = "/login";
        return true;
      })
      .catch((error) => {
        console.log(error);
        console.log("비밀번호 초기화에 실패하였습니다. 다시 시도해주세요");
        // alert("비밀번호 초기화에 실패하였습니다. 다시 시도해주세요 : " + error);
        return false;
      });
  },
  //현재 비밀번호 일치 확인
  pwCurrentCheck: (data) => {
    return instance
      .post(`/member/pw`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("pwCurrentCheck 정보");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("pwCurrentCheck 정보를 불러오는데 실패하였습니다");
        // alert("pwCurrentCheck 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //프로필수정
  modifyProfile: (data) => {
    instance
      .patch("/member/myinfo", data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("프로필수정 되었습니다.");
        // alert("프로필수정 되었습니다." + response.data);
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("프로필수정 실패");
        // alert("프로필수정 실패 : " + error);
      });
  },
  //유저아바타 수정
  modifyAvatar: (avatarData) => {
    return instance
      .patch(`/member/myavatar`, avatarData, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("아바타 수정 성공");
        // alert("아바타 수정 성공" + response);
        return true;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("아바타 수정 실패");
        // alert("아바타 수정 실패" + error);
        return false;
      });
  },

  //방명록 불러오기
  getGuestBookList: (memberNo) => {
    return instance
      .get(`/guestbook/${parseInt(memberNo)}/list`)
      .then((response) => {
        console.log(response.data);
        console.log("방명록 목록조회 성공");
        // alert("방명록 목록조회 성공 : " + response.data);
        if (response.data === "" || response.data.length == 0) {
          return [];
        } else {
          return response.data;
        }
      })
      .catch((error) => {
        console.log(error);
        console.log("방명록 목록을 불러오는데 실패하였습니다");
        // alert("방명록 목록을 불러오는데 실패하였습니다 : " + error);
        return [];
      });
  },
  //로그인한 유저가 쓴 방명록 조회
  getMyGuestBook: (memberNo) => {
    return instance
      .get(`/guestbook/${parseInt(memberNo)}`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("내가 쓴 방명록 조회 성공");
        // alert("내가 쓴 방명록 조회 성공 : " + response);
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("내가 쓴 방명록 조회 실패");
        // alert("내가 쓴 방명록 조회 실패 : " + error);
        return {};
      });
  },
  //방명록 등록
  registGuestBook: (memberNo, content) => {
    console.log(content);
    return instance
      .post(`/guestbook/${parseInt(memberNo)}`, content, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("방명록 등록 성공");
        // alert("방명록 등록 성공 : " + response);
        return true;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("방명록 등록 실패");
        // alert("방명록 등록 실패 : " + error);
        return false;
      });
  },

  //팔로워 수 확인
  getFollowerCount: (profileMemberNo) => {
    return instance
      .get(`/profile/${profileMemberNo}/follow/fans/count`)
      .then((response) => {
        console.log(response.data.fanCount);
        console.log("FollowerCount 정보");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("FollowerCount 정보를 불러오는데 실패하였습니다");
        // alert("FollowerCount 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //팔로우 여부 확인
  isFollowing: (data) => {
    const profileMemberNo = data.profileMemberNo;
    console.log(data);
    console.log(profileMemberNo);
    return instance
      .get(`/profile/${profileMemberNo}/follow`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data.isFollowing);
        console.log("isFollowing 정보");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("isFollowing 정보를 불러오는데 실패하였습니다");
        // alert("isFollowing 정보를 불러오는데 실패하였습니다 : " + error);
        return { isFollowing: false };
      });
  },
  //팔로우 언팔로우 클릭
  follow: (data) => {
    console.log(data);
    console.log(sessionStorage.getItem("accessToken"));
    return data.currentFollow
      ? instance
          .delete(`/profile/${parseInt(data.profileMemberNo)}/follow`, {
            headers: {
              accessToken: sessionStorage.getItem("accessToken"),
            },
          })
          .then((response) => {
            console.log(response.data);
            console.log("팔로우 취소하였습니다");
            // alert("팔로우 취소하였습니다" + response.data);
            return true;
          })
          .catch((error) => {
            if (error.response.status === 500) {
              const refresh = apiClient.refreshAccessToken();
              if (refresh) {
                alert("잠시 후 다시 시도해 주세요");
              }
            }
            console.log(error);
            console.log("팔로우 취소실패");
            // alert(" 팔로우 취소실패 : " + error);
            return false;
          })
      : instance
          .post(
            `/profile/${parseInt(data.profileMemberNo)}/follow`,
            {},
            {
              headers: {
                accessToken: sessionStorage.getItem("accessToken"),
              },
            },
          )
          .then((response) => {
            console.log(response.data);
            console.log("팔로우 하였습니다");
            // alert("팔로우 하였습니다." + response.data);
            return true;
          })
          .catch((error) => {
            if (error.response.status === 500) {
              const refresh = apiClient.refreshAccessToken();
              if (refresh) {
                alert("잠시 후 다시 시도해 주세요");
              }
            }
            console.log(error);
            console.log("팔로우실패");
            // alert(" 팔로우실패 : " + error);
            return false;
          });
  },

  //내정보 불러오기
  getMyinfo: () => {
    return instance
      .get(`/member/myinfo`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("Member 정보");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("Member 정보를 불러오는데 실패하였습니다");
        // alert("Member 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //유저아바타 불러오기
  getMemberAvatar: (memberNo) => {
    return instance
      .get(`/member/${parseInt(memberNo)}/avatar`)
      .then((response) => {
        console.log(response.data);
        console.log("Member Avatar 정보");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("Member Avatar 정보를 불러오는데 실패하였습니다");
        // alert("Member Avatar 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //회원정보 불러오기
  getMemberInfo: (memberNo) => {
    return instance
      .get(`/member/${parseInt(memberNo)}`)
      .then((response) => {
        console.log(response.data);
        console.log("Member 정보");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("Member 정보를 불러오는데 실패하였습니다");
        // alert("Member 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //관심태그 불러오기
  getInterestTag: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/interest/tag`)
      .then((response) => {
        console.log(response.data);
        console.log("관심태그 불러오기 성공");
        // alert("category : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("관심태그 정보를 불러오는데 실패하였습니다");
        // alert("category 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //관심카테고리 불러오기
  getInterestCategory: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/interest/category`)
      .then((response) => {
        console.log(response.data);
        console.log("관심 category 불러오기 성공");
        // alert("category : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("관심 category 정보를 불러오는데 실패하였습니다");
        // alert("category 정보를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //호감도 순위정보
  getLikeabilityData: (memberNo) => {
    return instance
      .get(`/profile/${memberNo}/likability/topstars/`)
      .then((response) => {
        console.log(response.data);
        console.log("likability data");
        // alert("likability data : " + response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("likability data를 불러오는데 실패하였습니다");
        // alert("likability data를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //호감도 공연자 Top3
  getPerformerTop3: (data) => {
    return instance
      .get(`/profile/${parseInt(data.profileMemberNo)}/likability/topstars`)
      .then((response) => {
        console.log(response.data);
        console.log("호감도 공연자 Top3 불러오기 성공");
        // alert("호감도 공연자 Top3 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("호감도 공연자 Top3 불러오기 실패");
        // alert("호감도 공연자 Top3 불러오기 실패" + error);
        return null;
      });
  },
  //호감도 공연자 Top100
  getPerformerTop100: (data) => {
    return instance
      .get(`/profile/${parseInt(data.profileMemberNo)}/likability/topfans`)
      .then((response) => {
        console.log(response.data);
        console.log("호감도 공연자 Top100 불러오기 성공");
        // alert("호감도 공연자 Top100 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("호감도 공연자 Top100 불러오기 실패");
        // alert("호감도 공연자 Top100 불러오기 실패" + error);
        return null;
      });
  },
  //공연자에 대한 나의 호감도 data
  getMyLikeabilityData: (data) => {
    return instance
      .get(`/profile/${parseInt(data.profileMemberNo)}/likability`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("공연자에 대한 나의 호감도 data 불러오기 성공");
        // alert("공연자에 대한 나의 호감도 data 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연자에 대한 나의 호감도 data 불러오기 실패");
        // alert("공연자에 대한 나의 호감도 data 불러오기 실패" + error);
        return null;
      });
  },
  //아바타 번호, 이미지목록 불러오기
  getAvatarListAll: () => {
    return instance
      .get(`/avatar/all`)
      .then((response) => {
        console.log(response.data);
        console.log("Avatar 이미지 리스트");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("Avatar 이미지 리스트를 불러오는데 실패하였습니다");
        // alert("Avatar 이미지 리스트를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },
  //[string]아바타 번호, 이미지목록 불러오기
  getAvatarList: (string) => {
    return instance
      .get(`/avatar/${string}`)
      .then((response) => {
        console.log(response.data);
        console.log("Avatar 이미지 리스트");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("Avatar 이미지 리스트를 불러오는데 실패하였습니다");
        // alert("Avatar 이미지 리스트를 불러오는데 실패하였습니다 : " + error);
        return null;
      });
  },

  //피드 목록 테스트용(후에 삭제)
  getFeedTest: () => {
    // const change = data.change;
    // const pageNumber = data.pageNumber;
    return instance
      .get(`/feed/list/all?pageNumber=0`)
      .then((response) => {
        console.log(response.data);
        console.log("피드 불러오기 성공");
        // alert("피드 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("피드 불러오기 실패");
        // alert("피드 불러오기 실패" + error);
        return null;
      });
  },
  //피드 검색 테스트용(후에 삭제)
  getFeedSearchTest: (data) => {
    return instance
      .get(`/feed/search?pageNumber=0`, {
        params: {
          tags: data,
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 검색 불러오기 성공");
        // alert("피드 검색 불러오기 성공");
        window.location.href = "/feed/list/search";
        console.log(response.data);
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("피드 검색 불러오기 실패");
        // alert("피드 검색 불러오기 실패" + error);
        return error;
      });
  },
  //전체 피드 목록 불러오기
  getAllFeedList: async ({ pageParam = 0 }) => {
    const url = `/feed/list/all?pageNumber=${pageParam}`;
    return await instance
      .get(url)
      .then((response) => {
        console.log(response.data);
        console.log(url);
        console.log("전체 피드 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("전체 피드 불러오기 실패");
        // alert("전체 피드 불러오기 실패" + error);
      });
  },
  //저장된 피드 목록 불러오기
  getSavedFeedList: async ({ pageParam = 0 }) => {
    return await instance
      .get(`/feed/list/save?pageNumber=${pageParam}`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("저장 피드 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("저장 피드 불러오기 실패");
        // alert("저장 피드 불러오기 실패" + error);
        return null;
      });
  },
  //팔로우한 memeber의 피드 목록 불러오기
  getFollowFeedList: async ({ pageParam = 0 }) => {
    return await instance
      .get(`/feed/list/follow?pageNumber=${pageParam}`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("팔로우 피드 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("팔로우 피드 불러오기 실패");
        // alert("팔로우 피드 불러오기 실패" + error);
        return null;
      });
  },
  //태그검색 피드 목록 불러오기
  feedTagSearch: (tags, pageNumber) => {
    if(tags.length === 0) {
      var url = `/feed/search?pageNumber=${pageNumber}`
    }
    else {
      for (let i = 0; i < tags.length; i++) {
        var url = `/feed/search?pageNumber=${pageNumber}` + `&tags=${tags[i]}`;
      }
    }
    return instance
      .get(url)
      .then((response) => {
        console.log(response.data);
        console.log(url);
        console.log("Feed Search 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("Feed Search 실패");
        return error;
        // alert("Feed Search Failed" + error);
      });
  },
  //피드 좋아요수
  getFeedTotalLike: (feedNo) => {
    return instance
      .get(`/feed/${feedNo}/like`)
      .then((response) => {
        console.log(response.data);
        console.log("피드 좋아요 수 불러오기 성공");
        // alert("피드 좋아요 수 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("피드 좋아요 수 불러오기 실패");
        // alert("피드 좋아요 수 불러오기 실패" + error);
        return error;
      });
  },
  //피드 좋아요 여부 조회
  feedLikeCheck: (feedNo) => {
    return instance
      .get(`/feed/${feedNo}/like/now`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 좋아요 여부 조회 성공");
        // alert("피드 좋아요 여부 get 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 좋아요 여부 조회 실패");
        // alert("피드 좋아요 여부 get 실패" + error);
        return error;
      });
  },
  //피드 좋아요
  feedLike: (feedNo) => {
    return instance
      .patch(`/feed/${feedNo}/like`, feedNo, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 좋아요 성공");
        // alert("피드 좋아요 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.loog("피드 좋아요 실패");
        // alert("피드 좋아요 실패" + error);
        return error;
      });
  },
  //피드 저장 여부 조회
  feedSaveCheck: (feedNo) => {
    return instance
      .get(`/feed/${feedNo}/save/now`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 저장 여부 조회 성공");
        // alert("피드 저장 여부 get 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 저장 여부 조회 실패");
        // alert("피드 저장 여부 get 실패" + error);
        return error;
      });
  },
  //피드 저장
  feedSave: (feedNo) => {
    return instance
      .patch(`/feed/${feedNo}/save`, feedNo, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 저장 성공");
        // alert("피드 저장 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 저장 조회 실패");
        // alert("피드 저장 실패" + error);
        return error;
      });
  },
  //피드 상세
  getFeedDetail: (feedNo) => {
    return instance
      .get(`/feed/${feedNo}`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("피드 상세 불러오기 성공");
        // alert("피드 상세 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 상세 불러오기 실패");
        // alert("피드 상세 불러오기 실패" + error);
        return error;
      });
  },
  //피드 등록
  feedNew: (data) => {
    instance
      .post("/feed", data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        console.log("피드 등록 성공");
        // alert("피드 등록 성공");
        window.location.href = "/feed/list/all/0";
        return response;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 등록 실패");
        // alert("피드 등록 실패" + error);
        return error;
      });
  },
  //피드 수정
  feedModify: (data, feedNo) => {
    instance
      .post(`/feed/${feedNo}/modify`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        console.log("피드 수정 성공");
        // alert("피드 수정 성공");
        window.location.href = "/feed/list/all/0";
        return response;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("피드 수정 실패" + error);
        // alert("피드 수정 실패" + error);
        return error;
      });
  },
  //피드 삭제
  feedRemove: (feedNo) => {
    if (window.confirm("삭제하시겠습니까?")) {
      instance
        .patch(`/feed/${feedNo}/del`)
        .then((response) => {
          console.log(response);
          console.log("피드가 삭제되었습니다");
          // alert("피드가 삭제되었습니다" + response);
          window.location.href = "/feed/list/all/0";
        })
        .catch((error) => {
          console.log(error);
          console.log(feedNo + " 피드 삭제 실패");
          // alert("피드 삭제 실패 :" + error + feedNo);
          return error;
        });
    } else {
      alert("취소합니다.");
    }
  },
  // 피드 댓글 목록 불러오기(완성)
  getFeedCommentList: (feedNo) => {
    return instance
      .get(`/feed/${feedNo}/comment/list`)
      .then((response) => {
        console.log(response.data);
        console.log("피드 댓글 불러오기 성공");
        // alert("피드 댓글 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("피드 댓글 불러오기 실패");
        // alert("피드 댓글 불러오기 실패" + error);
        return null;
      });
  },
  // 피드 대댓글 목록 불러오기
  getFeedChildReviewList: (feedNo, commentNo) => {
    return instance
      .get(`/feed/${feedNo}/comment/${commentNo}`)
      .then((response) => {
        console.log(response.data);
        console.log("대댓글 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("대댓글 불러오기 실패");
        return null;
      });
  },
  // 피드댓글 등록
  feedCommentNew: (feedNo, data, refreshFunction) => {
    instance
      .post(`/feed/${feedNo}/comment`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("댓글 등록 성공");
        // alert("댓글 등록 성공");
        const json = {
          memberNo: response.data.memberNo,
          reviewNo: response.data.commentNo,
          profileImageUrl: response.data.profileImageUrl,
          comment: response.data.content,
          nickname: response.data.nickname,
        };
        refreshFunction(json);
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 등록 실패");
        // alert("댓글 등록 실패 : " + error);
        return error;
      });
  },
  // 피드댓글 수정
  feedCommentModify: (feedNo, commentNo, data) => {
    return instance
      .patch(`/feed/${feedNo}/comment/${commentNo}`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("댓글 수정 성공");
        // alert("댓글 수정 성공");
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 수정 실패");
        // alert("댓글 수정 실패 : " + error);
        return error;
      });
  },
  // 피드댓글 삭제
  feedCommentDelete: (feedNo, commentNo) => {
    return instance
      .patch(`/feed/${feedNo}/comment/${commentNo}/del`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response);
        console.log("댓글 삭제 성공");
        // alert("댓글 삭제 성공");
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 삭제 실패");
        // alert("댓글 삭제 실패 : " + error);
        return error;
      });
  },
  //  피드 대댓글 작성
  feedChildCommentNew: (
    feedNo,
    parentCommentNo,
    data,
    refreshChildFunction,
  ) => {
    instance
      .post(`/feed/${feedNo}/comment`, data, {
        params: {
          parentCommentNo: parentCommentNo,
        },
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("대댓글 등록 성공");
        // alert("대댓글 등록 성공");
        const json = {
          memberNo: response.data.memberNo,
          reviewNo: response.data.commentNo,
          profileImageUrl: response.data.profileImageUrl,
          comment: response.data.content,
          nickname: response.data.nickname,
        };
        refreshChildFunction(json);
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("대댓글 등록 실패");
        // alert("대댓글 등록 실패 : " + error);
      });
  },

  //공연 목록 불러오기
  getPerfList: async ({ pageParam = 0 }) => {
    return await instance
      .get(`/perf/list?pageNumber=${pageParam}`)
      .then((response) => {
        console.log(response.data);
        console.log("공연 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("공연 불러오기 실패");
        // alert("공연 불러오기 실패" + error);
      });
  },
  //공연 검색
  perfSearch: async ({ condition = "title", keyword = "" }) => {
    let url = `/perf/list/search?condition=${condition}&keyword=${keyword}`;
    return await instance
      .get(url)
      .then((response) => {
        console.log(response.data);
        console.log("검색 피드 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("검색 피드 불러오기 실패");
        // alert("검색 피드 불러오기 실패" + error);
      });
  },
  //공연 저장 여부 조회
  perfSaveCheck: (pfNo) => {
    return instance
      .get(`/perf/${pfNo}/save/now`, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("공연 저장 여부 조회 성공");
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연 저장 여부 조회 실패");
        return error;
      });
  },
  //공연 저장
  perfSave: (performanceNo) => {
    return instance
      .post(`/perf/${performanceNo}/save`, performanceNo, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("공연 저장 성공");
        // alert("북마크저장")
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연 저장 실패");
        // alert("공연 북마크 실패" + error);
        return error;
      });
  },
  //공연 저장 취소
  perfSaveDelete: (performanceNo) => {
    return instance
      .patch(`/perf/${performanceNo}/save/del`, performanceNo, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("공연 저장 취소 성공");
        // alert("북마크저장 취소")
        return response.data;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연 저장 취소 실패");
        // alert("공연 북마크 취소 실패" + error);
        return error;
      });
  },
  // 공연 상세 정보 불러오기
  getPerfDetail: (performanceNo) => {
    return instance
      .get(`/perf/${performanceNo}`)
      .then((response) => {
        console.log(response);
        console.log("공연상세 불러 오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("공연상세 불러 오기 실패 ");
      });
  },
  //공연 등록
  perfNew: (data) => {
    // console.log(data);
    instance
      .post("/perf", data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        console.log("공연등록 되었습니다.");
        // alert("공연등록 되었습니다.");
        window.location.href = "/perf/list/0";
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연등록 실패");
        // alert("공연등록 실패 : " + error);
      });
  },
  //공연 삭제하기
  perfRemove: (performanceNo) => {
    if (window.confirm("삭제하시겠습니까?")) {
      instance
        .patch(`/perf/${performanceNo}/del`, null, {
          headers: {
            accessToken: sessionStorage.getItem("accessToken"),
          },
        })
        .then((response) => {
          console.log(response);
          console.log("공연이 삭제되었습니다");
          window.location.href = "/perf/list/0";
          // alert("공연이 삭제되었습니다" + response);
        })
        .catch((error) => {
          console.log(error);
          console.log("공연삭제 실패");
          // alert(data + "공연삭제 실패 :" + error);
        });
    } else {
      alert("취소합니다.");
    }
  },
  //  공연 후기 목록 불러오기(완성)
  getPerfReviewList: (performanceNo) => {
    return instance
      .get(`/perf/${performanceNo}/review/list`)
      .then((response) => {
        console.log(response.data);
        console.log("불러오기 성공");
        // alert("불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("공연 후기 불러오기 실패");
        // alert("공연 후기 불러오기 실패" + error);
        return null;
      });
  },
  // 공연 후기 대댓글 목록 불러오기
  getPerfChildReviewList: (performanceNo, reviewNo) => {
    return instance
      .get(`/perf/${performanceNo}/review/${reviewNo}/list`)
      .then((response) => {
        console.log(response);
        console.log("대댓글 불러오기 성공");
        return response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log("대댓글 불러오기 실패");
        return null;
      });
  },
  //  공연 후기 등록(완성)
  perfReviewNew: (pfNo, data, refreshFunction) => {
    instance
      .post(`/perf/${pfNo}/review`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("댓글 등록 성공");
        // alert("댓글 등록 성공");
        refreshFunction(response.data);
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 등록 실패");
        // alert("댓글 등록 실패 : " + error);
      });
  },
  //  공연 후기 대댓글 작성
  perfChildReviewNew: (
    performanceNo,
    parentCommentNo,
    data,
    refreshChildFunction,
  ) => {
    instance
      .post(`/perf/${parseInt(performanceNo)}/review`, data, {
        params: {
          parentCommentNo: parentCommentNo,
        },
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("대댓글 등록 성공");
        // alert("대댓글 등록 성공");
        refreshChildFunction(response.data);
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("대댓글 등록 실패");
        // alert("대댓글 등록 실패 : " + error);
      });
  },
  //  공연 후기 수정
  perfReviewModify: (performanceNo, reviewNo, data) => {
    return instance
      .patch(`/perf/${performanceNo}/review/${reviewNo}/modify`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("댓글 수정 성공");
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 수정 실패");
        // alert("댓글 수정 실패 : " + error);
        return error;
      });
  },
  //  공연 후기 삭제
  perfReviewDelete: (performanceNo, reviewNo) => {
    return instance
      .patch(`/perf/${performanceNo}/review/${reviewNo}/del`, null, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("댓글 삭제 성공");
        // alert("댓글 삭제 성공");
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("댓글 삭제 실패");
        // alert("댓글 삭제 실패 : " + error);
        return error;
      });
  },

  // 공연 수정
  perfModify: (performanceNo, data) => {
    console.log(data);
    instance
      .post(`/perf/${performanceNo}/modify`, data, {
        headers: {
          accessToken: sessionStorage.getItem("accessToken"),
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        window.location.href = `/perf/detail/${performanceNo}`
        return response;
      })
      .catch((error) => {
        if (error.response.status === 500) {
          const refresh = apiClient.refreshAccessToken();
          if (refresh) {
            alert("잠시 후 다시 시도해 주세요");
          }
        }
        console.log(error);
        console.log("공연 수정 실패" + error);
        // alert("피드 수정 실패" + error);
        return error;
      });
  }
};
export default apiClient;
