import React from 'react';
import { Box, Center, Heading, Text, Stack } from '@chakra-ui/react';

export default function Card_simple(prop) {
  return (
    <Center py={6}>
      <Box maxW={'945px'} w={'full'} boxShadow={'2xl'} rounded={'md'} p={6} overflow={'hidden'}>
        <Box h={'20px'} bg={'teal'} mt={-6} mx={-6} mb={6} pos={'relative'}></Box>
        <Stack>
          <Text
            color={'green.500'}
            textTransform={'uppercase'}
            fontWeight={800}
            fontSize={'sm'}
            letterSpacing={1.1}
          >
            {prop.type}
          </Text>
          <Heading fontSize={'2xl'} fontFamily={'body'}>
            {prop.title}
          </Heading>
          <Text color={'gray.500'}>{prop.description}</Text>
        </Stack>
      </Box>
    </Center>
  );
}
