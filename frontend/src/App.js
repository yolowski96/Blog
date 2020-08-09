import React from "react";
import { useSelector } from "react-redux";
import "./App.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import Login from "./components/Login/Login.jsx";
import Register from "./components/Register/Register";
import Home from "./components/Home/Home";
import Posts from "./components/Posts/Posts";
import Users from "./components/Users/Users";
import PostForm from "./components/Posts/PostForm";
import PostDetails from "./components/Post/PostDetails";
import TipForm from "./components/TravelTips/TipForm";
import TravelTips from "./components/TravelTips/TravelTips";
import Footer from "./components/Footer/Footer";

function PrivateRoute({ component: Component, authed, ...rest }) {
  return (
    <Route
      {...rest}
      render={(props) =>
        authed === true ? (
          <>
            <Home />
            <Component {...props} />
            <Footer />
          </>
        ) : (
          <Redirect
            to={{ pathname: "/login", state: { from: props.location } }}
          />
        )
      }
    />
  );
}

function App() {
  const isLoggedIn = useSelector((state) => Boolean(state.username));

  return (
    <div className={"app"}>
      <Router>
        <Switch>
          <Route exact path="/">
            <Home />
            <Footer />
          </Route>
          <Route path="/login">
            <Home />
            <Login />
            <Footer />
          </Route>
          <Route path="/register">
            <Home />
            <Register />
            <Footer />
          </Route>
          <PrivateRoute
            exact
            authed={isLoggedIn}
            path="/posts"
            component={Posts}
          />

          <PrivateRoute
            authed={isLoggedIn}
            path="/users/all"
            component={Users}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/posts/category/:category"
            component={Posts}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/posts/add"
            component={PostForm}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/posts/edit/:title"
            component={PostForm}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/posts/details/:postTitle"
            component={PostDetails}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/posts/user/:user"
            component={Posts}
          />
          <PrivateRoute
            authed={isLoggedIn}
            exact
            path="/travel-tips"
            component={TravelTips}
          />
          <PrivateRoute
            authed={isLoggedIn}
            path="/travel-tips/add"
            component={TipForm}
          />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
