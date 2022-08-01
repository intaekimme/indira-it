import React from "react"
import { Button, ButtonGroup } from "@mui/material"

export default function PerfFeedToggle(){
    return (
      <ButtonGroup size='large' sx={{pt:7, justifyContent:'center'}}>
        <Button href='/perf/list' sx={{color:'black',background:'orange', fontFamily:'IBM Plex Sans KR'}}>Performance</Button>
        <Button href='/feed/list' sx={{color:'black',background:'pink', fontFamily:'IBM Plex Sans KR'}}>Feed</Button>
      </ButtonGroup>
    )
  }