import { Flex, Spacer } from '@chakra-ui/layout'
import React from 'react'
import { ChakraComponent, Box, BoxProps } from "@chakra-ui/react"
import { HStack, Heading, Input, Button, FormControl, FormLabel} from "@chakra-ui/react"


function Card(props) {

    const greeting = 'Project';

    return(
    <Flex bg='black' w='half' align='center' p={3} justifyContent={'space-between'}>

    <header className="Card">
    <HStack>
        <p style={{"margin": "1px", "padding": "5px"}}>
          <heading>{props.title}</heading>
        </p>
        <a style={{"color": "white", "backgroundColor": "blue"}}
          className="App-link" href={props.link} target="_blank">
          Open Project
        </a>
        </HStack>
      </header>

</Flex>
);
}

export default Card
