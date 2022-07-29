# 목차
- [기본속성](#기본속성)
  * [inline](#inline)
  * [block](#block)
  * [inline-block](#inline-block)
- [Position](#position)
  * [position: static](#position-static)
  * [position: relative](#position-relative)
  * [position: absolute](#position-absolute)
  * [position: fixed](#position-fixed)
  * [position: sticky](#position-sticky)
- [가로배치 float](#float)
  * [object-fit](#object-fit)
- [Image](#image)

## 기본속성
### inline
width/height 적용불가
margin/padding 의 top/bottom 적용불가
line-height 적용불가
### block
무조건 한줄 점유, 다음태그는 줄바꿈
### inline block
width/height 적용가능
margin/padding 의 top/bottom 적용가능
line-height 적용가능

inline-block 사이에 공백이 생기는 경우 parnet 태그에 font-size: 0을 적용
inline-block 끼리 높이가 맞지않아 상위공백이 생기는 경우 vertical-align: top 적용


## Position
### position static
기본적인 순서에 따라 배치 (위>아래, 왼쪽>오른쪽)
부모요소 내의 자식요소로서 존재할 때는 부모요소의 위치를 기준으로 배치
top, bottom, left, right 무시됨

### position relative
position: static 일때 위치가 기준위치
top, bottom, left, right 속성을 통해 기준위치에서 이동
부모의 (0,0) 에서 (top/bottom/left/right) 만큼 이동

### position absolute
특정 부모에 대한 절대적 위치
조상이 모두 static인 경우 document body가 기준이됨
부모요소를 기준으로 하고싶을 때 반드시 부모요소가 relative, fixed, absolute 중 하나 여야함
일반적으로 부모요소를 기준으로 상대위치를 지정하고 싶을 때 부모-relative, 자식-absolute 면 됨
top, bottom, left, right 속성을 통해 기준위치에서 이동
부모위치의 (top/bottom/left/right) 로 부터 (top/bottom/left/right 값) 만큼 이동

### position fixed
고정위치
화면의 (top/bottom/left/right) 에서 (top/bottom/left/right 값) 만큼 떨어져있음

### position sticky
스크롤기준 고정위치

## float
float을 사용하면 element를 공중에 띄우는 효과
헤더의 양끝에 요소들을 배치하고 싶은 경우

	<div style={{width: "100%", height: "100px"}}>
		<div id="왼쪽" style={{ float: "left", width: "100px" }}>
		<div id="오른쪽" style={{ float: "right", width: "100px" }}>
		<div id="중앙" style={{ width: "100%"}}
	</div>

## Image
### object fit
object-fit: fill;	가로세로 꽉채움
object-fit: contain;	비율유지한채로 가로 세로중 한쪽이 다 채워질때까지
object-fit: cover;	비율유지한채로 가로 세로 모두 다 채워질때까지
object-fit: none;	조절X
object-fit: scale-down;	contain, none 중 작아지는 쪽
