import React from "react";

import LikeabilityBar from "./LikeabilityBar";
export default function LikeabilityRank(props) {
  const [rank, setRank] = React.useState(
    props.likeabilityData[0] ? props.likeabilityData[0].rank : -1,
  );
  React.useEffect(()=>{
    if(props.likeabilityData[0]){
      console.log(props.likeabilityData[0]);
      setRank(props.likeabilityData[0].likabilityResponse.rank);
    }
  }, [props.likeabilityData]);
  return (
    <div>
      {props.likeabilityWithMember ? (
        <div style={{ fontSize: "100px" }}>
          <span
            style={{
              color: "#fda085",
            }}
          >
            {rank}
          </span>
          <span
            style={{
              fontSize: "70px",
            }}
          >
            위
          </span>
        </div>
      ) : (
        <div></div>
      )}
      {props.likeabilityData.length == 0 ? (
        <div></div>
      ) : (
        props.likeabilityData.map((data, index) => (
          <LikeabilityBar
            likeabilityData={data}
            key={`LikeabilityBar_${data.memberNo}_${index}`}
          />
        ))
      )}
      <div
        style={{
          position: "relative",
          textAlign: "center",
          verticalAlign: "bottom",
          height: "10%",
        }}
      >
        {props.likeabilityWithMember ? (
          <div
            style={{
              position: "relative",
              bottom: "0%",
            }}
          >
            {props.nickname} 님에 대한{" "}
            <span
              style={{
                color: "#fda085",
              }}
            >
              나의 호감도 순위
            </span>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}
