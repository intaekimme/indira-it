import instance from 'axios';

// const instance = axios.create({
//   baseURL: process.env.REACT_APP_MAGAZINE_API_BASE_URL,
// });

const apiClient = {
  login: (loginInfo) => {
    instance.post('/member/login', loginInfo).then((data)=>{
      window.sessionStorage.setItem('loginCheck', true);
      window.sessionStorage.setItem('loginUser', data);
      alert('로그인 되었습니다.');
    }).catch((error)=>{
      alert('로그인 실패 : ' + error);
    });
  },

  signup: (data) => {
    instance.post('/member/signup', data).then((data)=>{
      alert('회원가입 되었습니다.' + data);
    }).catch((error)=>{
      alert('회원가입 실패 : ' + error);
    });
  },

  existEmail: (data) => {
    instance.get('/member/signup/email', data).then((data)=>{
      alert('사용 가능합니다.' + data);
    }).catch((error)=>{
      alert('중복된 email입니다. : ' + error);
    });
  },

  existNickname: (data) => {
    instance.get('/member/signup/nickname', data).then((data)=>{
      alert('사용 가능합니다.' + data);
    }).catch((error)=>{
      alert('중복된 nickname입니다. : ' + error);
    });
  },
}

export default apiClient;