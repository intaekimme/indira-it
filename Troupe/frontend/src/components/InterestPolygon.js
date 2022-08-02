import React from "react";
import styledPoly from "../css/poly.module.css";
export default function InterestPolygon(props) {
  const [width, setWidth] = React.useState(200);
  //유저의 관심사
  const interest = [...props.interest];
  //다각형 단위길이
  const [unitLength, setUnitLength] = React.useState(30);
  //수치 단위길이
  const interestUnit = (unitLength / 2) * Math.sqrt(6.76);
  //svg 구역 높이
  const height = unitLength * 10 + 200;
  //n각형
  const poly = 8;
  //가로길이 보정
  const widthCut = 50;
  //다각형 회전시작각도
  const startDeg = -22.5;
  React.useEffect(()=>{
    const size = window.innerWidth;
    if (size < 1000) {
      setWidth(size / 2 - widthCut);
      setUnitLength(size/4/10);
    } else {
      setWidth(size / 4 - widthCut);
      setUnitLength(size/8/10);
    }
  },[window.innerWidth]);

  //회전각
  let deg = 360 / poly;
  //다각형 element
  let polyElement = [];
  //interest 수치 element
  let interestElement = [];
  for (let i = 0; i < poly; i++) {
    for (let j = 1; j <= 5; j++) {
      polyElement.push(
        <path
          key={`poly${i}${j}`}
          fill="none"
          stroke="#000"
          strokeWidth={1}
          strokeDasharray="none"
          data-z-index={1}
          className={`highcharts-grid-line ${styledPoly.guideline}`}
          transform={`rotate(${startDeg + deg * i})`}
          transform-origin={`${width / 2} ${height / 2}`}
          d={`M ${width / 2 - (unitLength / 2) * j} ${
            height / 2 - ((unitLength * 6) / 5) * j
          }
          L ${width / 2 + (unitLength / 2) * j} ${
            height / 2 - ((unitLength * 6) / 5) * j
          }`}
        ></path>
      );
    }
    interestElement.push(
      <path
        key={`interest${i}`}
        fill="none"
        stroke="#000"
        strokeWidth={1}
        strokeDasharray="none"
        data-z-index={1}
        className={`highcharts-grid-line ${styledPoly.valueline}`}
        transform={`rotate(${deg * i})`}
        transform-origin={`${width / 2} ${height / 2}`}
        d={`M ${width / 2} ${height / 2}
        L ${width / 2} ${height / 2 - ((interestUnit*0.05) * interest[i])}`}
      ></path>
    );
  }
  return (
    <div style={{ textAlign: "center" }}>
      <div style={{ position: "relative", textAlign: "center" }}>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 - interestUnit * 0.4}px`,
            top: `${height / 2 - interestUnit * 5.6}px`,
            fontSize: "10px",
          }}
        >
          연극
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 - interestUnit * 4.6}px`,
            top: `${height / 2 - interestUnit * 4}px`,
            fontSize: "10px",
          }}
        >
          뮤지컬
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 - interestUnit * 5.8}px`,
            top: `${height / 2 + interestUnit * 0}px`,
            fontSize: "10px",
          }}
        >
          무용
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 - interestUnit * 4.6}px`,
            top: `${height / 2 + interestUnit * 3.4}px`,
            fontSize: "10px",
          }}
        >
          클래식
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 - interestUnit * 0.6}px`,
            top: `${height / 2 + interestUnit * 5}px`,
            fontSize: "10px",
          }}
        >
          오페라
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 + interestUnit * 3.6}px`,
            top: `${height / 2 + interestUnit * 3.4}px`,
            fontSize: "10px",
          }}
        >
          국악
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 + interestUnit * 5.2}px`,
            top: `${height / 2 + interestUnit * 0}px`,
            fontSize: "10px",
          }}
        >
          가요
        </div>
        <div
          style={{
            position: "absolute",
            left: `${width / 2 + interestUnit * 3.6}px`,
            top: `${height / 2 - interestUnit * 4}px`,
            fontSize: "10px",
          }}
        >
          전시
        </div>
      </div>
      <svg
        version="1.1"
        className="highcharts-root"
        style={{ fontSize: "12px", border: "1px solid black" }}
        xmlns="http://www.w3.org/2000/svg"
        width={width}
        height={height}
        viewBox={`0 0 ${width} ${height}`}
        aria-hidden="false"
        aria-label="Interactive chart"
      >
        {/* 다각형틀 */}
        {polyElement}
        {/* 다각형틀끝 */}
        {interestElement}
      </svg>
    </div>
  );
}
