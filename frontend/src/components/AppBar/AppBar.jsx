import React from "react";
import { createStyles, makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import { Link, useHistory } from "react-router-dom";
import { useSelector } from "react-redux";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Add from "@material-ui/icons/Add";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import { useDispatch } from "react-redux";
import { removeUser } from "../../store";
import { fetchApi } from "../../utils";

const categories = ["Spring", "Summer", "Autumn", "Winter"];

const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    title: {
      flexGrow: ({ user }) => (!user ? 1 : 0.15),
    },
    categories: {
      flexGrow: 1,
      display: "flex",
      alignItems: "center",
      justifyContent: "space-evenly",
    },
  })
);

export default function BlogAppBar() {
  const user = useSelector((state) => state.username);
  const authorities = useSelector((state) => state.authorities);
  const classes = useStyles({ user });
  const history = useHistory();
  const dispatch = useDispatch();
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleTravelTips = () => {
    history.push("/travel-tips");
  };

  const handleAddTravelTips = () => {
    history.push("/travel-tips/add");
  };

  const handleLogout = async () => {
    const response = await fetchApi({
      api: "logout",
    });

    if (response.ok) {
      dispatch(removeUser());
      localStorage.removeItem("user");
      history.push("/login");
    }
  };

  const handleAddNewPost = () => {
    history.push("/posts/add");
  };

  const handleMyPosts = () => {
    history.push(`/posts/user/${user}`);
  };

  const handleViewAllUsers = () => {
    history.push(`/users/all/`);
  };

  const isAdminOrRoot = authorities ?
    Boolean(
      authorities.find(
        ({ authority }) =>
          authority === "ROLE_ROOT" || authority === "ROLE_ADMIN"
      )
    ) : false;

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <div className={classes.title}>
            <Typography variant="h6">Travel Blog</Typography>
          </div>
          {user ? (
            <>
              <div className={classes.categories}>
                {categories.map((category) => {
                  const to = `/posts/category/${category.toLowerCase()}`;
                  return (
                    <Button
                      key={category}
                      component={Link}
                      to={to}
                      color="inherit"
                    >
                      {category}
                    </Button>
                  );
                })}
              </div>
              <div>
                <IconButton
                  aria-label="add new post"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleAddNewPost}
                  color="inherit"
                >
                  <Add />
                </IconButton>
                <IconButton
                  aria-label="account of current user"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleMenu}
                  color="inherit"
                >
                  <AccountCircle />
                </IconButton>
              </div>
            </>
          ) : (
            <div>
              <Button component={Link} to="/login" color="inherit">
                Login
              </Button>
              <Button component={Link} to="/register" color="inherit">
                Register
              </Button>
            </div>
          )}
          <Menu
            id="menu-appbar"
            anchorEl={anchorEl}
            anchorOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            keepMounted
            transformOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            open={open}
            onClose={handleClose}
          ><MenuItem onClick={handleTravelTips}>Travel tips</MenuItem>
            {isAdminOrRoot && (
                <MenuItem onClick={handleAddTravelTips}>Add travel tips</MenuItem>
            )}
            <MenuItem onClick={handleMyPosts}>My posts</MenuItem>
            {isAdminOrRoot && (
              <MenuItem onClick={handleViewAllUsers}>View All Users</MenuItem>
            )}
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>
    </div>
  );
}
