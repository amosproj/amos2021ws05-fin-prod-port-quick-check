import React from 'react';
import {
  Button,
  Link,
  Table,
  Thead,
  Th,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Center,
  Text,
  Stack,
} from '@chakra-ui/react';
import { IconButton } from '@chakra-ui/react';




export default function BaseCard(props) {
  return (
      <Center py={6}>
        <Box maxW={'945px'} w={'full'} boxShadow={'2xl'} rounded={'md'} p={6} overflow={'hidden'}>
          <Box h={'20px'} bg={props.barColor} mt={-6} mx={-6} mb={6} pos={'relative'}></Box>
          <Center>
          
          {props.children}
          </Center>
        </Box>
      </Center>
    );
  }
