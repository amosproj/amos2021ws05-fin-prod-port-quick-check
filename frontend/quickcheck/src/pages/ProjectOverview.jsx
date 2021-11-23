import React, { useState, useEffect } from 'react';
import { Component } from 'react';

import {
    SimpleGrid,
    Heading,
    CardLabel,
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
  Hstack,

} from '@chakra-ui/react';
import Menubar from '../components/Menubar';
import BaseCard from '../components/BaseCard';
import { VStack, List, Button } from '@chakra-ui/react';

const mocks = {
  projects: [
    {
      title: 'Volksbank berlin brandenburg',
      role: 'Consultant',
    },
    {
      title: 'ING',
      role: 'Project Manager',
    },
    {
      title: 'Sparkasse Berlin',
      role: 'Consultant',
    },
  ],
};

function ProjectCard(props) {
  return (
    <BaseCard
    barColor="blue.500"
    >

    <Stack>
      <Text
        color={'green.500'}
        textTransform={'uppercase'}
        fontWeight={800}
        fontSize={'sm'}
        letterSpacing={1.1}
      >
        {props.type}
      </Text>
      <Heading fontSize={'2xl'} fontFamily={'body'}>
        {props.title}
      </Heading>
      <Text color={'gray.500'}>{props.description}</Text>
          </Stack>
      <SimpleGrid
        columns={{ base: 1, sm: 2 }}
        rounded="lg"
        align="right"
        alignItems="center"
        px={3}
      >

      

        <Box align="right">
          {props.buttonLabel ? (
            <Link to="/ManageProject">
              <Button bg="teal.400" align="center" _hover={{ bg: 'teal.500' }} w={24}>
                {props.buttonLabel}
              </Button>
            </Link>
          ) : (
            <div />
          )}
        </Box>

      </SimpleGrid>


</BaseCard>
  );
}
export default function ProjectOverview() {
  const [data, setData] = useState({ projects: [] });

  useEffect(() => {
    fetch('https://randomuser.me/api/')
      .then((response) => response.json())
      .then((data) => setData(data));
  }, []);

  return (
    <div>
      <Menubar mb={5} title="Project Overview"></Menubar>
      <VStack justifyContent="center" spacing={10} mt={5}>
        <List spacing={3} maxW={800} mx={2}>
          {mocks.projects.map((project) => (
            <ProjectCard title={project.title}
            buttonLabel="open" type="Project"
            labels={[['Role', project.role]]}></ProjectCard>
          ))}
        </List>

        <Button size="lg">Add new</Button>
      </VStack>
    </div>
  );
}
