import React from 'react';
import { Box, Wrap } from '@chakra-ui/react';

export default function Card(props) {
  return (
    <Box
      w="full"
      minW="15em"
      bg="gray.700"
      p={3}
      mb={6}
      align="center"
      rounded="md"
      boxShadow="md"
      overflow="hidden"
      _hover={{ boxShadow: '2xl' }}
    >
      <Box h={2} bg={props.barColor} mt={-3} mx={-3} mb={props.barColor ? 3 : 0} pos={'relative'} />
      <Wrap
        wrap="wrap"
        direction={{ base: 'column', sm: 'row' }}
        spacing={{ base: 2, md: 4 }}
        px={4}
        py={2}
        align="center"
        justify="center"
      >
        {props.children}
      </Wrap>
    </Box>
  );
}
