import { React, useEffect, useState } from 'react';
import {
  Button,
  HStack,
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
} from '@chakra-ui/react';
import Page from '../../components/Page';
import Card from '../../components/Card';
import RatingTable from './RatingTable';
import { score } from '../../utils/const';


const mockRatings = {
  ratings: [
    {
      answer: 'test answer',
      comment: 'test comment',
      score: score.gering,
      rating: {
        criterion: 'test frage',
      },
    },
    {
      answer: '',
      comment: '',
      score: score.mittel,
      rating: {
        criterion: 'Wer bin ich',
      },
    },
  ],
};

export default function Rating() {
  const [editMode, setEditMode] = useState(false);
  const [ratingsData, setRatingstData] = useState({
    //TODO : Rating data structure
    ratings: [],
  });

  const handleChange = (key) => (value) => {
    setRatingstData({
      ...ratingsData,
      [key]: value,
    });
  };

  const setRatings = handleChange('ratings');

  useEffect(() => {
    // fetchProject();
    setRatingstData(mockRatings);
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

  return (
    <Page title="Wirtschaftliche Bewertung">
      <Tabs>
        <TabList>
          <Tab>Produkt</Tab>
          <Tab>Produktvariante</Tab>
        </TabList>
        <TabPanels>
          <TabPanel>
            <Card direction="column">
              <RatingTable
                editMode={editMode}
                ratings={ratingsData.ratings}
                handleChange={setRatings}
              />
            </Card>
          </TabPanel>
          <TabPanel>
            <Card direction="column">
              <RatingTable
                editMode={editMode}
                ratings={ratingsData.ratings}
                handleChange={setRatings}
              />
            </Card>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </Page>
  );
}
