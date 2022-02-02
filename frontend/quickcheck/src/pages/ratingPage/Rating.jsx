import { React, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { Button, Link, Tabs, TabList, TabPanels, Tab, TabPanel, HStack } from '@chakra-ui/react';

import Page from '../../components/Page';
import RatingTable from './RatingTable';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings

function capitalizeFirst(str) {
  const first = str.charAt(0);
  const rest = str.substr(1);
  return first.toUpperCase() + rest;
}

export default function Rating() {
  const product = useStoreState((state) => state.rating.product);
  const categories = useStoreState((state) => state.rating.categories);

  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);
  const sendRatings = useStoreActions((actions) => actions.rating.sendUpdate);

  const { projectID, productAreaID, productID, ratingArea } = useParams();

  useEffect(() => {
    fetchRatings([productID, ratingArea]);
  }, []);

  return (
    <Page
      title={capitalizeFirst(ratingArea) + ' Rating'}
      backref={`/projects/${projectID}/productArea/${productAreaID}`}
    >
      <Tabs w="full">
        <TabList>
          {categories.map((c) => (
            <Tab key={c}>{c}</Tab>
          ))}
        </TabList>
        <TabPanels>
          {categories.map((c) => (
            <TabPanel p={4} key={c}>
              <RatingTable category={c} />
            </TabPanel>
          ))}
        </TabPanels>
      </Tabs>
      <HStack>
        <Button
          variant="whisper"
          size="md"
          onClick={() => {
            sendRatings(product);
          }}
        >
          Save
        </Button>
        <Link href={`/../../projects/${projectID}/productArea/${productAreaID}/`}>
          <Button variant="whisper">Back</Button>
        </Link>
      </HStack>
    </Page>
  );
}
