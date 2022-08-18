import React from 'react';

import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";

import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

export default function AvatarSelectBar(props) {
	//아바타 종류 string
	const [string, setString] = React.useState("");
	//아바타 이미지 data
	const [imgDatas, setImgdatas] = React.useState([]);
	//아바타 현재 선택 값
	const [currentValue, setCurrentValue] = React.useState(1);
	React.useEffect(() => {
    console.log(props.string)
    setString(props.string);
	}, [props.string]);
	React.useEffect(() => {
    console.log(props.imgDatas);
		setImgdatas(props.imgDatas);
		if (props.imgDatas && !(props.imgDatas.length===0)) {
			console.log(props.imgDatas[0][`${props.string}Url`]);
		}
	}, [props.imgDatas]);
	React.useEffect(() => {
    console.log(props.currentValue);
    setCurrentValue(props.currentValue);
	}, [props.currentValue]);
	
	const previousButton = () => {
		props.setValue((currentValue>1) ? currentValue - 1 : 1);
	};
	const nextButton = () => {
		props.setValue((currentValue<imgDatas.length) ? currentValue + 1 : imgDatas.length);
	};

	return (
		<Grid container item xs={12} alignItems="center"
			style={{margin:"10px", border:"5px solid white", backgroundColor:"#ffd400",  boxShadow: "3px 3px 5px #000",
			borderRadius: "1%"}}>
			<Grid item xs={1.5}><Button onClick={previousButton}><ArrowBackIosNewIcon style={{color:"black"}} /></Button></Grid>
			{(imgDatas && !(imgDatas.length===0)) ?
				<Grid container item xs={9}>
					<Grid item xs={4}>
						{ currentValue>1 ? 
							<Button onClick={() => { props.setValue(imgDatas[currentValue - 2][`${string}No`]) }}
								style={{width:"100%", height:"100%"}}>
								<img src={imgDatas[currentValue-2][`${string}Url`]} alt={imgDatas[currentValue-2][`${string}Url`]} width="100%" height="100%" />
							</Button>
							: <div></div>
						}
					</Grid>
					<Grid item xs={4}>
						<Button onClick={() => { props.setValue(imgDatas[currentValue-1][`${string}No`]) } }
							style={{width:"100%", height:"100%"}}>
							<img src={imgDatas[currentValue - 1][`${string}Url`]} alt={imgDatas[currentValue - 1][`${string}Url`]} width="100%" height="100%" />
						</Button>
					</Grid>
					<Grid item xs={4}>
						{ currentValue<imgDatas.length ? 
							<Button onClick={() => { props.setValue(imgDatas[currentValue][`${string}No`]) } }
							style={{width:"100%", height:"100%"}}>
								<img src={imgDatas[currentValue][`${string}Url`]} alt={imgDatas[currentValue][`${string}Url`]} width="100%" height="100%" />
							</Button>
							: <div></div>
						}
					</Grid>
				</Grid>
				:
				<div></div>
			}
			<Grid item xs={1.5}><Button onClick={ nextButton }><ArrowForwardIosIcon style={{color:"black"}} /></Button></Grid>
		</Grid>
	);
}