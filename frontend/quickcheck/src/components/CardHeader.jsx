import { Heading } from '@chakra-ui/react';
import { React } from 'react';

export default function CardHeader({ text, ...rest }) {
  return (
    <Heading
      {...rest}
      size="md"
      align="center"
      letterSpacing={1.5}
      fontWeight={800}
      color="gray.500"
      py={2}
    >
      {text}
    </Heading>
  );
}
