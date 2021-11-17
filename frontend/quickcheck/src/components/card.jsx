import { Flex, Spacer } from '@chakra-ui/layout'
import React from 'react'

function Card(props) {

    const greeting = 'Project';

    return(
    <div>
    <header className="Card">
        <p style={{"margin": "1px", "padding": "5px"}}>
          <h1>{props.title}</h1>
        </p>
        <a style={{"color": "Blue", "backgroundColor": "white"}}
          className="App-link" href={props.link} target="_blank">
          Open Project
        </a>
      </header>
</div>
     );


}

export default Card
