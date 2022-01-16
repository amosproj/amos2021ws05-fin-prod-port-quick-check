import {Flex, Input, Spacer, List, Text, Textarea} from '@chakra-ui/react';
import React from 'react';
import Card from '../../components/Card';
import Selection from '../../components/Selection';

function RatingRow({ rating }) {
  return (
    <div>
      <Card
        layerStyle="card_bordered"
        justifyContent="space-between"
        // w={(parentID > 0) ? ' 90%' : 'full'}
        _hover={{ boxShadow: '2xl' }}
      >
        <Spacer />
        <Text>{rating.rating.category}</Text>
        <Spacer />
        <Textarea isDisabled={true}
          align="center"
          size="md"
          width="100%"
          placeholder={'Frage'}
          value={rating.rating.criterion}
        />
        <Spacer />
        <Selection
          options={['GERING', 'MITTEL', 'HOCH']}
          isDisabled={true}
          selected={rating.score}
        ></Selection>
        <Spacer />
        <Textarea
          align="center"
          size="md"
          width="100%"
          isDisabled={true}
          value={rating.comment}
        />
        <Spacer />
        <Input
          align="center"
          size="md"
          w="100%"
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

export default function EvaluationTable({  ratings,  }) {


  return (
    <List spacing={2} direction="column" w="full" align="center">
      {ratings.map((rating) => (
        <Flex gridGap={3}>
          <RatingRow
            rating={rating}
          ></RatingRow>
        </Flex>
      ))}
    </List>
  );
}
