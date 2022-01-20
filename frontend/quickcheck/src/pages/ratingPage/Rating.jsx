import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, HStack, Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import Page from '../../components/Page';
import Card from '../../components/Card';
import RatingTable from './RatingTable';
import { score } from '../../utils/const';
import { useStoreActions, useStoreState } from 'easy-peasy';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings

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

export default function Rating() {
  const [ratingsPerCategory, setRatingsPerCategory] = useState([]);
  const productData = useStoreState((state) => state.rating.ratings);
  const createNew = useStoreActions((actions) => actions.rating.createNew);
  const setRatingsData = useStoreActions((actions) => actions.rating.set);
  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);
  const sendRatings = useStoreActions((actions) => actions.rating.sendUpdate);

  const { productID } = useParams();

  const handleChange = (key) => (value) => {
    let newProductData = Object.assign({}, productData); // creating copy of state variable jasper
    setRatingsData(newProductData);
  };

  const setRatings = handleChange('ratings');

  function computeRatingsPerCategory(ratings) {
    if (ratings.length === 0 || Object.keys(ratingsPerCategory).length != 0) {
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
    fetchRatings(productID);
  }, []);

  function DataTabs({ data }) {
    return (
      <Page title="Komplexitätsbewertung">
        <Tabs>
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
        <Button
          variant="whisper"
          size="md"
          onClick={() => {
            sendRatings(productData);
          }}
        >
          Save
        </Button>
      </Page>
    );
  }
  if (productData.ratings != undefined) {
    computeRatingsPerCategory(productData.ratings);
  }
  return <DataTabs data={Object.entries(ratingsPerCategory)} />;
}
