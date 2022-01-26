import { Flex, Input, Spacer, List, Textarea } from '@chakra-ui/react';
import React, { useState } from 'react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';

function RatingRow({ rating, onChangeScore, onChangeComment, onChangeAnswer }) {
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
        <Input
          align="center"
          size="md"
          width="100%"
          placeholder={'Anwort'}
          value={rating.answer}
          onChange={onChangeAnswer}
        />
        <Spacer />
        <Selection
          options={['GERING', 'MITTEL', 'HOCH']}
          selected={rating.score}
          onChange={onChangeScore}
        ></Selection>
        <Spacer />
        <Input
          align="center"
          size="md"
          width="100%"
          placeholder={'Anmerkungen'}
          value={rating.comment}
          onChange={onChangeComment}
        />
        <Spacer />
        <Input
          align="center"
          size="md"
          w="25%"
          isDisabled={true}
          onChange={(e) => {
            console.log(e.target.value);
          }}
          value={'Upload'}
        />
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

  const handleCommentChange = (rating) => (newRating) => {
    let index = ratings.map((r) => r.rating.criterion).indexOf(rating.rating.criterion);
    rating.comment = newRating.target.value;
    ratings[index] = rating;
    handleChange(ratings);
  };

  const handleAnswerChange = (rating) => (newRating) => {
    let index = ratings.map((r) => r.rating.criterion).indexOf(rating.rating.criterion);
    rating.answer = newRating.target.value;
    ratings[index] = rating;
    handleChange(ratings);
  };

  return (
    <List spacing={2} direction="column" w="full" align="center">
      {ratings.map((rating) => (
        <Flex gridGap={3}>
          <RatingRow
            rating={rating}
            onChangeScore={handleScoreChange(rating)}
            onChangeComment={handleCommentChange(rating)}
            onChangeAnswer={handleAnswerChange(rating)}
          ></RatingRow>
        </Flex>
      ))}
    </List>
  );
}
