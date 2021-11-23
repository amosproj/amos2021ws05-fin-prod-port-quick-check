import React from 'react';
import { Box, Center } from '@chakra-ui/react';

export default function BaseCard(props) {
  return (
    <Center py={6}>
      <Box maxW={'945px'} w={'full'} boxShadow={'2xl'} rounded={'md'} p={6} overflow={'hidden'}>
        <Box h={'20px'} bg={props.barColor} mt={-6} mx={-6} mb={6} pos={'relative'}></Box>
        <Center>{props.children}</Center>
      </Box>
    </Center>
  );
}
