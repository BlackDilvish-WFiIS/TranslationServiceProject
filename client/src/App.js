import "./App.css";
import { Provider } from "react-redux";
import store from "./store";
import Layout from "./components/Layout/Layout";

function App() {
  return (
    <Provider store={store}>
      <div className="app-container">
        <Layout />
      </div>
    </Provider>
  );
}

export default App;
