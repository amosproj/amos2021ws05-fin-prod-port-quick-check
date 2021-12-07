import React from 'react';

import { Flex, VStack, useColorModeValue } from '@chakra-ui/react';

//components
import MenuBar from "./MenuBar.jsx";

export default function Page(props) {
  const bgColor = useColorModeValue('gray.200', 'gray.900');
  return (
    <Flex w="full" direction="column" align="center" bg={bgColor}>
      <MenuBar title={props.title} />
      <VStack
        minH="85vh"
        maxW="1200px"
        w={{ base: '100%', sm: '95%', lg: '80%' }}
        spacing={10}
        my={10}
        alignContent="center"
      >
        {props.children}
      </VStack>
    </Flex>
  );
}
