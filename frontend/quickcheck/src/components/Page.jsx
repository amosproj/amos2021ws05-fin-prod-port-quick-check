import React from 'react';
import { Flex, Heading, Spacer, Avatar, VStack } from '@chakra-ui/react';

function Menubar({ title }) {
  return (
    <Flex bg="gray.700" w="full" h={20} justifyContent="center">
      <Flex align="center" basis={1000} px={5}>
        <div />
        <Heading size="xl" w="full" align="center">
          {title}
        </Heading>
        <Spacer />
        <Avatar></Avatar>
      </Flex>
    </Flex>
  );
}

export default function Page(props) {
  return (
    <Flex w="full" direction="column" align="center">
      <Menubar title={props.title} />
      <VStack
        maxW="1000px"
        w="90%"
        spacing={10}
        my={10}
        justifyContent="center"
        alignContent="center"
      >
        {props.children}
      </VStack>
    </Flex>
  );
}