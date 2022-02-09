import { Flex, VStack, List, Text } from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';

/** Renders a evaluation row */
function RatingRow({ rating }) {
  return (
    <Card layerStyle="card_bordered" justifyContent="space-between" direction="column">
      <Flex direction="row" justifyContent="space-between" w="full" mb={4}>
        <Text fontSize="xl">{rating.rating.criterion}</Text>
      </Flex>
      <Flex direction="row" justifyContent="space-between" w="full" mb={2} alignItems="center">
        <VStack w="40%" alignItems="left" spacing={0}>
          <Text fontSize="sm" align="left">
            Answer
          </Text>
          <Text variant="cell" minH="5rem" align="left" placeholder="Answer">
            {rating.answer}
          </Text>
        </VStack>
        <Text variant="cell">{rating.score != null ? rating.score : 'Nicht gesetzt'}</Text>
        <VStack w="40%" alignItems="left" spacing={0}>
          <Text fontSize="sm" align="left">
            Comment
          </Text>
          <Text variant="cell" minH="5rem" placeholder="Comment" align="left">
            {rating.comment}
          </Text>
        </VStack>
      </Flex>
    </Card>
  );
}

/** Renders the evaluation table */
export default function EvaluationTable({ ratings }) {
  return (
    <List spacing={2} w="full" align="center">
      {ratings.map((rating) => (
        <Flex gridGap={3}>
          <RatingRow rating={rating}></RatingRow>
        </Flex>
      ))}
    </List>
  );
}
