import { React, useEffect, useState } from 'react';
import { Button, HStack, Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import Page from '../../components/Page';
import Card from '../../components/Card';
import RatingTable from './RatingTable';
import { score } from '../../utils/const';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { useParams } from 'react-router-dom';

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
  const [editMode, setEditMode] = useState(false);
  const [ratingsPerCategory, setRatingsPerCategory] = useState([]);
  const productData = useStoreState((state) => state.rating.ratings);
  const createNew = useStoreActions((actions) => actions.rating.createNew);
  const setRatingsData = useStoreActions((actions) => actions.rating.set);
  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);
  const sendRatings = useStoreActions((actions) => actions.rating.sendUpdate);

  const { productID } = useParams();

  const handleChange = (key) => (value) => {
    let index = -1;
    for (let i = 0; i < productData.ratings.length; i++) {
      if (value[0].rating.id == productData.ratings[i].rating.id) {
        index = i;
        break;
      } else continue;
    }
    let a = productData;

    let newProductData = Object.assign({}, productData); // creating copy of state variable jasper
    newProductData.ratings[index] = value[0];
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
    return ratingsPerCategory;
  }

  useEffect(() => {
    //initRatingsData();
    fetchRatings(productID);
    //setRatingsData(mockRatings.ratings);
  }, []);

  const EditButtons = () => {
    if (editMode) {
      return (
        <HStack>
          <Button variant="whisper" size="md" onClick={() => setEditMode(false)}>
            Cancel
          </Button>
          <Button variant="primary" size="md" onClick={() => setEditMode(false)}>
            Confirm
          </Button>
        </HStack>
      );
    } else {
      return (
        <Button variant="whisper" size="md" onClick={() => setEditMode(true)}>
          Edit
        </Button>
      );
    }
  };

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
                  <RatingTable
                    editMode={editMode}
                    ratings={complexityDriver[1]}
                    handleChange={setRatings}
                  />
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
