import React from 'react';
import ReactDOM from 'react-dom';
import { ChakraProvider } from "@chakra-ui/react"
import Login from './pages/Login'



ReactDOM.render(
  <React.StrictMode>
    <ChakraProvider>
      <Login/>
    </ChakraProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
