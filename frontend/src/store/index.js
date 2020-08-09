import { createSlice, configureStore } from "@reduxjs/toolkit";

const initialState = {};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    init: (state) => {
      const user = JSON.parse(localStorage.getItem("user"));
      const expirationTime = 15 * 60 * 1000; // 15 minutes
      const nowInMs = new Date().getTime();
      state = initialState;
      if (user && nowInMs <= expirationTime) {
        state = user;
      }
      return state;
    },
    setUser: (state, { payload }) => (state = payload),
    removeUser: (state) => (state = initialState),
  },
});

const store = configureStore({
  reducer: userSlice.reducer,
});

const { setUser, removeUser, init } = userSlice.actions;

store.dispatch(init());

export { setUser, removeUser };
export default store;
