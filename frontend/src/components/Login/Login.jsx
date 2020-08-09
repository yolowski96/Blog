import React from "react";
import clsx from "clsx";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import IconButton from "@material-ui/core/IconButton";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import InputAdornment from "@material-ui/core/InputAdornment";
import FormControl from "@material-ui/core/FormControl";
import Visibility from "@material-ui/icons/Visibility";
import VisibilityOff from "@material-ui/icons/VisibilityOff";
import Container from "@material-ui/core/Container";
import Button from "@material-ui/core/Button";
import ErrorIcon from "@material-ui/icons/Error";
import SnackbarContent from "@material-ui/core/SnackbarContent";
import { useDispatch } from "react-redux";
import { setUser } from "../../store";
import { fetchApi } from "../../utils";
import { useSelector} from "react-redux";
import { useHistory } from "react-router-dom"

const useStyles = makeStyles((theme) =>
  createStyles({
    rootContainer: {
      height: "100vh",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
    },
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
    column: {
      display: "flex",
      "flex-direction": "column",
      marginTop: theme.spacing(3),
      marginBottom: theme.spacing(3),
    },
    title: {
      "text-align": "center",
    },
    warning: {
      display: "flex",
      "flex-direction": "row",
      textAlign: "center",
      backgroundColor: theme.palette.warning.light,
      marginTop: theme.spacing(1),
      padding: "1rem",
      color: theme.palette.warning.contrastText,
    },
    warningIcon: {
      margin: "2rem",
    },
    error: {
      marginTop: 10,
      backgroundColor: theme.palette.error.dark,
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

const LoginMessage = ({ message, classes }) => (
  <SnackbarContent
    className={classes.error}
    message={
      <span>
        <ErrorIcon />
        {message}
      </span>
    }
  />
);

const Login = ({ loginMessage }) => {
  const classes = useStyles(undefined);
  const history = useHistory();
  const [values, setValues] = React.useState({
    username: "",
    password: "",
    showPassword: false,
  });
  const dispatch = useDispatch();
  const isLoggedIn = useSelector((state) => Boolean(state.username)) ;
  React.useEffect(() =>{
      if(isLoggedIn) history.push("/posts");
  },[isLoggedIn]);
  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const loginUser = async () => {
    const response = await fetchApi({
      api: "login",
      searchParams: values,
    });

    if (response.ok) {
      const userData = await response.json();
      localStorage.setItem("user", JSON.stringify(userData));
      dispatch(setUser({ ...userData, logginTime: new Date().getTime() }));
    }
  };

  return (
    <Container
      maxWidth="sm"
      data-testid="login page"
      className={clsx(classes.column, classes.root)}
    >
      <form className={classes.column} noValidate autoComplete="on">
        <FormControl className={clsx(classes.margin, classes.textField)}>
          <InputLabel htmlFor="input-with-icon-adornment">Username</InputLabel>
          <Input
            id="input-with-icon-adornment"
            value={values.username}
            onChange={handleChange("username")}
            autoFocus
            startAdornment={
              <InputAdornment position="start">
                <AccountCircle />
              </InputAdornment>
            }
          />
        </FormControl>

        <FormControl className={clsx(classes.margin, classes.textField)}>
          <InputLabel htmlFor="adornment-password">Password</InputLabel>
          <Input
            id="adornment-password"
            autoComplete="on"
            type={values.showPassword ? "text" : "password"}
            value={values.password}
            onChange={handleChange("password")}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={handleClickShowPassword}
                  tabIndex={-1}
                >
                  {values.showPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            }
          />
        </FormControl>
        <div className={classes.buttons}>
          <Button
            color="primary"
            variant="contained"
            fullWidth
            onClick={loginUser}
          >
            Login
          </Button>
        </div>
      </form>
      {loginMessage && (
        <LoginMessage classes={classes} message={loginMessage} />
      )}
    </Container>
  );
};

export default Login;
