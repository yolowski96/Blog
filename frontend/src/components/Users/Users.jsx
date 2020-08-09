import React from "react";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import User from "./User";
import clsx from "clsx";
import { fetchApi } from "../../utils";

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

const fetchUsers = async () => {
  const response = await fetchApi({
    api: "all",
    options: {
      method: "GET",
    },
  });
  if (response.ok) {
    return response.json();
  }
};

export default function Users() {
  const classes = useStyles();
  const [users, setUsers] = React.useState([
    {
      username: "goran",
      authorities: [
        {
          id: "3527f52d-6452-4a23-92a3-1729dddd6150",
          authority: "ROLE_USER",
        },
      ],
    },
  ]);

  React.useEffect(() => {
    const getUsers = async () => {
      const users = await fetchUsers();
      setUsers(users);
    };
    getUsers();
  }, []);
  return (
    <>
      <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
        <div className={classes.posts}>
          {users && users.map((user) => <User user={user} />)}
        </div>
      </Container>
    </>
  );
}
