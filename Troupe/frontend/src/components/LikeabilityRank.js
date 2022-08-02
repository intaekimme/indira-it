import React from "react";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Stage from "../img/stage.jpg";
export default function LikeabilityRank(props) {
  //유저의 호감도 순위
//   const [likeabilityElement, setLikeabilityElement] = React.useState([]);
//   const [likeabilityData, setlikeabilityData] = React.useState(props.likeabilityData);
//   React.useEffect(
//     ()=>{
//       console.log(likeabilityData);
//       setLikeabilityElement((current)=>{
//         likeabilityData.map((data)=>{
//           console.log(data.exp%100);
//           current.push(
//             {
//               character: (
//                 <Grid item xs={2}>
//                   {/* <img src={data.imgUrl} */}
//                   <img src={Stage}
//                   style={{ width: "30px", height: "50px" }}></img>
//                 </Grid>
//               ),
//               data: (
//                 <Grid item xs={8} style={{ position: "relative" }}>
//                   <Box
//                     style={{
//                       position: "absolute",
//                       backgroundColor: "#AAAAAA",
//                       borderRadius: "50px",
//                       width: "90%",
//                       height: "30px",
//                     }}
//                   ></Box>
//                   <Box
//                     style={{
//                       position: "absolute",
//                       backgroundColor: "#FF88DD",
//                       borderRadius: "50px",
//                       width: `${90 * (data.exp%100)*0.01}%`,
//                       height: "30px",
//                     }}
//                   ></Box>
//                 </Grid>
//               ),
//               level: (
//                 <Grid item xs={2}>
//                   Lv.{data.exp/100}
//                 </Grid>
//               ),
//             }
//           );
//         });
//       });
//       console.log(likeabilityElement);
//     }
//   , [likeabilityData]);

//   const temp =
//   {
//     character: (
//       <Grid item xs={2}>
//         {/* <img src={data.imgUrl} */}
//         <img src={Stage}
//         style={{ width: "30px", height: "50px" }}></img>
//       </Grid>
//     ),
//     data: (
//       <Grid item xs={8} style={{ position: "relative" }}>
//         <Box
//           style={{
//             position: "absolute",
//             backgroundColor: "#AAAAAA",
//             borderRadius: "50px",
//             width: "90%",
//             height: "30px",
//           }}
//         ></Box>
//         <Box
//           style={{
//             position: "absolute",
//             backgroundColor: "#FF88DD",
//             borderRadius: "50px",
//             width: `${90 * (20)*0.01}%`,
//             height: "30px",
//           }}
//         ></Box>
//       </Grid>
//     ),
//     level: (
//       <Grid item xs={2}>
//         Lv.{5}
//       </Grid>
//     ),
//   };

// console.log(temp);
  const likeabilityData = [
    {
      character: (
        <Grid item xs={2}>
          <img src={Stage} style={{ width: "30px", height: "50px" }}></img>
        </Grid>
      ),
      data: (
        <Grid item xs={8} style={{ position: "relative" }}>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#AAAAAA",
              borderRadius: "50px",
              width: "90%",
              height: "30px",
            }}
          ></Box>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#FF88DD",
              borderRadius: "50px",
              width: `${90 * 0.5}%`,
              height: "30px",
            }}
          ></Box>
        </Grid>
      ),
      level: (
        <Grid item xs={2}>
          Lv.{1}
        </Grid>
      ),
    },
    {
      character: (
        <Grid item xs={2}>
          <img src={Stage} style={{ width: "30px", height: "50px" }}></img>
        </Grid>
      ),
      data: (
        <Grid item xs={8} style={{ position: "relative" }}>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#AAAAAA",
              borderRadius: "50px",
              width: "90%",
              height: "30px",
            }}
          ></Box>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#FF88DD",
              borderRadius: "50px",
              width: `${90 * 0.5}%`,
              height: "30px",
            }}
          ></Box>
        </Grid>
      ),
      level: (
        <Grid item xs={2}>
          Lv.{1}
        </Grid>
      ),
    },
    {
      character: (
        <Grid item xs={2}>
          <img src={Stage} style={{ width: "30px", height: "50px" }}></img>
        </Grid>
      ),
      data: (
        <Grid item xs={8} style={{ position: "relative" }}>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#AAAAAA",
              borderRadius: "50px",
              width: "90%",
              height: "30px",
            }}
          ></Box>
          <Box
            style={{
              position: "absolute",
              backgroundColor: "#FF88DD",
              borderRadius: "50px",
              width: `${90 * 0.5}%`,
              height: "30px",
            }}
          ></Box>
        </Grid>
      ),
      level: (
        <Grid item xs={2}>
          Lv.{props.likeabilityData[0].exp/100}
        </Grid>
      ),
    },
  ];
  return (
    <div style={{ height: "100%" }}>
      {props.likeabilityWithUser ? (
        <div></div>
      ) : (
        <div
          style={{
            textAlign: "center",
            height: "90%",
          }}
        >
          {likeabilityData.map((element, index) => (
            <Grid
              container
              spacing={3}
              style={{ position: "relative", top: "20%", textAlign: "center", padding:"10px"}}
              key={`likeabillityData${index}`}
            >
              {element.character}
              {element.data}
              {element.level}
            </Grid>
          ))}
        </div>
      )}
      <div
        style={{
          position: "relative",
          textAlign: "center",
          verticalAlign: "bottom",
          height: "10%",
        }}
      >
        {props.likeabilityWithUser ? (
          <div style={{ position: "relative", bottom: "0%" }}>
            {props.nickname} 님에 대한 나의 호감도 순위
          </div>
        ) : (
          <div style={{ position: "relative", bottom: "0%" }}>
            {props.nickname} 님의 호감도 순위
          </div>
        )}
      </div>
    </div>
  );
}
