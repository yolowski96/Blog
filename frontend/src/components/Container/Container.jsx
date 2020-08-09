import React from "react";
import { makeStyles, Theme, createStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      height: "100vh",
    },
  })
);

export default function Container({ children }) {
  const classes = useStyles();

  return <div className={classes.root}>{children}</div>;
}
