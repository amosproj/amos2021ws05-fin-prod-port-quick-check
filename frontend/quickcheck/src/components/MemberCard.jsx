import React from 'react';
import {
  Button,
  Link,
  Table,
  Thead,
  Th,
  Tr,
  Td,
  Tfoot,
  Tbody,
  Box,
  Center,
  Text,
  Stack,
} from '@chakra-ui/react';
import { IconButton } from '@chakra-ui/react';
import BaseCard from './BaseCard.jsx';

function MemberRow(prop) {
  return (
      <Tr>
          <Td>
            <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="blue.500" p={3}>
              <Text color={'gray.100'} fontWeight={800} fontSize={'sm'} letterSpacing={1.1}>
                {prop.name}
              </Text>
            </Box>
          </Td>

          <Td>
            <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="green.500" p={3}>
              <Text
                color={'gray.100'}
                textTransform={'uppercase'}
                fontWeight={700}
                fontSize={'sm'}
                letterSpacing={1.1}
              >
                {prop.role}
              </Text>
            </Box>
          </Td>
          <Td>
            <Link to="../projects">
              <Button
                size="lg"
                color="red.900"
                boxShadow={'2xl'}
                rounded={'md'}
                w="100px"
                bg="red.400"
                p={3}
              >
                REMOVE
              </Button>
            </Link>
          </Td>
      </Tr>
  );
}

export default function MemberCard(props) {
  return (
      <BaseCard barColor="teal.500" >
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
          <Table variant="simple" size="sm">
            <Thead>
              <Tr>
                <Th>Name</Th>
                <Th>Role</Th>
                <Th>Remove</Th>
              </Tr>
            </Thead>
            <Tbody>
                {props.Members.map((member) => (
                  <MemberRow name={member.Name} role={member.role}></MemberRow>
                ))}
            </Tbody>
            <Tfoot></Tfoot>
          </Table>
      </Stack>
    </BaseCard>
);


}
