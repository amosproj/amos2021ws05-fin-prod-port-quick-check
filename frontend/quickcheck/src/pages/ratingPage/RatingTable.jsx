import { Flex, Input, Text, Spacer, List, Textarea, IconButton, HStack } from '@chakra-ui/react';
import React, { useState } from 'react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { AttachmentIcon } from "@chakra-ui/icons";

function RatingRow({ rating, onChangeScore }) {
  const changeRatingAnswer = useStoreActions((actions) => actions.product_rating.changeAnswer);
  const changeRatingCommet = useStoreActions((actions) => actions.product_rating.changeComment);
  const productData = useStoreState((state) => state.product_rating.product);

  const handleAnswerChange = (newRating) => {
    if (rating.ratingID == 10) {
      if (newRating.match("^[1-9]\\d{0,2}(?:,\\d{0,2}(?:,\\d{0,2})?)?$") == null) {
        alert("Falsches Format")
      }
      else {
        rating.answer = newRating;
        changeRatingAnswer(rating);
      }
    }
    else {
      rating.answer = newRating;
      changeRatingAnswer(rating);
    }
  };
  const handleCommentChange = (newRating) => {
    rating.comment = newRating;
    changeRatingCommet(rating);
  };

  return (
    <Card
      layerStyle="card_bordered"
      justifyContent="space-between"
      // w={(parentID > 0) ? ' 90%' : 'full'}
      direction="column"
    >
      <Flex direction="row" justifyContent={"space-between"} w="full" mb={2}>
        {/* <Input
          isReadOnly={true}
          align="center"
          placeholder={'Frage'}
          w="full"
          value={rating.rating.criterion}
        /> */}
        <Text>{rating.rating.criterion}</Text>
      </Flex>
      <Flex direction="row" justifyContent={"space-between"} w="full" mb={2} alignItems={"center"}>
        <Textarea
          align="center"
          w="40%"
          placeholder={'Anwort'}
          value={rating.answer}
          onChange={(e) => {
            handleAnswerChange(e.target.value);
          }}
        />

        <Selection
          options={['GERING', 'MITTEL', 'HOCH']}
          w="12.5%"
          selected={rating.score}
          onChange={onChangeScore}
        ></Selection>
        <HStack w="40%">
          <Textarea
            placeholder={'Anmerkungen'}
            value={rating.comment}
            onChange={(e) => {
              handleCommentChange(e.target.value);
            }}
          />
          <IconButton variant="whisper" icon={<AttachmentIcon />} />
        </HStack>
      </Flex>
    </Card>
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
