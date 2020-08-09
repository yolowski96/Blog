import React from "react";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Post from "../Post/Post";
import clsx from "clsx";
import { fetchApi } from "../../utils";
import { useParams } from "react-router";

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
    },
    buttons: {
      display: "flex",
      flexDirection: "row",
      justifyContent: "space-between",
    },
  })
);

const fetchPosts = async (filter) => {
  let api;
  const { key, value } = filter;

  switch (key) {
    case "user":
      api = `posts/${value}`;
      break;
    case "category":
      api = `posts/category/${value}`;
      break;
    default:
      api = "posts";
  }

  const response = await fetchApi({
    api,
    options: {
      method: "GET",
    },
  });
  if (response.ok) {
    return response.json();
  }
};

const getFilter = (user, category) => {
  if (user) return { key: "user", value: user };
  if (category) return { key: "category", value: category };
  return {};
};

export default function Posts() {
  const classes = useStyles();
  const { user, category } = useParams();
  const [posts, setPosts] = React.useState([]);

  React.useEffect(() => {
    const getPosts = async () => {
      const posts = await fetchPosts(getFilter(user, category));
      setPosts(posts);
    };
    getPosts();
  }, [user, category]);
  return (
    <>
      <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
        <div className={classes.posts}>
          {posts && posts.map((post) => <Post post={post} user={user} />)}
        </div>
      </Container>
    </>
  );
}
