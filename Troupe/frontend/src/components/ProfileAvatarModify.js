import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";
import AvatarSelectBar from "./AvatarSelectBar";

import { createTheme, ThemeProvider } from "@mui/material/styles";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

import stylesButton from "../css/button.module.css"

import Avatar from "./Avatar";

const theme = createTheme();

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
    apiClient.getMemberAvatar(memberNo).then((data) => {
			console.log(data);
      setAvatar(data);

			setShapeValue(data.avatarShapeResponse.shapeNo);
			setEyeValue(data.avatarEyeResponse.eyeNo);
			setNoseValue(data.avatarNoseResponse.noseNo);
			setMouthValue(data.avatarMouthResponse.mouthNo);
			setHairValue(data.avatarHairResponse.hairNo);
			setClothesValue(data.avatarClothesResponse.clothesNo);
    });
	}, [memberNo]);

	React.useEffect(() => {
		const avatarData = {
			avatarShapeResponse: (shapes && !(shapes.length===0)) ? shapes[shapeValue - 1] : "",
			avatarEyeResponse: (eyes && !(eyes.length===0)) ? eyes[eyeValue - 1] : "",
			avatarNoseResponse: (noses && !(noses.length===0)) ? noses[noseValue - 1] : "" ,
			avatarMouthResponse: (mouthes && !(mouthes.length===0)) ? mouthes[mouthValue - 1] : "" ,
			avatarHairResponse: (hairs && !(hairs.length===0)) ? hairs[hairValue - 1] : "" ,
			avatarClothesResponse: (clothes && !(clothes.length===0)) ? clothes[clothesValue - 1] : "" ,
		}
		setAvatar(avatarData);
		console.log(avatarData);
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
	
	return (
    <ThemeProvider theme={theme}>
      <Container
        component="main"
        maxWidth="1920px"
        style={{ justifyContent: "center", alignItems: "center" }}
      >
        <Grid container spacing={2} style={{ textAlign: "center" }}>
					<Grid
            item
            xs={gridItemxs}
            container
            justifyContent="center"
            alignItems="flex-end"
            style={{
              backgroundColor: "gray",
              paddingRight: 20,
              paddingBottom: 50,
              position: "relative",
            }}
					>
						<Avatar
								avatarResponse={ avatar }
								imgWidth={75 * 4}
								imgHeight={100 * 4}
								divWidth={75 * 4}
								divHeight={100 * 4}
						/>
						<Grid item xs={12}><Button className={stylesButton.btn} style={{color:"black", backgroundColor:"#44FFC8"}}>Finish</Button></Grid>
          </Grid>
          <Grid
            item
            xs={gridItemxs}
            container
            justifyContent="center"
            alignItems="center"
            style={{ backgroundColor: "blue", padding: 0 }}
          >
            <FormControl>
              <FormLabel id="demo-form-control-label-placement">
                Label placement
              </FormLabel>
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
            </FormControl>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}