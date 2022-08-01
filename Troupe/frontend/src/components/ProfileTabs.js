import * as React from "react";
import PropTypes from "prop-types";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export default function CenterTab(props) {
  const [value, setValue] = React.useState(0);

  const tabContent = props.tabContent;
  const tabText = props.tabText;

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box
      sx={{ width: "100%" }}
      style={{ border: "3px solid black", backgroundColor: "#FFCF24" }}
    >
      <Box sx={{ width: "100%", bgcolor: "background.paper" }}>
        <Tabs value={value} onChange={handleChange} centered>
          {tabText.map((text, index) => (
            <Tab key={text + index} label={text} {...a11yProps(index)} />
          ))}
        </Tabs>
      </Box>
      {tabContent.map((content, index) => (
        <div key={`content${index}`}>
          <TabPanel
            value={value}
            index={index}
          >
            {content}
          </TabPanel>
        </div>
      ))}
    </Box>
  );
}
