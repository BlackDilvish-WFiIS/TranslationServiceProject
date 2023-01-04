import { createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import combineReducer from "./reducers/combineReducer";

const store = createStore(combineReducer);

export default store;
