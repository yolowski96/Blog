import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import { useHistory } from "react-router";
import clsx from "clsx";
import { fetchApi } from "../../utils";

const useStyles = makeStyles({
  media: {
    height: (props) => (props.showDetails ? 320 : 140),
  },
  card: {
    height: "inherit",
  },
  cardMedia: {
    display: "flex",
    justifyContent: "space-between",
  },
  postDetails: {
    height: (props) => (props.showDetails ? 300 : "initial"),
  },
  postFooter: {
    display: "flex",
    justifyContent: "space-between",
    paddingTop: 40,
  },
});

export default function MediaCard({ post, showDetails, user }) {
  const classes = useStyles({ showDetails });
  const history = useHistory();
  const { title, summary, url, published, author, content } = post;

  const handleClick = (e) => {
    history.push(`/posts/details/${title}`);
  };

  const handleEdit = () => {
    history.push(`/posts/edit/${title}`);
  };

  const handleDelete = async () => {
    const response = await fetchApi({
      api: `posts/delete/${title}`,
      options: {
        method: "DELETE",
      },
    });
    if (response.ok) {
      history.push("/posts");
    }
  };

  return (
    <Card className={clsx(showDetails && classes.card)}>
      <CardActionArea onClick={handleClick}>
        <CardMedia
          className={classes.media}
          image={url}
          title="Contemplative Reptile"
        />
        <CardContent className={classes.postDetails}>
          <Typography gutterBottom variant="h5" component="h2">
            {title}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {!showDetails ? summary : content}
          </Typography>
        </CardContent>
        {showDetails && (
          <CardContent className={classes.postFooter}>
            <Typography gutterBottom component="p">
              {published}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              {author}
            </Typography>
          </CardContent>
        )}
      </CardActionArea>
      {user && (
        <CardActions>
          <Button onClick={handleEdit} size="small" color="primary">
            Edit
          </Button>
          <Button onClick={handleDelete} size="small" color="primary">
            Delete
          </Button>
        </CardActions>
      )}
    </Card>
  );
}
