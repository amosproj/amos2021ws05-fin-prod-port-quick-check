import React from 'react';
import { Flex, useColorModeValue } from '@chakra-ui/react';

export default function Card({layerStyle, children, ...rest}) {
  const bg = useColorModeValue('gray.50', 'gray.700');
  const barColor = useColorModeValue('primary', 'secondary');

  return (
    <Flex
      {...rest}
      bg={bg}
      layerStyle={layerStyle || 'card'}
      borderColor={layerStyle === 'card_bar' ? barColor : 'transparent'}
      align="center"
    >
      {children}
    </Flex>
  );
}
