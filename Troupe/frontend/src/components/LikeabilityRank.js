import React from "react";
export default function LikeabilityRank(props) { 
  return (
    <div
      style={{
        position: "relative",
        textAlign: "center",
        verticalAlign: "bottom",
        height: "100%",
      }}
    >
      {props.likeabilityWithUser ? (
        <div style={{ position: "relative", top: "80%" }}>
          {props.nickname} 님에 대한 나의 호감도 순위
        </div>
      ) : (
        <div style={{ position: "relative", top: "80%" }}>
          {props.nickname} 님의 호감도 순위
        </div>
      )}
    </div>
  );
}
