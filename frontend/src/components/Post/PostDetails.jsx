import React from "react";
import { makeStyles, Theme, createStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Post from "../Post/Post";
import clsx from "clsx";
import { fetchApi } from "../../utils";
import { useParams } from "react-router";

const useStyles = makeStyles((theme: Theme) =>
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
      margin: "5",
      height: "inherit",
    },
    column: {
      height: "80vh",
      overflowY: "auto",
      marginTop: theme.spacing(3),
      marginBottom: theme.spacing(3),
    },
    title: {
      "text-align": "center",
    },
    flexSpacer: {
      "flex-grow": 1,
    },
    buttons: {
      display: "flex",
      flexDirection: "row",
      justifyContent: "space-between",
    },
  })
);

const fetchPostDetails = async (postTitle) => {
  const response = await fetchApi({
    api: `posts/details/${postTitle}`,
    options: {
      method: "GET",
    },
  });
  if (response.ok) {
    return response.json();
  }
};

export default function PostDetails() {
  const classes = useStyles();
  const { postTitle } = useParams();

  const [postDetails, setPostDetails] = React.useState([]);
  React.useEffect(() => {
    const getPostDetails = async () => {
      const postDetails = await fetchPostDetails(postTitle);
      setPostDetails(postDetails);
    };
    getPostDetails();
  }, [postTitle]);

  return (
    <>
      <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
        <div className={classes.posts}>
          <Post post={postDetails} showDetails />
        </div>
      </Container>
    </>
  );
}
