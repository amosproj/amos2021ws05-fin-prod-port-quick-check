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
import { Progress } from "@chakra-ui/react"
import { IconButton } from '@chakra-ui/react';
import BaseCard from './BaseCard.jsx';

function ProjectArea(prop) {
  return (
      <Tr>
          <Td>
            <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px"  bg="green.500" p={3}>
              <Text color={'gray.100'} fontWeight={800} fontSize={'sm'} letterSpacing={1.1}>
                {prop.name}
              </Text>
            </Box>
          </Td>

          <Td>
            <Box color="white" boxShadow={'2xl'} rounded={'md'} w="200px"  bg="blue.500" p={3}>
              <Text
                color={'gray.100'}
                textTransform={'uppercase'}
                fontWeight={700}
                fontSize={'sm'}
                letterSpacing={1.1}
              >
            <Progress  colorScheme="pink" value={prop.percent} />
              </Text>
            </Box>
          </Td>
          <Td>
            <Link to="../projects">
              <Button
                size="lg"
                color="green.900"
                boxShadow={'2xl'}
                rounded={'md'}
                w="100px"
                bg="green.400"
                p={3}
              >
                Open
              </Button>
            </Link>
          </Td>
      </Tr>
  );
}

export default function ProjectAreaCard(props) {
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
          Product Areas
        </Text>
          <Table variant="simple" size="sm">
            <Thead>
              <Tr>
                <Th>Name</Th>
                <Th>Progress</Th>
                <Th>Files</Th>

              </Tr>
            </Thead>
            <Tbody>
                {props.areas.map((area) => (
                  <ProjectArea name={area.type} role={area.role} percent={area.percent}></ProjectArea>
                ))}
            </Tbody>
            <Tfoot></Tfoot>
          </Table>
      </Stack>
    </BaseCard>
);
}
