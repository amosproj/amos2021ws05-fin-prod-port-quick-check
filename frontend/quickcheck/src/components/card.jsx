import { Flex, Spacer } from '@chakra-ui/layout'
import React from 'react'

function Card(props) {

    const greeting = 'Hello Function Component!';

    return <h1>{props.title}</h1>;

}

export default Card
