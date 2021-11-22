import React from 'react';
import {
  Button,
  Link,
  List,
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

function MemberRow(prop) {
  return (
    <React.Fragment>
      <Tr>
        <Center>
          <Td>
            <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px" bg="blue.500" p={3}>
              <Text color={'gray.100'} fontWeight={800} fontSize={'sm'} letterSpacing={1.1}>
                {prop.name}
              </Text>
            </Box>
          </Td>

          <Td isNumeric>
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
          <Td isNumeric>
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
        </Center>
      </Tr>
    </React.Fragment>
  );
}

export default function MemberCard(props) {
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
          <Text color={'gray.100'}>
            <Table variant="simple" size="sm">
              <Thead>
                <Tr>
                  <Th>Name</Th>
                  <Th isNumeric>Role</Th>
                  <Th>Remove</Th>
                </Tr>
              </Thead>
              <Tbody>
                <List spacing={3} maxW={800} mx={2}>
                  {props.Members.map((member) => (
                    <MemberRow name={member.Name} role={member.role}></MemberRow>
                  ))}
                </List>
              </Tbody>
              <Tfoot></Tfoot>
            </Table>
          </Text>
        </Stack>
      </Box>
    </Center>
  );
}
