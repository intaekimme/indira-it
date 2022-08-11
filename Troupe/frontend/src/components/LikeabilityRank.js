import React from "react";

import LikeabilityBar from "./LikeabilityBar";
export default function LikeabilityRank(props) {
  const [rank, setRank] = React.useState(props.likeabilityData[0] ? props.likeabilityData[0].rank : -1);
  return (
    <div>
      {props.likeabilityWithMember ? (
        <div style={{ fontSize: "100px" }}>{rank}위</div>
      ) : (
        <div></div>
      )}
      {props.likeabilityData.length == 0 ? <div></div>
      : props.likeabilityData.map((data, index) => (
        <LikeabilityBar
          likeabilityData={data}
          key={`LikeabilityBar_${data.memberNo}_${index}`}
        />
      ))}
      <div
        style={{
          position: "relative",
          textAlign: "center",
          verticalAlign: "bottom",
          height: "10%",
        }}
      >
        {props.likeabilityWithMember ? (
          <div style={{ position: "relative", bottom: "0%" }}>
            {props.nickname} 님에 대한 나의 호감도 순위
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}
