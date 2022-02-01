import {Flex, Input, Spacer, List, Textarea, IconButton} from '@chakra-ui/react';
import React, { useState } from 'react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';
import { useStoreActions, useStoreState } from 'easy-peasy';
import {AttachmentIcon} from "@chakra-ui/icons";

function RatingRow({ rating, onChangeScore }) {
  const changeRatingAnswer = useStoreActions((actions) => actions.product_rating.changeAnswer);
  const changeRatingCommet = useStoreActions((actions) => actions.product_rating.changeComment);
  const productData = useStoreState((state) => state.product_rating.product);

  const handleAnswerChange = (newRating) => {
    rating.answer = newRating;
    changeRatingAnswer(rating);
  };
  const handleCommentChange = (newRating) => {
    rating.comment = newRating;
    changeRatingCommet(rating);
  };

  return (
    <div>
      <Card
        layerStyle="card_bordered"
        justifyContent="space-between"
        // w={(parentID > 0) ? ' 90%' : 'full'}
        _hover={{ boxShadow: '2xl' }}
        align="center"
      >
        <Textarea
          isReadOnly={true}
          align="center"
          size="md"
          width="100%"
          placeholder={'Frage'}
          value={rating.rating.criterion}
        />
      </Card>
      <Card layerStyle="card_bordered" justifyContent="space-between" _hover={{ boxShadow: '2xl' }}>
        <Spacer />
        <Textarea
          align="center"
          size="md"
          width="100%"
          placeholder={'Anwort'}
          value={rating.answer}
          onChange={(e) => {
            handleAnswerChange(e.target.value);
          }}
        />
        <Spacer />
        <Selection
          options={['GERING', 'MITTEL', 'HOCH']}
          selected={rating.score}
          onChange={onChangeScore}
        ></Selection>
        <Spacer />
        <Textarea
          align="center"
          size="md"
          width="100%"
          placeholder={'Anmerkungen'}
          value={rating.comment}
          onChange={(e) => {
            handleCommentChange(e.target.value);
          }}
        />
        <Spacer />
        <IconButton variant="whisper" icon={<AttachmentIcon />} />
      </Card>
    </div>
  );
}

export default function RatingTable({ ratings, handleChange }) {
  const handleScoreChange = (rating) => (newRating) => {
    // This is a curried function in JS
    let index = ratings.map((r) => r.rating.criterion).indexOf(rating.rating.criterion);
    rating.score = newRating;
    ratings[index] = rating;
    handleChange(ratings);
  };

  return (
    <List spacing={2} direction="column" w="full" align="center">
      {ratings.map((rating) => (
        <Flex gridGap={3}>
          <RatingRow rating={rating} onChangeScore={handleScoreChange(rating)}></RatingRow>
        </Flex>
      ))}
    </List>
  );
}
