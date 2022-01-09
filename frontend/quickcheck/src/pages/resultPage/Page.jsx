import React from 'react';
import Card from '../../components/Card';
import Page from '../../components/Page';
import PieChartGraph from './PieChart';
import BubbleGraph from './BubbleChart';

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

function SourceRow({ source, key }) {
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

          <Card alignItems="center" bg="gray.100">
            <BubbleGraph></BubbleGraph>
          </Card>


        <Flex
          flexDirection="row"
          w="full"
          gridGap={1}
          justifyContent="space-between"
          alignItems="stretch"
        >
        <Flex
          w="50%"
        >
        <Card alignItems="center" bg="gray.600">
          <PieChartGraph data={data}></PieChartGraph>
        </Card>
        </Flex>

        <Flex
          w="50%"
        >
        <Card alignItems="center" bg="gray.600">
           <PieChartGraph data={data}></PieChartGraph>
        </Card>
        </Flex>
      </Flex>
              <Heading size="lg">Sources</Heading>
              {sources.map((source) => (
                <SourceRow source={source} key={source.ID} />
              ))}
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
