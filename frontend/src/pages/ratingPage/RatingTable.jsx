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
  IconButton,
} from '@chakra-ui/react';
import { useStoreActions, useStoreState } from 'easy-peasy';

import { score } from '../../utils/const';

import Card from '../../components/Card';
import Selection from '../../components/Selection';
import UploadButton from '../../components/Upload';
import { useState, useEffect } from 'react';
import { CheckIcon } from '@chakra-ui/icons';

/**
 *
 * @param rating - a rating - most likely the rating with id = 10
 * @returns renders a rating row for percentage
 * @constructor
 */
function RatingRowPercentage({ rating }) {
  const [low, setLow] = useState(0);
  const [medium, setMedium] = useState(0);
  const [high, setHigh] = useState(0);
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  /** This function updates an attribute of rating. possible attributes are score, answer, comment  */
  const updateRatingAttribute = (key) => (value) => {
    let change = {};
    change[key] = value;
    updateRating({ ratingID: rating.ratingID, ...change });
  };

  const handleUpdateComment = updateRatingAttribute('comment');

  /** speacial case for rating with the id 10, answer must be seperated */
  const getAnswerArray = () => {
    if (rating.answer == null || !rating.answer.includes(','))
    {
      return [0, 0, 0];
    }
    const answerValues = rating.answer.split(',').map((s) => parseInt(s));
    console.log(answerValues);
    if (answerValues.length === 3) {
      return answerValues;
    } else {
      return [0, 0, 0];
    }
  };

  const updateAnswer = () => {
    const percentageString = `${low},${medium},${high}`;
    console.log('answer set to', percentageString);
    updateRatingAttribute('answer')(percentageString);
  };

  useEffect(() => {
    const values = getAnswerArray();
    setLow(values[0]);
    setMedium(values[1]);
    setHigh(values[2]);
  }, []);

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
            value={low}
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
            value={medium}
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
            value={high}
            onChange={(value) => {
              setHigh(value);
            }}
          >
            <NumberInputField></NumberInputField>
          </NumberInput>
        </VStack>
        <IconButton
          aria-label="confirm percentages"
          variant="whisper"
          size="sm"
          onClick={updateAnswer}
          icon={<CheckIcon />}
        />
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

/**
 *
 * @param rating - A rating
 * @returns Renders A normal rating row
 * @constructor
 */
function RatingRowCategorical({ rating }) {
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  /** The update function for comment, score or answer */
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
      <Flex direction="row" justifyContent="space-between" w="full" mb={2} alignItems="flex-start">
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
        <VStack w={40} alignItems="left" spacing={0} mx={1}>
          <Text fontSize={'sm'} align="left">
            Complexity
          </Text>
          <Selection
            // w="9rem"
            w="full"
            options={[score.GERING, score.MITTEL, score.HOCH]}
            selected={rating.score ? rating.score : undefined}
            placeholder="---"
            onChange={handleUpdateScore}
          />
        </VStack>

        <VStack w="40%" alignItems="left" spacing={0} mx={1}>
          <Text fontSize="sm" align="left">
            Comment
          </Text>
          <Textarea
            placeholder="Comment"
            value={rating.comment}
            onChange={(e) => handleUpdateComment(e.target.value)}
          />
        </VStack>
        <UploadButton variant="whisper" />
      </Flex>
    </Card>
  );
}

/**
 * Select which type of rating row should be created based on rating id
 * @param rating - A rating with an id
 * @returns
 * @constructor
 */
function RatingRow({ rating }) {
  return rating.ratingID === 10 ? (
    <RatingRowPercentage rating={rating} />
  ) : (
    <RatingRowCategorical rating={rating} />
  );
}

/**
 *
 * @param category - A category
 * @returns Renders the rating table
 * @constructor
 */
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
