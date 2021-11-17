import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'
import { ChakraProvider } from "@chakra-ui/react"


import Login from './pages/Login'
import ProjectOverview from './pages/ProjectOverview'

ReactDOM.render(
  <React.StrictMode>
    <ChakraProvider>

      <Router>
        <Link to='/'>Home </Link>
        <Link to='/projects'>Projects </Link>
        <Link to='/login'>Login </Link>

        <Routes>
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/projects' element={<ProjectOverview/>}></Route>
        </Routes>

      </Router>
    </ChakraProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
