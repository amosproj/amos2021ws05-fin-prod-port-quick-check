import React from 'react';
import { Flex, useColorModeValue } from '@chakra-ui/react';

export default function Card({ layerStyle, children, ...rest }) {
  const bg = useColorModeValue('gray.50', 'gray.700');
  const barColor = useColorModeValue('primary', 'secondary');

  // use property gridGap={some_int} with have gap between children
  // use justifyContent property to align items left, right, center, spaced out, spaced evenly
  //      (check out https://medium.com/@lainakarosic/getting-started-with-css-flexbox-basics-58d875a574ce)
  return (
    <Flex
      bg={bg}
      layerStyle={layerStyle || 'card'}
      borderColor={layerStyle === 'card_bar' ? barColor : 'transparent'}
      align="center"
      {...rest}
    >
      {children}
    </Flex>
  );
}
