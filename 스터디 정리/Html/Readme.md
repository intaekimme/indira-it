#목자
- [Position](#position)
  * [position: static](#position-static)
  * [position: relative](#position-relative)
  * [position: absolute](#position-absolute)
  * [position: fixed](#position-fixed)
  * [position: sticky](#position-sticky)

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