import { Flex, VStack, List, Text } from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';

function RatingRow({ rating }) {
  return (
    <Card layerStyle="card_bordered" justifyContent="space-between" direction="column">
      <Flex direction="row" justifyContent={'space-between'} w="full" mb={4}>
        <Text fontSize="xl">{rating.rating.criterion}</Text>
      </Flex>
      <Flex direction="row" justifyContent={'space-between'} w="full" mb={2} alignItems={'center'}>
        <VStack w="40%" alignItems={'left'} direction={'column'} spacing="0">
          <Text fontSize={'sm'}>Answer</Text>
          <Text variant="cell" minH="100px" align="left" placeholder={'Answer'}>
            {rating.answer}
          </Text>
        </VStack>
        <Text variant="cell">{rating.score}</Text>
        <VStack w="40%" alignItems={'left'} spacing="0">
          <Text fontSize={'sm'}>Comment</Text>
          <Text variant="cell" minH="100px" placeholder={'Comment'}>
            {' '}
            {rating.comment}
          </Text>
        </VStack>
      </Flex>
    </Card>
  );
}

export default function EvaluationTable({ ratings }) {
  return (
    <List spacing={2} direction="column" w="full" align="center">
      {ratings.map((rating) => (
        <Flex gridGap={3}>
          <RatingRow rating={rating}></RatingRow>
        </Flex>
      ))}
    </List>
  );
}
