import { React, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { Button, Tabs, TabList, TabPanels, Tab, TabPanel, HStack, Link } from '@chakra-ui/react';
import { notification } from '../../utils/notification';

import Page from '../../components/Page';
import RatingTable from './RatingTable';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings

function capitalizeFirst(str) {
  const first = str.charAt(0);
  const rest = str.substr(1);
  return first.toUpperCase() + rest;
}
/** Renders the rating page */
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
          {categories.map((category) => (
            <Tab key={category}>{category}</Tab>
          ))}
        </TabList>
        <TabPanels>
          {categories.map((category) => (
            <TabPanel p={4} key={category}>
              <RatingTable category={category} />
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
            notification('Rating Saved!', '', 'success');
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
