import React from 'react';
import { Box, Center, Heading, Text, Stack, Avatar, useColorModeValue } from '@chakra-ui/react';

export default function MemberCard(prop) {
  return (
    <Center py={6}>
      <Box maxW={'945px'} w={'full'} boxShadow={'2xl'} rounded={'md'} p={6} overflow={'hidden'}>
        <Box h={'20px'} bg={'red'} mt={-6} mx={-6} mb={6} pos={'relative'}>
          Members
        </Box>
        <Stack>
          <Text
            color={'green.500'}
            textTransform={'uppercase'}
            fontWeight={800}
            fontSize={'sm'}
            letterSpacing={1.1}
          >
            ype: "Project", title: 'Volksbank berlin brandenburg', role: 'Consultant', description:
            "Project with Volksbank. Project with Volksbank. Project with Volk
          </Text>
        </Stack>
      </Box>
    </Center>
  );
}
