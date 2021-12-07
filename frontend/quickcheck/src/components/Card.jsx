import React from 'react';
import { Box, Wrap, useColorModeValue } from '@chakra-ui/react';

export default function Card(props) {

  const bg = useColorModeValue('gray.50', 'gray.700')

  return (
    <Box
      bg={bg}
      layerStyle={ props.layerStyle || "card"}
      w={props.w || 'full'}
      minW="12em"
      maxW={props.maxW}
      // bg={props.bg || bgColor}
      p={3}
      mb={6}
      align="center"
      rounded={props.rounded}
      overflow="hidden"
    >
      <Box h={2} bg={props.barColor} mt={-3} mx={-3} mb={props.barColor ? 3 : 0} pos={'relative'} />
      <Wrap
        wrap="wrap"
        direction={props.direction || 'row'}
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
