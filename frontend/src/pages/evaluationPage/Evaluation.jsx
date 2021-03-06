import { React, useEffect, useState } from 'react';
import { Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import Page from '../../components/Page';
import EvaluationTable from './EvaluationTable';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { useParams } from 'react-router-dom';

//http://localhost:3000/projects/100/productArea/1/products/100/ratings
/** Renders the evaluation page */
export default function Evaluation() {
  const [ratingsPerCategory, setRatingsPerCategory] = useState([]);
  const productData = useStoreState((state) => state.rating.product);
  const fetchRatings = useStoreActions((actions) => actions.rating.fetch);

  const { projectID, productAreaID, productID } = useParams();

  /**
   *
   * @param ratings - A list of ratings
   * @returns dict - A dictionary with a category as key and a list of ratings as value
   */
  function computeEvaluationPerCategory(ratings) {
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
    return ratingsPerCategory;
  }

  useEffect(() => {
    fetchRatings([productID, 'COMPLEXITY']);
  }, []);

  /**
   * Creates the tabs
   * @param data - A dictionary with a category as key and a list of ratings as value
   * @returns renders the page
   * @constructor
   */
  function DataTabs({ data }) {
    return (
      <Page title="Evaluation" backref={`/projects/${projectID}/productArea/${productAreaID}`}>
        <Tabs w="full">
          <TabList>
            {data.map((complexityDriver) => (
              <Tab key={complexityDriver[1]}>{complexityDriver[0]}</Tab>
            ))}
          </TabList>
          <TabPanels>
            {data.map((complexityDriver) => (
              <TabPanel p={5} key={complexityDriver[0]}>
                <EvaluationTable ratings={complexityDriver[1]} />
              </TabPanel>
            ))}
          </TabPanels>
        </Tabs>
      </Page>
    );
  }
  if (productData.ratings != undefined && productData.productID != -1) {
    computeEvaluationPerCategory(productData.ratings);
  }
  return <DataTabs data={Object.entries(ratingsPerCategory)} />;
}
