import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  Button,
  Heading,
  HStack,
  Input,
  List,
  Spacer,
  Tabs,
  TabList,
  TabPanels,
  Tab,
  TabPanel,
} from '@chakra-ui/react';
import Page from '../../components/Page';
import Card from '../../components/Card';
import MemberTable from '../projectPage/MemberTable';
import RatingTable from './RatingTable';
import { score } from '../../utils/const';

/*
    ratingID:
      type: integer
      example: 111
    answer:
      type: string
      example: "an answer to a question or the volume of an economic criterion"
    comment:
      type: string
      example: "a comment to an answer or an economic criterion"
    score:
      type: string
      enum: [ HOCH, MITTEL, GERING ]
    rating:
      type: object
      properties:
        ratingID:
          $ref: '#/components/schemas/ratingID'
        category:
          type: string
          example: "Komplexitätstreiber oder Gruppierung von Finanzkriterien"
        criterion:
          type: string
          example: "question or criterion to be analysed"
        ratingArea:
          type: string
          enum: [ ECONOMIC, COMPLEXITY ]
 */

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
  const [ratingsData, setRatingstData] = useState({
    ratings: [],
  });
  const [ratingsPerCategory, setRatingsPerCategory]= useState(
      []
  );

  const handleChange = (key) => (value) => {
    setRatingstData({
      ...ratingsData,
      [key]: value,
    });
  };

  const handleChange2 = (key) => (value) => {
    setRatingsPerCategory({
      ...ratingsPerCategory,
      [key]: value,
    });
  };

  const setRatings = handleChange('ratings');

  function computeRatingsPerCategory(ratings)
  {
    for (const rating of ratings)
    {
      if (ratingsPerCategory[rating.rating.category] != null)
      {
        (ratingsPerCategory[rating.rating.category]).push(rating);
      }
      else
      {
        ratingsPerCategory[rating.rating.category] = [rating];
      }
    }
    return ratingsPerCategory;
  }

  useEffect(() => {
    // fetchProject();
    setRatingstData(mockRatings);
    computeRatingsPerCategory(mockRatings.ratings);
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

  function DataTabs({data})
  {
    var a = data.map(complexityDriver => {
      return complexityDriver[1];
    })
    var b = ratingsPerCategory;
    return (
        <Page title="Komplexitätsbewertung">
          <Tabs>
            <TabList>
              {data.map(complexityDriver=> (
                  <Tab key={complexityDriver[1]}>{complexityDriver[0]}</Tab>
              ))}
            </TabList>
            <TabPanels>
              {data.map(complexityDriver => (
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
        </Page>
    )
  }

  //TODO: komp treiber änderungen dynamisch gestallten (https://chakra-ui.com/docs/disclosure/tabs)
  return <DataTabs data={ Object.entries(ratingsPerCategory)}/>;
  /*return (
    <Page title="Komplexitätsbewertung">
      <Tabs>
        <TabList>
          <Tab>Treiber 1:</Tab>
          <Tab>Treiber 2:</Tab>
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
  );*/
}
