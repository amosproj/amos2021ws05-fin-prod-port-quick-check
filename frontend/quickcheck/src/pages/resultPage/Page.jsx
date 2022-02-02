import React from 'react';
import { Button, Heading, VStack, Text, Flex, HStack, Spacer, Link } from '@chakra-ui/react';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { score } from '../../utils/const';
import { useStoreActions, useStoreState } from 'easy-peasy';

import Card from '../../components/Card';
import Page from '../../components/Page';
import Figure from './Figure';
import { colors } from './Figure';
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
    <Card layerStyle={'card_bordered'}>
      <Flex direction="row" justifyContent={'space-between'} w="full" alignItems={'center'}>
        <Heading color="primary" size="md" align="center" maxW="50%">
          {source.name}
        </Heading>
        <Flex direction={'column'} w="10%">
          <VStack>
            <Text fontWeight="bolder" fontSize="md" color={source.color}>
              {source.author}
            </Text>
            <Text fontSize="small" color="gray.400">
              Product
            </Text>
          </VStack>
        </Flex>
      </Flex>
    </Card>
  );
}

export default function ResultPage() {
  const { projectID } = useParams();
  const fetchResults = useStoreActions((actions) => actions.resultList.fetch);
  //const setResultsData = useStoreActions((actions) => actions.resultList.set);

  const results = useStoreState((state) => state.resultList.results);

  const [sources, setSources] = useState(mock);
  useEffect(() => {
    //initRatingsData();
    fetchResults(projectID);
    //setRatingsData(mockRatings.ratings);
  }, []);
  if ((results != null) & (results.length !== 0)) {
    for (let s = 0; s < sources.length; s++) {
      console.log(results);
      sources[s]['color'] = colors[s % colors.length];
      sources[s]['author'] = results[s % results.length]['productName'];
    }
  }
  console.log('First', { results });
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
          <Figure results={results}></Figure>
          <Heading size="lg" mt={5}>
            Sources
          </Heading>
          {sources.map((source) => (
            <SourceRow source={source} key={source.id} />
          ))}
        </Flex>
        <HStack>
        
          <Link href={'/projects'}>
            <Button> Back</Button>
          </Link>
        </HStack>
      </Page>
    </div>
  );
}
