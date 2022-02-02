import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { Button, Tabs, TabList, TabPanels, Tab, TabPanel, HStack } from '@chakra-ui/react';
import { notification } from '../../utils/notification';

import Page from '../../components/Page';
import Card from '../../components/Card';
import RatingTable from './RatingTable';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings

<<<<<<< HEAD
function toTitles(s) {
  return s.replace(/\w\S*/g, function (t) {
    return t.charAt(0).toUpperCase() + t.substr(1).toLowerCase();
  });
}

const mockRatings = {
  ratings: [
    {
      answer: 'test answer',
      comment: 'test comment',
      score: score.gering,
      rating: {
        category: 'Treiber 1',
        criterion: 'test frage',
      },
    },
    {
      answer: 'test answer',
      comment: 'test comment',
      score: score.gering,
      rating: {
        category: 'Treiber 1',
        criterion: 'test frage 1',
      },
    },
    {
      answer: '',
      comment: '',
      score: score.mittel,
      rating: {
        category: 'Treiber 2',
        criterion: 'Wer bin ich',
      },
    },
  ],
};
=======
function capitalizeFirst(str) {
  const first = str.charAt(0);
  const rest = str.substr(1);
  return first.toUpperCase() + rest;
}
>>>>>>> development

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
        <Tabs w="full">
          <TabList>
            {data.map((complexityDriver) => (
              <Tab key={complexityDriver[1]}>{complexityDriver[0]}</Tab>
            ))}
          </TabList>
          <TabPanels>
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
              notification('Rating Saved!', '', 'success');
            }}
          >
            Save
          </Button>
        </HStack>
      </Page>
    );
  }
  if (productData.ratings != undefined && productData.productID != -1) {
    computeRatingsPerCategory(productData.ratings);
  }
  return <DataTabs data={Object.entries(ratingsPerCategory)} />;
}
