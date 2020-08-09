import React from "react";
import { useSelector } from "react-redux";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import clsx from "clsx";
import { fetchApi } from "../../utils";

const useStyles = makeStyles({
  card: {
    height: "inherit",
  },
  cardMedia: {
    display: "flex",
    justifyContent: "space-between",
  },
  username: {
    textAlign: "center",
  },
  postFooter: {
    display: "flex",
    justifyContent: "space-between",
    paddingTop: 40,
  },
  actions: {
    justifyContent: "center",
  },
});

 export default function User({ tip }) {
  const classes = useStyles();
  const history = useHistory();
  const authorities = useSelector((state) => state.authorities);
  const { title, description } = tip;
  const deleteTip = async () => {
    const response = await fetchApi({
      api: `tips/delete/${title}`,
      options: {
        method: "DELETE",
      },
    });

    if (response.ok) {
      history.push("/tips");
    }
  };

  const isRoot = Boolean(
    authorities.find(({ authority }) => authority === "ROLE_ROOT")
  );

  return (
    <Card className={clsx(classes.card)}>
      <CardContent className={classes.username}>
        <Typography gutterBottom variant="h5" component="h2">
          {title}
        </Typography>
        <Typography variant="body2" color="textSecondary" component="p">
          {description}
        </Typography>
      </CardContent>

      <CardActions className={classes.actions}>
        {isRoot && (
          <Button onClick={deleteTip} size="small" color="primary">
            Delete
          </Button>
        )}
      </CardActions>
    </Card>
  );
}
