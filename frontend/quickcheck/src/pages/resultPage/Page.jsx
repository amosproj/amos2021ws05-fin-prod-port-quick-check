import React from 'react';
import Card from '../../components/Card';
import Page from '../../components/Page';
import PieChartGraph from './PieChart';
import { Link } from 'react-router-dom';

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
const data = {
  labels: ['source 1', 'source 2', 'source 3', 'source 4', 'source 5'],

  datasets: [
    {
      label: 'Values',
      data: [25, 2, 25, 5, 23],
      borderColor: ['rgba(255,255,255)'],
      borderWidth: 3,
      backgroundColor: [
        'rgba(147,213,34)',
        'rgba(41,213,255)',
        'rgba(82,8,129)',
        'rgba(255,72,166)',
        'rgba(108,3,168)',
      ],
      pointBackgroundColor: 'rgba(255,206,86,0.2)',
    },
  ],
};

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
          flexDirection="row"
          w="full"
          gridGap={3}
          justifyContent="space-between"
          alignItems="stretch"
        >
          <Flex w="55%">
            <Card alignItems="center" bg="gray.600">
              <PieChartGraph data={data}></PieChartGraph>
            </Card>
          </Flex>

          <Flex flexDirection="column" w="45%" justifyContent="space-between" alignItems="stretch">
            <VStack>
              <Heading size="lg">Sources</Heading>
              {sources.map((source) => (
                <SourceRow source={source} key={source.id} />
              ))}
            </VStack>
          </Flex>
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