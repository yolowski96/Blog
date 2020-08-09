import React from "react";
import clsx from "clsx";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import { TextField } from "formik-material-ui";
import LinearProgress from "@material-ui/core/LinearProgress";
import Container from "@material-ui/core/Container";
import Button from "@material-ui/core/Button";
import { Formik, Form, Field } from "formik";
import { useHistory } from "react-router-dom";
import { fetchApi } from "../../utils";
import * as Yup from "yup";

const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      marginTop: theme.spacing(3),
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

const SignupSchema = Yup.object().shape({
  firstName: Yup.string()
    .min(2, "First name must be longer than 2 characters!")
    .max(20, "First name must be shorter than 20 characters!")
    .required("Required"),
  lastName: Yup.string()
    .min(2, "Last name must be longer than 2 characters!")
    .max(20, "Last name must be shorter than 20 characters!")
    .required("Required"),
  username: Yup.string()
    .min(2, "User name must be longer than 2 characters!")
    .max(20, "User name must be shorter than 20 characters!")
    .required("Required"),
  password: Yup.string()
    .min(3, "Password must be longer than 3 characters!")
    .max(10, "Password must be shorter than 10 characters!")
    .required("Required"),
  confirmPassword: Yup.string().oneOf(
    [Yup.ref("password"), null],
    "Passwords must match"
  ),
  email: Yup.string().email("Invalid email").required("Required"),
});

const Register = () => {
  const classes = useStyles(undefined);
  const history = useHistory();

  const register = async (values) => {
    const response = await fetchApi({ api: "register", values });
    if (response.status === 201) {
      history.push("/login");
    }
  };

  return (
    <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
      <Formik
        enableReinitialize
        initialValues={{
          firstName: "",
          lastName: "",
          username: "",
          password: "",
          confirmPassword: "",
          email: "",
        }}
        validationSchema={SignupSchema}
        onSubmit={async (values, { setSubmitting }) => {
          await register(values);
          setSubmitting(false);
        }}
      >
        {({ submitForm, isSubmitting, handleSubmit }) => (
          <Form className={classes.margin}>
            <Field
              component={TextField}
              fullWidth
              name="firstName"
              label="First name"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              name="lastName"
              label="Last name"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              name="username"
              label="Username"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              type="password"
              label="Password"
              name="password"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              type="password"
              label="Confirm password"
              name="confirmPassword"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              name="email"
              type="email"
              label="Email"
            />
            <br />
            {isSubmitting && <LinearProgress />}
            <br />
            <Button
              variant="contained"
              color="primary"
              fullWidth
              disabled={isSubmitting}
              type="submit"
              //   onClick={handleSubmit}
            >
              Submit
            </Button>
          </Form>
        )}
      </Formik>
    </Container>
  );
};

export default Register;
