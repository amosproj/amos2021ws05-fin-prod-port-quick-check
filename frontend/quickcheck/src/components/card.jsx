import React from 'react';
import { Heading, Stack, Text, Button, Box, SimpleGrid } from '@chakra-ui/react';
import { Link } from 'react-router-dom';
function CardLabel(props) {
  return (
    <Stack direction="column" p={2} spacing={0}>
      <Text fontWeight={600} fontSize="md">
        {props.value}
      </Text>
      <Text fontSize={'sm'} color="gray.500" justify="right">
        {props.name}
      </Text>
    </Stack>
  );
}

function Card(props) {
  return (
    <SimpleGrid
      p={4}
      columns={{ base: 1, md: 2 }}
      bg="gray.700"
      w="100%"
      rounded="lg"
      alignItems="center"
      minW="12em"
    >
      <Heading size="lg" color="teal.400" align="center" py={{ base: 4, md: 0 }}>
        {props.title}
      </Heading>

      <SimpleGrid
        columns={{ base: 1, sm: 2 }}
        rounded="lg"
        align="center"
        alignItems="center"
        px={3}
      >
        {props.labels.map((label) => (
          <CardLabel name={label[0]} value={label[1]} key={label} />
        ))}

        <Box align="right">
          {props.buttonLabel ? (
              <Button bg="teal.400" align="center" _hover={{ bg: 'teal.500' }} w={24}>
                {props.buttonLabel}
              </Button>
          ) : (
            <div />
          )}
        </Box>
      </SimpleGrid>
    </SimpleGrid>
  );
}

export default Card;
