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
    fileInput: {
      width: "100%",
      marginTop: theme.spacing(2),
    },
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
    marginTop: {
      marginTop: theme.spacing(2),
    },
  })
);

const TipSchema = Yup.object().shape({
  title: Yup.string()
    .min(2, "Title must be longer than 2 characters!")
    .max(40, "Title must be shorter than 40 characters!")
    .required("Required"),
  description: Yup.string()
    .min(2, "Description must be longer than 2 characters!")
    .max(50, "Description must be shorter than 50 characters!")
    .required("Required"),
});

const TipForm = () => {
  const classes = useStyles();
  const history = useHistory();
  const initialValues = {
    title: "",
    description: "",
  };

  const createTravelTip = async (values) => {
    const response = await fetchApi({
      api: "tips/add",
      values,
    });
    if (response.ok) {
      history.push("/tips");
    }
  };

  return (
    <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
      <Formik
        enableReinitialize
        initialValues={initialValues}
        validationSchema={TipSchema}
        onSubmit={async (values, { setSubmitting }) => {
          await createTravelTip(values);
          setSubmitting(false);
        }}
      >
        {({ isSubmitting }) => (
          <Form className={classes.margin}>
            <Field component={TextField} fullWidth name="title" label="Title" />
            <br />
            <Field
              component={TextField}
              fullWidth
              name="description"
              label="Description"
            />
            {isSubmitting && <LinearProgress />}
            <br />
            <Button
              variant="contained"
              color="primary"
              fullWidth
              disabled={isSubmitting}
              type="submit"
            >
              Submit
            </Button>
          </Form>
        )}
      </Formik>
    </Container>
  );
};

export default TipForm;
