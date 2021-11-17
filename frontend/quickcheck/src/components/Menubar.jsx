import { Heading, Avatar, Flex, Spacer } from '@chakra-ui/react'
import React from 'react'

function Menubar(props) {
  return (
    <Flex bg='gray.700' w='full' h={20} justifyContent='center' as='nav'>

      <Flex align='center' basis={1000} px={5} justifyContent='space-between' alignItems='center'>
        <Heading size="xl">{props.title}</Heading>
        <Spacer />
        <Avatar ></Avatar>
      </Flex>
    </Flex>
    )
  }

  export default Menubar
