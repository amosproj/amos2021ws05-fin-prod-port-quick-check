import React from 'react';
import { Button, Heading, VStack, Text, Flex, HStack, Spacer, Link } from '@chakra-ui/react';
import { useState,  useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { score } from '../../utils/const';
import { useStoreActions, useStoreState } from 'easy-peasy';

import Card from '../../components/Card';
import Page from '../../components/Page';
import Figure from './Figure';

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

const { projectID} = useParams();

const setResultsData = useStoreActions((actions) => actions.resultList.set);
const fetchResults = useStoreActions((actions) => actions.resultList.fetch);
const results = useStoreState((state) => state.resultList.results);


 const [sources, setSources] = useState(mock);
 useEffect(() => {
   //initRatingsData();
   fetchResults(projectID);
   //setRatingsData(mockRatings.ratings);
 }, []);
console.log( {results})
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
          <Link href={'/projects'}>
            <Button> Back</Button>
          </Link>
        </HStack>
      </Page>
    </div>
  );
}
