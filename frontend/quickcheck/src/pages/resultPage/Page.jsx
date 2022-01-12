import React from 'react';
import Card from '../../components/Card';
import Page from '../../components/Page';
import Figure from './Figure';
import { Button, Heading, VStack, Text, Flex, HStack, Spacer } from '@chakra-ui/react';
import { useState } from 'react';

const mock = [
  {
    name: 'Interview 1',
    author: 'Franz Kafka',
    id: 0,
  },
  {
    name: 'Interview 2',
    author: 'Heinrich Heine',
    id: 1,
  },
  {
    name: 'Analysis 1',
    author: 'Günther Netzer',
    id: 2,
  },
  {
    name: 'Paper 1',
    author: 'Olaf Scholz',
    id: 3,
  },
];

function SourceRow({ source }) {
  return (
    <Card bg={'gray.600'} layerStyle={'card_bordered'}>
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
          flexDirection="column"
          w="full"
          gridGap={1}
          justifyContent="space-between"
          alignItems="stretch"
        >
          <Figure></Figure>
          <Heading size="lg">Sources</Heading>
          {sources.map((source) => (
            <SourceRow source={source} key={source.id} />
          ))}
        </Flex>
        <HStack>
          <Button> Export Results </Button>
          <Link to={'/projects'}>
            <Button> Back</Button>
          </Link>
        </HStack>
      </Page>
    </div>
  );
}
