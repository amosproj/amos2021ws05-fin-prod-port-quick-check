import React from 'react';
import {
  Table,
  Thead,
  Th,
  TableCaption,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Center,
  Heading,
  Text,
  Stack,
  Avatar,
  useColorModeValue,
} from '@chakra-ui/react';

export default function MemberCard(prop) {
  return (
    <Center py={6}>
      <Box maxW={'945px'} w={'full'} boxShadow={'2xl'} rounded={'md'} p={6} overflow={'hidden'}>
        <Box h={'20px'} bg={'blue.600'} mt={-6} mx={-6} mb={6} pos={'relative'}></Box>
        <Stack>
          <Text
            color={'green.500'}
            textTransform={'uppercase'}
            fontWeight={800}
            fontSize={'sm'}
            letterSpacing={1.1}
          >
            Members
          </Text>
          <Text color={'gray.500'}>
            <Table variant="simple">
              <Thead>
                <Tr>
                  <Th>Name</Th>
                  <Th>Role</Th>
                </Tr>
              </Thead>
              <Tbody>
                <Tr>
                  <Td>inches</Td>
                  <Td>millimetres (mm)</Td>
                </Tr>
                <Tr>
                  <Td>feet</Td>
                  <Td>centimetres (cm)</Td>
                </Tr>
                <Tr>
                  <Td>yards</Td>
                  <Td>metres (m)</Td>
                </Tr>
              </Tbody>
              <Tfoot></Tfoot>
            </Table>
          </Text>
        </Stack>
      </Box>
    </Center>
  );
}
