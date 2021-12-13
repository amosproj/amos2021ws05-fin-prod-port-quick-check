import React from 'react';
import Card from '../components/Card';
import Page from '../components/Page';
import {
  List,
  Button,
  Heading,
  VStack,
  Text,
  Flex,
  Square,
  Center,
  Box,
  HStack,
  Input,
  TextArea,
  Spacer,
} from '@chakra-ui/react';
import { useState } from 'react';
// import { Link } from 'react-router-dom';

const mock = [
  {
    name: 'Interview 1',
    author: 'Theodor Schabicki',
    id: 0,
  },
  {
    name: 'Interview 2',
    author: 'Theodor Schabicki',
    id: 1,
  },
  {
    name: 'Analysis 1',
    author: 'Theodor Schabicki',
    id: 2,
  },
  {
    name: 'Paper 1',
    author: 'Theodor Schabicki',
    id: 3,
  },
];

function SourceRow({ source, key }) {
  return (
    <Card bg={'gray.600'} layerStyle={'card_bordered'}>
      {console.log('aksjhkajshfkjash')}
      <Heading color="primary" size="md" align="center" w="40%" maxW="50%">
        {source.name}
      </Heading>
      <Spacer />
      <VStack p={2}>
        <Text fontWeight="bolder" fontSize="md">
          {source.author}
        </Text>
        <Text fontSize="small" color="gray.400">
          Author
        </Text>
      </VStack>
    </Card>
  );
}

export default function ResultPage() {
  const [sources, setSources] = useState(mock);
  return (
    <div>
      <Page title="Results">
        <Flex
          flexDirection="row"
          w="full"
          gridGap={3}
          justifyContent="space-between"
          alignItems="stretch"
        >
          <Flex w="55%">
            <Card alignItems="center" bg="gray.600">
              <Heading>Pie Chart</Heading>
            </Card>
          </Flex>
          <Flex flexDirection="column" w="45%" justifyContent="space-between" alignItems="stretch">
            <VStack>
              <Heading size="lg">Sources</Heading>
              {sources.map((source) => (
                <SourceRow source={source} key={source.ID} />
              ))}
            </VStack>
          </Flex>
        </Flex>
        <HStack>
          <Button> Export Results </Button>
          <Button> Back</Button>
        </HStack>
        <p>{/*JSON.stringify(sources)*/}</p>
      </Page>
    </div>
  );
}
