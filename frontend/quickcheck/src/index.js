import React from 'react';
import ReactDOM from 'react-dom';
import { ChakraProvider } from "@chakra-ui/react"
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';


import Login from './pages/Login'
import {
  BrowserRouter as Router,
  Route, Routes,
  Link
 } from 'react-router-dom'

import { NavigationBar } from './components/nav_bar';
ReactDOM.render(
  <React.StrictMode>
    <ChakraProvider>

      <Router>
       <NavigationBar />
        <Link to='/'>Home </Link>

        <Link to='/login'>Login </Link>

        <Routes>
          <Route path='/login' element={<Login/>}>

          </Route>
        </Routes>

      </Router>
    </ChakraProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
