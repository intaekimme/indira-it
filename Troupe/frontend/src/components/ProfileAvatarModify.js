import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";
import AvatarSelectBar from "./AvatarSelectBar";

import { ThemeProvider } from "@mui/material/styles";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";

import stylesButton from "../css/button.module.css"

import Avatar from "./Avatar";
import Theme from "./Theme";

export default function ProfileAvatarModify() {
	//memberNo
	const { memberNo } = useParams();
  //이 member의 아바타
	const [avatar, setAvatar] = React.useState({
		avatarHairResponse: {},
		avatarEyeResponse: {},
		avatarNoseResponse: {},
		avatarMouthResponse: {},
		avatarClothesResponse: {},
		avatarShapeResponse: {},
	});
	

	//아바타 이미지 목록
	const [shapes, setShapes] = React.useState([]);
	const [eyes, setEyes] = React.useState([]);
	const [noses, setNoses] = React.useState([]);
	const [mouthes, setMouthes] = React.useState([]);
	const [hairs, setHairs] = React.useState([]);
	const [clothes, setClothes] = React.useState([]);

	//선택된 아바타 번호
	const [shapeValue, setShapeValue] = React.useState(1);
	const [eyeValue, setEyeValue] = React.useState(1);
	const [noseValue, setNoseValue] = React.useState(1);
	const [mouthValue, setMouthValue] = React.useState(1);
	const [hairValue, setHairValue] = React.useState(1);
	const [clothesValue, setClothesValue] = React.useState(1);

	//memberNo update 시 아바타 update
	React.useEffect(() => {
		if (!memberNo) {
			return;
		}
    if(!(parseInt(window.sessionStorage.getItem("loginMember"))===parseInt(memberNo))){
      window.location.href=`/profile/${memberNo}`;
      return;
    };
    apiClient.getMemberAvatar(memberNo).then((data) => {
			console.log(data);
      //setAvatar(data);

			setShapeValue(data.avatarShapeResponse.shapeNo);
			setEyeValue(data.avatarEyeResponse.eyeNo);
			setNoseValue(data.avatarNoseResponse.noseNo);
			setMouthValue(data.avatarMouthResponse.mouthNo);
			setHairValue(data.avatarHairResponse.hairNo);
			setClothesValue(data.avatarClothesResponse.clothesNo);

      const avatarData = {
        avatarShapeResponse: data.avatarShapeResponse,
        avatarEyeResponse: data.avatarEyeResponse,
        avatarNoseResponse: data.avatarNoseResponse,
        avatarMouthResponse: data.avatarMouthResponse,
        avatarHairResponse: data.avatarHairResponse,
        avatarClothesResponse: data.avatarClothesResponse,
      }
      if(avatarData.avatarShapeResponse &&
        avatarData.avatarEyeResponse &&
        avatarData.avatarNoseResponse &&
        avatarData.avatarMouthResponse &&
        avatarData.avatarHairResponse &&
        avatarData.avatarClothesResponse){
          console.log(avatarData);
          setAvatar(avatarData);
      }
    });
	}, [memberNo]);

	React.useEffect(() => {
		if((shapes && !(shapes.length===0)) &&
    (eyes && !(eyes.length===0)) &&
    (noses && !(noses.length===0)) &&
    (mouthes && !(mouthes.length===0)) &&
    (hairs && !(hairs.length===0)) &&
    (clothes && !(clothes.length===0))){
        const avatarData = {
          avatarShapeResponse: shapes[shapeValue - 1],
          avatarEyeResponse: eyes[eyeValue - 1],
          avatarNoseResponse: noses[noseValue - 1],
          avatarMouthResponse: mouthes[mouthValue - 1],
          avatarHairResponse: hairs[hairValue - 1],
          avatarClothesResponse: clothes[clothesValue - 1],
        }
        console.log(avatarData);
        setAvatar(avatarData);
    }
	}, [shapeValue, eyeValue, noseValue, mouthValue, hairValue, clothesValue]);

	React.useEffect(() => {
		apiClient.getAvatarListAll().then((data) => {
			console.log(data.hairList);
			setShapes(data.shapeList);
			setEyes(data.eyeList);
			setNoses(data.noseList);
			setMouthes(data.mouthList);
			setHairs(data.hairList);
			setClothes(data.clothesList);
		});
	}, []);

	//화면 width에 따른 화면분할여부
	const [gridItemxs, setGridItemxs] = React.useState(window.innerWidth < 1000 ? 12 : 6);
	//1000보다 큰경우 2화면분할
  const handleGrid = () => {
    const size = window.innerWidth;
    if (size < 1000) {
      setGridItemxs(12);
    } else {
      setGridItemxs(6);
    }
  };
  //화면분할 update
  React.useEffect(() => {
		window.addEventListener("resize", handleGrid);
		console.log(memberNo);
	}, [window.innerWidth]);
	
  //수정완료
  const finish = ()=>{
    const avatarData = {
      shapeNo: parseInt(shapeValue),
      eyeNo: parseInt(eyeValue),
      noseNo: parseInt(noseValue),
      mouthNo: parseInt(mouthValue),
      hairNo: parseInt(hairValue),
      clothesNo: parseInt(clothesValue),
    }
    apiClient.modifyAvatar(avatarData).then((data)=>{
      if(data){
        window.location.href=`/profile/${memberNo}`;
      }
    });
  };

  const reset = (memberNo) => {
    if (window.confirm("캐릭터 수정을 취소하고 돌아가시겠습니까?")) {
      window.location.href = "/profile/" + memberNo;
    } 
  }
	return (
    <ThemeProvider theme={Theme}>
      <Container
        component="main"
        maxWidth="1920px"
        style={{ justifyContent: "center", alignItems: "center", fontFamily: "SBAggroB", width: "100%", height: "100%"}}
      >
        <Grid container spacing={2} style={{ textAlign: "center" }}>
          <Grid container item xs={gridItemxs} justifyContent="center" alignItems="center"
            style={{
              backgroundColor: "#fda085",
              border: "5px solid white",
              paddingRight: 20,
              paddingBottom: 50,
              position: "relative",
              boxShadow: "3px 3px 5px #000",
              borderRadius: "1%",
              paddingBottom: "300px",
              border:"5px solid white"
            }}
          >
            <Grid container direction="row" justifyContent="center" alignItems="center"
             style={{ width:"100%", height: "100%" }}>
            {/* style={{ width: "100%", height: "100%" }}> */}
              <Grid item xs={12} style={{color: "white", textShadow: "1px 1px 1px #000", fontSize: "40px"}}>내 모습을 꾸며보세요!</Grid>
              <Grid item xs={12} justifyContent="center" alignItems="center"
                style={{ minHeight: "500px", width: "100%", height: "100%"}}>
                <Avatar
                  style={{
                    padding: "10px",
                    overflow: "auto",
                    maxWidth: "100%",
                    height: "100%",
                    // height: "calc(100vh + 100px)",
                    // width: "100%",
                    width: "calc(100vh + 100px)",
                    paddingBottom: "75%",
                  }}
                  avatarResponse={avatar}
                  imgWidth={100}
                  imgHeight={100}
                  divWidth={100}
                  divHeight={100}
                  top={"0%"}
                  left={"0%"}
                  sizeString="%"
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                    className={stylesButton.btn}
                    variant="contained"
                    color="neutral"
                    style={{border: "3px solid white", marginRight:"5px", color:"black"}}
                    onClick={()=>reset(memberNo)}
                    maxWidth
                  >
                    돌아가기
                </Button>
                <Button
                  className={stylesButton.btn2}
                  variant="contained"
                  color="neutral"
                  style={{border: "3px solid white", marginLeft:"5px", color:"black"}}
                  onClick={finish}
                  maxWidth
                >
                  저장하기
                </Button>
              </Grid>
            </Grid>
          </Grid>
          <Grid container item xs={gridItemxs} justifyContent="center" alignItems="center"
            style={{ padding: 0, }}
          >
          <Grid item xs={12}>
            <AvatarSelectBar
              string={"hair"}
              imgDatas={hairs}
              currentValue={hairValue}
              setValue={setHairValue}
            />
          </Grid>
          <Grid item xs={12}>
            <AvatarSelectBar
              string={"eye"}
              imgDatas={eyes}
              currentValue={eyeValue}
              setValue={setEyeValue}
            />
          </Grid>
          <Grid item xs={12}>
            <AvatarSelectBar
              string={"nose"}
              imgDatas={noses}
              currentValue={noseValue}
              setValue={setNoseValue}
            />
          </Grid>
          <Grid item xs={12}>
            <AvatarSelectBar
              string={"mouth"}
              imgDatas={mouthes}
              currentValue={mouthValue}
              setValue={setMouthValue}
            />
          </Grid>
          <Grid item xs={12}>
            <AvatarSelectBar
              string={"clothes"}
              imgDatas={clothes}
              currentValue={clothesValue}
              setValue={setClothesValue}
            />
          </Grid>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}