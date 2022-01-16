import { React, useEffect, useState } from 'react';
import { Button, HStack, Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import Page from '../../components/Page';
import Card from '../../components/Card';
import EvaluationTable from './EvaluationTable';
import { score } from '../../utils/const';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { useParams } from 'react-router-dom';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings


export default function Evaluation() {
  const [ratingsPerCategory, setRatingsPerCategory] = useState([]);
  const productData = useStoreState((state) => state.rating.ratings);
  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);

  const { productID } = useParams();


  function computeEvaluationPerCategory(ratings) {
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
    fetchRatings(productID);
  }, []);


  function DataTabs({ data }) {
    return (
      <Page title="Evaluation">
        <Tabs>
          <TabList>
            {data.map((complexityDriver) => (
              <Tab key={complexityDriver[1]}>{complexityDriver[0]}</Tab>
            ))}
          </TabList>
          <TabPanels>
            {data.map((complexityDriver) => (
              <TabPanel p={5} key={complexityDriver[0]}>
                <Card direction="column">
                  <EvaluationTable
                    ratings={complexityDriver[1]}
                  />
                </Card>
              </TabPanel>
            ))}
          </TabPanels>
        </Tabs>
      </Page>
    );
  }
  if (productData.ratings != undefined) {
    computeEvaluationPerCategory(productData.ratings);
  }
  return <DataTabs data={Object.entries(ratingsPerCategory)} />;
}
