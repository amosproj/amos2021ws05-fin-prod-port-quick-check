import { Heading, Avatar, Flex, Spacer } from '@chakra-ui/react'
import React from 'react'

function Menubar(props) {
  return (
    <Flex bg='gray.700' w='full' h={20} justifyContent='center'>

      <Flex align='center' basis={1000} px={5}>
        <Heading size="xl">{props.title}</Heading>
        <Spacer />
        <Avatar ></Avatar>
      </Flex>
    </Flex>
    )
  }

  export default Menubar
