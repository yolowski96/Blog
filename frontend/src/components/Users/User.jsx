import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import clsx from "clsx";
import { fetchApi } from "../../utils";
import {useSelector} from "react-redux";

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

export default function User({ user }) {
  const classes = useStyles();
  const history = useHistory();
  const authorities = useSelector(state => state.authorities);
  const { username, authorities:userAuthorities} = user;

  const makeAdmin = async () => {
    const response = await fetchApi({
      api: `set-admin/${username}`,
    });

    if (response.ok) {
      history.push("/posts");
    }
  };

  const makeUser = async () => {
    const response = await fetchApi({
      api: `set-user/${username}`,
    });

    if (response.ok) {
      history.push("/posts");
    }
  };

  const deleteUser = async () => {
    const response = await fetchApi({
      api: `delete/${username}`,
      options: {
        method: "DELETE",
      },
    });

    if (response.ok) {
      history.push("/posts");
    }
  };

  const isRoot = Boolean(
    authorities.find(({ authority }) => authority === "ROLE_ROOT")
  );

  return (
    <Card className={clsx(classes.card)}>
      <CardContent className={classes.username}>
        <Typography gutterBottom variant="h5" component="h2">
          {username}
        </Typography>
      </CardContent>
      <CardActions className={classes.actions}>
        {userAuthorities.length === 1 &&
          <Button onClick={makeAdmin} size="small" color="primary">
            Make admin
          </Button>
        }
        {isRoot && userAuthorities.length === 2 && (
          <Button onClick={makeUser} size="small" color="primary">
            Make user
          </Button>
        )}

        {isRoot && (
          <Button onClick={deleteUser} size="small" color="primary">
            Delete user
          </Button>
        )}
      </CardActions>
    </Card>
  );
}
