import React from 'react';
import ReactDOM from 'react-dom';
import { ChakraProvider } from "@chakra-ui/react"
import Login from './pages/Login'
import {
  BrowserRouter as Router,
  Route, Routes,
  Link
 } from 'react-router-dom'


 
ReactDOM.render(
  <React.StrictMode>
    <ChakraProvider>

      <Router>
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
