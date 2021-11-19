import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";

import Login from "./pages/Login";
import ProjectOverview from "./pages/ProjectOverview";


function App() {
  return (
    <div>
      <Router>
        <Link to="/">Home </Link>
        <Link to="/projects">Projects </Link>
        <Link to="/login">Login </Link>

        <Routes>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/projects" element={<ProjectOverview />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
