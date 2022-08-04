# 목차
  * [for](#for)
  * [배열](#배열)
    + [...](#점점점)
    + [map](#map)
  * [JSX](#jsx)
  * [css 개별적용](#css-개별적용)
  * [prop types](#prop-types)
  * [React](#react)
    + [React useState()](#react-usestate)
    + [React memo](#react-memo)
    + [React useEffect](#react-useeffect)
  * [프로젝트 생성](#프로젝트-생성)
  * [Router](#router)
    + [react-router-dom useParams](#useparams)
  * [Axios](#axios)
    + [Promise 반환](#promise)
  * [error](#error)
    + [is not a component All component children of must be a or](#is-not-a-component-all-component-children-of-must-be-a-or)
    + [ERESOLVE unable to resolve dependency tree](#eresolve-unable-to-resolve-dependency-tree)
## for
index 읽기, index 조절, break문 가능

	for(int i=0;i<array.length; ++i
		console.log('basic' + array[i]

index 읽기, break문 가능

	for(const i in array)
		console.log('basic' + array[i]

break문 가능

	for(const v of array)
		console.log('of' + v)

break문 가능

	array.som(v=> {
		console.log('some' + v);
		return (v==3);
	})

전부 조절 불가능

	array.forEach(v=> console.log('each' + v));

## 배열

	1. const food = ["tomato", "potato"];
	2-1. const [myFavFood, mySecondFavFood] = food;
	2-2. const myFavFood = food[0]; const mySecondFavFood = food[1];

2-1과 2-2는 같음

### 점점점
각 element를 할당함

	const array = [1,2,3,4];
	const abc = [2, array]; //[2,array]
	const abcd = [2, ...array]; //[2,1,2,3,4]

### map
array의 요소들을 다른값으로 변형가능

	['one', 'two', 'three', 'four', 'five'].map((item)=>item.toUpperCase());  //ONE TWO THREE FOURE FIVE

## JSX
JSX : Element를 HTML 형식으로 JS로 작성

	const btn = <button id="btn" onClick = {()=> console.log("im clicked")}>버튼</button> //JSX
	const btn = React.createElement(  //React형식으로 자동변환
		"button",
		{
			id: "btn"`,
			onClick: () => console.log("im clicked"),
		},
		"버튼"
	);`

	<tag class=""> //X 동작안함
	<tag className=""> //O
	<label for=""> //X 동작안함
	<label htmlFor=""> //O

## css 개별적용
[name].module.css
css의 모듈화
보통 css import 시 전체 적용됨
.module.css로 작성하면 개별적으로 적용가능

	import styled from "./Button.module.css"
	<button className={styled.btn}>

## prop types
PropTypes - props의 타입을 체크하여 에러메세지 표시

	MemorizedBtn.propTypes = {
		text: PropTypes.string.isRequired,
		//fontSize: PropTypes.number.isRequired,
	}

## React
modifier	: 값을 counter에 할당하고 ReactDOM.render 진행
React	: Javascript로 시작해서 HTML로 종료 > 사용자에게 보여지는 것을 컨트롤(변경)할수있음
	: app이 상호작용하도록 만들어주는 library
React-Dom : React Element를 body에 넣어주는 library

	const Button = ()=> (
		<button
			style={{
			backgroundColor: "tomato",
			}}
			onClick = {() => console.log("im clicked")}
		>
		Click me
		</button>
	)
	const Container = () => (
		<div>
			<Button/>
		</div>
	)
	ReactDOM.render(<Container/>, root);

### React useState

	const [counter, setCounter]= React.useState(0); //counter 0으로 초기화, 반드시 setCounter를 통해 수정
	const onClick = () => {
		setCounter((current) => current + 1); //현재 counter 값을 기준으로 +1
	};

### React memo
리렌더링할 때 상태를 기억하여 바뀌지않은 부분은 렌더링하지 않음

	//클릭 시 text1 부분의 버튼만 렌더링
	const MemorizedBtn = React.memo(Btn);
	function PropsApp(){
		const [text1, setText] = React.useState("First");
		const changeText = () => setText("Change");
		return(
			<div>
				<MemorizedBtn text={text1} onClick={changeText}/>
				<MemorizedBtn text="second"/>
			</div>
		);
	}

### React useEffect
useEffect(함수, 변수배열)
	변수가 변경될때마다 실행 ([] 빈배열인 경우 최초1번만실행)
	모든화면이 렌더링 된 후 실행됨

Cleanup function	useEffect를 통해 오브젝트가 destroy될때도 코드실행

	import {useState, useEffect} from "react";
	function Object(){
		const [counter, setCounter] = useState(0);
		console.log("I running every time");
		useEffect(() => console.log("I run only one"), [counter]);
		useEffect(() =>{
			console.log("I run when this function created");
			return () => console.log("I run when this function destroyed");
		}, []);
	}

## 프로젝트 생성

	$npx create-react-app [폴더명]
	$cd [폴더명]
	$npm i prop-types
	$npm i react-router-dom

## Router

	import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

### useParams
: 를 통해 path에 params 설정 후 useParams()를 통해 params의 이름과 같은 변수에 data 저장

route

	import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
	<Route path="/profile/:memberNo" element={<PerfList />}></Route>

element

	import { useParams } from "react-router-dom";
	const { memberNo } = useParams();

## Axios
### Promise
axios 요청

	getMemberInfo: (memberNo) => {
		return instance
		.get(`/member/${memberNo}`)
		.then((response) => {
			console.log(response.data);
			return response.data;
		})
		.catch((error) => {
			alert("Member 정보를 불러오는데 실패하였습니다 : " + error);
			return null;
		});
	},

외부에서 axios data받을 때 Promise 객체로 반환되므로 then을 통해 data 처리

	const [memberInfo, setMemberInfo] = React.useState("");
	React.useEffect(() => {
		console.log(memberNo);
		apiClient.getMemberInfo(memberNo).then((data) => {
		setMemberInfo(data);
		});
	}, [memberNo]);

## Error

### is not a component All component children of must be a or
Routes의 자식으로 Route만 가능하게 바뀜
아래코드처럼 수정

	import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
	function App() {
		return (
			<div className="App">
				<Router>
					<Routes>
						<Route path="/">
							<LandingPage />
						</Route>
						<Route path="/login">
							<LoginPage />
						</Route>
						<Route path="/register">
							<RegisterPage />
						</Route>
					</Routes>
				</Router>
			</div>
		);
	}
	export default App;

	import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
	function App() {
		return (
			<div className="App">
				<Router>
					<Routes>
						<Route path="/" element={ <LandingPage /> } />
						<Route path="/login" element={ <LoginPage /> } />
						<Route path="/register" element={ <RegisterPage /> } />
					</Routes>
				</Router>
			</div>
		);
	}
	export default App;

### ERESOLVE unable to resolve dependency tree
npm ERR! ERESOLVE unable to resolve dependency tree
npm ERR! this command with --force, or --legacy-peer-deps

	npm install @mui/material @mui/icons-material @emotion/react @emotion/styled @material-ui/core @material-ui/icons --legacy-peer-deps
