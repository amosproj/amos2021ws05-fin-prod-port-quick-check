import React from 'react';

import {
  Flex,
  Heading,
  Spacer,
  Avatar,
  VStack,
  useColorModeValue,
  IconButton,
  Link,
} from '@chakra-ui/react';
import { ColorModeSwitcher } from './ColorModeSwitcher';
import { ChevronLeftIcon } from '@chakra-ui/icons';

function Menubar({ title, backref }) {
  const bgGradient = useColorModeValue(
    'linear(to-r, bp.blue, bp.lblue)',
    'linear(to-r, bp.blue, bp.purple)'
  );
  // bgGradient="linear(to-l, #7928CA, #FF0080)"

  return (
    <Flex bgGradient={bgGradient} w="full" h={20} justifyContent="center">
      <Flex align="center" basis={1000} px={5}>
        {backref ? (
          <Link href={backref}>
            <IconButton size="lg" icon={<ChevronLeftIcon w={6} h={6} />} />
          </Link>
        ) : undefined}
        <Spacer />
        <Heading size="xl" align="center" color="white">
          {title}
        </Heading>
        <Spacer />
        <ColorModeSwitcher mr={4} />
        <Avatar />
      </Flex>
    </Flex>
  );
}

export default function Page({ title, backref, children }) {
  const bgColor = useColorModeValue('gray.200', 'gray.900');
  return (
    <Flex w="full" direction="column" align="center" bg={bgColor}>
      <Menubar title={title} backref={backref} />
      <VStack
        minH="85vh"
        maxW="1200px"
        w={{ base: '100%', sm: '95%', lg: '80%' }}
        spacing={10}
        my={10}
        alignContent="center"
      >
        {children}
      </VStack>
    </Flex>
  );
}
