import React from 'react';
import { Flex, Text, List, Textarea, VStack } from '@chakra-ui/react';
import { useStoreActions, useStoreState } from 'easy-peasy';

import { score } from '../../utils/const';

import Card from '../../components/Card';
import Selection from '../../components/Selection';
import UploadButton from '../../components/Upload';

const validCategoricalPercentage = (str) => {
  // valid format: "int,int,int"
  // example: "15,5,80"
  const percentages = str.split(',');

  if (percentages.length !== 3) {
    // check if three percentage values
    return false;
  }
  for (let p in percentages) {
    if (isNaN(p)) {
      // check if each value is a number
      return false;
    }
    const value = parseInt(p);
    if (value < 0 || value > 100) {
      // check if each value is in range [0, 100]
      return false;
    }
  }
  return true; // format valid
};

function RatingRow({ rating }) {
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  const updateRatingAttribute = (key) => (value) => {
    let change = {};
    change[key] = value;
    updateRating({ ratingID: rating.ratingID, ...change });
  };

  const handleUpdateComment = updateRatingAttribute('comment');
  const handleUpdateScore = updateRatingAttribute('score');
  const handleUpdateAnswer = (newAnswer) => {
    if (rating.ratingID === 10) {
      if (validCategoricalPercentage(newAnswer)) {
        alert('Falsches Format');
      }
    }
    updateRatingAttribute('answer')(newAnswer);
  };

  return (
    <Card layerStyle="card_bordered" justifyContent="space-between" direction="column">
      <Text fontSize="xl" mb={4} align="left" w="full">
        {rating.rating.criterion}
      </Text>
      <Flex direction="row" justifyContent="space-between" w="full" mb={2} alignItems={'center'}>
        <VStack w="40%" alignItems="left" spacing={0} mx={1}>
          <Text fontSize={'sm'} align="left">
            Answer
          </Text>
          <Textarea
            align="center"
            placeholder="Answer"
            value={rating.answer ? rating.answer : ''}
            onChange={(e) => handleUpdateAnswer(e.target.value)}
          />
        </VStack>

        <Selection
          w="125px"
          mb="5"
          options={[score.GERING, score.MITTEL, score.HOCH]}
          selected={rating.score ? rating.score : score.MITTEL}
          onChange={handleUpdateScore}
        ></Selection>
        <VStack w="40%" alignItems="left" spacing={0} mx={1}>
          <Text fontSize="sm" align="left">
            Comment
          </Text>
          <Textarea
            placeholder="Comment"
            value={rating.comment ? rating.comment : ''}
            onChange={(e) => handleUpdateComment(e.target.value)}
          />
        </VStack>
        <UploadButton variant="whisper" />
      </Flex>
    </Card>
  );
}

export default function RatingTable({ category }) {
  const getRatingsByCategory = useStoreState((state) => state.rating.getRatingsByCategory);

  return (
    <List spacing={2} direction="column" w="full" align="center">
      {getRatingsByCategory(category).map((rating) => (
        <RatingRow rating={rating} key={rating.ratingID}></RatingRow>
      ))}
    </List>
  );
}
