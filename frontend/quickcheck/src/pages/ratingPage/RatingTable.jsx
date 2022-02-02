import { Flex, Text, List, Textarea, IconButton, VStack } from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { AttachmentIcon } from '@chakra-ui/icons';

function RatingRow({ rating }) {
  const updateRating = useStoreActions((actions) => actions.rating.updateRating);

  const updateRatingAttribute = (key) => (value) => {
    let change = {};
    change[key] = value;
    updateRating({ ratingID: rating.ratingID, ...change });
  };

  const handleUpdateComment = updateRatingAttribute('comment');
  const handleUpdateAnswer = updateRatingAttribute('answer');
  const handleUpdateScore = updateRatingAttribute('score');

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
            value={rating.answer}
            onChange={(e) => handleUpdateAnswer(e.target.value)}
          />
        </VStack>

        <Selection
          w="125px"
          mb="5"
          options={['GERING', 'MITTEL', 'HOCH']}
          selected={rating.score}
          onChange={handleUpdateScore}
        ></Selection>
        <VStack w="40%" alignItems="left" spacing="0" mx={1}>
          <Text fontSize="sm" align="left">
            Comment
          </Text>
          <Textarea
            placeholder="Comment"
            value={rating.comment}
            onChange={(e) => handleUpdateComment(e.target.value)}
          />
        </VStack>
        <IconButton variant="whisper" icon={<AttachmentIcon />} />
      </Flex>
    </Card>
  );
}

export default function RatingTable({ category }) {
  const getRatingsByCategory = useStoreState((state) => state.rating.getRatingsByCategory);

  return (
    <>
      <List spacing={2} direction="column" w="full" align="center">
        {getRatingsByCategory(category).map((rating) => (
          <RatingRow rating={rating} key={rating.ratingID}></RatingRow>
        ))}
      </List>
      <p>{JSON.stringify(getRatingsByCategory(category))}</p>
    </>
  );
}
