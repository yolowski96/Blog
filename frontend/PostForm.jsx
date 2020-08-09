import React from "react";
import clsx from "clsx";
import { makeStyles, createStyles } from "@material-ui/core/styles";
import { TextField, Select } from "formik-material-ui";
import LinearProgress from "@material-ui/core/LinearProgress";
import Container from "@material-ui/core/Container";
import Button from "@material-ui/core/Button";
import { Formik, Form, Field } from "formik";
import { useHistory, useParams } from "react-router-dom";
import { fetchApi } from "../../utils";
import * as Yup from "yup";
import MenuItem from "@material-ui/core/MenuItem";

const categories = ["Spring", "Summer", "Autumn", "Winter"];

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

const fetchPost = async (postTitle) => {
  const response = await fetchApi({
    api: `posts/edit/${postTitle}`,
    options: {
      method: "GET",
    },
  });
  if (response.ok) {
    return response.json();
  }
};

const PostSchema = Yup.object().shape({
  title: Yup.string()
    .min(2, "Title must be longer than 2 characters!")
    .max(40, "Title must be shorter than 40 characters!")
    .required("Required"),
  summary: Yup.string()
    .min(2, "Summary must be longer than 2 characters!")
    .max(50, "Summary must be shorter than 50 characters!")
    .required("Required"),
  content: Yup.string()
    .min(2, "Content must be longer than 2 characters!")
    .required("Required"),
  file: Yup.mixed().required("A file is required"),
  category: Yup.string().required("Required"),
});

const PostForm = () => {
  const classes = useStyles(undefined);
  const [post, setPost] = React.useState();
  const { title } = useParams();
  const history = useHistory();
  const initialValues = post
    ? { ...post, category: post.category.name }
    : {
        title: "",
        summary: "",
        content: "",
        category: "",
        url: "",
      };

  React.useEffect(() => {
    const getPostDetails = async () => {
      const post = await fetchPost(title);
      setPost(post);
    };
    getPostDetails();
  }, [title]);

  const createPost = async (values) => {
    const response = await fetchApi({
      api: post ? `posts/edit/${title}` : "posts/add",
      values,
    });
    if (response.ok) {
      history.push("/posts");
    }
  };

  const uploadImage = async (file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", "t8rg3ihv");
    const options = {
      method: "POST",
      body: formData,
    };

    const response = await fetch(
      "https://api.cloudinary.com/v1_1/goranblog/image/upload",
      options
    );
    const responseJson = await response.json();
    return responseJson;
  };

  const pad = (value) => (value < 10 ? "0" + value : value);

  const parseDate = () => {
    const date = new Date();

    return `${date.getFullYear()}-${pad(date.getMonth())}-${pad(
      date.getDate()
    )} ${pad(date.getHours())}:${pad(date.getMinutes())}`;
  };

  return (
    <Container maxWidth="xs" className={clsx(classes.column, classes.root)}>
      <Formik
        enableReinitialize
        initialValues={initialValues}
        validationSchema={PostSchema}
        onSubmit={async (values, { setSubmitting }) => {
          const response = await uploadImage(values.file);
          if (response.error) {
            //TODO:
          } else {
            const { url } = response;
            const isSuccessful = await createPost({
              ...values,
              url,
              published: parseDate(),
              // author
            });
            setSubmitting(false);
          }
        }}
      >
        {({
          submitForm,
          isSubmitting,
          handleSubmit,
          setFieldValue,
          errors,
          touched,
        }) => (
          <Form className={classes.margin}>
            <Field component={TextField} fullWidth name="title" label="Title"/>
            <br />
            <Field
              component={TextField}
              fullWidth
              name="summary"
              label="Summary"
            />
            <br />
            <Field
              component={TextField}
              fullWidth
              label="Content"
              name="content"
            />
            <Field
              component={Select}
              fullWidth
              placeholder="Category"
              name="category"
              className={classes.marginTop}
            >
              {categories.map((category) => (
                <MenuItem key={category} value={category}>
                  {category}
                </MenuItem>
              ))}
            </Field>
            {errors.category ? (
              <p style={{ color: "#f44336", fontSize: "0.75rem" }}>
                {errors.category}
              </p>
            ) : null}
            {post ? (
              <>
                <Field
                  component={TextField}
                  fullWidth
                  disabled
                  label="Uploaded file"
                  name="url"
                />
                <br />
                <input
                  className={classes.fileInput}
                  id="file"
                  name="file"
                  type="file"
                  onChange={(event) => {
                    setFieldValue("file", event.currentTarget.files[0]);
                  }}
                />
                {errors.file ? (
                  <p style={{ color: "#f44336", fontSize: "0.75rem" }}>
                    {errors.file}
                  </p>
                ) : null}
              </>
            ) : (
              <>
                <input
                  className={classes.fileInput}
                  id="file"
                  name="file"
                  type="file"
                  onChange={(event) => {
                    setFieldValue("file", event.currentTarget.files[0]);
                  }}
                />
                {errors.file ? (
                  <p style={{ color: "#f44336", fontSize: "0.75rem" }}>
                    {errors.file}
                  </p>
                ) : null}
              </>
            )}

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

export default PostForm;
