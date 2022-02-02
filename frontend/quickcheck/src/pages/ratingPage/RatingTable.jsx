import React from 'react';
import {
  Flex,
  Text,
  List,
  Textarea,
  VStack,
  NumberInput,
  NumberInputField,
  Spacer,
} from '@chakra-ui/react';
import { useStoreActions, useStoreState } from 'easy-peasy';

import { score } from '../../utils/const';

import Card from '../../components/Card';
import Selection from '../../components/Selection';
import UploadButton from '../../components/Upload';
import { useState, useEffect } from 'react';

function RatingRowPercentage({ rating }) {
  const [low, setLow] = useState(0);
  const [medium, setMedium] = useState(0);
  const [high, setHigh] = useState(0);
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  const updateRatingAttribute = (key) => (value) => {
    let change = {};
    change[key] = value;
    updateRating({ ratingID: rating.ratingID, ...change });
  };

  const handleUpdateComment = updateRatingAttribute('comment');

  const updateAnswer = () => {
    const percentageString = `${low},${medium},${high}`;
    updateRatingAttribute('answer')(percentageString);
  };

  useEffect(() => {
    updateAnswer();
  }, [low, medium, high]);

  return (
    <Card layerStyle="card_bordered" justifyContent="space-between" direction="column">
      <Text fontSize="xl" mb={4} align="left" w="full">
        {rating.rating.criterion}
      </Text>
      <Flex direction="row" justifyContent="space-between" w="full" mb={2} alignItems={'center'}>
        <Spacer />
        <VStack spacing={0} mx={1} w={24} alignItems="left">
          <Text fontSize={'sm'} align="left">
            Low
          </Text>
          <NumberInput
            min={0}
            max={100}
            onChange={(value) => {
              setLow(value);
            }}
          >
            <NumberInputField></NumberInputField>
          </NumberInput>
        </VStack>
        <VStack spacing={0} mx={1} w={24} alignItems="left">
          <Text fontSize={'sm'} align="left">
            Medium
          </Text>
          <NumberInput
            min={0}
            max={100}
            onChange={(value) => {
              setMedium(value);
            }}
          >
            <NumberInputField></NumberInputField>
          </NumberInput>
        </VStack>
        <VStack spacing={0} mx={1} w={24} alignItems="left">
          <Text fontSize={'sm'} align="left">
            High
          </Text>
          <NumberInput
            min={0}
            max={100}
            onChange={(value) => {
              setHigh(value);
            }}
          >
            <NumberInputField></NumberInputField>
          </NumberInput>
        </VStack>

        <Spacer />
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

function RatingRowCategorical({ rating }) {
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  const updateRatingAttribute = (key) => (value) => {
    let change = {};
    change[key] = value;
    updateRating({ ratingID: rating.ratingID, ...change });
  };

  const handleUpdateComment = updateRatingAttribute('comment');
  const handleUpdateScore = updateRatingAttribute('score');
  const handleUpdateAnswer = updateRatingAttribute('answer');

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

function RatingRow({ rating }) {
  return rating.ratingID === 10 ? (
    <RatingRowPercentage rating={rating} />
  ) : (
    <RatingRowCategorical rating={rating} />
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
