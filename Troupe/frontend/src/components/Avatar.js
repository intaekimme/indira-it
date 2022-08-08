import React from "react";

export default function Avatar(props) {
	const avatarResponse = props.avatarResponse;
	const imgWidth = 30;
  const imgHeight = 50;
	return (
		<div>
      <img
        src={avatarResponse.shapeUrl}
        alt={avatarResponse.shapeUrl}
        style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
			></img>
      <img
        src={avatarResponse.clothesUrl}
        alt={avatarResponse.clothesUrl}
				style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
			></img>
			<img
        src={avatarResponse.eyeUrl}
        alt={avatarResponse.eyeUrl}
        style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
			></img>
			<img
        src={avatarResponse.mouthUrl}
        alt={avatarResponse.mouthUrl}
        style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
			></img>
			<img
        src={avatarResponse.noseUrl}
        alt={avatarResponse.noseUrl}
        style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
			></img>
      <img
        src={avatarResponse.hairUrl}
        alt={avatarResponse.hairUrl}
        style={{
					position: "absolute",
					top: "0%",
          width: `${imgWidth}px`,
          height: `${imgHeight}px`,
        }}
      ></img>
    </div>
  );
}