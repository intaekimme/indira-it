import React from "react";
import { useParams } from "react-router-dom";
import apiClient from "../apiClient";

import { createTheme, ThemeProvider } from "@mui/material/styles";

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

import Avatar from "./Avatar";

const theme = createTheme();

export default function ProfileAvatarModify() {
	//memberNo
	const { memberNo } = useParams();
  //이 member의 아바타
  const [avatar, setAvatar] = React.useState("");

	React.useEffect(() => {
		if (!memberNo) {
			return;
		}
    apiClient.getMemberAvatar(memberNo).then((data) => {
      console.log(data);
      setAvatar(data);
    });
	}, [memberNo]);

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

	const [shapes, setShapes] = React.useState([]);
	const [eyes, setEyes] = React.useState([]);
	const [noses, setNoses] = React.useState([]);
	const [mouthes, setMouthes] = React.useState([]);
	const [hairs, setHairs] = React.useState([]);
	const [clothes, setClothes] = React.useState([]);

	React.useEffect(() => {
		// apiClient.getAvatarList().then((data) => {
		// 	setShape(data.shape);
		// 	setEye(data.eye);
		// 	setNose(data.nose);
		// 	setMouth(data.mouth);
		// 	setHair(data.hair);
		// 	setClothes(data.clothes);
		// });
		apiClient.getAvatarList("shape").then((data) => {
			setShapes(data);
			console.log(data);
		});
		apiClient.getAvatarList("eye").then((data) => {
			setEyes(data);
			console.log(data);
		});
		apiClient.getAvatarList("nose").then((data) => {
			setNoses(data);
			console.log(data);
		});
		apiClient.getAvatarList("mouth").then((data) => {
			setMouthes(data);
			console.log(data);
		});
		apiClient.getAvatarList("hair").then((data) => {
			setHairs(data);
			console.log(data);
		});
		apiClient.getAvatarList("clothes").then((data) => {
			setClothes(data);
			console.log(data);
		});
	}, []);
	
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
            alignItems="center"
            style={{
              backgroundColor: "gray",
              paddingRight: 20,
              paddingBottom: 50,
              position: "relative",
            }}
          >
            <Avatar
              avatarResponse={avatar}
              imgWidth={75 * 4}
              imgHeight={100 * 4}
              divWidth={"100%"}
              divHeight={"100%"}
            />
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
                <RadioGroup
                  row
                  aria-labelledby="demo-form-control-label-placement"
                  name="position"
                  defaultValue="top"
                >
                  {hairs.map((hair) => (
                    <FormControlLabel
                      key={`hair${hair.hairNo}`}
                      value={hair.hairNo}
                      control={<Radio />}
                      label={
                        <img src={hair.hairUrl} alt={hair.hairUrl} width="150px" height="200px" />
                      }
                      labelPlacement="bottom"
                    />
                  ))}
                </RadioGroup>
              </Grid>
              <Grid item xs={12}>
								<RadioGroup
                  row
                  aria-labelledby="demo-form-control-label-placement"
                  name="position"
                  defaultValue="top"
                >
                  {eyes.map((eye) => (
                    <FormControlLabel
                      key={`eye${eye.eyeNo}`}
                      value={eye.eyeNo}
                      control={<Radio />}
                      label={
                        <img src={eye.eyeUrl} alt={eye.eyeUrl} width="150px" height="200px" />
                      }
                      labelPlacement="bottom"
                    />
                  ))}
                </RadioGroup>
              </Grid>
              <Grid item xs={12}>
								<RadioGroup
                  row
                  aria-labelledby="demo-form-control-label-placement"
                  name="position"
                  defaultValue="top"
                >
                  {noses.map((nose) => (
                    <FormControlLabel
                      key={`nose${nose.noseNo}`}
                      value={nose.noseNo}
                      control={<Radio />}
                      label={
                        <img src={nose.noseUrl} alt={nose.noseUrl} width="150px" height="200px" />
                      }
                      labelPlacement="bottom"
                    />
                  ))}
                </RadioGroup>
              </Grid>
              <Grid item xs={12}>
								<RadioGroup
                  row
                  aria-labelledby="demo-form-control-label-placement"
                  name="position"
                  defaultValue="top"
                >
                  {mouthes.map((mouth) => (
                    <FormControlLabel
                      key={`mouth${mouth.mouthNo}`}
                      value={mouth.mouthNo}
                      control={<Radio />}
                      label={
                        <img src={mouth.mouthUrl} alt={mouth.mouthUrl} width="150px" height="200px" />
                      }
                      labelPlacement="bottom"
                    />
                  ))}
                </RadioGroup>
              </Grid>
              <Grid item xs={12}>
								<RadioGroup
                  row
                  aria-labelledby="demo-form-control-label-placement"
                  name="position"
                  defaultValue="top"
                >
                  {clothes.map((cloth) => (
                    <FormControlLabel
                      key={`cloth${cloth.clothesNo}`}
                      value={cloth.clothesNo}
                      control={<Radio />}
                      label={
                        <img src={cloth.clothesUrl} alt={cloth.clothesUrl} width="150px" height="200px" />
                      }
											style={{margin:0, padding:0}}
                      labelPlacement="bottom"
                    />
                  ))}
                </RadioGroup>
              </Grid>
            </FormControl>
          </Grid>
        </Grid>
      </Container>
    </ThemeProvider>
  );
}