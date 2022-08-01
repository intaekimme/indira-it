import Button from "@mui/material/Button";
export default function LoginPopup() {
  const login = () => {
    window.location.href = "/login";
  };

  return (
    <div style={{ width: "320px", height: "160px", textAlign: "center" }}>
      로그인이 필요합니다.
      <br />
      로그인 하시겠습니까?
      <Button style={{ width: "50%", color: "black" }}>취소</Button>
      <Button onClick={login} style={{ width: "50%" }}>
        로그인
      </Button>
    </div>
  );
}
