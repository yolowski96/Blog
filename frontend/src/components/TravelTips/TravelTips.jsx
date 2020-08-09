import React from "react";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import clsx from "clsx";
import { fetchApi } from "../../utils";
import TravelTip from "./TravelTip";

const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      backgroundColor: "ghostwhite",
    },
    margin: {
      margin: theme.spacing(1),
    },
    withoutLabel: {
      marginTop: theme.spacing(3),
    },
    textField: {
      // flexBasis: 200,
    },
    posts: {
      margin: "20px 0px",
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-between",
    },
    column: {
      height: "85vh",
      overflowY: "auto",
      marginTop: theme.spacing(3),
      marginBottom: theme.spacing(3),
    },
    title: {
      "text-align": "center",
    },
    flexSpacer: {
      "flex-grow": 1,
    }
  })
);

const fetchTravelTips = async () => {
  const response = await fetchApi({
    api: "tips",
    options: {
      method: "GET",
    },
  });
  if (response.ok) {
    return response.json();
  }
};

export default function TravelTips() {
  const classes = useStyles();
  const [travelTips, setTravelTips] = React.useState([]);

  React.useEffect(() => {
    const getTravelTips = async () => {
      const travelTips = await fetchTravelTips();
      setTravelTips(travelTips);
    };
    getTravelTips();
  }, []);
  return (
    <>
      <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
        <div className={classes.posts}>
          {travelTips && travelTips.map((tip) => <TravelTip tip={tip} />)}
        </div>
      </Container>
    </>
  );
}
