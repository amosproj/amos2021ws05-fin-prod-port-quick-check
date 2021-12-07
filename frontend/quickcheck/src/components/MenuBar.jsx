import React from 'react';
import { Flex, Heading, Spacer, Avatar, useColorModeValue } from '@chakra-ui/react';

//components
import { ColorModeSwitcher } from './ColorModeSwitcher';

export default function MenuBar({ title }) {
  const bgGradient = useColorModeValue(
    'linear(to-r, bp.blue, bp.lblue)',
    'linear(to-r, bp.blue, bp.purple)'
  );
  // bgGradient="linear(to-l, #7928CA, #FF0080)"

  return (
    <Flex bgGradient={bgGradient} w="full" h={20} justifyContent="center">
      <Flex align="center" basis={1000} px={5}>
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
