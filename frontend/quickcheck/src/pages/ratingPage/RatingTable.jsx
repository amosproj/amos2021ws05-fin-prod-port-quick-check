import {
  Flex,
  Input,
  Text,
  Spacer,
  List,
  Textarea,
  IconButton,
  HStack,
  VStack,
} from '@chakra-ui/react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';
import { useStoreActions, useStoreState } from 'easy-peasy';
import { AttachmentIcon } from '@chakra-ui/icons';

function RatingRow({ rating, onChangeScore }) {
  const changeRatingAnswer = useStoreActions((actions) => actions.product_rating.changeAnswer);
  const changeRatingCommet = useStoreActions((actions) => actions.product_rating.changeComment);
  const changeRatingScore = useStoreActions((actions) => actions.product_rating.changeScore);
  const productData = useStoreState((state) => state.product_rating.product);

  const handleAnswerChange = (newRating) => {
    if (rating.ratingID == 10) {
      if (newRating.match('^[1-9]\\d{0,2}(?:,\\d{0,2}(?:,\\d{0,2})?)?$') == null) {
        alert('Falsches Format');
      } else {
        rating.answer = newRating;
        changeRatingAnswer(rating);
      }
    } else {
      rating.answer = newRating;
      changeRatingAnswer(rating);
    }
  };
  const handleCommentChange = (newRating) => {
    rating.comment = newRating;
    changeRatingCommet(rating);
  };

  const handleScoreChange = (newRating) => {
    rating.score = newRating;
    changeRatingCommet(rating);
  };

  return (
    <Card
      layerStyle="card_bordered"
      justifyContent="space-between"
      // w={(parentID > 0) ? ' 90%' : 'full'}
      direction="column"
    >
      <Flex direction="row" justifyContent={'space-between'} w="full" mb={4}>
        <Text fontSize="xl">{rating.rating.criterion}</Text>
      </Flex>
      <Flex direction="row" justifyContent={'space-between'} w="full" mb={2} alignItems={'center'}>
        <VStack w="40%" alignItems={'left'} spacing="0">
          <Text fontSize={'sm'}>Answer</Text>
          <Textarea
            align="center"
            placeholder={'Answer'}
            value={rating.answer}
            onChange={(e) => {
              handleAnswerChange(e.target.value);
            }}
          />
        </VStack>

        <Selection
          w="125px"
          mb="5"
          options={['GERING', 'MITTEL', 'HOCH']}
          selected={rating.score}
          onChange={(e) => {
            handleScoreChange(e);
          }}
        ></Selection>
        <VStack w="40%" alignItems={'left'} spacing="0">
          <Text fontSize={'sm'}>Comment</Text>
          <Textarea
            placeholder={'Comment'}
            value={rating.comment}
            onChange={(e) => {
              handleCommentChange(e.target.value);
            }}
          />
        </VStack>
        <IconButton variant="whisper" icon={<AttachmentIcon />} />
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
          <RatingRow rating={rating} key={rating.ratingID} onChangeScore={handleScoreChange(rating)}></RatingRow>
      ))}
    </List>
  );
}
