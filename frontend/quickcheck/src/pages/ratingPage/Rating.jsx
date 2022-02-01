import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { Button, Link, Tabs, TabList, TabPanels, Tab, TabPanel, HStack } from '@chakra-ui/react';

import Page from '../../components/Page';
import Card from '../../components/Card';
import RatingTable from './RatingTable';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings

function capitalizeFirst(str) {
  const first = str.charAt(0);
  const rest = str.substr(1);
  return first.toUpperCase() + rest;
}

export default function Rating() {
  const [ratingsPerCategory, setRatingsPerCategory] = useState([]);
  const productData = useStoreState((state) => state.rating.product);
  const setRatingsData = useStoreActions((actions) => actions.rating.set);
  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);
  const sendRatings = useStoreActions((actions) => actions.rating.sendUpdate);

  const { productID, ratingArea, productAreaID, projectID } = useParams();

  const handleChange = (key) => (value) => {
    let newProductData = Object.assign({}, productData); // creating copy of state variable jasper
    setRatingsData(newProductData);
  };

  const setRatings = handleChange('ratings');

  function computeRatingsPerCategory(ratings) {
    if (ratings.length === 0 || Object.keys(ratingsPerCategory).length !== 0) {
      return [];
    }
    for (const rating of ratings) {
      if (ratingsPerCategory[rating.rating.category] != null) {
        ratingsPerCategory[rating.rating.category].push(rating);
      } else {
        ratingsPerCategory[rating.rating.category] = [rating];
      }
    }
    setRatingsPerCategory(ratingsPerCategory);
    return ratingsPerCategory;
  }

  useEffect(() => {
    fetchRatings([productID, ratingArea]);
  }, []);

  function DataTabs({ data }) {
    return (
      <Page
        title={capitalizeFirst(ratingArea) + ' Rating'}
        backref={`/projects/${projectID}/productArea/${productAreaID}`}
      >
        <Tabs>
          <TabList w="full">
            {data.map((complexityDriver) => (
              <Tab key={complexityDriver[1]}>{complexityDriver[0]}</Tab>
            ))}
          </TabList>
          <TabPanels w="full">
            {data.map((complexityDriver) => (
              <TabPanel p={4} key={complexityDriver[0]}>
                <Card direction="column">
                  <RatingTable ratings={complexityDriver[1]} handleChange={setRatings} />
                </Card>
              </TabPanel>
            ))}
          </TabPanels>
        </Tabs>
        <HStack>
          <Button
            variant="whisper"
            size="md"
            onClick={() => {
              sendRatings(productData);
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
  if (productData.ratings != undefined && productData.productID != -1) {
    computeRatingsPerCategory(productData.ratings);
  }
  return <DataTabs data={Object.entries(ratingsPerCategory)} />;
}
